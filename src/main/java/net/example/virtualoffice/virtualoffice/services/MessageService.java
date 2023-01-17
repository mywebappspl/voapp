package net.example.virtualoffice.virtualoffice.services;

import net.example.virtualoffice.virtualoffice.Utils.Voutils;
import net.example.virtualoffice.virtualoffice.exception.CustomExceptionHandler;
import net.example.virtualoffice.virtualoffice.exception.ExceptionMessages;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
                List<Integer> members = membersRepository.findByCompanyId(source.getCompanyId()).orElseThrow(() -> new CustomExceptionHandler(ExceptionMessages.MEMBER_NOT_EXIST_IN_COMPANY, HttpStatus.NOT_FOUND));
                if (!members.containsAll(source.getMembers())) {
                    throw new CustomExceptionHandler(ExceptionMessages.MEMBER_NOT_EXIST_IN_COMPANY, HttpStatus.NOT_FOUND);
                }
            } else {
                throw new CustomExceptionHandler(ExceptionMessages.COMPANY_WITH_ID_NOT_EXISTS, HttpStatus.NOT_FOUND);
            }

            Message message = messagesRopository.save(source.bindMessagesWithMembers());

            List<MessageForRabbit> MsgList = message.getTakenForMembers().stream()
                    .map(m -> new MessageForRabbit(m.getId(), m.getMessage().getId(), m.getMemberId()))
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
        if (Voutils.logsDatesValidation(startDate, endDate))
            return takenForRepository.findAllFromQueueDTOByDate(startDate, Date.from(Voutils.endDateCalculation(endDate)), pageable);
        else
            throw new CustomExceptionHandler(ExceptionMessages.INCORRECT_DATES, HttpStatus.BAD_REQUEST);
    }

    public Page<QueueView> getQueuedMessages(Pageable pageable) {
        return takenForRepository.findAllFromQueueDTO(pageable);
    }
}
