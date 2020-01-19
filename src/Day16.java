import java.util.Arrays;
import java.util.stream.IntStream;

public class Day16 {

    public static void main(String[] args) throws Exception {

        String str = UtilityFunctions.convertInputToString("./inputs/Day16.txt");

        int[] target = new int[str.length()];

        char[] arr = str.toCharArray();

        for (int i = 0; i < arr.length; i++) {
            target[i] = Integer.parseInt(String.valueOf(arr[i]));
        }

        applyFFT(target);

        System.out.print("Part One: ");

        for (int i = 0; i < 8; i++){
            System.out.print(target[i]);
        }
//
//        for (int i = 0; i < arr.length; i++){
//            target[i] = Integer.parseInt(String.valueOf(arr[i]));
//        }
//
//        int[] array = new int[arr.length * 10000];
//
//        int index = 0;
//        for (int i = 0; i < 10000; i++){
//            System.out.println(i);
//            for (int j = 0; j < target.length; j++){
//                array[index] = target[j];
//            }
//        }
//
//        applyFFT(array);
//
//        System.out.println("Done");
    }

    public static int[] applyFFT(int[] target) {
        for (int m = 0; m < 100; m++) {
            for (int i = 0; i < target.length; i++) {
                int sum = 0;
                int[] pattern = createExtendedPattern(i, target.length);
                for (int j = 0; j < target.length; j++) {

                    int h = pattern[j];
                    sum += target[j] * h;
                }
                target[i] = Math.abs(sum) % 10;
            }
        }

        return target;
    }

    public static int[] createExtendedPattern(int pos, int len) {

        int[] basePattern = {0, 1, 0, -1};
        int[] extendedPattern = new int[len + 1];

        for (int i = 0; i < len + 1; i++) {
            extendedPattern[i] = basePattern[(i / (pos + 1)) % 4];
        }

        return Arrays.copyOfRange(extendedPattern, 1, extendedPattern.length);
    }
}
