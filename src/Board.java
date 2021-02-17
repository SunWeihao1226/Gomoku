import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

/*
 * GoBang board class
 */
public class Board extends JPanel implements MouseListener {
    List<Piece> pieceList;
    int width, height; //Width and height of the board.
    int numPiece; // Number of pieces on the board.
    int pieceX, pieceY; // x and y coordinate of the piece
    boolean isOver; // If the game is over
    boolean isBlackFirst; // If black starts first
    Color currColor; // Color of the piece

    public static final int ROWS = 20; // Number of rows
    public static final int COLS = 20; // NUmber of columns
    public static final int SPACE = 35; // Space between the grids
    public static final int MARGIN = 30;

    Image image;

    // Constructor
    public Board() {
        pieceList = new ArrayList<>();
        image = Toolkit.getDefaultToolkit().getImage("./data/board.jpg");
        width = image.getWidth(this);
        height = image.getHeight(this);
        isOver = false;
        isBlackFirst = false;

        addMouseListener(this);
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // Transform the mouse coordinate to the coordinate on the grid
                int x = (e.getX() - MARGIN + SPACE / 2) / SPACE;
                int y = (e.getY() - MARGIN + SPACE / 2) / SPACE;
                // Check where the piece can be put on a position on the board, set the cursor
                if (x < 0 || y < 0 || x > ROWS || y > COLS || isOver || piecePos(x,y)) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                } else {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }
        });
    }

    // Find a piece on (x,y) position
    private boolean piecePos(int x, int y) {
        return false;
    }

    // Paint the board
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Get the width and height of the window
        int windowWidth = getWidth();
        int windowHeight = getHeight();
        int x = (windowWidth - width) / 2;
        int y = (windowHeight - height) / 2;
        g.drawImage(image, x, y, null);
        drawLines(g);
        drawPieces(g);
    }


    // Draw rows and column lines of the board
    private void drawLines(Graphics g) {
        // Draw horizontal lines
        for (int i = 0; i < ROWS; i++) {
            g.drawLine(MARGIN, MARGIN + i*SPACE, MARGIN + COLS*SPACE, MARGIN + i*SPACE);
        }
        //Draw vertical lines
        for (int j = 0; j < COLS; j++) {
            g.drawLine(MARGIN + j*SPACE, MARGIN,MARGIN + j*SPACE, MARGIN + ROWS*SPACE);
        }
    }

    // Draw pieces on the board
    private void drawPieces(Graphics g) {
        int xPos, yPos;
        for (int i = 0; i < numPiece; i++) {
            xPos = pieceList.get(i).getXCord() * SPACE + MARGIN;
            xPos = pieceList.get(i).getYCord() * SPACE + MARGIN;
            g.setColor(pieceList.get(i).getColor());
            currColor = pieceList.get(i).getColor();
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
