package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberStatus {
    private boolean active;
    public MemberStatus(){}
    public MemberStatus(boolean active)
    {
        this.active=active;
    }

}
