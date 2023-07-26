import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class GraphGenerator {
    public static void createGraph(int[] values1, int[] values2, int precision, String filepath) {
        int width = 800;
        int height = 600;

        // Create a BufferedImage object with the given width and height
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // Set background color
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Set graph area dimensions
        int graphWidth = width - 100;
        int graphHeight = height - 100;
        int startX = 50;
        int startY = height - 50;

        // Draw x-axis and y-axis
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawLine(startX, startY, startX + graphWidth, startY); // x-axis
        g2d.drawLine(startX, startY, startX, startY - graphHeight); // y-axis

        // Calculate the maximum value in the array
        int maxValue = Integer.MIN_VALUE;
        for (int value : values1) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        for (int value : values2) {
            if (value > maxValue) {
                maxValue = value;
            }
        }

        // Draw the values as points on the graph
        g2d.setColor(Color.BLUE);
        for (int i = 0; i < values1.length; i++) {
            int x = startX + (i * graphWidth) / (values1.length - 1);
            int y = startY - (values1[i] * graphHeight) / maxValue;
            Ellipse2D.Double point = new Ellipse2D.Double(x - 3, y - 3, 6, 6);
            g2d.fill(point);
        }

        g2d.setColor(Color.RED);
        for (int i = 0; i < values2.length; i++) {
            int x = startX + (i * graphWidth) / (values2.length - 1);
            int y = startY - (values2[i] * graphHeight) / maxValue;
            Ellipse2D.Double point = new Ellipse2D.Double(x - 3, y - 3, 6, 6);
            g2d.fill(point);
        }

      /*
        g2d.setColor(Color.PINK);
        int[] approx2 = GraphApproximation.approximate(values2);
        for (int i = 0; i < approx2.length; i++) {
            int x = startX + (i * graphWidth) / (approx2.length - 1);
            int y = startY - (approx2[i] * graphHeight) / maxValue;
            Ellipse2D.Double point = new Ellipse2D.Double(x - 3, y - 3, 6, 6);
            g2d.fill(point);
        } */

        // Draw y-axis labels
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        for (int i = 0; i <= maxValue; i += maxValue / 10) {
            int x = startX;
            int y = startY - (i * graphHeight) / maxValue;
            String label = String.valueOf(i / (int)Math.pow(10, precision));
            g2d.drawString(label, x - g2d.getFontMetrics().stringWidth(label) - 5, y + 5);
        }

        // Save the image as a PNG file
        try {
            File output = new File(filepath);
            ImageIO.write(image, "png", output);
            System.out.println("Graph image saved successfully.");
        } catch (IOException e) {
            System.out.println("Error while saving the graph image.");
            e.printStackTrace();
        }
    }

}

