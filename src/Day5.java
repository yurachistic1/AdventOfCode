import java.util.ArrayList;
import java.util.Arrays;

public class Day5 {

    public static void Day5a() throws Exception {
        IntCodeComputer intCodeComputer = new IntCodeComputer("./inputs/Day5.txt", 1);
        intCodeComputer.executeProgram();
    }

    public static void Day5b() throws Exception {
        IntCodeComputer intCodeComputer = new IntCodeComputer("./inputs/Day5.txt", 5);
        intCodeComputer.executeProgram();
    }
}

