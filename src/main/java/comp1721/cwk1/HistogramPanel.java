package comp1721.cwk1;

import javax.swing.*;
import java.awt.*;

public class HistogramPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        int frameHeight=getHeight();
        int frameWidth=getWidth();
        String font="Arial";
        int fontSize=20;
        int width=40;
        int height=40;
        g.setColor(Color.white);
        g.fillRect(0,0,frameWidth,frameHeight);
    }
}
