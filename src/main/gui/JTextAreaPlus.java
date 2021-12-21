package main.gui;

import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import java.awt.*;

public class JTextAreaPlus extends JTextArea {
    Image image;
    int x, y, width, height;

    public JTextAreaPlus() {
        super();
    }

    public JTextAreaPlus(String text) {
        super(text);
    }

    public void setImage(ImageIcon icon, int x, int y, int width, int height) {
        this.image = icon.getImage();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setOpaque(false);
        repaint();
    }

    public void paint(Graphics g) {
        g.drawImage(image, x, y, width, height,null);
        super.paint(g);
    }
}