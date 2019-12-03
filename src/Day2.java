import java.util.ArrayList;
import java.util.Arrays;

public class Day2 {
    public static void Day2a() throws Exception {
        ArrayList<String> changes = new ArrayList<>(Arrays.asList(UtilityFunctions.convertInputToString(
                "./inputs/Day2.txt").split(",")));

        ArrayList<Integer> opcodes = new ArrayList<>();
        for(String opcode : changes){
            opcodes.add(Integer.parseInt(opcode));
        }

        opcodes.set(1, 12);
        opcodes.set(2, 2);

        for (int i = 0; i < opcodes.size(); i += 4){
            if (opcodes.get(i) == 1){
                opcodes.set(opcodes.get(i + 3), opcodes.get(opcodes.get(i + 1)) + opcodes.get(opcodes.get(i + 2)));
            } else if (opcodes.get(i) == 2){
                opcodes.set(opcodes.get(i + 3), opcodes.get(opcodes.get(i + 1)) * opcodes.get(opcodes.get(i + 2)));
            } else if (opcodes.get(i) == 99){
                break;
            }
        }

        System.out.println(opcodes.get(0));

    }

    public static void Day2b() throws Exception{
        ArrayList<String> changes = new ArrayList<>(Arrays.asList(UtilityFunctions.convertInputToString(
                "./inputs/Day2.txt").split(",")));

        ArrayList<Integer> opcodes = new ArrayList<>();
        for(String opcode : changes){
            opcodes.add(Integer.parseInt(opcode));
        }

        for(int k = 0; k < 100; k++){
            for (int j = 0; j < 100; j++){
                ArrayList<Integer> opcodesCopy = new ArrayList<>();
                for(Integer opcode : opcodes){
                    opcodesCopy.add(opcode);
                }
                opcodesCopy.set(1, k);
                opcodesCopy.set(2, j);

                for (int i = 0; i < opcodesCopy.size(); i += 4){
                    if (opcodesCopy.get(i) == 1){
                        opcodesCopy.set(opcodesCopy.get(i + 3), opcodesCopy.get(opcodesCopy.get(i + 1)) + opcodesCopy.get(opcodesCopy.get(i + 2)));
                    } else if (opcodesCopy.get(i) == 2){
                        opcodesCopy.set(opcodesCopy.get(i + 3), opcodesCopy.get(opcodesCopy.get(i + 1)) * opcodesCopy.get(opcodesCopy.get(i + 2)));
                    } else if (opcodesCopy.get(i) == 99){
                        break;
                    }
                }

                if (opcodesCopy.get(0) == 19690720){
                    System.out.println(100 * k + j);
                }
            }
        }

        //code goes here

    }


}
