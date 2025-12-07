package com.example.projektgit.service;

import com.example.projektgit.model.Paragraph;
import com.example.projektgit.repository.ParagraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParagraphService {

    private final ParagraphRepository paragraphRepository;

    @Autowired
    public ParagraphService(ParagraphRepository paragraphRepository) {
        this.paragraphRepository = paragraphRepository;
    }

    public Paragraph createParagraph(Paragraph paragraph) {
        return paragraphRepository.save(paragraph);
    }

    public List<Paragraph> getAllParagraphs() {
        return paragraphRepository.findAll();
    }

    public Optional<Paragraph> getParagraphById(Long id) {
        return paragraphRepository.findById(id);
    }

    public void deleteParagraph(Long id) {
        paragraphRepository.deleteById(id);
    }

    public Paragraph updateParagraph(Long id, Paragraph paragraphDetails) {
        Paragraph paragraph = paragraphRepository.findById(id)
                .orElseThrow(
                        () -> new com.example.projektgit.exception.ResourceNotFoundException("Paragraph not found with id: " + id));
        paragraph.setCode(paragraphDetails.getCode());
        paragraph.setDescription(paragraphDetails.getDescription());
        return paragraphRepository.save(paragraph);
    }
}
