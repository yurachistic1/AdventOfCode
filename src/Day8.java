import java.util.ArrayList;
import java.util.Arrays;

public class Day8 {

    public static void day8a() throws Exception{
        String input = UtilityFunctions.convertInputToString(
                "./inputs/Day8.txt");

        ArrayList<String> arr = new ArrayList<>(Arrays.asList(input.split("(?<=\\G.{25})")));

        int min = Integer.MAX_VALUE;
        int one = 0;
        int two = 0;
        int layer = 0;
        int totalOnLayerZero = 0;
        int totalOnLayerOne = 0;
        int totalOnLLayerTwo = 0;

        for(int i = 1; i < arr.size() + 1; i++){
            totalOnLayerZero = totalOnLayerZero + arr.get(i - 1).length() - arr.get(i - 1).replace("0", "").length();
            totalOnLayerOne = totalOnLayerOne + arr.get(i - 1).length() - arr.get(i - 1).replace("1", "").length();
            totalOnLLayerTwo = totalOnLLayerTwo + arr.get(i - 1).length() - arr.get(i - 1).replace("2", "").length();
            if (i % 6 == 0){
                if (totalOnLayerZero < min){
                    min = totalOnLayerZero;
                    one = totalOnLayerOne;
                    two = totalOnLLayerTwo;
                    layer = i / 6 + 1;
                }
                totalOnLayerZero = 0;
                totalOnLayerOne = 0;
                totalOnLLayerTwo = 0;
            }
        }
        System.out.println(one * two);
    }

    public static void day8b() throws Exception{
        int dimX = 25;
        int dimY = 6;
        String input = UtilityFunctions.convertInputToString(
                "./inputs/Day8.txt");

        ArrayList<String> arr = new ArrayList<>(Arrays.asList(input.split("(?<=\\G.{25})")));

        char[][] layers = new char[arr.size()][dimX];

        for (int i = 0; i < arr.size(); i++){
            layers[i] = arr.get(i).toCharArray();
        }

        char[][] image = new char[dimY][dimX];
        char[][] restOfTheLayers = new char[layers.length - image.length][dimX];

        for (int i = 0; i < dimY; i++){
            image[i] = layers[i];
        }

        for (int i = dimY; i < layers.length; i++){
            restOfTheLayers[i - dimY] = layers[i];
        }

        for (int i = 0; i < restOfTheLayers.length; i++){
            for (int j = 0; j < dimX; j++){
                if (image[i % dimY][j] == '2'){
                    image[i % dimY][j] = restOfTheLayers[i][j];
                }
            }
        }

        for (char[] arr1 : image){
            for(char ch : arr1){
                if(ch == '0'){
                    System.out.print("  ");
                } else {
                    System.out.print("# ");
                }
            }
            System.out.println();
        }
    }
}






























