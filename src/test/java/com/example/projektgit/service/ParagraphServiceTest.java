package com.example.projektgit.service;

import com.example.projektgit.model.Paragraph;
import com.example.projektgit.repository.ParagraphRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParagraphServiceTest {

    @Mock
    private ParagraphRepository paragraphRepository;

    @InjectMocks
    private ParagraphService paragraphService;

    @Test
    public void testCreateParagraph() {
        Paragraph paragraph = new Paragraph(null, "Art. 51", "Zakłócanie spokoju");
        Paragraph savedParagraph = new Paragraph(1L, "Art. 51", "Zakłócanie spokoju");

        when(paragraphRepository.save(paragraph)).thenReturn(savedParagraph);

        Paragraph result = paragraphService.createParagraph(paragraph);

        assertNotNull(result.getId());
        assertEquals("Art. 51", result.getCode());
        verify(paragraphRepository, times(1)).save(paragraph);
    }

    @Test
    public void testGetAllParagraphs() {
        List<Paragraph> paragraphs = Arrays.asList(
                new Paragraph(1L, "Art. 51", "Zakłócanie spokoju"),
                new Paragraph(2L, "Art. 92", "Niestosowanie się do znaków"));

        when(paragraphRepository.findAll()).thenReturn(paragraphs);

        List<Paragraph> result = paragraphService.getAllParagraphs();

        assertEquals(2, result.size());
        verify(paragraphRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateParagraph() {
        Paragraph existingParagraph = new Paragraph(1L, "Art. 51", "Stary opis");
        Paragraph updatedDetails = new Paragraph(null, "Art. 51a", "Nowy opis");

        when(paragraphRepository.findById(1L)).thenReturn(Optional.of(existingParagraph));
        when(paragraphRepository.save(existingParagraph)).thenReturn(existingParagraph);

        Paragraph result = paragraphService.updateParagraph(1L, updatedDetails);

        assertEquals("Art. 51a", result.getCode());
        assertEquals("Nowy opis", result.getDescription());
        verify(paragraphRepository, times(1)).findById(1L);
        verify(paragraphRepository, times(1)).save(existingParagraph);
    }

    @Test
    public void testDeleteParagraph() {
        doNothing().when(paragraphRepository).deleteById(1L);

        paragraphService.deleteParagraph(1L);

        verify(paragraphRepository, times(1)).deleteById(1L);
    }
}
