package com.company;

public class Rectangle {
    private int left;
    private int right;
    private int top;
    private int bottom;
    private int width;
    private int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = Math.max(left, 0);
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = Math.min(right, width - 1);
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = Math.max(top, 0);
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = Math.min(bottom, height - 1);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTotalItemsInRectangle() {
        return (getBottom() - getTop() + 1) * (getRight() - getLeft() + 1);
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "left=" + left +
                ", right=" + right +
                ", top=" + top +
                ", bottom=" + bottom +
                '}';
    }
}
