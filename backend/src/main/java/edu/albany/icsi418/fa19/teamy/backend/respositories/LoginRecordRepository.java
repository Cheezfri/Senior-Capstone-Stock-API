package edu.albany.icsi418.fa19.teamy.backend.respositories;

import edu.albany.icsi418.fa19.teamy.backend.models.audit.LoginRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRecordRepository extends JpaRepository<LoginRecord, Long> {
}
