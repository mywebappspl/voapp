package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class MessageForRabbit implements Serializable {
    private int id;
    private int messageId;
    private int memberId;
}
