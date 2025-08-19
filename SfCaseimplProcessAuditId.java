package com.example.audit.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class SfCaseimplProcessAuditId implements Serializable {
  @Column(name = "processid")
  private Integer processId;

  @Column(name = "eventid", length = 100, nullable = false)
  private String eventId;

  @Column(name = "eventtime", nullable = false)
  private LocalDateTime eventTime;
}
