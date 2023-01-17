package net.example.virtualoffice.virtualoffice.repository;

import net.example.virtualoffice.virtualoffice.model.TakenFor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import net.example.virtualoffice.virtualoffice.model.projection.QueueView;

import java.util.Date;
import java.util.List;

public interface TakenForRepository {
    
    List<TakenFor> findAllByMessageId(Integer messageId);
    Page<TakenFor> findAllByMemberId(Integer memberId, Pageable pageable);
    List<TakenFor> findAllMessages(Integer id);
    List<TakenFor> findAllMessagesByDate(Integer id, Date startDate, Date endDate);
    Page<QueueView> findAllFromQueueDTO(Pageable pageable);
    Page<QueueView> findAllFromQueueDTOByDate(Date startDate, Date endDate, Pageable pageable);
}
