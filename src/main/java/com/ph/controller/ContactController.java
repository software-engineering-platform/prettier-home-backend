package com.ph.controller;

import com.ph.payload.request.ContactRequest;
import com.ph.payload.response.ContactResponse;
import com.ph.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contact-messages")
public class ContactController {

    private final ContactService contactService;


    // Not :J02 - Save() *************************************************************************
    @PostMapping()
    public ResponseEntity<String> save(@RequestBody @Valid ContactRequest request) {
        return contactService.save(request);
    }

    // Not :J01 - GetAll() *************************************************************************
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @GetMapping() // http://localhost:8080/contact-messages?page=0&size=20&sort=id&type=asc
    public ResponseEntity<Page<ContactResponse>> getAllContact(
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "20", required = false) int size,
            @RequestParam(value = "sort", defaultValue = "id", required = false) String sort,
            @RequestParam(value = "type", defaultValue = "asc", required = false) String type,
            @RequestParam(value = "status", required = false) boolean status
    ) {
        return contactService.getAllContact(query, page, size, sort, type, status);
    }

    // Not :J03 - getById() *************************************************************************
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @GetMapping("/{id}") // http://localhost:8080/contact-messages/1
    public ResponseEntity<ContactResponse> getById(@PathVariable Long id) {
        return contactService.getById(id);
    }


    // Not :J04 - delete() *************************************************************************
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @DeleteMapping("/{id}") // http://localhost:8080/contact-messages/1
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return contactService.delete(id);
    }


    // Not :J05 - deleteAll() **********************************************************************
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @DeleteMapping() // http://localhost:8080/contact-messages
    public ResponseEntity<?> deleteAllMessages() {
        return contactService.deleteAllMessages();
    }


    // Not :J06 - updateStatus() *************************************************************************
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @PatchMapping("/{id}") // http://localhost:8080/contact-messages/1
    public ResponseEntity<ContactResponse> updateMessage(@PathVariable Long id) {
        return contactService.updateMessage(id);
    }


    // Not :J07 - getOlderMessages() *************************************************************************
    @GetMapping("/older-messages") // http://localhost:8080/contact-messages/older-messages?startDate=2022-01-01T00:00:00&endDate=2022-02-01T00:00:00&page=0&size=20&sort=createdAt&type=asc
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseEntity<Page<ContactResponse>> getOlderMessages(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "20", required = false) int size,
            @RequestParam(value = "sort", defaultValue = "createdAt", required = false) String sort,
            @RequestParam(value = "type", defaultValue = "asc", required = false) String type,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        return contactService.getOlderMessages(page, size, sort, type, startDate, endDate);
    }

}
