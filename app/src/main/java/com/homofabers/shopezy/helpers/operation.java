package com.homofabers.shopezy.helpers;

import com.homofabers.shopezy.interf.arithematic;

public class operation implements arithematic {
    @Override
    public void add(int a, int b) {
        System.out.println(a+b);
    }

    @Override
    public void subtract(int a, int b) {
        System.out.println(a-b);
    }

    @Override
    public void mul(int a, int b) {
        System.out.println(a*b);
    }

    @Override
    public void div(int a, int b) {
        System.out.println(a / b);
    }

    @Override
    public void mod(int a, int b) {
        System.out.println(a % b);
    }
}
