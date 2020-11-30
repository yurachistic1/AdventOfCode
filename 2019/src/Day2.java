public class Day2 {

    public static void main(String[] args) throws Exception{
        day2a();
        day2b();
    }
    public static void day2a() throws Exception {
        IntCodeComputer intCodeComputer =  new IntCodeComputer("./inputs/Day2.txt", 0);
        intCodeComputer.changeProgram(1, 12);
        intCodeComputer.changeProgram(2,2);
        intCodeComputer.executeProgram();

        System.out.println(intCodeComputer.getMem()[0]);

    }

    public static void day2b() throws Exception{
        IntCodeComputer intCodeComputer = new IntCodeComputer("./inputs/Day2.txt", 0);
        for(int k = 0; k < 100; k++){
            for (int j = 0; j < 100; j++){
                intCodeComputer.refreshProgram();
                intCodeComputer.changeProgram(1, k);
                intCodeComputer.changeProgram(2, j);
                intCodeComputer.executeProgram();

                if (intCodeComputer.getMem()[0] == 19690720){
                    System.out.println(100 * k + j);
                }
            }
        }
    }


}
