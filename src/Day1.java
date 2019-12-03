import java.util.ArrayList;

public class Day1 {

    public static void Day1a() throws Exception {
        ArrayList<String> changes = UtilityFunctions.convertInputToArrayListString(
                "./inputs/Day1.txt");
        int sum = 0;

        for(int i = 0; i < changes.size(); i++){
            sum = sum + Integer.parseInt(changes.get(i))/3 - 2;
        }

        System.out.println(sum);
    }

    public static void Day1b() throws Exception{
        ArrayList<String> changes = UtilityFunctions.convertInputToArrayListString(
                "./inputs/Day1.txt");
        int test = 1969;
        int sum = 0;
        while(test > 5){
            System.out.println(test);
            test = test/3 - 2;
            sum += test;
        }
        System.out.println(sum);

        int bigSum = 0;
        int smallSum;
        int currentMod;

        for(String module : changes){
            currentMod = Integer.parseInt(module);
            smallSum = 0;
            while (currentMod > 5){
                currentMod = currentMod/3 - 2;
                smallSum += currentMod;
            }
            System.out.println(smallSum);
            bigSum += smallSum;
        }

        System.out.println(bigSum);

    }

}
