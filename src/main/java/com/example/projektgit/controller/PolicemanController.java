package com.example.projektgit.controller;


import com.example.projektgit.model.Fine;
import com.example.projektgit.model.Report;
import com.example.projektgit.service.FineService;
import com.example.projektgit.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policeman")
public class PolicemanController {

    private final ReportService reportService;
    private final FineService fineService;

    @Autowired
    public PolicemanController(ReportService reportService, FineService fineService) {
        this.reportService = reportService;
        this.fineService = fineService;
    }

    @GetMapping("/reports")
    public List<Report> getAllReports() {
        return reportService.getAllReports();
    }

    @PutMapping("/reports/{id}/status")
    public Report updateReportStatus(@PathVariable Long id, @RequestParam Long statusId) {
        return reportService.updateReportStatus(id, statusId);
    }

    @PostMapping("/fines")
    public Fine createFine(@RequestBody Fine fine) {
        return fineService.createFine(fine);
    }

    @GetMapping("/fines")
    public List<Fine> getAllFines() {
        return fineService.getAllFines();
    }

    @DeleteMapping("/fines/{id}")
    public ResponseEntity<Void> deleteFine(@PathVariable Long id) {
        fineService.deleteFine(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/fines/{id}")
    public Fine updateFine(@PathVariable Long id, @RequestBody Fine fine) {
        return fineService.updateFine(id, fine);
    }
}
