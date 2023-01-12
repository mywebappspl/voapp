package net.example.virtualoffice.virtualoffice.adapter;

import net.example.virtualoffice.virtualoffice.model.Message;
import net.example.virtualoffice.virtualoffice.repository.MessagesRopository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

interface SQLMessagesRepository extends MessagesRopository, JpaRepository<Message,Integer> {
    @Override
    @Query(nativeQuery = true, value = "select * from messages where company_id=:id")
    Page<Message> findAllMessagesByCompany_Id(@Param("id") Integer id,Pageable pageable);

    @Query(nativeQuery = true, value = "select * from messages where company_id=:id")
    List<Message> findAllMessagesForExportByCompany_Id(@Param("id") Integer id);
    @Query(nativeQuery = true, value = "select * from messages where company_id=:id and created_on between :startDate and :endDate")
    List<Message> findAllMessagesWithDatesForExportByCompany_Id(@Param("id") Integer id, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    @Query(nativeQuery = true, value = "select * from messages where company_id=:id and created_on between :startDate and :endDate")
    Page<Message> findAllMessagesWithDatesByCompany_Id(@Param("id") Integer id, @Param("startDate") Date startDate, @Param("endDate") Date endDate,Pageable pageable);


}
