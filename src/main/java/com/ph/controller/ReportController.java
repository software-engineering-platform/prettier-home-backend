package com.ph.controller;

import com.ph.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    // Not: get most popular properties
    @GetMapping("/most-popular-properties")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<?> getProperties(@RequestParam Integer amount){ //http://localhost:8080/report/most-popular-properties?amount=10

      return  reportService.getMostPopularProperties(amount);

    }

    // Not: get  advert





    // Not: get all






    // Not: get all users





    // Not: get all tour request




}
