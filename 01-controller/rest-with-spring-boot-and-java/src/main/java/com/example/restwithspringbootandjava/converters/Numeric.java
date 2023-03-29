package com.example.restwithspringbootandjava.converters;

public class Numeric {
    public static Double convertToDouble(String number) {
        if(number == null) {
            return 0D;
        }

        String strNumber = number.replaceAll(",", ".");
        if (isNumber(strNumber)){
            return Double.parseDouble(strNumber);
        }

        return 0D;
    }

    public static boolean isNumber(String srtNumber) {
        if(srtNumber == null) {
            return false;
        }

        String number = srtNumber.replaceAll(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}
