import java.util.ArrayList;

public class Day4 {

    public static void day4a() throws Exception{

        int count = 0;

        for(int i = 372037; i < 905157; i++){
            if (checkIfPass(i)){
                count++;
            }

        }

        System.out.println(count);
    }

    public static void day4b() throws Exception {
        //System.out.println(checkIfPass(122345));

        int count = 0;

        for (int i = 372037; i < 905157; i++) {
            if (checkIfPass2(i)) {
                count++;
            }
        }

        System.out.println(count);
    }

    public static boolean checkIfPass(int i){
        boolean twoTogether = false;
        boolean increase = true;
        int pass = i;
        int previousDigit = pass % 10;
        pass /= 10;
        int currentDigit = pass % 10;
        pass /= 10;

        while (pass > 0){
            int nextDigit = pass % 10;
            pass /= 10;
            if (nextDigit == currentDigit ^ currentDigit == previousDigit){
                twoTogether = true;
            }
            if (nextDigit > currentDigit || currentDigit > previousDigit){
                increase = false;
            }
            previousDigit = currentDigit;
            currentDigit = nextDigit;
        }
        if (twoTogether && increase){
            return true;
        }

        return false;
    }

    public static boolean checkIfPass2(int i){
        boolean increase = true;
        boolean twoTog = false;
        int pass = i;
        int previous = pass % 10;
        int seqLen = 1;
        pass /= 10;
        while(pass > 0){
            int digit = pass % 10;
            pass /= 10;

            if (digit > previous){
               increase = false;
            }

            if(digit == previous){
                seqLen++;
            } else {
                if (seqLen == 2){
                    twoTog = true;
                }
                seqLen = 1;
            }
            previous = digit;
        }
        if (seqLen == 2){
            twoTog = true;
        }

        if (twoTog && increase){
            return true;
        }
        return false;
    }
}
