package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyStatus {
    private boolean active;
    public CompanyStatus(){}
    public CompanyStatus(boolean active)
    {
        this.active=active;
    }

}
