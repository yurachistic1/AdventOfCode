public class Day7 {

    public static void day7a() throws Exception {

        IntCodeComputer intCodeComputer = new IntCodeComputer("./inputs/Day7.txt", 0);

        int currentMax = 0;

        for (int i = 0; i < 5; i++) {
            intCodeComputer.setInput(0);
            intCodeComputer.setPhaseSetting(i);
            intCodeComputer.executeProgram();
            intCodeComputer.refreshProgram();
            int output1 = intCodeComputer.getOutput();
            for (int j = 0; j < 5; j++) {
                if (j == i) {
                    continue;
                }
                intCodeComputer.setInput(output1);
                intCodeComputer.setPhaseSetting(j);
                intCodeComputer.executeProgram();
                intCodeComputer.refreshProgram();
                int output2 = intCodeComputer.getOutput();
                for (int k = 0; k < 5; k++) {
                    if (k == j || k == i) {
                        continue;
                    }
                    intCodeComputer.setInput(output2);
                    intCodeComputer.setPhaseSetting(k);
                    intCodeComputer.executeProgram();
                    intCodeComputer.refreshProgram();
                    int output3 = intCodeComputer.getOutput();
                    for (int l = 0; l < 5; l++) {
                        if (l == i || l == j || l == k) {
                            continue;
                        }
                        intCodeComputer.setInput(output3);
                        intCodeComputer.setPhaseSetting(l);
                        intCodeComputer.executeProgram();
                        intCodeComputer.refreshProgram();
                        int output4 = intCodeComputer.getOutput();
                        for (int m = 0; m < 5; m++) {
                            if (m == i || m == j || m == k || m == l) {
                                continue;
                            }
                            intCodeComputer.setInput(output4);
                            intCodeComputer.setPhaseSetting(m);
                            intCodeComputer.executeProgram();
                            intCodeComputer.refreshProgram();
                            if (intCodeComputer.getOutput() > currentMax) {
                                currentMax = intCodeComputer.getOutput();
                            }
                        }
                    }
                }
            }
        }
        System.out.println(currentMax);
    }

    public static void day7b() {

        IntCodeComputer thrusterA = new IntCodeComputer("./inputs/Day7.txt", 0);
        IntCodeComputer thrusterB = new IntCodeComputer("./inputs/Day7.txt", 0);
        IntCodeComputer thrusterC = new IntCodeComputer("./inputs/Day7.txt", 0);
        IntCodeComputer thrusterD = new IntCodeComputer("./inputs/Day7.txt", 0);
        IntCodeComputer thrusterE = new IntCodeComputer("./inputs/Day7.txt", 0);
//
//        thrusterA.setPhaseSetting(9);
//        thrusterB.setPhaseSetting(8);
//        thrusterC.setPhaseSetting(7);
//        thrusterD.setPhaseSetting(6);
//        thrusterE.setPhaseSetting(5);

        thrusterA.setInterruptAfterOutput(true);
        thrusterB.setInterruptAfterOutput(true);
        thrusterC.setInterruptAfterOutput(true);
        thrusterD.setInterruptAfterOutput(true);
        thrusterE.setInterruptAfterOutput(true);

        int max = 0;
//
//        while(!thrusterE.isTerminated()){
//            thrusterA.setInput(outputE);
//            thrusterA.executeProgram();
//            outputA = thrusterA.getOutput();
//
//            thrusterB.setInput(outputA);
//            thrusterB.executeProgram();
//            outputB = thrusterB.getOutput();
//
//            thrusterC.setInput(outputB);
//            thrusterC.executeProgram();
//            outputC = thrusterC.getOutput();
//
//            thrusterD.setInput(outputC);
//            thrusterD.executeProgram();
//            outputD = thrusterD.getOutput();
//
//            thrusterE.setInput(outputD);
//            thrusterE.executeProgram();
//            outputE = thrusterE.getOutput();
//            System.out.println(outputE);
//        }
//
//        System.out.println(outputE);
//

        for (int i = 5; i < 10; i++) {
            for (int j = 5; j < 10; j++) {
                if (j == i) {
                    continue;
                }
                for (int k = 5; k < 10; k++) {
                    if (k == j || k == i) {
                        continue;
                    }
                    for (int l = 5; l < 10; l++) {
                        if (l == i || l == j || l == k) {
                            continue;
                        }
                        for (int m = 5; m < 10; m++) {
                            if (m == i || m == j || m == k || m == l) {
                                continue;
                            }

                            //System.out.println(String.format("%d %d %d %d %d", i, j, k, l, m));
                            thrusterA.refreshProgram();
                            thrusterB.refreshProgram();
                            thrusterC.refreshProgram();
                            thrusterD.refreshProgram();
                            thrusterE.refreshProgram();


                            thrusterA.setPhaseSetting(i);
                            thrusterB.setPhaseSetting(j);
                            thrusterC.setPhaseSetting(k);
                            thrusterD.setPhaseSetting(l);
                            thrusterE.setPhaseSetting(m);

                            int outputA = 0;
                            int outputB = 0;
                            int outputC = 0;
                            int outputD = 0;
                            int outputE = 0;

                            while(!thrusterE.isTerminated()) {

                                thrusterA.setInput(outputE);
                                thrusterA.executeProgram();
                                outputA = thrusterA.getOutput();

                                thrusterB.setInput(outputA);
                                thrusterB.executeProgram();
                                outputB = thrusterB.getOutput();

                                thrusterC.setInput(outputB);
                                thrusterC.executeProgram();
                                outputC = thrusterC.getOutput();

                                thrusterD.setInput(outputC);
                                thrusterD.executeProgram();
                                outputD = thrusterD.getOutput();

                                thrusterE.setInput(outputD);
                                thrusterE.executeProgram();
                                outputE = thrusterE.getOutput();
                            }
                            //System.out.println(outputE);
                            if(outputE > max){
                                max = outputE;
                            }
                        }
                    }
                }
            }
        }
        System.out.println(max);
    }
}
