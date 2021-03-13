package com.challange.singledigit.service;

import com.challange.singledigit.exception.ApplicationException;
import com.challange.singledigit.model.User;
import com.challange.singledigit.util.RSAUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.IllegalBlockSizeException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CryptoServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private CryptoService cryptoService;

    private final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkI8ez7VAMQpE7UKn9lQ4YJaRTRRN7btG5pAf+h2H/5l9kYpsFHvseYRadS7Z+DoP2uaI9Msz11bWEnR2LHfprjPZjjpcJwne+A7aC6GGQsyO6VFTYyNlsczj8ZnPROlQgo8c+lIXkYk/L470vS83sYYaHzTWvuzzZqpAGPJ278h5/gLxrHqNnp8JY2NHEKRMxuLZwr/5/ISa3x5nMLjaEcqWQ+VO01MAHuQrcJUhtIWfbrKkheZ5ehJ7gRD35fgP0FizKNCFpP7b8TcSAPyR0Pgh0t7yZse3L2CSI6vjlNMZLqPaDNxg+EDdmituFIIbhww44BaBM4dmXcDl25iZ/QIDAQAB";
    private final String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCQjx7PtUAxCkTtQqf2VDhglpFNFE3tu0bmkB/6HYf/mX2RimwUe+x5hFp1Ltn4Og/a5oj0yzPXVtYSdHYsd+muM9mOOlwnCd74DtoLoYZCzI7pUVNjI2WxzOPxmc9E6VCCjxz6UheRiT8vjvS9LzexhhofNNa+7PNmqkAY8nbvyHn+AvGseo2enwljY0cQpEzG4tnCv/n8hJrfHmcwuNoRypZD5U7TUwAe5CtwlSG0hZ9usqSF5nl6EnuBEPfl+A/QWLMo0IWk/tvxNxIA/JHQ+CHS3vJmx7cvYJIjq+OU0xkuo9oM3GD4QN2aK24UghuHDDjgFoEzh2ZdwOXbmJn9AgMBAAECggEAMwTwIOqdXzF6pioyUXPj+eLqIUQbRN4l5dXUE1g82W74rFcoUgpyqWiW7Gi+sSb81YsrpwnYoBWbtCO6WXNw2/Scwd/DClwnf3Dub9BwLD856Qq03XZuu9POV0SrrGyT56BO3+H9Q3YnAMf5hJOXmmXz7jOd3wCN3xwTRzq702Bryejl7lM25QZIWDGs/P5axztEK5TU+4Z2ZSkD34JZ85/UB1MyyXaETi6c6Ekg/WEhszbpkpes+3W6RyjqS0GT/a9mljo1KUcZUrj8yMsiRs4Nh/VE9QyT00XycIz1cpAiETVs1LemOwdSMg+EMcVBsjaeIyzWgGruyGR4YH54GQKBgQDP9fCuzw1x+TOVhc0vYFeRTI2Y1GEM4pX4rcQ2zCNmAn3Tbs32LV41epExgDB+5Xl9MDLcw1CG+fFOtpfsBynVm1OdUZrrarj0ygesgWyRpXNDPkkWroCJoBQ9N8Hx0Wt/fd+ip1/SVlzw8GOznBOP8VYEGbA7bn78Z6iI1RczTwKBgQCx89TnE5oTCGArm/QctMGclLflrZzVaX6EUE37FY6COhc38J1PhWFNuNsfDP36dfMbwSjjhhjXN3jhNanARlupOd/T7KwaFuWxvdDFho7tIkjmZOsSIEE8ZHIkjKXxgMIx7OSOaRbgjJ9x7WLPBTL73PxDDgrJ/qiTL4Be6x068wKBgQCWGDcnpC9bFHbDs3e9evaY2zzTkm2dhCij/J7CwiHGbYPtR2sGBAab6KPEq3XVEMDXtyLurVWApgMhQa5y31S1ZE88G3sJ8NQfcXWKbzsx/80qOlk8MqR5MH2LFaQ4aWMB+JKhbdY+FLEuAQKkzsBbwt7HRNcqffzPJe3BHLOLBwKBgHb1CqOWJO8IMFG2pQ7zfmHPwMcWsbqwFNUaYaaxGFzRvGe2v1JDVXKBWml9Y+KkKmmpTE4cfEqSWIokN1/DKM8NUyT2F24xVEbnHsKMWssGmjj5yXKKgVF/zBXGt7+jVS3OsFLKyXI+Tu/3wo+laBmZ9kBVCL1TMRmDLYtGHHolAoGAFDUT6xJ/YY7sazTPglyk/n9V0GbpuQGMPukN3fkY313d2imDeYn2nPK+daYLYZD9OsAkJwZcYKwG+TdaaA4jPaaFdyOXbTYU/juJMiSFoIsFTxVodIWaR4guen9EyYLNeQnlJa3F71atS1RNGa2x5wQYmjVwKVlUfmdTQQ++SWc=";

    @Test
    public void shouldEncryptUserNameAndEmail_whenUserExistsAndPublicKeyIsValid() {
        var userMock = User.builder()
                .name("rodrick")
                .email("rodrik@email.com")
                .build();
        when(userService.find(any())).thenReturn(userMock);
        cryptoService.encrypt(UUID.randomUUID(), PUBLIC_KEY);
        var singleDigitCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).update(singleDigitCaptor.capture());
        var result = singleDigitCaptor.getValue();

        assertTrue(Base64.isBase64(result.getEmail()));
        assertTrue(Base64.isBase64(result.getName()));
        assertEquals("rodrick", RSAUtil.decrypt(result.getName(), PRIVATE_KEY));
        assertEquals("rodrik@email.com", RSAUtil.decrypt(result.getEmail(), PRIVATE_KEY));
    }

    @Test
    public void shouldDecryptUserNameAndEmail_whenUserExistsAndPrivateKeyIsValid() {
        var userMock = User.builder()
                .name(RSAUtil.encrypt("rodrick", PUBLIC_KEY))
                .email(RSAUtil.encrypt("rodrik@email.com", PUBLIC_KEY))
                .build();
        when(userService.find(any())).thenReturn(userMock);
        cryptoService.decrypt(UUID.randomUUID(), PRIVATE_KEY);
        var singleDigitCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).update(singleDigitCaptor.capture());
        var result = singleDigitCaptor.getValue();

        assertEquals("rodrick", result.getName());
        assertEquals("rodrik@email.com", result.getEmail());
    }

    @Test
    public void shouldThrowApplicationException_whenTryingToEncryptAnEncryptedInformation() {
        var userMock = User.builder()
                .name(RSAUtil.encrypt("rodrick", PUBLIC_KEY))
                .email(RSAUtil.encrypt("rodrik@email.com", PUBLIC_KEY))
                .build();
        when(userService.find(any())).thenReturn(userMock);
        assertThrows(ApplicationException.class, () ->
                cryptoService.encrypt(UUID.randomUUID(), PUBLIC_KEY));
    }

    @Test
    public void shouldThrowApplicationException_whenTryingToDecryptAnDecryptedInformation() {
        var userMock = User.builder()
                .name("rodrick")
                .email("rodrik@email.com")
                .build();
        when(userService.find(any())).thenReturn(userMock);
        assertThrows(ApplicationException.class, () ->
                cryptoService.decrypt(UUID.randomUUID(), PRIVATE_KEY));
    }
}
