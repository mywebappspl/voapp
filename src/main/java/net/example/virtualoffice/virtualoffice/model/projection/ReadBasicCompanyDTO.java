package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.Getter;
import lombok.Setter;
import net.example.virtualoffice.virtualoffice.model.Company;

@Getter
@Setter
public class ReadBasicCompanyDTO {
    private int id;
    private String companyName;
    private boolean active;
    public ReadBasicCompanyDTO(final Company source) {
        this.id= source.getId();
        this.companyName=source.getCompanyName();
        this.active=source.isActive();
    }
}
