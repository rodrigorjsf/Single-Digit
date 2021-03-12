package com.challange.singledigit.service;

import com.challange.singledigit.repository.SingleDigitRepository;
import com.challange.singledigit.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private SingleDigitRepository singleDigitRepository;

    @InjectMocks
    private UserService userService;

//    @Test
//    public void shouldCreateNewUser_whenEmailIsValid() {
//        var userMock = new User("rodrick", "rodrik@email.com", Collections.emptyList());
//        when(repository.findByEmailAndRemovedAtIsNull(anyString())).thenReturn(of(userMock));
//
//        var requestMock = new UserRequest("rodrick", "rodrik@email.com");
//        userService.create(requestMock);
//
//        var singleDigitCaptor = ArgumentCaptor.forClass(User.class);
//        verify(repository).save(singleDigitCaptor.capture());
//
//        var result = singleDigitCaptor.getValue();
//
//        assertEquals(userMock, result);
//    }
}
