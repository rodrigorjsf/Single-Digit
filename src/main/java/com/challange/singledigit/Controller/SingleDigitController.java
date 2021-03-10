package com.challange.singledigit.Controller;

import com.challange.singledigit.service.SingleDigitService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping
    public ResponseEntity calculate(@RequestParam("digit") String digit,
                                    @RequestParam("repetitions") Integer repetitions) {
        var singleDigit = singleDigitService.getSingleDigit(digit, repetitions);
        return ResponseEntity.ok(singleDigit);
    }
}
