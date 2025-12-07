package com.example.projektgit.service;

import com.example.projektgit.model.Status;
import com.example.projektgit.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {

    private final StatusRepository statusRepository;

    @Autowired
    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public Status createStatus(Status status) {
        return statusRepository.save(status);
    }

    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }

    public Optional<Status> getStatusById(Long id) {
        return statusRepository.findById(id);
    }

    public void deleteStatus(Long id) {
        statusRepository.deleteById(id);
    }

    public Status updateStatus(Long id, Status statusDetails) {
        Status status = statusRepository.findById(id)
                .orElseThrow(
                        () -> new com.example.projektgit.exception.ResourceNotFoundException("Status not found with id: " + id));
        status.setName(statusDetails.getName());
        return statusRepository.save(status);
    }
}
