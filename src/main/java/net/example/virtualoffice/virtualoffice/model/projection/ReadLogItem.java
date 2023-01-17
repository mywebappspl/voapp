package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
public class ReadLogItem {
    private int id;
    private LocalDateTime createdOn;
    private String message;
    private int agentId;
    private int companyId;
}
