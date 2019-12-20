public class Day9 {

    public static void main(String[] args){
        IntCodeComputer intCodeComputer = new IntCodeComputer("./inputs/Day9.txt", 1);
        intCodeComputer.executeProgram();

        System.out.printf("Part one: %d\n", intCodeComputer.getOutput());

        intCodeComputer.refreshProgram();
        intCodeComputer.setInput(2);
        intCodeComputer.executeProgram();

        System.out.printf("Part two: %d\n", intCodeComputer.getOutput());
    }
}
