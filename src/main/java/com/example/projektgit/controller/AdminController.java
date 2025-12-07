package com.example.projektgit.controller;

import com.example.projektgit.model.Paragraph;
import com.example.projektgit.model.Role;
import com.example.projektgit.model.Status;
import com.example.projektgit.model.User;
import com.example.projektgit.service.ParagraphService;
import com.example.projektgit.service.StatusService;
import com.example.projektgit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final StatusService statusService;
    private final ParagraphService paragraphService;
    private final UserService userService;

    @Autowired
    public AdminController(StatusService statusService, ParagraphService paragraphService, UserService userService) {
        this.statusService = statusService;
        this.paragraphService = paragraphService;
        this.userService = userService;
    }

    @PostMapping("/statuses")
    public Status createStatus(@RequestBody Status status) {
        return statusService.createStatus(status);
    }

    @GetMapping("/statuses")
    public List<Status> getAllStatuses() {
        return statusService.getAllStatuses();
    }

    @DeleteMapping("/statuses/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable Long id) {
        statusService.deleteStatus(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/statuses/{id}")
    public Status updateStatus(@PathVariable Long id, @RequestBody Status status) {
        return statusService.updateStatus(id, status);
    }

    @PostMapping("/paragraphs")
    public Paragraph createParagraph(@RequestBody Paragraph paragraph) {
        return paragraphService.createParagraph(paragraph);
    }

    @GetMapping("/paragraphs")
    public List<Paragraph> getAllParagraphs() {
        return paragraphService.getAllParagraphs();
    }

    @DeleteMapping("/paragraphs/{id}")
    public ResponseEntity<Void> deleteParagraph(@PathVariable Long id) {
        paragraphService.deleteParagraph(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/paragraphs/{id}")
    public Paragraph updateParagraph(@PathVariable Long id, @RequestBody Paragraph paragraph) {
        return paragraphService.updateParagraph(id, paragraph);
    }

    @PutMapping("/users/{id}/role")
    public User updateUserRole(@PathVariable Long id, @RequestParam Role role) {
        return userService.updateUserRole(id, role);
    }
}
