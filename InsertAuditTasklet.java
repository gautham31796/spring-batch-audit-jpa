package com.example.audit.batch;

import com.example.audit.domain.SfCaseimplProcessAudit;
import com.example.audit.domain.SfCaseimplProcessAuditId;
import com.example.audit.repo.AuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class InsertAuditTasklet implements Tasklet {

  private final AuditRepository repo;

  @Override
  @Transactional
  public RepeatStatus execute(StepContribution contribution,
                              org.springframework.batch.core.scope.context.ChunkContext chunkContext) {
    log.info("Inserting demo rows into sf_caseimpl_process_audit using JPA...");

    LocalDateTime now = LocalDateTime.now();

    repo.save(
        SfCaseimplProcessAudit.builder()
            .id(new SfCaseimplProcessAuditId(1001, "CREATE", now.minusSeconds(2)))
            .sfDeclarationAccNumber(1111111111L)
            .sfDOpportunityNumber(2222222222L)
            .status("CREATED")
            .build()
    );

    repo.save(
        SfCaseimplProcessAudit.builder()
            .id(new SfCaseimplProcessAuditId(1001, "VALIDATE", now.minusSeconds(1)))
            .sfDeclarationAccNumber(1111111111L)
            .sfDOpportunityNumber(2222222222L)
            .status("VALIDATED")
            .build()
    );

    repo.save(
        SfCaseimplProcessAudit.builder()
            .id(new SfCaseimplProcessAuditId(1001, "PUBLISH", now))
            .sfDeclarationAccNumber(1111111111L)
            .sfDOpportunityNumber(2222222222L)
            .status("PUBLISHED")
            .build()
    );

    log.info("Insert complete.");
    return RepeatStatus.FINISHED;
  }
}
