import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class IntCodeComputer {

    int[] program;
    int pointer;
    int output = 0;
    int input;

    public void setInput(int input) {
        this.input = input;
    }

    public int[] getProgram() {
        return program;
    }

    public void changeProgram(int memAddr, int value){
        program[memAddr] = value;
    }

    public void refreshProgram(String source){
        try {
            program = UtilityFunctions.convertInputToIntArray(source);
        } catch (Exception ignored){}
    }

    public IntCodeComputer(String source, int input) {
        try {
            program = UtilityFunctions.convertInputToIntArray(source);
            this.input = input;
        } catch (Exception ignored) {}
    }

    public void executeProgram(){
        for (pointer = 0; pointer < program.length;){
            if(!executeInstruction(decodeInstruction(program[pointer]))){
                break;
            }
        }
    }

    public boolean executeInstruction(@NotNull int[] decodedInstruction){
        int opcode = decodedInstruction[0];
        if (opcode == 99){ return false; }

        int par1MemAddr = decodedInstruction[1] == 0 ? program[pointer + 1] : pointer + 1;
        int par2MemAddr = decodedInstruction[2] == 0 ? program[pointer + 2] : pointer + 2;
        int writeAddr = program[pointer + 3];

        switch (opcode){
            case 1:
                program[writeAddr] = program[par1MemAddr] + program[par2MemAddr];
                pointer += 4;
                break;
            case 2:
                program[writeAddr] = program[par1MemAddr] * program[par2MemAddr];
                pointer += 4;
                break;
            case 3:
                program[par1MemAddr] = input;
                pointer += 2;
                break;
            case 4:
                output = program[par1MemAddr];
                System.out.println(output);
                pointer += 2;
                break;
            case 5:
                pointer = program[par1MemAddr] != 0 ? program[par2MemAddr] : pointer + 3;
                break;
            case 6:
                pointer = program[par1MemAddr] == 0 ? program[par2MemAddr] : pointer + 3;
                break;
            case 7:
                program[writeAddr] = program[par1MemAddr] < program[par2MemAddr] ? 1 : 0;
                pointer += 4;
                break;
            case 8:
                program[writeAddr] = program[par1MemAddr] == program[par2MemAddr] ? 1 : 0;
                pointer += 4;
                break;
            default:
                break;
        }
        return true;
    }

    public int[] decodeInstruction(int instruction) {
        int[] decodedInstruction = new int[4];
        int insLen = 0;
        while (instruction > 0) {
            decodedInstruction[insLen] = insLen == 0 ? instruction % 100 : instruction % 10;
            instruction = insLen == 0 ? instruction / 100 : instruction/10;
            insLen++;
        }

        return decodedInstruction;
    }
}
