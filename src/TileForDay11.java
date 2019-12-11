import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class TileForDay11 extends JPanel {

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
