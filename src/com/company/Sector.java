package com.company;

public class Sector extends Thread {

    private static int THREAD_NUMBER = 0;

    private final Element[][] elements;
    private Element[][] results;

    private final int changeableWidth;
    private final int width;
    private final int height;

    public Sector(Element[][] elements, int changeableWidth, int width, int height) {
        this.elements = elements;
        this.changeableWidth = changeableWidth;
        this.width = width;
        this.height = height;
        super.setName(String.format("Thread %d", ++THREAD_NUMBER));
    }

    @Override
    public void run() {
        results = blur();
    }

    private Element[][] blur() {
        var results = new Element[changeableWidth][height];
        var index = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (!elements[i][j].isChangeable()) continue;

                var rectangle = setRectangle(i, j);
                var rgba = new RGBAMath();

                for (int k = rectangle.getLeft(); k <= rectangle.getRight(); k++) {
                    for (int l = rectangle.getTop(); l <= rectangle.getBottom(); l++) {
                        rgba.sum(elements[k][l].getColor());
                    }
                }

                rgba.avg(rectangle.getTotalItemsInRectangle());
                results[index][j] = new Element(rgba.newColor(), true);

                if (j + 1 == height) index++;
            }
        }

        return results;
    }

    private Rectangle setRectangle(int indexI, int indexJ) {
        var rectangle = new Rectangle(width, height);

        rectangle.setLeft(indexI - Blur.RADIUS);
        rectangle.setRight(indexI + Blur.RADIUS);
        rectangle.setTop(indexJ - Blur.RADIUS);
        rectangle.setBottom(indexJ + Blur.RADIUS);

        return rectangle;
    }

    public Element[][] getResults() {
        return results;
    }

    public int getChangeableWidth() {
        return changeableWidth;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
