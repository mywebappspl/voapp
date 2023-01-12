package net.example.virtualoffice.virtualoffice.services.utils;

import net.example.virtualoffice.virtualoffice.model.projection.ReadCompanyMessageForExport;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CSVExport {
    boolean ExportToCSV(HttpServletResponse servletResponse, int id, List<ReadCompanyMessageForExport> messagesToExport);
}
