public class Day9 {

    public static void day9a() throws Exception{
        IntCodeComputer intCodeComputer = new IntCodeComputer("./inputs/Day9.txt", 1);
        intCodeComputer.executeProgram();
    }

    public static void day9b(){
        IntCodeComputer intCodeComputer = new IntCodeComputer( "./inputs/Day9.txt", 2);
        intCodeComputer.executeProgram();
    }
}
