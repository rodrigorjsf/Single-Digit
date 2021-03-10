package com.challange.singledigit.service;

import com.challange.singledigit.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Objects;

import static com.challange.singledigit.exception.ApplicationExceptionType.INVALID_DIGIT;
import static org.apache.commons.lang.StringUtils.isNumeric;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SingleDigitService {

    private final CacheService cacheService;

    public Integer getSingleDigit(String digit, int repetitions) {
        if (isNumeric(digit)) {
            var number = repetitions <= 0 ? digit : digit.repeat(repetitions);
            var cachedResult = cacheService.get(number);

            if (Objects.nonNull(cachedResult))
                return cachedResult;

            var result = calculateSingleDigit(number);
            cacheService.put(number, result);
            return result;
        }
        throw new ApplicationException(INVALID_DIGIT);
    }

    private Integer calculateSingleDigit(String number) {
        BigInteger bigInt = new BigInteger(number);
        if(bigInt.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) <= 0) {
            return (int) (bigInt.longValue() % 9);
        }
        while (number.length() > 1) {
            number = Integer.toString(number.chars().map(Character::getNumericValue).sum());
        }
        return Integer.parseInt(number);
    }
}
