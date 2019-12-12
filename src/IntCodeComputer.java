import java.util.Arrays;

public class IntCodeComputer {

    long[] mem;
    long[] ogCopy;

    long pointer;
    long relativeBase;

    long output = 0;
    long input;
    int phaseSetting;

    boolean usePhaseToInput;
    boolean interruptAfterOutput;
    boolean terminated;

    public void setInput(long input) {
        this.input = input;
    }

    public void setInterruptAfterOutput(boolean mode) {
        this.interruptAfterOutput = mode;
    }

    public boolean isTerminated() {
        return terminated;
    }

    public long getOutput() {
        return output;
    }

    public void setPhaseSetting(int phaseSetting) {
        this.phaseSetting = phaseSetting;
        usePhaseToInput = true;
    }

    public long[] getMem() {
        return mem;
    }

    public void changeProgram(int memAddr, int value) {
        mem[memAddr] = value;
    }

    public void refreshProgram() {
        //mem = Arrays.copyOf(ogCopy, mem.length);
        System.arraycopy(ogCopy, 0, mem, 0, ogCopy.length);
        pointer = 0;
        terminated = false;
    }

    public IntCodeComputer(String source, long input) {
        try {
            //mem = UtilityFunctions.convertInputToIntArray(source);
            ogCopy = UtilityFunctions.convertInputToIntArray(source);
            mem = new long[ogCopy.length + ogCopy.length * 100];
            System.arraycopy(ogCopy, 0, mem, 0, ogCopy.length);

            pointer = 0;
            relativeBase = 0;

            this.input = input;
        } catch (Exception ignored) {
        }
    }

    public void executeProgram() {
        for (; pointer < mem.length; ) {
            if (!executeInstruction(decodeInstruction((mem[(int) pointer])))) {
                break;
            }
        }
    }

    public boolean executeInstruction(long[] decodedInstruction) {
        long opcode = decodedInstruction[0];
        if (opcode == 99) {
            terminated = true;
            return false;
        }

        int par1MemAddr = resolveMemoryAddress(decodedInstruction[1], 1);
        int par2MemAddr = resolveMemoryAddress(decodedInstruction[2], 2);
        int writeAddr = resolveMemoryAddress(decodedInstruction[3], 3);

        switch ((int) opcode) {
            case 1:
                mem[writeAddr] = mem[par1MemAddr] + mem[par2MemAddr];
                pointer += 4;
                break;
            case 2:
                mem[writeAddr] = mem[par1MemAddr] * mem[par2MemAddr];
                pointer += 4;
                break;
            case 3:
                mem[par1MemAddr] = usePhaseToInput ? phaseSetting : input;
                if (usePhaseToInput) usePhaseToInput = false;
                pointer += 2;
                break;
            case 4:
                output = mem[par1MemAddr];
                pointer += 2;
                //System.out.println(output);
                if (interruptAfterOutput) return false;
                break;
            case 5:
                pointer = mem[par1MemAddr] != 0 ? mem[par2MemAddr] : pointer + 3;
                break;
            case 6:
                pointer = mem[par1MemAddr] == 0 ? mem[par2MemAddr] : pointer + 3;
                break;
            case 7:
                mem[writeAddr] = mem[par1MemAddr] < mem[par2MemAddr] ? 1 : 0;
                pointer += 4;
                break;
            case 8:
                mem[writeAddr] = mem[par1MemAddr] == mem[par2MemAddr] ? 1 : 0;
                pointer += 4;
                break;
            case 9:
                relativeBase += mem[par1MemAddr];
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
            instruction = insLen == 0 ? instruction / 100 : instruction / 10;
            insLen++;
        }

        return decodedInstruction;
    }

    public int resolveMemoryAddress(long mode, int parNo) {
        long temp = 0;
        switch ((int) mode) {
            case 0:
                temp = this.mem[(int) pointer + parNo];
                break;
            case 1:
                temp = pointer + parNo;
                break;
            case 2:
                temp = relativeBase + this.mem[(int) pointer + parNo];
        }
        return (int) temp;
    }
}
