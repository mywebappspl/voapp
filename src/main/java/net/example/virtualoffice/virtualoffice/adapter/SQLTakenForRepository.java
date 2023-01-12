package net.example.virtualoffice.virtualoffice.adapter;

import net.example.virtualoffice.virtualoffice.model.TakenFor;
import net.example.virtualoffice.virtualoffice.repository.TakenForRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import net.example.virtualoffice.virtualoffice.model.projection.QueueView;

import java.util.Date;
import java.util.List;

interface SQLTakenForRepository extends TakenForRepository, JpaRepository<TakenFor, Integer> {
    List<TakenFor> findAllByMessage_Id(Integer message_id);
    @Query(nativeQuery=true, value="SELECT * FROM taken_for t INNER JOIN messages m ON t.message_id=m.id INNER JOIN members e ON t.member_id=e.id WHERE m.company_id=:id ORDER BY t.message_id")
    List<TakenFor> findAllMessages(Integer id);
    @Query(nativeQuery=true, value="SELECT * FROM taken_for t INNER JOIN messages m ON t.message_id=m.id INNER JOIN members e ON t.member_id=e.id WHERE m.company_id=:id AND m.created_on BETWEEN :startDate AND :endDate ORDER BY t.message_id")
    List<TakenFor> findAllMessagesByDate(Integer id, Date startDate, Date endDate);
    @Query(nativeQuery=true)
    Page<QueueView> findAllFromQueueDTO(Pageable pageable);
    @Query(nativeQuery=true)
    Page<QueueView> findAllFromQueueDTOByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);
}
