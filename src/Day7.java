import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day7 {

    public static void main(String[] args) {
        day7(5, 5);
    }

    public static void day7(int noOfPhases, int noOfThrusters) {

        ArrayList<int[]> permutations = generatePermutations(noOfPhases);

        ArrayList<IntCodeComputer> thrusters = (ArrayList<IntCodeComputer>) IntStream
                .range(0, noOfThrusters)
                .mapToObj(i -> new IntCodeComputer("./inputs/Day7.txt", 0))
                .collect(Collectors.toList());

        long max = 0;

        for (int[] permutation : permutations) {
            long output = 0;
            thrusters.forEach(IntCodeComputer::refreshProgram);
            for (int i = 0; i < permutation.length; i++) {
                thrusters.get(i).setInput(output);
                thrusters.get(i).setPhaseSetting(permutation[i]);
                thrusters.get(i).executeProgram();
                output = thrusters.get(i).getOutput();
            }
            max = Math.max(output, max);
        }

        thrusters.forEach(intCodeComputer -> intCodeComputer.setExecutingLazily(true));
        long maxWithFeedbackLoop = 0;

        for (int[] permutation : permutations) {
            long output = 0;
            thrusters.forEach(IntCodeComputer::refreshProgram);
            IntStream.range(0,5).forEach(i -> thrusters.get(i).setPhaseSetting(permutation[i] + 5)); //instead of generating new set of permutations just add five to each element
            while (!thrusters.get(thrusters.size() - 1).isTerminated()) {
                for (int i = 0; i < thrusters.size(); i++) {
                    thrusters.get(i).setInput(output);
                    output = thrusters.get(i).getOutput();
                }
            }
            maxWithFeedbackLoop = Math.max(output, maxWithFeedbackLoop);
        }

        System.out.printf("Part one: %d\n", max);
        System.out.printf("Part two: %d\n", maxWithFeedbackLoop);
    }

    public static ArrayList<int[]> generatePermutations(int noOfPhases) {
        ArrayList<int[]> permutations = new ArrayList<>();
        int[] members = IntStream.range(0, noOfPhases).toArray();
        heapPermutation(permutations, members, noOfPhases);
        return permutations;
    }

    public static void heapPermutation(ArrayList<int[]> permutations, int a[], int size) {
        if (size == 1)
            permutations.add(Arrays.copyOf(a, a.length));

        for (int i = 0; i < size; i++) {
            heapPermutation(permutations, a, size - 1);

            if (size % 2 == 1) {
                int temp = a[0];
                a[0] = a[size - 1];
                a[size - 1] = temp;
            } else {
                int temp = a[i];
                a[i] = a[size - 1];
                a[size - 1] = temp;
            }
        }
    }
}
