package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ReadLogItem {
    private int id;
    private LocalDateTime createdOn;
    private String message;
    private int agentId;
    private int companyId;

    public ReadLogItem(final int id, final LocalDateTime createdOn, final String message, final int agentId, final int companyId) {
        this.id = id;
        this.createdOn = createdOn;
        this.message = message;
        this.agentId = agentId;
        this.companyId = companyId;
    }
}
