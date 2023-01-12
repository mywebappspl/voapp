package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.Getter;
import lombok.Setter;
import net.example.virtualoffice.virtualoffice.model.Log;
import org.springframework.data.domain.Page;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class ReadLogs {
    private final long logsAmount;
    private long currentPage;
    private Set<ReadLogItem> logs;

    public ReadLogs(final Page<Log> logs) {
        this.currentPage=logs.getNumber();
        this.logsAmount=logs.getTotalElements();
        this.logs=logs.stream().map(l->new ReadLogItem(l.getId(),l.getCreatedOn(),l.getMessage(),l.getAgentId(),l.getCompanyId())).collect(Collectors.toSet());
    }
}
