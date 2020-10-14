package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Blur {

    public static final int RADIUS = 10;

    public static final int THREADS = 10;

    /**
     * Do not change this as the program divides an image into sectors vertically,
     * therefore it has to be always zero that indicates the picture origin (Y coordinate)
     * */
    public static final int START_Y = 0;

    public static final String FILE_FORMAT = "jpg";

    public static final String INPUT_FILE = "input." + FILE_FORMAT;

    public static final String OUTPUT_FILE = "output." + FILE_FORMAT;

    private Sector[] sectors;

    private long startTime;

    private long endTime;

    public Blur() throws InterruptedException, IOException {
        var inputImage = readImage();

        startStopwatch();

        var outputPixels = blur(inputImage);
        var outputImage = PixelMapper.mapToBufferedImage(outputPixels, inputImage.getWidth(), inputImage.getHeight());

        finishStopwatch();

        showResults();
        writeImage(outputImage);
    }

    private int[][] blur(BufferedImage image) throws InterruptedException, IOException {
        initAndStartSectors(image);
        waitThreads();
        return collectSectors(image.getWidth(), image.getHeight());
    }

    private BufferedImage readImage() throws IOException {
        return ImageIO.read(new File(INPUT_FILE));
    }

    private void writeImage(BufferedImage image) throws IOException {
        ImageIO.write(image, FILE_FORMAT, new File(OUTPUT_FILE));
    }

    private void waitThreads() throws InterruptedException {
        for (var sector : sectors) sector.join();
    }

    private void initAndStartSectors(BufferedImage image) throws IOException {
        sectors = new Sector[THREADS];

        var width = image.getWidth() / THREADS;

        for (int i = 0; i < THREADS; i++) {
            var startX = i * width;

            var colors = PixelMapper.mapToColors(image, startX, width, image.getHeight());

            var left = computeLeftNeighbors(startX);
            var leftColors = PixelMapper.mapToColors(image, startX - left, left, image.getHeight());

            var right = computeRightNeighbors(startX, width, image.getWidth());
            var rightColors = PixelMapper.mapToColors(image, startX + width, right, image.getHeight());

            var collectedElements = PixelMapper.collectToElements(colors, leftColors, rightColors);

            var globalWidth = width + leftColors.length + rightColors.length;

            sectors[i] = new Sector(collectedElements, width, globalWidth, image.getHeight());
            sectors[i].start();
        }
    }

    /**
     * Computes the number of left neighbors in order to get rid of the picture merging problem
     * @param startX start X coordinate (pixel)
     * @return the number of left neighbors
     */
    private int computeLeftNeighbors(int startX) {
        var left = RADIUS;

        for (int j = startX - RADIUS; j < startX; j++) {
            if (j >= 0) {
                break;
            }
            left--;
        }

        return left;
    }

    /**
     * Computes the number of right neighbors in order to get rid of the picture merging problem
     * @param startX start X coordinate (pixel)
     * @param localWidth width of the sector
     * @param globalWidth width of the whole picture
     * @return the number of right neighbors
     */
    private int computeRightNeighbors(int startX, int localWidth, int globalWidth) {
        var right = RADIUS;
        var x = startX + localWidth;

        for (int j = x + RADIUS; j >= x; j--) {
            if (j <= globalWidth) {
                break;
            }
            right--;
        }

        return right;
    }

    private int[][] collectSectors(int width, int height) {
        var pixels = new int[width][height];
        var index = 0;

        for (var sector : sectors) {
            var sectorColors = PixelMapper.mapToColors(sector.getResults());
            var sectorPixels = PixelMapper.mapToPixels(sectorColors, sector.getChangeableWidth(), sector.getHeight());
            System.arraycopy(sectorPixels, 0, pixels, index, sectorPixels.length);
            index += sectorPixels.length;
        }

        return pixels;
    }

    private void startStopwatch() {
        this.startTime = System.currentTimeMillis();
    }

    private void finishStopwatch() {
        this.endTime = System.currentTimeMillis();
    }

    private void showResults() {
        var milliseconds = endTime - startTime;
        var seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds);
        var minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);

        System.out.printf("BLUR RADIUS: \n\t%d pixel%s\n", RADIUS, RADIUS != 1 ? "s" : "");

        System.out.printf("TOTAL THREADS: \n\t%d unit%s\n", THREADS, THREADS != 1 ? "s" : "");

        System.out.println("ELAPSED TIME:");

        System.out.printf("\t%d minute%s\n", minutes, minutes != 1 ? "s" : "");
        System.out.printf("\t%d second%s\n", seconds, seconds != 1 ? "s" : "");
        System.out.printf("\t%d millisecond%s\n", milliseconds, milliseconds != 1 ? "s" : "");
    }
}
