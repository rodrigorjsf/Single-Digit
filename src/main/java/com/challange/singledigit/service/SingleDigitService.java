package com.challange.singledigit.service;

import com.challange.singledigit.exception.ApplicationException;
import com.challange.singledigit.model.SingleDigit;
import com.challange.singledigit.model.dto.SingleDigitResponse;
import com.challange.singledigit.repository.SingleDigitRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Objects;
import java.util.UUID;

import static com.challange.singledigit.exception.ApplicationExceptionType.INVALID_DIGIT;
import static org.apache.commons.lang.StringUtils.isNumeric;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SingleDigitService {

    private final CacheService cacheService;
    private final SingleDigitRepository repository;
    private final UserService userService;

    public SingleDigitResponse getSingleDigit(String digit, int repetitions, String userUid) {
        var singleDigit = calculate(digit, repetitions);
        if (StringUtils.isNotEmpty(userUid)) {
            var user = userService.find(UUID.fromString(userUid));
            singleDigit.setUser(user);
            repository.save(singleDigit);
            return new SingleDigitResponse(singleDigit, user);
        }
        return new SingleDigitResponse(singleDigit);
    }

    private SingleDigit calculate(String digit, int repetitions) {
        if (isNumeric(digit)) {
            var singleDigit = SingleDigit.builder()
                    .number(digit)
                    .repetitions(repetitions)
                    .build();
            var numberWithRepetitions = repetitions <= 0 ? digit : digit.repeat(repetitions);
            var cachedResult = cacheService.get(numberWithRepetitions);

            if (Objects.nonNull(cachedResult)) {
                singleDigit.setResult(cachedResult);
                return singleDigit;
            }

            var result = calculateSingleDigit(numberWithRepetitions);
            cacheService.put(numberWithRepetitions, result);
            singleDigit.setResult(result);
            return singleDigit;
        }
        throw new ApplicationException(INVALID_DIGIT);
    }

    private Integer calculateSingleDigit(String number) {
        BigInteger bigInt = new BigInteger(number);
        if (bigInt.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) <= 0) {
            return (int) (bigInt.longValue() % 9);
        }
        while (number.length() > 1) {
            number = Integer.toString(number.chars().map(Character::getNumericValue).sum());
        }
        return Integer.parseInt(number);
    }
}
