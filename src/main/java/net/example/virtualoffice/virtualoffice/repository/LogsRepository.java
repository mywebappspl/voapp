package net.example.virtualoffice.virtualoffice.repository;

import net.example.virtualoffice.virtualoffice.model.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface LogsRepository {
    Log save(Log Entity);
    Page<Log> findAll(Pageable pageable);
    Page<Log> findAllByCreatedOn(@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);
}
