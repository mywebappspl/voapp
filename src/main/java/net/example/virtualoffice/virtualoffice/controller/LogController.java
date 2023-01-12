package net.example.virtualoffice.virtualoffice.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import net.example.virtualoffice.virtualoffice.model.projection.ReadLogs;
import net.example.virtualoffice.virtualoffice.services.LogService;

import java.util.Date;

@RestController
@CrossOrigin
public class LogController {
    LogService logService;

    LogController(final LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/logs")
    public ResponseEntity<ReadLogs> GetLogs(
            @RequestParam(defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(defaultValue = "5", required = false) int itemsPerPage,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate
            ){

        Pageable pageable = PageRequest.of(pageNumber, itemsPerPage, Sort.by(Sort.Direction.ASC,"id"));
        if(startDate==null && endDate==null) {
            return ResponseEntity.ok().body(new ReadLogs(logService.GetLogs(pageable)));
        }else {
            return ResponseEntity.ok().body(new ReadLogs(logService.GetLogsByDate(pageable,startDate,endDate)));
        }
    }
}
