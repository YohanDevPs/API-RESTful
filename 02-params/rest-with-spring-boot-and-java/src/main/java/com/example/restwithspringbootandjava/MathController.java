package com.example.restwithspringbootandjava;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MathController {

    private static final String template = "Hello, %s!";
    private static final AtomicLong counter = new AtomicLong();

    @RequestMapping("/sum/{numberOne}/{numberTwo}")
    @GetMapping
    public Double sum(@PathVariable(value = "numberOne") String numberOne,
                      @PathVariable(value = "numberTwo") String numberTwo) throws Exception {

        if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new Exception();
        }

        return convertToDouble(numberOne) + convertToDouble(numberTwo);
    }

    private Double convertToDouble(String number) {
        if(number == null) {
            return 0D;
        }

        String strNumber = number.replaceAll(",", ".");
        if (isNumeric(strNumber)){
            return Double.parseDouble(strNumber);
        }

        return 0D;
    }

    private boolean isNumeric(String srtNumber) {
        if(srtNumber == null) {
            return false;
        }

        String number = srtNumber.replaceAll(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}
