package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PixelMapper {

    public static int[][] mapToPixels(Color[][] colors, int width, int height) {
        if (colors == null || colors.length == 0) {
            return new int[0][0];
        }

        var result = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                result[i][j] = colors[i][j].getRGB();
            }
        }

        return result;
    }

    public static Color[][] mapToColors(BufferedImage image, int startX, int width, int height) {
        if (width == 0) {
            return new Color[0][0];
        }

        var result = new Color[width][height];

        for (int i = startX; i < startX + width; i++) {
            for (int j = Blur.START_Y; j < Blur.START_Y + height; j++) {
                result[i - startX][j - Blur.START_Y] = new Color(image.getRGB(i, j));
            }
        }

        return result;
    }

    public static Color[][] mapToColors(Element[][] elements) {
        if (elements == null || elements.length == 0) {
            return new Color[0][0];
        }

        var colors = new Color[elements.length][elements[0].length];

        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[i].length; j++) {
                colors[i][j] = elements[i][j].getColor();
            }
        }

        return colors;
    }

    public static BufferedImage mapToBufferedImage(int[][] pixels, int width, int height) {
        var image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                image.setRGB(i, j, pixels[i][j]);
            }
        }

        return image;
    }

    public static Element[][] collectToElements(Color[][] colors, Color[][] left, Color[][] right) {
        if (colors == null || colors.length == 0) {
            return new Element[0][0];
        }

        var elements = new Element[colors.length + left.length + right.length][colors[0].length];

        var generalElements = mapToElements(colors, true);
        var leftElements = mapToElements(left, false);
        var rightElements = mapToElements(right, false);

        System.arraycopy(leftElements, 0, elements, 0, leftElements.length);
        System.arraycopy(generalElements, 0, elements, left.length, generalElements.length);
        System.arraycopy(rightElements, 0, elements, left.length + colors.length, rightElements.length);

        return elements;
    }

    public static Element[][] mapToElements(Color[][] colors, boolean changeable) {
        if (colors == null || colors.length == 0) {
            return new Element[0][0];
        }

        var elements = new Element[colors.length][colors[0].length];

        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < colors[i].length; j++) {
                elements[i][j] = new Element(colors[i][j], changeable);
            }
        }

        return elements;
    }
}
