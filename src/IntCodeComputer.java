import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class IntCodeComputer {

    int[] program;
    int[] ogCopy;
    int pointer;
    int output = 0;
    int input;
    int phaseSetting;
    boolean usePhaseToInput;
    boolean interruptAfterOutput;
    boolean terminated;

    public void setInput(int input) {
        this.input = input;
    }

    public void setInterruptAfterOutput(boolean mode){ this.interruptAfterOutput = mode; }

    public boolean isTerminated(){ return terminated; }

    public int getOutput(){ return output; }

    public void setPhaseSetting(int phaseSetting){
        this.phaseSetting = phaseSetting;
        usePhaseToInput = true;
    }

    public int[] getProgram() {
        return program;
    }

    public void changeProgram(int memAddr, int value){
        program[memAddr] = value;
    }

    public void refreshProgram(){
        program = Arrays.copyOf(ogCopy, program.length);
        pointer = 0;
        terminated = false;
    }

    public IntCodeComputer(String source, int input) {
        try {
            program = UtilityFunctions.convertInputToIntArray(source);
            ogCopy = UtilityFunctions.convertInputToIntArray(source);
            pointer = 0;
            this.input = input;
        } catch (Exception ignored) {}
    }

    public void executeProgram(){
        for (; pointer < program.length;){
            if(!executeInstruction(decodeInstruction(program[pointer]))){
                break;
            }
        }
    }

    public boolean executeInstruction(@NotNull int[] decodedInstruction){
        int opcode = decodedInstruction[0];
        if (opcode == 99){
            terminated = true;
            return false;
        }

        int par1MemAddr = (decodedInstruction[1] == 0 && pointer + 1 < program.length) ? program[pointer + 1] : pointer + 1;
        int par2MemAddr = (decodedInstruction[2] == 0 && pointer + 2 < program.length) ? program[pointer + 2] : pointer + 2;
        int writeAddr = (pointer + 3 < program.length) ? program[pointer + 3] : 0;

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
                program[par1MemAddr] = usePhaseToInput ? phaseSetting : input;
                if (usePhaseToInput){usePhaseToInput = false;}
                pointer += 2;
                break;
            case 4:
                output = program[par1MemAddr];
                pointer += 2;
                if(interruptAfterOutput) { return false; }
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
