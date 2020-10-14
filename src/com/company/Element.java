package com.company;

import java.awt.*;

public class Element {
    private Color color;
    private boolean changeable;

    public Element() {}

    public Element(Color color, boolean changeable) {
        this.color = color;
        this.changeable = changeable;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isChangeable() {
        return changeable;
    }

    public void setChangeable(boolean changeable) {
        this.changeable = changeable;
    }
}
