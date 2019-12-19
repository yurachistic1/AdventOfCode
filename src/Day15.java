import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Day15 {

    public static final HashMap<PVector, Integer> changeInPos = new HashMap<>() {{
        put(new PVector(0, 1), 1);
        put(new PVector(0, -1), 2);
        put(new PVector(-1, 0), 3);
        put(new PVector(1, 0), 4);
    }};

    public static final HashMap<Integer, PVector> inputs = new HashMap<>() {{
        put(1, new PVector(0, 1));
        put(2, new PVector(0, -1));
        put(3, new PVector(-1, 0));
        put(4, new PVector(1, 0));
    }};

    public static void main(String[] args) {
        IntCodeComputer intCodeComputer = new IntCodeComputer("./inputs/Day15.txt", 0);

        solution(intCodeComputer);
    }

    public static void solution(IntCodeComputer intCodeComputer) {
        intCodeComputer.setExecutingLazily(true);

        HashMap<PVector, Node> map = new HashMap<>();
        map.put(new PVector(0, 0), new Node(3, new PVector(0, 0), -1));

        PVector currentPosition = new PVector(0, 0);
        int count = 0;

        while (count != 5000000) {
            int input = ThreadLocalRandom.current().nextInt(1, 5);

            intCodeComputer.setInput(input);

            int output = (int) intCodeComputer.getOutput();

            currentPosition = updateState(output, input, map, currentPosition);
            count++;
        }

        JFrame jFrame = new JFrame();

        TileForDay15 tiles = new TileForDay15(map);
        jFrame.getContentPane().add(tiles);

        jFrame.setResizable(false);
        jFrame.setSize(800, 200);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);

        PVector oxygen = map.keySet()
                .stream()
                .filter(i -> map.get(i).status == 2)
                .findFirst()
                .get();

        System.out.printf("Part one: %d steps\n", map.get(oxygen).steps);

        map.keySet()
                .stream()
                .filter(i -> map.get(i).status != 0)
                .forEach(i -> map.get(i).setSteps(Integer.MAX_VALUE));
        map.get(oxygen).setSteps(0);
        map.get(oxygen).update();

        int timeToFill = map.keySet()
                .stream()
                .filter(i -> map.get(i).status != 0)
                .mapToInt(i -> map.get(i).steps)
                .max()
                .getAsInt();

        System.out.printf("Part two: %d minutes\n",timeToFill);


    }

    public static PVector updateState(int output, int input, HashMap<PVector, Node> map, PVector currentPos) {
        PVector positionLookedAt = new PVector(currentPos).add(inputs.get(input));
        if (!map.containsKey(positionLookedAt)) {
            map.put(positionLookedAt, new Node(output, positionLookedAt, map.get(currentPos).steps));
            map.get(currentPos).addNeighbour(map.get(positionLookedAt));
            map.get(positionLookedAt).addNeighbour(map.get(currentPos));
        }
        if (output != 0) {
            return positionLookedAt;
        } else {
            return currentPos;
        }

    }

}
class Node {

    ArrayList<Node> neighbours;
    int steps;
    final int status;
    PVector position;

    public void addNeighbour(Node neighbour) {
        neighbours.add(neighbour);
        update();
    }

    public void setSteps(int steps){
        this.steps = steps;
    }

    public Node(int status, PVector position, int stepsBase) {
        neighbours = new ArrayList<>();
        this.position = position;
        this.status = status;
        steps = stepsBase + 1;
    }

    public void update() {
        neighbours.forEach(this::updateNeighbour);
    }

    public void updateNeighbour(Node neighbour) {
        if (neighbour.status != 0 && status != 0) {
            long diff = neighbour.steps - steps;
            if (Math.abs(diff) == 1 || diff == 0) {
                return;
            } else if (diff < -1) {
                steps = neighbour.steps + 1;
                update();
            } else {
                neighbour.steps = steps + 1;
                neighbour.update();
            }
        }
    }
}

class TileForDay15 extends JPanel {

    HashMap<PVector, Node> tiles;

    public TileForDay15(HashMap<PVector, Node> coords){
        this.tiles = coords;

        Dimension dimension = new Dimension(800, 800);
        setBackground(Color.BLACK);

        setPreferredSize(dimension);

        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (PVector pVector : tiles.keySet()){
            if (tiles.get(pVector).status == 0){
                g.setColor(Color.GRAY);
            } else if(tiles.get(pVector).status == 1){
                g.setColor(Color.WHITE);
            } else if (tiles.get(pVector).status == 2){
                g.setColor(Color.BLUE);
            } else {
                g.setColor(Color.GREEN);
            }
            g.fillRect((pVector.getX() + 40) * 10, Math.abs(pVector.getY() - 40) * 10, 10, 10);
        }
    }
}



