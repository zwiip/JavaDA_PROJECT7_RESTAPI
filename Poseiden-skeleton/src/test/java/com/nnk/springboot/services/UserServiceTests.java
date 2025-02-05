package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

    private UserService userService;
    private User user;

    @Mock
    private UserRepository userRepository;

    @Captor
    ArgumentCaptor<User> userCaptor;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    public void givenTwoUsers_whenGetUsers_thenReturnTheListWithTwoUsers() {
        User firstUser = new User("firstUser", "1password!", "First User", "USER");
        User secondUser = new User( "secondUser", "2password!", "Second User", "USER");
        List<User> listOfTwoUsers = new ArrayList<>();
        listOfTwoUsers.add(firstUser);
        listOfTwoUsers.add(secondUser);
        when(userRepository.findAll()).thenReturn(listOfTwoUsers);

        List<User> actualListOfUsers = userService.getUsers();

        assertEquals(2, actualListOfUsers.size());
        assertEquals("firstUser", actualListOfUsers.getFirst().getUsername());
    }

    @Test
    public void givenACorrectId_whenGetUserById_thenReturnCorrespondingUser() {
        User user = new User("John", "1password!", "Jonh Doe", "USER");
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        Optional<User> actualUser = userService.getUser(1);

        assertTrue(actualUser.isPresent());
        assertEquals("John", actualUser.get().getUsername());
    }

    @Test
    public void givenCorrectUser_whenSaveNewUser_thenUserIsSaved() {
        User user = new User("John", "1password!", "Jonh Doe", "USER");
        doNothing().when(userRepository.save(user));

        userService.saveNewUser(user);

        verify(userRepository).save(user);
    }

    @Test
    public void givenNewUsername_whenUpdateUser_thenItsUsernameIsUpdated() {
        User user = new User("John", "1password!", "Jonh Doe", "USER");
        User updatedUser = new User("Johnny", "1password!", "Jonh Doe", "USER");
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).save(any(User.class));

        userService.updateUser(updatedUser, 1);

        verify(userRepository).save(userCaptor.capture());
        User capturedUserValue = userCaptor.getValue();
        assertEquals(updatedUser.getUsername(), capturedUserValue.getUsername());
    }
}