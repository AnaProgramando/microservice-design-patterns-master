package com.ms.user.services;

import com.ms.user.models.UserModel;
import com.ms.user.producers.UserProducer;
import com.ms.user.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserProducer userProducer;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveUser() {
        UserModel userModel = new UserModel();
        userModel.setName("John Doe");
        userModel.setEmail("john.doe@example.com");

        when(userRepository.findByNameAndEmail(userModel.getName(), userModel.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(userModel)).thenReturn(userModel);

        System.out.println("email: "+ userModel.getEmail());
        UserModel savedUser = userService.save(userModel);

        assertNotNull(savedUser);
        assertEquals("John Doe", savedUser.getName());
        assertEquals("john.doe@example.com", savedUser.getEmail());
    }


    @Test
    public void testSaveUser_Conflict() {
        UserModel userModel = new UserModel();
        userModel.setName("John");
        userModel.setEmail("john@example.com");

        when(userService.userRepository.findByNameAndEmail(userModel.getName(), userModel.getEmail())).thenReturn(Optional.of(userModel));

        assertThrows(IllegalArgumentException.class, () -> {
            userService.save(userModel);
        });

        UserModel userModelDuplicated = new UserModel();
        userModelDuplicated.setName("John");
        userModelDuplicated.setEmail("john@example.com");

        assertThrows(IllegalArgumentException.class, () -> {
            userService.save(userModelDuplicated);
        });
    }

    @Test
    public void testFindByNameAndEmail() {
        UserModel userModel = new UserModel();
        userModel.setEmail("user@example.com");
        userModel.setName("John Doe");

        doNothing().when(userService.userRepository).disableUser(userModel.getEmail(), userModel.getName());

        userService.disableUser(userModel);

        verify(userService.userRepository, times(1)).disableUser(userModel.getEmail(), userModel.getName());
    }

}