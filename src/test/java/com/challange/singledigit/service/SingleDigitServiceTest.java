package com.challange.singledigit.service;

import com.challange.singledigit.exception.ApplicationException;
import com.challange.singledigit.model.SingleDigit;
import com.challange.singledigit.model.User;
import com.challange.singledigit.repository.SingleDigitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SingleDigitServiceTest {

    @Mock
    private CacheService cacheService;

    @Mock
    private SingleDigitRepository repository;

    @Mock
    private UserService userService;

    @InjectMocks
    private SingleDigitService singleDigitService;

    @Test
    void shouldThrowException_whenStringHasANonDigitChar() {
        assertThrows(ApplicationException.class, () ->
                singleDigitService.getSingleDigit("sasa7s4as", 1, null));
    }

    @Test
    void shouldCheckCacheServiceBeforeCalculateSingleDigit() {
        when(cacheService.get(any())).thenReturn(null);

        singleDigitService.getSingleDigit("9875", 4, null);

        verify(cacheService).get("9875987598759875");
    }

    @Test
    void shouldStoreCalculationOnCacheService_whenCalculatingSingleDigit() {
        when(cacheService.get(any())).thenReturn(null);

        singleDigitService.getSingleDigit("9875", 1, null);

        verify(cacheService).put(eq("9875"), eq(2));
    }

    @Test
    void shouldReturn2_whenInputsAreNumber9875AndRepeatTimes1() {
        setupCacheToReturnNull();
        assertEquals(2, singleDigitService.getSingleDigit("9875", 1, null).getResult());
    }

    @Test
    void shouldReturn8_whenInputsAreNumber9875AndRepeatTimes4() {
        setupCacheToReturnNull();
        assertEquals(8, singleDigitService.getSingleDigit("9875", 4, null).getResult());
    }

    @Test
    void shouldReturnSingleDigitWithoutRepeatingInputNumber_whenInputRepeatTimeIsZero() {
        setupCacheToReturnNull();
        assertEquals(2, singleDigitService.getSingleDigit("9875", 0, null).getResult());
    }

    @Test
    void shouldReturnSingleDigitWithoutRepeatingInputNumber_whenInputRepeatTimeIsLessThanZero() {
        setupCacheToReturnNull();
        assertEquals(2, singleDigitService.getSingleDigit("9875", -1, null).getResult());
    }

    private void setupCacheToReturnNull() {
        when(cacheService.get(any())).thenReturn(null);
        doNothing().when(cacheService).put(anyString(), anyInt());
    }

    @Test
    void shouldThrowApplicationException_whenDigitIsntNumeric() {
        assertThrows(ApplicationException.class, () ->
                singleDigitService.getSingleDigit("2daew", 1, null));
    }

    @Test
    void shouldSaveSingleDigitToAUser_whenUserUUIDIsNotNull() {
        when(cacheService.get(any())).thenReturn(null);
        var userMock = new User("rodrick", "rodrik@email.com", Collections.emptyList());
        when(userService.find(any())).thenReturn(userMock);
        when(repository.save(any())).thenReturn(mock(SingleDigit.class));

        var uid = UUID.randomUUID().toString();
        singleDigitService.getSingleDigit("9875", 1, uid);

        var singleDigitCaptor = ArgumentCaptor.forClass(SingleDigit.class);
        verify(repository).save(singleDigitCaptor.capture());

        var result = singleDigitCaptor.getValue();
        var singleDigitExpected = SingleDigit.builder()
                .number("9875")
                .repetitions(1)
                .result(2)
                .user(userMock)
                .build();

        assertEquals(singleDigitExpected, result);
    }

    @Test
    void shouldFetchUserByUUID_whenUserUUIDIsNotNull() {
        when(userService.find(any())).thenReturn(mock(User.class));

        var uid = UUID.randomUUID().toString();
        singleDigitService.getSingleDigit("9875", 1, uid);

        verify(userService).find(UUID.fromString(uid));
    }

}
