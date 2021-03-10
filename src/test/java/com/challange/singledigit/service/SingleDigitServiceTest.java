package com.challange.singledigit.service;

import com.challange.singledigit.exception.ApplicationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SingleDigitServiceTest {

    @Mock
    private CacheService cacheService;

    @InjectMocks
    private SingleDigitService singleDigitService;

    @Test
    public void shouldThrowException_whenStringHasANonDigitChar() {
        assertThrows(ApplicationException.class, () ->
                singleDigitService.getSingleDigit("sasa7s4as", 1));
    }

    @Test
    public void shouldCheckCacheServiceBeforeCalculateSingleDigit() {
        when(cacheService.get(any())).thenReturn(null);

        singleDigitService.getSingleDigit("9875", 4);

        verify(cacheService).get(eq("9875987598759875"));
    }

    @Test
    public void shouldStoreCalculationOnCacheService_whenCalculatingSingleDigit() {
        when(cacheService.get(any())).thenReturn(null);

        singleDigitService.getSingleDigit("9875", 1);

        verify(cacheService).put(eq("9875"), eq(2));
    }

    @Test
    public void shouldReturn2_whenInputsAreNumber9875AndRepeatTimes1() {
        setupCacheToReturnNull();
        assertEquals(2, singleDigitService.getSingleDigit("9875", 1));
    }

    @Test
    public void shouldReturn8_whenInputsAreNumber9875AndRepeatTimes4() {
        setupCacheToReturnNull();
        assertEquals(8, singleDigitService.getSingleDigit("9875", 4));
    }

    @Test
    public void shouldReturnSingleDigitWithoutRepeatingInputNumber_whenInputRepeatTimeIsZero() {
        setupCacheToReturnNull();
        assertEquals(2, singleDigitService.getSingleDigit("9875", 0));
    }

    @Test
    public void shouldReturnSingleDigitWithoutRepeatingInputNumber_whenInputRepeatTimeIsLessThanZero() {
        setupCacheToReturnNull();
        assertEquals(2, singleDigitService.getSingleDigit("9875", -1));
    }

    private void setupCacheToReturnNull() {
        when(cacheService.get(any())).thenReturn(null);
        doNothing().when(cacheService).put(anyString(), anyInt());
    }
}