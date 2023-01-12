package net.example.virtualoffice.virtualoffice.model.projection;

import net.example.virtualoffice.virtualoffice.model.TakenFor;
import org.springframework.data.domain.Page;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ReadMemberMessgesDTO {
    private int memberId;
    private int totalPages;
    private long currentPage;
    private Set<ReadMessagesDTO> messages;
    public ReadMemberMessgesDTO(int id, Page<TakenFor> message){
        this.memberId=id;
        this.totalPages=message.getTotalPages();
        this.currentPage=message.getNumber();
        this.messages=message.stream().map(m->new ReadMessagesDTO(m.getMessage().getId(),m.getMessage().getFromName(),m.getMessage().getFromEmail(),m.getMessage().getFromPhone(), m.getMessage().getContent(),m.getMessage().getCreatedOn())).collect(Collectors.toCollection(LinkedHashSet::new));
    }
    public int getMemberId() {
        return memberId;
    }
    public Set<ReadMessagesDTO> getMessages() {
        return messages;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(final int totalPages) {
        this.totalPages = totalPages;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(final long currentPage) {
        this.currentPage = currentPage;
    }
}
