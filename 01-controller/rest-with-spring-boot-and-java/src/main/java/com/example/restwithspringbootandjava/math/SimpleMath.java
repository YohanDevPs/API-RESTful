package com.example.restwithspringbootandjava.math;

public class SimpleMath {

    public Double sum(Double numberOne, Double numberTwo) {
        return numberOne + numberTwo;
    }
    
    public Double subtr(Double numberOne, Double numberTwo) {
        return numberOne - numberTwo;
    }

    public Double mult(Double numberOne, Double numberTwo)  {
        return numberOne * numberTwo;
    }

    public Double div(Double numberOne, Double numberTwo)  {
        return numberOne / numberTwo;
    }

    public Double media( Double numberOne, Double numberTwo)  {
        return (numberOne + numberTwo)/2;
    }

    public Double raiz(Double number)  {
        return Math.sqrt(number);
    }
}
