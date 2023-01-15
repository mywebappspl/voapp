package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.Getter;
import lombok.Setter;
import net.example.virtualoffice.virtualoffice.model.TakenFor;
import org.springframework.data.domain.Page;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
@Getter
@Setter
public class ReadMemberMessgesDTO {
    private final int memberId;
    private int totalPages;
    private long currentPage;
    private final Set<ReadMessagesDTO> messages;
    public ReadMemberMessgesDTO(int id, Page<TakenFor> message){
        this.memberId=id;
        this.totalPages=message.getTotalPages();
        this.currentPage=message.getNumber();
        this.messages=message.stream().map(m->new ReadMessagesDTO(m.getMessage().getId(),m.getMessage().getFromName(),m.getMessage().getFromEmail(),m.getMessage().getFromPhone(), m.getMessage().getContent(),m.getMessage().getCreatedOn())).collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
