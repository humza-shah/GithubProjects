package javaproject.Control;

import javax.swing.*;

/**
    Main class that runs the game
*/
class Main {
    public static void main(String[] args) {
        //Initial frame created
        JFrame window = new JFrame("Knight Game!");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Board board = new Board();
        window.add(board);
        window.addKeyListener(board);
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}