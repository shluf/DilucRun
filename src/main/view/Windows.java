package main.view;

import main.GameEngine;

import javax.swing.*;
import java.awt.*;

public class Windows {
    private JFrame frame;
    private Dimension size;

    public Windows(int width, int height, String title, GameEngine game) {
        size = new Dimension(width, height);
        frame = new JFrame(title);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(size);
        frame.setMaximumSize(size);
        frame.setMinimumSize(size);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.pack();

        frame.add(game);

        frame.requestFocusInWindow();
        frame.setVisible(true);

    }
}
