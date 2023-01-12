package net.example.virtualoffice.virtualoffice.adapter;

import net.example.virtualoffice.virtualoffice.model.Log;
import net.example.virtualoffice.virtualoffice.repository.LogsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
interface SQLLogsRepository extends LogsRepository, JpaRepository<Log,Integer> {
    @Override
    Page<Log> findAll(Pageable pageable);
    @Query(nativeQuery = true, value = "select * from logs where created_on between :startDate and :endDate")
    Page<Log> findAllByCreatedOn(@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);
}
