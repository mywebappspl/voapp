package net.example.virtualoffice.virtualoffice.model.projection;

import lombok.Getter;
import lombok.Setter;
import net.example.virtualoffice.virtualoffice.model.Company;

@Getter
@Setter
public class ReadBasicCompanyDTO {
    private int id;
    private String company_name;
    private boolean active;
    public ReadBasicCompanyDTO(final Company source) {
        this.id= source.getId();
        this.company_name=source.getCompany_name();
        this.active=source.isActive();
    }
}
