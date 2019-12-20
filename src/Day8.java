import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Day8 {

    public static void main(String[] args) throws Exception{
        day8a();
        day8b();
    }

    public static void day8a() throws Exception {
        String input = UtilityFunctions.convertInputToString(
                "./inputs/Day8.txt");

        ArrayList<String> arr = new ArrayList<>(Arrays.asList(input.split("(?<=\\G.{25})")));

        int min = Integer.MAX_VALUE;
        int one = 0;
        int two = 0;
        int totalOnLayerZero = 0;
        int totalOnLayerOne = 0;
        int totalOnLLayerTwo = 0;

        for (int i = 1; i < arr.size() + 1; i++) {
            totalOnLayerZero = totalOnLayerZero + arr.get(i - 1).length() - arr.get(i - 1).replace("0", "").length();
            totalOnLayerOne = totalOnLayerOne + arr.get(i - 1).length() - arr.get(i - 1).replace("1", "").length();
            totalOnLLayerTwo = totalOnLLayerTwo + arr.get(i - 1).length() - arr.get(i - 1).replace("2", "").length();
            if (i % 6 == 0) {
                if (totalOnLayerZero < min) {
                    min = totalOnLayerZero;
                    one = totalOnLayerOne;
                    two = totalOnLLayerTwo;
                }
                totalOnLayerZero = 0;
                totalOnLayerOne = 0;
                totalOnLLayerTwo = 0;
            }
        }
        System.out.printf("Part one: %d", one * two);
    }

    public static void day8b() throws Exception {
        int dimX = 25;
        int dimY = 6;
        String input = UtilityFunctions.convertInputToString(
                "./inputs/Day8.txt");

        ArrayList<String> arr = new ArrayList<>(Arrays.asList(input.split(String.format("(?<=\\G.{%d})", dimX))));

        char[][] layers = new char[arr.size()][dimX];
        char[][] image = new char[dimY][dimX];

        JFrame jFrame = new JFrame();

        Tile tiles = new Tile(image);
        jFrame.getContentPane().add(tiles);

        jFrame.setResizable(false);
        jFrame.setSize(dimX * 30, dimY * 30);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);

        for (int i = 0; i < arr.size(); i++) {
            layers[i] = arr.get(i).toCharArray();
        }

        for (int i = 0; i < dimY; i++) {
            for (int j = 0; j < dimX; j++) {
                image[i][j] = layers[i][j];
                tiles.updateImage(image);
                TimeUnit.MICROSECONDS.sleep(2);
            }
        }

        for (int i = 0; i < layers.length - dimY; i++) {
            for (int j = 0; j < dimX; j++) {
                if (image[i % dimY][j] == '2') {
                    image[i % dimY][j] = layers[i + dimY][j];
                    tiles.updateImage(image);
                    TimeUnit.MICROSECONDS.sleep(2);
                }
            }
        }
    }
}

class Tile extends JPanel {

    int dimX = 30;
    int dimY = 30;
    char[][] image;

    public Tile(char[][] image){
        this.image = image;

        Dimension dimension = new Dimension(image[0].length * dimX, image.length * dimY);

        setPreferredSize(dimension);
    }

    public void updateImage(char[][] image){
        this.image = image;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        for (int i = 0; i < image.length; i++){
            for (int j = 0; j < image[0].length; j++){
                if (image[i][j] == '0'){
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }

                g.fillRect(j * dimY, i * dimX, dimX, dimY);
            }
        }
    }
}






























