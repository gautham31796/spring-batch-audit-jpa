package com.example.audit.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sf_caseimpl_process_audit", schema = "gbs_sfintegration") // adjust or remove schema if needed
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class SfCaseimplProcessAudit {

  @EmbeddedId
  private SfCaseimplProcessAuditId id;

  @Column(name = "sfdeclarationaccnumber")
  private Long sfDeclarationAccNumber;

  @Column(name = "sfdopportunitynumber")
  private Long sfDOpportunityNumber;

  @Column(name = "status", columnDefinition = "text")
  private String status;
}
