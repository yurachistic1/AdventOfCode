import javax.swing.*;
import java.awt.*;

public class Tile extends JPanel {

    int dimX = 30;
    int dimY = 30;
    char[][] image;

    public Tile(char[][] image){
        this.image = image;

        Dimension dimension = new Dimension(image[0].length * dimX, image.length * dimY);

        setPreferredSize(dimension);
    }

    public void updateImage(char[][] image){
        this.image = image;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        for (int i = 0; i < image.length; i++){
            for (int j = 0; j < image[0].length; j++){
                if (image[i][j] == '0'){
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }

                g.fillRect(j * dimY, i * dimX, dimX, dimY);
            }
        }
    }
}
