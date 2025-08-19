package com.example.audit.repo;

import com.example.audit.domain.SfCaseimplProcessAudit;
import com.example.audit.domain.SfCaseimplProcessAuditId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<SfCaseimplProcessAudit, SfCaseimplProcessAuditId> {
}
