package utility;

import main.GameEngine;

import javax.swing.*;
import java.awt.*;

public class Windows {
    private JFrame frame;
    private Dimension size;

    public Windows(int width, int height, String title, GameEngine game) {
        size = new Dimension(width, height);
        frame = new JFrame(title);

        frame.setPreferredSize(size);
        frame.setMaximumSize(size);
        frame.setMinimumSize(size);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);

    }
}
