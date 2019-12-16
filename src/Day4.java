import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day4 {

    public static void main(String[] args) throws Exception{
        ArrayList<Integer> passwords = (ArrayList<Integer>) IntStream
                .range(372037, 905157)
                .filter(pass -> checkIfPass(pass))
                .boxed()
                .collect(Collectors.toList());

        long count = passwords
                .stream()
                .filter(pass -> additionalCheck(pass))
                .count();

        System.out.printf("Part 1: %d\n", passwords.size());
        System.out.printf("Part 2: %d\n", count);
    }

    public static boolean checkIfPass(int i){
        boolean twoTogether = false;
        boolean increase = true;
        int pass = i;
        int previousDigit = pass % 10;
        pass /= 10;


        while (pass > 0){
            int currentDigit = pass % 10;
            pass /= 10;
            if (currentDigit == previousDigit){
                twoTogether = true;
            }
            if (currentDigit > previousDigit){
                increase = false;
            }
            previousDigit = currentDigit;
        }
        if (twoTogether && increase){
            return true;
        }

        return false;
    }

    public static boolean additionalCheck(int i){
        int pass = i;
        int previous = pass % 10;
        int seqLen = 1;

        while(pass > 0){
            pass /= 10;
            int current = pass % 10;
            if(current == previous){
                seqLen++;
            } else {
                if (seqLen == 2){
                    return true;
                }
                seqLen = 1;
            }
            previous = current;
        }
        return false;
    }
}
