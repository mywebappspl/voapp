package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
public class ReadMessagesDTO {
    private int id;
    private String fromName;
    private String fromEmail;
    private String fromPhone;
    private String content;
    private LocalDateTime takenOn;
}
