package com.challange.singledigit.service;

import com.challange.singledigit.exception.ApplicationException;
import com.challange.singledigit.model.User;
import com.challange.singledigit.model.dto.UserRequest;
import com.challange.singledigit.repository.SingleDigitRepository;
import com.challange.singledigit.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    public void shouldThrowApplicationException_whenEmailIsAlreadyRegistered() {
        var userMock = new User("rodrick", "rodrik@email.com", Collections.emptyList());
        when(repository.findByEmailAndRemovedAtIsNull(anyString())).thenReturn(Optional.of(userMock));

        var requestMock = new UserRequest("rodrick", "rodrik@email.com");
        assertThrows(ApplicationException.class, () ->
                userService.create(requestMock));
    }
}
