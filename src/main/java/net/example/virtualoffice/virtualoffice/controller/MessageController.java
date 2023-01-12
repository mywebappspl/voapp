package net.example.virtualoffice.virtualoffice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import net.example.virtualoffice.virtualoffice.model.projection.QueueView;
import net.example.virtualoffice.virtualoffice.model.projection.ReadBasicMessageInfoDTO;
import net.example.virtualoffice.virtualoffice.model.projection.ReadMessageDTO;
import net.example.virtualoffice.virtualoffice.model.projection.WriteMessageDTO;
import net.example.virtualoffice.virtualoffice.services.MessageService;

import java.net.URI;
import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/message")
class MessageController {
    private MessageService messageService;

    MessageController(final MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<?> createMessage(@RequestBody WriteMessageDTO source){
        ReadBasicMessageInfoDTO message=messageService.createMessage(source);
        return ResponseEntity.created(URI.create("/")).body(message);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ReadMessageDTO> getMessage(@PathVariable int id){
        ReadMessageDTO message= messageService.getSingleMessage(id);
        return ResponseEntity.ok(message);
    }
    @GetMapping("/queue")
    public Page<QueueView> getQueue(
            @RequestParam(defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(defaultValue = "10", required = false) int itemsPerPage,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate
    )
    {
        Pageable pageable = PageRequest.of(pageNumber, itemsPerPage, Sort.by(Sort.Direction.ASC, "id"));
        if(startDate==null && endDate==null) {
            return messageService.getQueuedMessages(pageable);
        }
        else
        {
            return messageService.getQueuedMessages(startDate,endDate,pageable);
        }
    }
}
