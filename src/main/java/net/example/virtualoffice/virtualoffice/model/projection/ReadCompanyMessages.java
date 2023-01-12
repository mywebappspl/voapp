package net.example.virtualoffice.virtualoffice.model.projection;

import net.example.virtualoffice.virtualoffice.model.Message;
import org.springframework.data.domain.Page;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    public int getCompanyId() {
        return companyId;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(final int currentPage) {
        this.currentPage = currentPage;
    }

    public Set<ReadMessagesDTO> getMessages() {
        return messages;
    }
    public long getMessagesAmount() {
        return messagesAmount;
    }
}
