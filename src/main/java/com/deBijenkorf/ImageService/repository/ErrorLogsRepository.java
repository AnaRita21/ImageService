package com.deBijenkorf.ImageService.repository;

import com.deBijenkorf.ImageService.entity.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface used by the ImageService to log errors into RDS database.
 *
 */
@Repository
public interface ErrorLogsRepository extends JpaRepository<ErrorLog, Integer> {
}
