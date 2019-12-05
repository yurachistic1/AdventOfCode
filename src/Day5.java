import java.util.ArrayList;
import java.util.Arrays;

public class Day5 {

    public static void Day5a() throws Exception {
        ArrayList<String> changes = new ArrayList<>(Arrays.asList(UtilityFunctions.convertInputToString(
                "./inputs/Day5.txt").split(",")));

        //ArrayList<Integer> opcodes = new ArrayList<>();
        int pos = 0;
        int program[] = new int[changes.size()];
        for (String opcode : changes) {
            //opcodes.add(Integer.parseInt(opcode));
            program[pos] = Integer.parseInt(opcode);
            pos++;
        }

        int input = 5;
        int output = 0;
        int instLen = 0;

        for (int i = 0; i < program.length; i = instLen) {
            if (program[i] == 99){
                break;
            }
            int instruction[] = decodeInstruction(program[i]);
            if (instruction.length == 1){
                instLen = i + 2;
                if (instruction[0] == 3){
                    program[program[i + 1]] = input;
                } else if (instruction[0] == 4){
                    output = program[program[i + 1]];
                    System.out.println(output);
                }
            } else {
                instLen = i + 4;
                if (instruction[0] == 1){
                    int par1 = instruction[1] == 0 ? program[program[i + 1]] : program[i + 1];
                    int par2 = instruction[2] == 0 ? program[program[i + 2]] : program[i + 2];
                    int par3 = program[i + 3];
                    program[par3] = par1 + par2;
                } else if (instruction[0] == 2){
                    int par1 = instruction[1] == 0 ? program[program[i + 1]] : program[i + 1];
                    int par2 = instruction[2] == 0 ? program[program[i + 2]] : program[i + 2];
                    int par3 = program[i + 3];
                    program[par3] = par1 * par2;
                } else if ( instruction[0] == 5){
                    int par1 = instruction[1] == 0 ? program[program[i + 1]] : program[i + 1];
                    int par2 = instruction[2] == 0 ? program[program[i + 2]] : program[i + 2];
                    if(par1 != 0){
                        instLen = par2;
                    } else {
                        instLen = i + 3;
                    }
                } else if (instruction[0] == 6){
                    int par1 = instruction[1] == 0 ? program[program[i + 1]] : program[i + 1];
                    int par2 = instruction[2] == 0 ? program[program[i + 2]] : program[i + 2];
                    if(par1 == 0){
                        instLen = par2;
                    } else {
                        instLen = i + 3;
                    }
                } else if (instruction[0] == 7){
                    int par1 = instruction[1] == 0 ? program[program[i + 1]] : program[i + 1];
                    int par2 = instruction[2] == 0 ? program[program[i + 2]] : program[i + 2];
                    int par3 = program[i + 3];
                    program[par3] = par1 < par2 ? 1 : 0;
                } else if (instruction[0] == 8){
                    int par1 = instruction[1] == 0 ? program[program[i + 1]] : program[i + 1];
                    int par2 = instruction[2] == 0 ? program[program[i + 2]] : program[i + 2];
                    int par3 = program[i + 3];
                    program[par3] = par1 == par2 ? 1 : 0;
                }
            }
        }
    }

    public static void Day5b() throws Exception {
        ArrayList<String> changes = UtilityFunctions.convertInputToArrayListString(
                "./inputs/Day5.txt");
    }

    public static int[] decodeInstruction(int instruction) {
        if (instruction == 3 || instruction == 4) {
            return new int[]{instruction};
        } else {
            int decodedInstruction[] = new int[4];
            int insLen = 0;
            while (instruction > 0) {
                if (insLen == 1) {
                    instruction = instruction / 10;
                }
                decodedInstruction[insLen] = instruction % 10;
                instruction = instruction / 10;
                insLen++;
            }
            for (int i = insLen; i < 4; i++) {
                decodedInstruction[i] = 0;
            }
            return decodedInstruction;
        }
    }
}

