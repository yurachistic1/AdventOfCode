import java.util.ArrayList;
import java.util.Arrays;

public class Day5 {

    public static void main(String[] args) throws Exception{
        IntCodeComputer intCodeComputer = new IntCodeComputer("./inputs/Day5.txt", 1);
        intCodeComputer.executeProgram();
        System.out.printf("Part 1: %d\n", intCodeComputer.getOutput());

        intCodeComputer.refreshProgram();
        intCodeComputer.setInput(5);
        intCodeComputer.executeProgram();
        System.out.printf("Part 2: %d\n", intCodeComputer.getOutput());
    }
}

