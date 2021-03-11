package com.challange.singledigit.controller;

import com.challange.singledigit.model.dto.SingleDigitResponse;
import com.challange.singledigit.service.SingleDigitService;
import com.challange.singledigit.util.UUIDUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("single-digit")
@RequiredArgsConstructor
public class SingleDigitController {

    private final SingleDigitService singleDigitService;

    @ApiOperation(value = "Calculate the single digit")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns the single digit from a specific number."),
            @ApiResponse(code = 400, message = "Missing parameters"),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SingleDigitResponse> calculate(@NotNull @RequestParam("digit") String digit,
                                                         @RequestParam("repetitions") Integer repetitions,
                                                         @RequestParam(value = "userUid", required = false)
                                                         @Pattern(regexp = UUIDUtils.UUID_REGEXP, message = "Invalid uid") String userUid) {
        var singleDigit = singleDigitService.getSingleDigit(digit, repetitions, userUid);
        return ResponseEntity.ok(singleDigit);
    }
}
