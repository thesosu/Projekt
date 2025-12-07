package com.example.projektgit.service;

import com.example.projektgit.model.Status;
import com.example.projektgit.repository.StatusRepository;
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
public class StatusServiceTest {

    @Mock
    private StatusRepository statusRepository;

    @InjectMocks
    private StatusService statusService;

    @Test
    public void testCreateStatus() {
        Status status = new Status(null, "NEW");
        Status savedStatus = new Status(1L, "NEW");

        when(statusRepository.save(status)).thenReturn(savedStatus);

        Status result = statusService.createStatus(status);

        assertNotNull(result.getId());
        assertEquals("NEW", result.getName());
        verify(statusRepository, times(1)).save(status);
    }

    @Test
    public void testGetAllStatuses() {
        List<Status> statuses = Arrays.asList(
                new Status(1L, "NEW"),
                new Status(2L, "IN_PROGRESS"));

        when(statusRepository.findAll()).thenReturn(statuses);

        List<Status> result = statusService.getAllStatuses();

        assertEquals(2, result.size());
        verify(statusRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateStatus() {
        Status existingStatus = new Status(1L, "OLD_NAME");
        Status updatedDetails = new Status(null, "NEW_NAME");

        when(statusRepository.findById(1L)).thenReturn(Optional.of(existingStatus));
        when(statusRepository.save(existingStatus)).thenReturn(existingStatus);

        Status result = statusService.updateStatus(1L, updatedDetails);

        assertEquals("NEW_NAME", result.getName());
        verify(statusRepository, times(1)).findById(1L);
        verify(statusRepository, times(1)).save(existingStatus);
    }

    @Test
    public void testDeleteStatus() {
        doNothing().when(statusRepository).deleteById(1L);

        statusService.deleteStatus(1L);

        verify(statusRepository, times(1)).deleteById(1L);
    }
}
