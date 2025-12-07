package com.example.projektgit.service;

import com.example.projektgit.model.Report;
import com.example.projektgit.model.Status;
import com.example.projektgit.repository.ReportRepository;
import com.example.projektgit.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final StatusRepository statusRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository, StatusRepository statusRepository) {
        this.reportRepository = reportRepository;
        this.statusRepository = statusRepository;
    }

    public Report createReport(Report report) {
        return reportRepository.save(report);
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Optional<Report> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    public Report updateReportStatus(Long reportId, Long statusId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new com.example.projektgit.exception.ResourceNotFoundException(
                        "Report not found with id: " + reportId));
        Status status = statusRepository.findById(statusId)
                .orElseThrow(() -> new com.example.projektgit.exception.ResourceNotFoundException(
                        "Status not found with id: " + statusId));
        report.setStatus(status);
        return reportRepository.save(report);
    }
}
