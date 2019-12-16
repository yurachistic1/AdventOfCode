import java.util.HashMap;

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
    boolean waitingForInput;
    boolean executingLazily;

    OutputStore outputStore;

    public void setInput(long input) {
        this.input = input;
        if (executingLazily){
            waitingForInput = false;
            executeProgram();
        }
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

    public void setExecutingLazily(boolean executingLazily) {
        this.executingLazily = executingLazily;
    }

    public long[] getMem() {
        return mem;
    }

    public void changeProgram(int memAddr, int value) {
        mem[memAddr] = value;
    }

    public void refreshProgram() {
        System.arraycopy(ogCopy, 0, mem, 0, ogCopy.length);
        pointer = 0;
        input = 0;
        output = 0;
        terminated = false;
    }

    public void addOutputStore(){
        outputStore = new OutputStore();
    }

    public IntCodeComputer(String source, long input) {
        try {
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
                if (executingLazily && waitingForInput){return false;}
                mem[par1MemAddr] = usePhaseToInput ? phaseSetting : input;
                if (usePhaseToInput) usePhaseToInput = false;
                pointer += 2;
                waitingForInput = true;
                break;
            case 4:
                output = mem[par1MemAddr];
                pointer += 2;
                if (outputStore != null) outputStore.generateMatrix((int)output);
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

class OutputStore{

    public HashMap<PVector, Integer> store = new HashMap<>();
    private PVector coords = new PVector(0, 0);
    private int count = 0;

    public HashMap<PVector, Integer> getStore() {
        return store;
    }

    public void generateMatrix(int output){
        if (count % 3 == 0){
            coords.setX(output);
        } else if ( count % 3 == 1){
            coords.setY(output);
        } else{
            store.put(new PVector(coords), output);
        }
        count++;
    }
}

