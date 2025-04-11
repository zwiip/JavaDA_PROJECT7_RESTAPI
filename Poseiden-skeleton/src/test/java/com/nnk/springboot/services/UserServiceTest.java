package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

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

        user = new User("John", "1Password!", "Jonh Doe", "USER");
    }

    @Test
    public void givenTwoUsers_whenGetUsers_thenReturnTheListWithTwoUsers() {
        User secondUser = new User( "secondUser", "2Password!", "Second User", "USER");
        List<User> listOfTwoUsers = new ArrayList<>();
        listOfTwoUsers.add(user);
        listOfTwoUsers.add(secondUser);
        when(userRepository.findAll()).thenReturn(listOfTwoUsers);

        List<User> actualListOfUsers = userService.getUsers();

        assertEquals(2, actualListOfUsers.size());
        assertEquals("John", actualListOfUsers.getFirst().getUsername());
        assertEquals("secondUser", actualListOfUsers.getLast().getUsername() );
    }

    @Test
    public void givenACorrectId_whenGetUserById_thenReturnCorrespondingUser() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        Optional<User> actualUser = userService.getUser(1);

        assertTrue(actualUser.isPresent());
        assertEquals("John", actualUser.get().getUsername());
    }

    @Test
    public void givenACorrectUsername_whenGetUserByUsername_thenReturnCorrespondingUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        User actualUser = userService.findUserByUsername("John");

        assertEquals("John", actualUser.getUsername());
    }

    @Test
    public void givenCorrectUser_whenSaveNewUser_thenUserIsSaved() throws Exception {
        userService.saveNewUser(user);

        verify(userRepository).save(user);
    }

    @Test
    public void givenNewUsername_whenUpdateUser_thenItsUsernameIsUpdated() {
        User updatedUser = new User("Johnny", "1Password!", "Jonh Doe", "USER");
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        userService.updateUser(updatedUser, 1);

        verify(userRepository).save(userCaptor.capture());
        User capturedUserValue = userCaptor.getValue();
        assertEquals(updatedUser.getUsername(), capturedUserValue.getUsername());
    }

    @Test
    public void givenCorrecId_whenDeleteUser_thenUserIsDeleted() {
        userService.deleteUser(1);

        verify(userRepository).deleteById(1);
    }
}