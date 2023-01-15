package net.example.virtualoffice.virtualoffice.services.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;
import net.example.virtualoffice.virtualoffice.model.projection.ReadCompanyMessageForExport;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class CSVExportImpl implements CSVExport {
    @Override
    public boolean ExportToCSV(HttpServletResponse servletResponse, int id, final List<ReadCompanyMessageForExport> messagesToExport) {

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        servletResponse.setContentType("text/csv");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=msgs_cid_" + id + "_T_" + currentDateTime + ".csv";
        servletResponse.setHeader(headerKey, headerValue);
        try (
                CSVPrinter csvPrinter = new CSVPrinter(servletResponse.getWriter(), CSVFormat.DEFAULT)) {
            csvPrinter.printRecord(
                    "Date",
                    "Taken for",
                    "From name",
                    "From email address",
                    "From phone",
                    "Content");

            for (ReadCompanyMessageForExport msg : messagesToExport) {
                csvPrinter.printRecord(
                        msg.getCreated_on(),
                        msg.getMemberName(),
                        msg.getFromName(),
                        msg.getFromEmail(),
                        msg.getFromPhone(),
                        msg.getContent());
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
