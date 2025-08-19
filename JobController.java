package com.example.audit.web;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

  private final JobLauncher jobLauncher;
  private final Job auditJob;

  @PostMapping("/run")
  public ResponseEntity<Map<String, Object>> run() throws Exception {
    JobParameters params = new JobParametersBuilder()
        .addLong("run.id", Instant.now().toEpochMilli())
        .toJobParameters();

    JobExecution exec = jobLauncher.run(auditJob, params);

    return ResponseEntity.ok(Map.of(
        "jobName", auditJob.getName(),
        "status", exec.getStatus().toString(),
        "jobExecutionId", exec.getId()
    ));
  }
}
