package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.Getter;
import lombok.Setter;
import net.example.virtualoffice.virtualoffice.model.Message;
import org.springframework.data.domain.Page;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
@Getter
@Setter
public class ReadCompanyMessages {
    private int companyId;
    private int totalPages;
    private final long messagesAmount;
    private long currentPage;
    private Set<ReadMessagesDTO> messages;
    public ReadCompanyMessages(int id, Page<Message> message){
        this.companyId=id;
        this.totalPages=message.getTotalPages();
        this.currentPage=message.getNumber();
        this.messagesAmount=message.getTotalElements();
        this.messages=message.stream().map(m->new ReadMessagesDTO(m.getId(),m.getFromName(),m.getFromEmail(),m.getFromPhone(),m.getContent(),m.getCreatedOn())).collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
