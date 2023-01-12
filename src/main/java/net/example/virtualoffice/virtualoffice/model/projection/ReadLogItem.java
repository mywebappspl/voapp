package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ReadLogItem {
    private int id;
    private LocalDateTime created_on;
    private String message;
    private int agent_id;
    private int company_id;

    public ReadLogItem(final int id, final LocalDateTime created_on, final String message, final int agent_id, final int company_id) {
        this.id = id;
        this.created_on = created_on;
        this.message = message;
        this.agent_id = agent_id;
        this.company_id = company_id;
    }
}
