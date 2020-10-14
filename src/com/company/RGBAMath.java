package com.company;

import java.awt.*;

public class RGBAMath {
    private int r = 0;
    private int g = 0;
    private int b = 0;
    private int a = 0;

    public void sum(Color color) {
        r += color.getRed();
        g += color.getGreen();
        b += color.getBlue();
        a += color.getAlpha();
    }

    public void avg(int value) {
        r /= value;
        g /= value;
        b /= value;
        a /= value;
    }

    public Color newColor() {
        return new Color(r, g, b, a);
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }
}
