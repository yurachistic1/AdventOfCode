import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class IntCodeComputer {

    long[] program;
    long[] ogCopy;
    long[] memory;

    long pointer;
    long relativeBase;
    long resolvedMemAddress;

    long output = 0;
    long input;
    int phaseSetting;

    boolean usePhaseToInput;
    boolean interruptAfterOutput;
    boolean terminated;

    public void setInput(long input) {
        this.input = input;
    }

    public void setInterruptAfterOutput(boolean mode){ this.interruptAfterOutput = mode; }

    public boolean isTerminated(){ return terminated; }

    public long getOutput(){ return output; }

    public void setPhaseSetting(int phaseSetting){
        this.phaseSetting = phaseSetting;
        usePhaseToInput = true;
    }

    public long[] getProgram() {
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

    public IntCodeComputer(String source, long input) {
        try {
            program = UtilityFunctions.convertInputToIntArray(source);
            ogCopy = UtilityFunctions.convertInputToIntArray(source);
            memory = new long[program.length * 100];

            pointer = 0;
            relativeBase = 0;
            resolvedMemAddress = 0;

            this.input = input;
        } catch (Exception ignored) {}
    }

    public void executeProgram(){
        for (; pointer < program.length;){
            if(!executeInstruction(decodeInstruction((program[(int)pointer])))){
                break;
            }
        }
    }

    public boolean executeInstruction(@NotNull long[] decodedInstruction){
        long opcode = decodedInstruction[0];
        if (opcode == 99){
            terminated = true;
            return false;
        }

        boolean par1MemAddrLoc = resolveMemoryAddress(decodedInstruction[1], 1);
        int par1MemAddr = (int)resolvedMemAddress;
        //int par1MemAddr = (int)((decodedInstruction[1] == 0 && pointer + 1 < program.length) ? program[(int)pointer + 1] : pointer + 1);

        boolean par2MemAddrLoc = resolveMemoryAddress(decodedInstruction[2], 2);
        int par2MemAddr = (int)resolvedMemAddress;
        //int par2MemAddr = (int)((decodedInstruction[2] == 0 && pointer + 2 < program.length) ? program[(int)pointer + 2] : pointer + 2);

        boolean writeAddrLoc = resolveMemoryAddress(decodedInstruction[3], 3);
        int writeAddr = (int)resolvedMemAddress;
        //int writeAddr = (pointer + 3 < program.length) ? (int)program[(int)pointer + 3] : 0;

        long par1MemAddrValue = par1MemAddrLoc ? memory[par1MemAddr] : program[par1MemAddr];
        long par2MemAddrValue = par2MemAddrLoc ? memory[par2MemAddr] : program[par2MemAddr];

        switch ((int)opcode){
            case 1:
                if (writeAddrLoc){
                    memory[writeAddr] = par1MemAddrValue + par2MemAddrValue;
                } else {
                    program[writeAddr] = par1MemAddrValue + par2MemAddrValue;
                }
                pointer += 4;
                break;
            case 2:
                if (writeAddrLoc){
                    memory[writeAddr] = par1MemAddrValue * par2MemAddrValue;
                } else {
                    program[writeAddr] = par1MemAddrValue * par2MemAddrValue;
                }
                pointer += 4;
                break;
            case 3:
                if(par1MemAddrLoc){
                    memory[par1MemAddr] = usePhaseToInput ? phaseSetting : input;
                } else {
                    program[par1MemAddr] = usePhaseToInput ? phaseSetting : input;
                }
                if (usePhaseToInput){usePhaseToInput = false;}
                pointer += 2;
                break;
            case 4:
                output = par1MemAddrLoc ? memory[par1MemAddr] : program[par1MemAddr];
                pointer += 2;
                if(interruptAfterOutput)  return false;
                else  System.out.println(output);
                break;
            case 5:
                pointer = par1MemAddrValue != 0 ? par2MemAddrValue : pointer + 3;
                break;
            case 6:
                pointer = par1MemAddrValue == 0 ? par2MemAddrValue : pointer + 3;
                break;
            case 7:
                if(writeAddrLoc){
                    memory[writeAddr] = par1MemAddrValue < par2MemAddrValue ? 1 : 0;
                } else {
                    program[writeAddr] = par1MemAddrValue < par2MemAddrValue ? 1 : 0;
                }
                pointer += 4;
                break;
            case 8:
                if(writeAddrLoc){
                    memory[writeAddr] = par1MemAddrValue == par2MemAddrValue ? 1 : 0;
                } else {
                    program[writeAddr] = par1MemAddrValue == par2MemAddrValue ? 1 : 0;
                }
                pointer += 4;
                break;
            case 9:
                relativeBase += par1MemAddrValue;
                pointer += 2;
                break;
            default:
                break;
        }
        return true;
    }

    public long[] decodeInstruction(long instruction) {
        long[] decodedInstruction = new long[4];
        int insLen = 0;
        while (instruction > 0) {
            decodedInstruction[insLen] = insLen == 0 ? instruction % 100 : instruction % 10;
            instruction = insLen == 0 ? instruction / 100 : instruction/10;
            insLen++;
        }

        return decodedInstruction;
    }

    public boolean resolveMemoryAddress(long mode, int parNo){
        long temp = 0;
        boolean mem = false;
        switch ((int)mode){
            case 0:
                if (pointer + parNo < program.length){
                    temp = program[(int)pointer + parNo];
                } else {
                    temp = memory[(int)pointer + parNo - program.length];
                }
                if (temp > program.length){ mem = true; }
                else mem = false;
                break;
            case 1:
                if(pointer + parNo < program.length){
                    temp = pointer + parNo;
                    mem = false;
                } else {
                    temp = pointer + parNo - program.length;
                    mem = true;
                }
                break;
            case 2:
                if (pointer + parNo < program.length){
                    temp = relativeBase + program[(int)pointer + parNo];
                } else {
                    temp = relativeBase + memory[(int)pointer + parNo];
                }
                if (temp > program.length){ mem = true; }
                else mem = false;
        }

        resolvedMemAddress = temp;
        return mem;
    }
}
