import javax.swing.*;
import java.awt.*;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;

public class Day11 {

    public static void main(String[] args) {

        IntCodeComputer intCodeComputer = new IntCodeComputer("./inputs/Day11.txt", 0);
        intCodeComputer.setExecutingLazily(true);
        intCodeComputer.setAllowToRetrieveIndividualOutputs(true);

        HashMap<PVector, Integer> theHull = new HashMap<>();

        paintTheHull(intCodeComputer, theHull);

        System.out.printf("Part one: %d", theHull.size());

        theHull = new HashMap<>();
        intCodeComputer.refreshProgram();
        theHull.put(new PVector(0, 0), 1);

        paintTheHull(intCodeComputer, theHull);

        JFrame jFrame = new JFrame();
        TileForDay11 tiles = new TileForDay11(theHull);
        jFrame.getContentPane().add(tiles);
        jFrame.setResizable(false);
        jFrame.setSize(800, 200);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    public static void paintTheHull(IntCodeComputer intCodeComputer, HashMap<PVector, Integer> theHull){
        PVector currentLocation = new PVector(0, 0);
        PVector direction = new PVector(0, 1);

        double left = 90;
        double right = -90;

        int black = 0;

        int turnLeft = 0;

        int output = 0;
        int input = 0;

        while(!intCodeComputer.isTerminated()){

            input = theHull.getOrDefault(currentLocation, black);

            intCodeComputer.setInput(input);

            output = (int)intCodeComputer.getOutput();

            theHull.put(new PVector(currentLocation.getX(), currentLocation.getY()), output);

            intCodeComputer.executeProgram();
            output = (int)intCodeComputer.getOutput();

            if (output == turnLeft){
                direction.rotate(left);
            } else {
                direction.rotate(right);
            }
            currentLocation.add(direction);
        }
    }
}

class TileForDay11 extends JPanel {

    HashMap<PVector, Integer> coords;

    public TileForDay11(HashMap<PVector, Integer> coords){
        this.coords = coords;

        Dimension dimension = new Dimension(800, 120);

        setPreferredSize(dimension);

        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (PVector pVector : coords.keySet()){
            if (coords.get(pVector) == 0){
                g.setColor(Color.BLACK);
            } else {
                g.setColor(Color.WHITE);
            }
            g.fillRect((pVector.getX() - 1) * 20, Math.abs(pVector.getY()) * 20, 20, 20);
        }
    }
}
