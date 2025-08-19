package com.example.audit.batch;

import com.example.audit.domain.SfCaseimplProcessAudit;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BatchConfig {

  private final InsertAuditTasklet insertAuditTasklet;
  private final EntityManagerFactory emf;
  private final PlatformTransactionManager txManager;

  @Bean
  public JpaPagingItemReader<SfCaseimplProcessAudit> auditReader() {
    String jpql = """        SELECT a FROM SfCaseimplProcessAudit a
        ORDER BY a.id.processId, a.id.eventId, a.id.eventTime
        """;
    return new JpaPagingItemReaderBuilder<SfCaseimplProcessAudit>()
        .name("auditReader")
        .entityManagerFactory(emf)
        .queryString(jpql)
        .pageSize(10)
        .build();
  }

  @Bean
  public ItemWriter<SfCaseimplProcessAudit> consoleWriter() {
    return items -> {
      log.info("Printing audit rows:");
      for (SfCaseimplProcessAudit a : items) {
        log.info("procId={}, eventId={}, eventTime={}, declAcc={}, oppNum={}, status={}",
            a.getId().getProcessId(), a.getId().getEventId(), a.getId().getEventTime(),
            a.getSfDeclarationAccNumber(), a.getSfDOpportunityNumber(), a.getStatus());
      }
    };
  }

  @Bean
  public Step insertStep(JobRepository jobRepository) {
    return new StepBuilder("insertStep", jobRepository)
        .tasklet(insertAuditTasklet, txManager)
        .build();
  }

  @Bean
  public Step printStep(JobRepository jobRepository,
                        JpaPagingItemReader<SfCaseimplProcessAudit> auditReader,
                        ItemWriter<SfCaseimplProcessAudit> consoleWriter) {
    return new StepBuilder("printStep", jobRepository)
        .<SfCaseimplProcessAudit, SfCaseimplProcessAudit>chunk(5, txManager)
        .reader(auditReader)
        .writer(consoleWriter)
        .build();
  }

  @Bean
  public Job auditJob(JobRepository jobRepository, Step insertStep, Step printStep) {
    return new JobBuilder("auditJob", jobRepository)
        .start(insertStep)
        .next(printStep)
        .build();
  }
}
