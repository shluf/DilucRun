package main.view;

import main.GameUI;

import javax.swing.*;
import java.awt.*;

public class Windows {
    private final JFrame frame;
    private final Dimension size;

    public Windows(int width, int height, String title, GameUI game) {
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

        game.requestFocusInWindow(); // Request focus for the game component

        frame.requestFocusInWindow();
        frame.setVisible(true);

    }
}
