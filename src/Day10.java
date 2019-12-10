import java.util.*;
import java.util.stream.Collectors;

public class Day10 {

    public static void day10a() throws Exception {

        char[][] asteroidMap = setUp();
        int y = 0;
        int x = 0;
        int max = 0;
        HashSet<Double> maxGradients = new HashSet<>();

        for (int i = 0; i < asteroidMap.length; i++) {
            for (int j = 0; j < asteroidMap[0].length; j++) {
                HashSet<Double> gradients = new HashSet<>();
                if (asteroidMap[i][j] == '.') {
                    continue;
                } else {
                    for (int k = 0; k < asteroidMap.length; k++) {
                        for (int l = 0; l < asteroidMap[0].length; l++) {
                            double gradient = 0;
                            if (asteroidMap[k][l] == '.' || (i == k && j == l)) {
                                continue;
                            } else {
                                gradient = Math.atan2((double) (k - i), (double) (l - j)) * (180 / Math.PI);
                            }
                            gradients.add(gradient);
                        }
                    }
                }
                if (gradients.size() > max) {
                    max = gradients.size();
                    maxGradients = gradients;
                    y = i;
                    x = j;

                }
            }
        }
        List<Double> sorted = new ArrayList<>(maxGradients);
        Collections.sort(sorted);
        System.out.println(max);
        System.out.printf("(%d, %d)", y, x);
        System.out.println();
        System.out.println(sorted);
        System.out.println(sorted.indexOf(90.0));
    }

    public static void day10b() throws Exception {
        char[][] asteroidMap = setUp();

        HashMap<Double, ArrayList<PVector>> asteroids = new HashMap<>();

        for (int k = 0; k < asteroidMap.length; k++) {
            for (int l = 0; l < asteroidMap[0].length; l++) {
                double gradient = 0;
                if (asteroidMap[k][l] == '.' || (19 == k && 27 == l)) {
                    continue;
                } else {
                    gradient = Math.atan2((k - 19), (l - 27)) * (180 / Math.PI);
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

        while (count < 200){

            PVector min;
            min = Collections.min(asteroids.get(keySet.get(index)), (t0, t1) -> {
                Integer a = Math.abs(t0.getY() - 19) + Math.abs(t0.getX() - 27);
                Integer b = Math.abs(t1.getY() - 19) + Math.abs(t1.getX() - 27);
                return a.compareTo(b);
            });
            if (count == 199){
                System.out.println(min.getX() * 100 + min.getY());
            }
            index = (index + 1 ) % keySet.size();
            count++;
        }
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
