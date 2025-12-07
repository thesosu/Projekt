package com.example.projektgit.config;

import com.example.projektgit.model.*;
import com.example.projektgit.repository.ParagraphRepository;
import com.example.projektgit.repository.ReportRepository;
import com.example.projektgit.repository.StatusRepository;
import com.example.projektgit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private ParagraphRepository paragraphRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        User admin = new User(null, "admin", passwordEncoder.encode("admin"), Role.ADMIN);
        User policeman = new User(null, "policeman", passwordEncoder.encode("policeman"), Role.POLICEMAN);
        User user = new User(null, "user", passwordEncoder.encode("user"), Role.USER);

        userRepository.save(admin);
        userRepository.save(policeman);
        userRepository.save(user);


        Status statusNew = new Status(null, "NEW");
        Status statusInProgress = new Status(null, "IN_PROGRESS");
        Status statusResolved = new Status(null, "RESOLVED");
        Status statusRejected = new Status(null, "REJECTED");

        statusRepository.save(statusNew);
        statusRepository.save(statusInProgress);
        statusRepository.save(statusResolved);
        statusRepository.save(statusRejected);


        Paragraph p1 = new Paragraph(null, "Art. 51", "Zakłócanie spokoju");
        Paragraph p2 = new Paragraph(null, "Art. 92", "Niestosowanie się do znaków");
        Paragraph p3 = new Paragraph(null, "Art. 119", "Kradzież lub przywłaszczenie");

        paragraphRepository.save(p1);
        paragraphRepository.save(p2);
        paragraphRepository.save(p3);


        Report r1 = new Report(null, "Głośna muzyka w nocy", user, statusNew);
        Report r2 = new Report(null, "Parkowanie na zakazie", user, statusInProgress);

        reportRepository.save(r1);
        reportRepository.save(r2);

        System.out.println("Data initialized!");
    }
}
