import java.util.*;
import java.util.stream.Collectors;

public class Day10 {

    public static void main(String[] args) throws Exception{

        char[][] asteroidMap = setUp();

        PVector optimalPosition = findOptimalPosition(asteroidMap);

        giantLaser(asteroidMap, optimalPosition);
    }

    public static PVector findOptimalPosition(char[][] map){
        int y = 0;
        int x = 0;
        HashSet<Double> maxSeen = new HashSet<>();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                HashSet<Double> angles = new HashSet<>();
                if (map[i][j] == '.') {
                    continue;
                } else {
                    for (int k = 0; k < map.length; k++) {
                        for (int l = 0; l < map[0].length; l++) {
                            double angle = 0;
                            if (map[k][l] == '.' || (i == k && j == l)) {
                                continue;
                            } else {
                                angle = Math.atan2((double) (k - i), (double) (l - j)) * (180 / Math.PI);
                            }
                            angles.add(angle);
                        }
                    }
                }
                if (angles.size() > maxSeen.size()) {
                    maxSeen = angles;
                    y = i;
                    x = j;

                }
            }
        }
        System.out.printf("Part one: %d\n", maxSeen.size());
        return new PVector(y, x);
    }

    public static void giantLaser(char[][] asteroidMap, PVector optimalPosition){
        int x = optimalPosition.getX();
        int y = optimalPosition.getY();

        HashMap<Double, ArrayList<PVector>> asteroids = new HashMap<>();

        for (int k = 0; k < asteroidMap.length; k++) {
            for (int l = 0; l < asteroidMap[0].length; l++) {
                double gradient = 0;
                if (asteroidMap[k][l] == '.' || (x == k && y == l)) {
                    continue;
                } else {
                    gradient = Math.atan2((k - x), (l - y)) * (180 / Math.PI);
                }
                if (asteroids.containsKey(gradient)) {
                    asteroids.get(gradient).add(new PVector(l, k));
                } else {
                    ArrayList<PVector> temp = new ArrayList<>();
                    PVector coord = new PVector(l, k);
                    temp.add(coord);
                    asteroids.put(gradient, temp);
                }
            }
        }

        ArrayList<Double> keySet = new ArrayList<>(asteroids.keySet());
        Collections.sort(keySet);

        int count = 0;
        int index = keySet.indexOf(-90.0);
        PVector min = null;

        while (count < 200){
            if (asteroids.get(keySet.get(index)).size() != 0) {

                min = Collections.min(asteroids.get(keySet.get(index)), (t0, t1) -> {
                    Integer a = (int) (Math.abs(t0.getY() - x) + Math.abs(t0.getX() - y));
                    Integer b = (int) (Math.abs(t1.getY() - x) + Math.abs(t1.getX() - y));
                    return a.compareTo(b);
                });
                asteroids.get(keySet.get(index)).remove(min);
                index = (index + 1) % keySet.size();
                count++;
            } else {
                asteroids.remove(asteroids.get(index));
                keySet.remove(index);
            }
        }
        System.out.printf("Part two: %d\n", min.getX() * 100 + min.getY());
    }

    public static char[][] setUp() throws Exception {
        ArrayList<String> input = UtilityFunctions.convertInputToArrayListString("./inputs/Day10.txt");

        char[][] asteroidMap = new char[input.size()][input.get(0).length()];

        for (int i = 0; i < asteroidMap.length; i++) {
            asteroidMap[i] = input.get(i).toCharArray();
        }

        return asteroidMap;
    }


}
