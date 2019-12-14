public class Day13 {
    public static void day13a() {
        IntCodeComputer intCodeComputer = new IntCodeComputer("./inputs/Day13.txt", 0);
        intCodeComputer.addOutputStore();
        intCodeComputer.executeProgram();
        int countBlock = 0;

        for(PVector coord : intCodeComputer.outputStore.getStore().keySet()){
            if (intCodeComputer.outputStore.getStore().get(coord) == 2){
                countBlock++;
            }
        }

        System.out.println(countBlock);
    }
}
