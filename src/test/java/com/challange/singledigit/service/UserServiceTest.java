package com.challange.singledigit.service;

import com.challange.singledigit.exception.ApplicationException;
import com.challange.singledigit.model.SingleDigit;
import com.challange.singledigit.model.User;
import com.challange.singledigit.model.dto.UserRequest;
import com.challange.singledigit.repository.SingleDigitRepository;
import com.challange.singledigit.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private SingleDigitRepository singleDigitRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void shouldCreateNewUser_whenEmailIsValid() {
        var userMock = new User("rodrick", "rodrik@email.com", Collections.emptyList());
        when(repository.findByEmailAndRemovedAtIsNull(anyString())).thenReturn(Optional.empty());

        var requestMock = new UserRequest("rodrick", "rodrik@email.com");
        userService.create(requestMock);

        var singleDigitCaptor = ArgumentCaptor.forClass(User.class);
        verify(repository).save(singleDigitCaptor.capture());

        var result = singleDigitCaptor.getValue();

        assertEquals(userMock, result);
    }

    @Test
    public void shouldTryCreateNewUserButThrowsApplicationException_whenEmailIsAlreadyRegistered() {
        var userMock = new User("rodrick", "rodrik@email.com", Collections.emptyList());
        when(repository.findByEmailAndRemovedAtIsNull(anyString())).thenReturn(Optional.of(userMock));

        var requestMock = new UserRequest("rodrick", "rodrik@email.com");
        assertThrows(ApplicationException.class, () ->
                userService.create(requestMock));
    }

    @Test
    public void shouldUpdateUser_whenUserAlreadyExist() {
        var userMock = new User("rodrick", "rodrik@email.com", Collections.emptyList());
        when(repository.findByUidAndRemovedAtIsNull(any())).thenReturn(Optional.of(userMock));

        var requestMock = new UserRequest("rodrick", "rodrik@email.com");
        userService.updateOrCreate(UUID.randomUUID(), requestMock);

        var singleDigitCaptor = ArgumentCaptor.forClass(User.class);
        verify(repository).save(singleDigitCaptor.capture());

        var result = singleDigitCaptor.getValue();

        assertEquals(userMock, result);
    }

    @Test
    public void shouldCreateUser_whenTryingUpdateAUserThatDoNotExist() {
        var userUidMock = UUID.randomUUID();
        User userMock = getMockedUser(userUidMock);
        when(repository.findByUidAndRemovedAtIsNull(any())).thenReturn(Optional.empty());

        var requestMock = new UserRequest("rodrick", "rodrik@email.com");
        userService.updateOrCreate(userUidMock, requestMock);

        var singleDigitCaptor = ArgumentCaptor.forClass(User.class);
        verify(repository).save(singleDigitCaptor.capture());

        var result = singleDigitCaptor.getValue();

        assertEquals(userMock, result);
    }

    @Test
    public void shouldReturnUser_whenUserUuidIsAlreadyRegistered() {
        var userUidMock = UUID.randomUUID();
        User userMock = getMockedUser(userUidMock);
        when(repository.findByUidAndRemovedAtIsNull(userUidMock)).thenReturn(Optional.of(userMock));
        when(singleDigitRepository.findAllByUser(userMock)).thenReturn(null);

        var result = userService.find(userUidMock);
        assertEquals(userMock, result);
    }

    @Test
    public void shouldThrowApplicationException_whenUserNotFoundedByUuid() {
        var userUidMock = UUID.randomUUID();
        when(repository.findByUidAndRemovedAtIsNull(userUidMock)).thenReturn(Optional.empty());
        assertThrows(ApplicationException.class, () ->
                userService.find(userUidMock));
    }

    @Test
    public void shouldReturnUserSingleDigits_whenFetchingASpecificUser() {
        var singleDigitsMock = List.of(mock(SingleDigit.class));
        var userUidMock = UUID.randomUUID();
        User userMock = getMockedUser(userUidMock);
        when(repository.findByUidAndRemovedAtIsNull(userUidMock)).thenReturn(Optional.of(userMock));
        when(singleDigitRepository.findAllByUser(userMock)).thenReturn(singleDigitsMock);
        var result = userService.find(userUidMock);
        verify(singleDigitRepository).findAllByUser(userMock);
        assertEquals(singleDigitsMock, result.getSingleDigitList());
    }

    @Test
    public void shouldReturnAllUsers_whenExistsRegisteredUsers() {
        var userMock = mock(User.class);
        var userListMock = List.of(userMock);
        when(repository.findAllByRemovedAtIsNull()).thenReturn(userListMock);
        when(singleDigitRepository.findAllByUser(userMock)).thenReturn(null);
        var result = userService.findAll();
        assertEquals(userListMock, result);
    }

    @Test
    public void shouldReturnNoUsers_whenDoNotExistsRegisteredUsers() {
        when(repository.findAllByRemovedAtIsNull()).thenReturn(Collections.emptyList());
        var result = userService.findAll();
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void shouldDeleteUser_whenUserIsFoundedByUuid() {
        var userUidMock = UUID.randomUUID();
        var userMock = User.builder()
                .name("rodrick")
                .email("rodrik@email.com")
                .singleDigitList(Collections.emptyList())
                .uid(userUidMock)
                .removedAt(LocalDateTime.now())
                .build();
        when(repository.findByUidAndRemovedAtIsNull(any())).thenReturn(Optional.of(userMock));
        when(repository.save(any())).thenReturn(userMock);
        var result = userService.delete(userUidMock);
        assertNotNull(result.getRemovedAt());
    }

    @Test
    public void shouldThrowApplicationException_whenTryingToDeleteANonexistentUser() {
        var userUidMock = UUID.randomUUID();
        when(repository.findByUidAndRemovedAtIsNull(any())).thenReturn(Optional.empty());
        assertThrows(ApplicationException.class, () ->
                userService.delete(userUidMock));
    }

    private User getMockedUser(UUID userUidMock) {
        return User.builder()
                .name("rodrick")
                .email("rodrik@email.com")
                .singleDigitList(Collections.emptyList())
                .uid(userUidMock)
                .build();
    }

}
