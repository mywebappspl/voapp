package net.example.virtualoffice.virtualoffice.repository;

import net.example.virtualoffice.virtualoffice.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MessagesRopository {
    Optional<Message> findById(Integer id);
    Message save(Message Entity);
    Page<Message> findAllMessagesByCompanyId(Integer id, Pageable pageable);
    Page<Message> findAllMessagesWithDatesByCompanyId(Integer id, Date startDate, Date endDate, Pageable pageable);
    List<Message> findAllMessagesForExportByCompanyId(Integer id);
    List<Message> findAllMessagesWithDatesForExportByCompanyId(Integer id, Date startDate, Date endDate);
}
