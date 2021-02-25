import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * The main frame of the GoBang
 */

public class StartPanel extends JFrame {
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem startItem;
    private JMenuItem backItem;
    private JMenuItem quitItem;

    private Board board;

    // Constructor
    public StartPanel() {
        // Initialize elements
        menuBar = new JMenuBar();
        menu = new JMenu("System");
        startItem = new JMenuItem("Restart");
        backItem = new JMenuItem("Back Step");
        quitItem = new JMenuItem("Quit");
        board = new Board();

        // Set and add elements
        setTitle("Gomoku");
        Container container = getContentPane();
        container.add(board);
        board.setOpaque(true);
        menu.add(startItem);
        menu.add(backItem);
        menu.add(quitItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        add(board);
        initializeItemsInteraction();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }


    // Initialize items in the menu
    private void initializeItemsInteraction(){
        // Initialize interaction of restart button
        startItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("Restart");
                board.restart();
            }
        });

        // Initialize interaction of quit button
        quitItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Initialize interaction of back step button
        backItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("Go back on step");
                board.backStep();
            }
        });

    }



}

