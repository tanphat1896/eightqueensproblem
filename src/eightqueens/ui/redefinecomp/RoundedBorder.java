package eightqueens.ui.redefinecomp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.border.Border;

public class RoundedBorder implements Border {

    private int radius;
    private Color color;


    public RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }


    public boolean isBorderOpaque() {
        return true;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    	Graphics2D g2 = (Graphics2D)g;
    	g2.setColor(color);
        g2.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}