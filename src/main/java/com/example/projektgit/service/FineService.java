package com.example.projektgit.service;

import com.example.projektgit.model.Fine;
import com.example.projektgit.repository.FineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FineService {

    private final FineRepository fineRepository;

    @Autowired
    public FineService(FineRepository fineRepository) {
        this.fineRepository = fineRepository;
    }

    public Fine createFine(Fine fine) {
        return fineRepository.save(fine);
    }

    public List<Fine> getAllFines() {
        return fineRepository.findAll();
    }

    public Optional<Fine> getFineById(Long id) {
        return fineRepository.findById(id);
    }

    public void deleteFine(Long id) {
        fineRepository.deleteById(id);
    }

    public Fine updateFine(Long id, Fine fineDetails) {
        Fine fine = fineRepository.findById(id)
                .orElseThrow(() -> new com.example.projektgit.exception.ResourceNotFoundException("Fine not found with id: " + id));
        fine.setAmount(fineDetails.getAmount());
        fine.setRecipient(fineDetails.getRecipient());
        fine.setParagraph(fineDetails.getParagraph());
        return fineRepository.save(fine);
    }
}
