public class Day13 {

    public static void main(String[] args) {
        IntCodeComputer intCodeComputer = new IntCodeComputer("./inputs/Day13.txt", 0);
        intCodeComputer.addOutputStore();
        intCodeComputer.executeProgram();
        int countBlock = 0;

        for (PVector coord : intCodeComputer.outputStore.getStore().keySet()) {
            if (intCodeComputer.outputStore.getStore().get(coord) == 2) {
                countBlock++;
            }
        }

        System.out.printf("Part one: %d\n", countBlock);

        intCodeComputer.refreshProgram();
        intCodeComputer.setExecutingLazily(true);
        intCodeComputer.changeProgram(0, 2);
        intCodeComputer.executeProgram();

        long input = 0;
        PVector coordsBall = null;
        PVector coordsBoard = null;

        while (!intCodeComputer.isTerminated()) {

            for (PVector coord : intCodeComputer.outputStore.getStore().keySet()) {
                if (intCodeComputer.outputStore.getStore().get(coord) == 3) {
                    coordsBoard = coord;
                }

                if (intCodeComputer.outputStore.getStore().get(coord) == 4) {
                    coordsBall = coord;
                }
            }

            if (coordsBall.getX() < coordsBoard.getX()) {
                input = -1;
            } else if (coordsBall.getX() > coordsBoard.getX()) {
                input = 1;
            } else {
                input = 0;
            }

            intCodeComputer.setInput(input);
        }

        System.out.printf("Part two: %d\n", intCodeComputer.outputStore.getStore().get(new PVector(-1, 0)));
    }
}

