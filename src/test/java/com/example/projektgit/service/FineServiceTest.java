package com.example.projektgit.service;

import com.example.projektgit.model.Fine;
import com.example.projektgit.model.Paragraph;
import com.example.projektgit.model.Role;
import com.example.projektgit.model.User;
import com.example.projektgit.repository.FineRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FineServiceTest {

    @Mock
    private FineRepository fineRepository;

    @InjectMocks
    private FineService fineService;

    @Test
    public void testCreateFine() {
        User recipient = new User(1L, "user", "pass", Role.USER);
        Paragraph paragraph = new Paragraph(1L, "Art. 51", "Zakłócanie spokoju");
        Fine fine = new Fine(null, new BigDecimal("500.00"), recipient, paragraph);
        Fine savedFine = new Fine(1L, new BigDecimal("500.00"), recipient, paragraph);

        when(fineRepository.save(fine)).thenReturn(savedFine);

        Fine result = fineService.createFine(fine);

        assertNotNull(result.getId());
        assertEquals(new BigDecimal("500.00"), result.getAmount());
        verify(fineRepository, times(1)).save(fine);
    }

    @Test
    public void testGetAllFines() {
        User recipient = new User(1L, "user", "pass", Role.USER);
        Paragraph paragraph = new Paragraph(1L, "Art. 51", "Zakłócanie spokoju");
        List<Fine> fines = Arrays.asList(
                new Fine(1L, new BigDecimal("500.00"), recipient, paragraph),
                new Fine(2L, new BigDecimal("1000.00"), recipient, paragraph));

        when(fineRepository.findAll()).thenReturn(fines);

        List<Fine> result = fineService.getAllFines();

        assertEquals(2, result.size());
        verify(fineRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateFine() {
        User recipient = new User(1L, "user", "pass", Role.USER);
        Paragraph paragraph = new Paragraph(1L, "Art. 51", "Zakłócanie spokoju");
        Fine existingFine = new Fine(1L, new BigDecimal("500.00"), recipient, paragraph);
        Fine updatedDetails = new Fine(null, new BigDecimal("750.00"), recipient, paragraph);

        when(fineRepository.findById(1L)).thenReturn(Optional.of(existingFine));
        when(fineRepository.save(existingFine)).thenReturn(existingFine);

        Fine result = fineService.updateFine(1L, updatedDetails);

        assertEquals(new BigDecimal("750.00"), result.getAmount());
        verify(fineRepository, times(1)).findById(1L);
        verify(fineRepository, times(1)).save(existingFine);
    }

    @Test
    public void testDeleteFine() {
        doNothing().when(fineRepository).deleteById(1L);

        fineService.deleteFine(1L);

        verify(fineRepository, times(1)).deleteById(1L);
    }
}
