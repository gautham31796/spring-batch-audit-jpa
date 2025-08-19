-- CREATE SCHEMA IF NOT EXISTS gbs_sfintegration;

CREATE TABLE IF NOT EXISTS gbs_sfintegration.sf_caseimpl_process_audit (
  processid                 INTEGER,
  eventid                   VARCHAR(100) NOT NULL,
  sfdeclarationaccnumber    BIGINT,
  sfdopportunitynumber      BIGINT,
  status                    TEXT,
  eventtime                 TIMESTAMP NOT NULL,
  CONSTRAINT sf_caseimpl_process_audit_pk
    PRIMARY KEY (processid, eventid, eventtime)
);

CREATE INDEX IF NOT EXISTS sf_caseimpl_audit_idx
  ON gbs_sfintegration.sf_caseimpl_process_audit (processid, eventtime);
