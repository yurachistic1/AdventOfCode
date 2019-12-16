import javax.crypto.spec.PSource;
import java.util.*;
import java.util.stream.Collectors;

public class Day3 {

    public static final HashMap<String, Integer> changeX = new HashMap<>() {{
        put("U", 0);
        put("D", 0);
        put("L", -1);
        put("R", 1);
    }};

    public static final HashMap<String, Integer> changeY = new HashMap<>() {{
        put("U", 1);
        put("D", -1);
        put("L", 0);
        put("R", 0);
    }};

    public static void main(String[] args) throws Exception {
        day3();
    }

    public static void day3() throws Exception {

        ArrayList<String> input = UtilityFunctions.convertInputToArrayListString("./inputs/Day3.txt");
        ArrayList<String> wire1 = new ArrayList<>(Arrays.asList(input.get(0).split(",")));
        ArrayList<String> wire2 = new ArrayList<>(Arrays.asList(input.get(1).split(",")));

        HashMap<PVector, Integer> wire1Coords = traceWire(wire1);
        HashMap<PVector, Integer> wire2Coords = traceWire(wire2);
        ArrayList<PVector> intersection = new ArrayList<>();

        for (PVector coord : wire1Coords.keySet()){
            if (wire2Coords.containsKey(coord)){
                intersection.add(coord);
            }
        }
        PVector closest = intersection
                .stream()
                .min((vec1, vec2) -> Integer.compare(manhattanDistance(vec1), manhattanDistance(vec2)))
                .get();

        PVector leastSteps = intersection
                .stream()
                .min((vec1, vec2) -> Integer.compare(wire1Coords.get(vec1) + wire2Coords.get(vec1),
                                                     wire1Coords.get(vec2) + wire2Coords.get(vec2)))
                .get();

        System.out.printf("Part 1: %d\n", manhattanDistance(closest));
        System.out.printf("Part 2: %d\n", wire1Coords.get(leastSteps) + wire2Coords.get(leastSteps));
    }

    public static int manhattanDistance(PVector pVector) {
        return Math.abs(pVector.getX()) + Math.abs(pVector.getY());
    }

    public static HashMap<PVector, Integer> traceWire(ArrayList<String> wire){
        HashMap<PVector, Integer> wireCoords = new HashMap<>();
        PVector last = new PVector(0, 0);
        int x = 0;
        int y = 0;
        String dir;
        int limit;
        int count = 1;

        for (int i = 0; i < wire.size(); i++) {
            dir = wire.get(i).substring(0, 1);
            limit = Integer.parseInt(wire.get(i).substring(1));
            for (int j = 0; j < limit; j++) {
                x = last.getX() + changeX.get(dir);
                y = last.getY() + changeY.get(dir);
                PVector coord = new PVector(x, y);
                if (!wireCoords.containsKey(coord)) wireCoords.put(coord, count);
                last = coord;
                count++;
            }
        }
        return wireCoords;
    }

}
