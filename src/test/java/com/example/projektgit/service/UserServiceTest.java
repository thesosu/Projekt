package com.example.projektgit.service;

import com.example.projektgit.model.Role;
import com.example.projektgit.model.User;
import com.example.projektgit.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testUpdateUserRole() {
        User user = new User(1L, "test", "pass", Role.USER);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.updateUserRole(1L, Role.ADMIN);

        assertEquals(Role.ADMIN, updatedUser.getRole());
    }
}

