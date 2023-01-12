package net.example.virtualoffice.virtualoffice.services;

import net.example.virtualoffice.virtualoffice.Utils.voutils;
import net.example.virtualoffice.virtualoffice.exception.CompanyExceptionHandler;
import net.example.virtualoffice.virtualoffice.exception.DatesExceptionHandler;
import net.example.virtualoffice.virtualoffice.exception.MembersNotInCompanyExceptionHandler;
import net.example.virtualoffice.virtualoffice.model.Message;
import net.example.virtualoffice.virtualoffice.model.TakenFor;
import net.example.virtualoffice.virtualoffice.model.projection.*;
import net.example.virtualoffice.virtualoffice.repository.CompaniesRepository;
import net.example.virtualoffice.virtualoffice.repository.MembersRepository;
import net.example.virtualoffice.virtualoffice.repository.MessagesRopository;
import net.example.virtualoffice.virtualoffice.repository.TakenForRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.strefaphp.virtualoffice.virtualoffice.model.projection.*;

import java.util.Date;
import java.util.List;


@Service
public class MessageService {
    private final CompaniesRepository companiesRepository;
    private final MessagesRopository messagesRopository;
    private final MembersRepository membersRepository;
    private final TakenForRepository takenForRepository;
    private final RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(TakenFor.class);

    MessageService(final RabbitTemplate rabbitTemplate, final CompaniesRepository companiesRepository, final MessagesRopository messagesRopository, final MembersRepository membersRepository, final TakenForRepository takenForRepository) {
        this.companiesRepository = companiesRepository;
        this.messagesRopository = messagesRopository;
        this.membersRepository = membersRepository;
        this.takenForRepository = takenForRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public ReadBasicMessageInfoDTO createMessage(WriteMessageDTO source) {
        try {
            if (companiesRepository.existsById(source.getCompanyId())) {
                List<Integer> members = membersRepository.findByCompanyId(source.getCompanyId()).orElseThrow(MembersNotInCompanyExceptionHandler::new);
                if (!members.containsAll(source.getMembers())) {
                    throw new MembersNotInCompanyExceptionHandler("not all requested members in company id " + source.getCompanyId());
                }
            } else {
                throw new CompanyExceptionHandler();
            }

            Message message = messagesRopository.save(source.bindMessagesWithMembers());

            List<MessageForRabbit> MsgList = message.getTakenForMembers().stream()
                    .map(m -> new MessageForRabbit(m.getId(), m.getMessage().getId(), m.getMember_id()))
                    .toList();
            for (MessageForRabbit msg : MsgList) {
                rabbitTemplate.convertAndSend("vo_messages", msg);
            }
            return new ReadBasicMessageInfoDTO(message);
        } catch (Exception e) {
            logger.warn("message queue error");
            throw e;
        }
    }

    public ReadMessageDTO getSingleMessage(int id) {
        Message message = messagesRopository.findById(id).orElseThrow();
        // List<TakenFor> members = takenForRepository.findAllByMessage_Id(id);
        return new ReadMessageDTO(message);
    }

    public Page<QueueView> getQueuedMessages(Date startDate, Date endDate, Pageable pageable) {
        if (voutils.LogsDatesValidation(startDate, endDate))
            return takenForRepository.findAllFromQueueDTOByDate(startDate, Date.from(voutils.EndDateCalculation(endDate)), pageable);
        else
            throw new DatesExceptionHandler(" Wrong dates");
    }

    public Page<QueueView> getQueuedMessages(Pageable pageable) {
        return takenForRepository.findAllFromQueueDTO(pageable);
    }
}
