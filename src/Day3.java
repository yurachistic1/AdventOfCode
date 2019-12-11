import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Day3 {

    public static void Day3a() throws Exception {
        ArrayList<String> changes = UtilityFunctions.convertInputToArrayListString(
                "./inputs/Day3.txt");
        String wire1[] = changes.get(0).split(",");
        String wire2[] = changes.get(1).split(",");

        PVector coordsOfW1 = new PVector(0, 0);
        PVector coordsOfW2 = new PVector(0 , 0);

        HashMap<PVector, PVector> map = new HashMap<>();
        HashMap<PVector, PVector> map2 = new HashMap<>();

        int mapOffset = 1;
        int map2Offset = 1;

        int minDistance = Integer.MAX_VALUE;

        for (int i = 0; i < wire1.length; i++){
            mapOffset--;
            PVector start = new PVector(coordsOfW1.getX(), coordsOfW1.getY());
            PVector change = stringToPoint(wire1[i]);
            coordsOfW1.add(change);
            int changeCoeff = 0;
            int limit = 0;
            boolean axis = false;
            if(change.getX() == 0){
                limit = coordsOfW1.getY();
                changeCoeff = change.getY() / Math.abs(change.getY());
                axis = false;
            } else {
                limit = coordsOfW1.getX();
                changeCoeff = change.getX() / Math.abs(change.getX());
                axis = true;
            }

            if(changeCoeff > 0) {
                for (int j = axis ? start.getX() : start.getY(); j <= limit; j += changeCoeff) {
                    PVector pointOnLine = axis ? new PVector(j, start.getY()) : new PVector(start.getX(), j);
                    if (map.containsKey(pointOnLine)){
                        mapOffset++;
                    } else {
                        map.put(pointOnLine, new PVector(map.size(), mapOffset));
                    }
                }
            } else {
                for (int j = axis ? start.getX() : start.getY(); j >= limit; j += changeCoeff) {
                    PVector pointOnLine = axis ? new PVector(j, start.getY()) : new PVector(start.getX(), j);
                    if (map.containsKey(pointOnLine)) {
                        mapOffset++;
                    } else {
                        map.put(pointOnLine, new PVector(map.size(), mapOffset));
                    }
                }
            }
        }

        for (int i = 0; i < wire2.length; i++){
            map2Offset--;
            PVector start = new PVector(coordsOfW2.getX(), coordsOfW2.getY());
            PVector change = stringToPoint(wire2[i]);
            coordsOfW2.add(change);
            int changeCoeff = 0;
            int limit = 0;
            boolean axis = false;
            if(change.getX() == 0){
                limit = coordsOfW2.getY();
                changeCoeff = change.getY() / Math.abs(change.getY());
                axis = false;
            } else {
                limit = coordsOfW2.getX();
                changeCoeff = change.getX() / Math.abs(change.getX());
                axis = true;
            }

            if(changeCoeff > 0) {
                for (int j = axis ? start.getX() : start.getY(); j <= limit; j += changeCoeff) {
                    PVector pointOnLine = axis ? new PVector(j, start.getY()) : new PVector(start.getX(), j);
                    if (map2.containsKey(pointOnLine)) {
                        map2Offset++;
                    } else {
                        map2.put(pointOnLine, new PVector(map2.size(), map2Offset));
                    }
                    if (map.containsKey(pointOnLine)) {
                        int distance = map.get(pointOnLine).getX() + map2.get(pointOnLine).getX()
                                     + map.get(pointOnLine).getY() + map2.get(pointOnLine).getY();
                        if (distance != 0 && distance < minDistance) {
                            minDistance = distance;
                        }
                    }
                }
            } else {
                for (int j = axis ? start.getX() : start.getY(); j >= limit; j += changeCoeff) {
                    PVector pointOnLine = axis ? new PVector(j, start.getY()) : new PVector(start.getX(), j);
                    if (map2.containsKey(pointOnLine)){
                        map2Offset++;
                    } else {
                        map2.put(pointOnLine, new PVector(map2.size(), map2Offset));
                    }
                    if (map.containsKey(pointOnLine)) {
                        int distance = map.get(pointOnLine).getX() + map2.get(pointOnLine).getX()
                                     + map.get(pointOnLine).getY() + map2.get(pointOnLine).getY();
                        if (distance != 0 && distance + map2Offset < minDistance) {
                            minDistance = distance;
                        }
                    }
                }
            }

        }

        System.out.println(minDistance);
//        System.out.println(map2Offset - wire2.length + 1);
//        System.out.println(mapOffset - wire1.length + 1);

    }

    public static void Day3b() throws Exception {
        ArrayList<String> changes = UtilityFunctions.convertInputToArrayListString(
                "./inputs/Day3.txt");
    }

    public static PVector stringToPoint(String string){
        if (string.charAt(0) == 'R'){
            return new PVector(Integer.parseInt(string.substring(1)), 0);
        } else if(string.charAt(0) == 'L'){
            return new PVector(-Integer.parseInt(string.substring(1)), 0);
        } else if(string.charAt(0) == 'D'){
            return new PVector(0, -Integer.parseInt(string.substring(1)));
        } else if(string.charAt(0) == 'U'){
            return new PVector( 0,Integer.parseInt(string.substring(1)));
        }

        return null;
    }
}
