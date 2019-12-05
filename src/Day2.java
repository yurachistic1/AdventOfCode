import java.util.ArrayList;
import java.util.Arrays;

public class Day2 {
    public static void Day2a() throws Exception {
        IntCodeComputer intCodeComputer =  new IntCodeComputer("./inputs/Day2.txt", 0);
        intCodeComputer.changeProgram(1, 12);
        intCodeComputer.changeProgram(2,2);
        intCodeComputer.executeProgram();

        System.out.println(intCodeComputer.getProgram()[0]);

    }

    public static void Day2b() throws Exception{
        IntCodeComputer intCodeComputer = new IntCodeComputer("./inputs/Day2.txt", 0);
        for(int k = 0; k < 100; k++){
            for (int j = 0; j < 100; j++){
                intCodeComputer.refreshProgram("./inputs/Day2.txt");
                intCodeComputer.changeProgram(1, k);
                intCodeComputer.changeProgram(2, j);
                intCodeComputer.executeProgram();

                if (intCodeComputer.getProgram()[0] == 19690720){
                    System.out.println(100 * k + j);
                }
            }
        }

        //code goes here

    }


}
