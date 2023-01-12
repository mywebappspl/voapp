package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MessageForRabbit implements Serializable {
    private int id;
    private int messageId;
    private int memberId;
    public MessageForRabbit(int id,int messageId,int memberId){
        this.id=id;
        this.messageId=messageId;
        this.memberId=memberId;
    }
}
