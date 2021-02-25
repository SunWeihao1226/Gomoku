import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
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
        isBlackFirst = true;

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
                if (x < 0 || y < 0 || x > ROWS || y > COLS || isOver || pieceFound(x,y)) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                } else {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }
        });
    }

    // Find a piece on (x,y) position
    private boolean pieceFound(int x, int y) {
        for (Piece piece : pieceList) {
            if (piece.getXCord() == x && piece.getYCord() == y && piece != null) {
                return true;
            }
        }
        return false;
    }

    // Find and return a piece on (x,y) position with certain color
    private Piece getPiece(int x, int y, Color color) {
        for(Piece piece : pieceList) {
            if (piece != null && piece.getXCord() == x && piece.getYCord() == y && piece.getColor().equals(color)) {
                return piece;
            }
        }
        return null;
    }


    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        ImageIcon img = new ImageIcon("./data/board.jpg");
        setBounds(0, 0, 800, 800);
        img.paintIcon(this, graphics, 0, 0);
        drawPieces(graphics);
        drawLines(graphics);
    }


    // Draw rows and column lines of the board
    private void drawLines(Graphics g) {
        // Draw horizontal lines
        for (int i = 0; i < ROWS + 1; i++) {
            g.drawLine(MARGIN, MARGIN + i*SPACE, MARGIN + COLS*SPACE, MARGIN + i*SPACE);
        }
        //Draw vertical lines
        for (int j = 0; j < COLS + 1; j++) {
            g.drawLine(MARGIN + j*SPACE, MARGIN,MARGIN + j*SPACE, MARGIN + ROWS*SPACE);
        }
    }

    // Draw pieces on the board
    private void drawPieces(Graphics g) {
        int xPos, yPos;
        for (int i = 0; i < numPiece; i++) {
            xPos = pieceList.get(i).getXCord() * SPACE + MARGIN;
            yPos = pieceList.get(i).getYCord() * SPACE + MARGIN;
            g.setColor(pieceList.get(i).getColor());
            currColor = pieceList.get(i).getColor();
            int currX = xPos - Piece.DIM / 2;
            int currY = yPos - Piece.DIM / 2;

            if (currColor.equals(Color.white)) {
                // Draw white pieces
                RadialGradientPaint gradientPaint = new RadialGradientPaint(currX + 25, currY + 10,
                        50, new float[]{0f, 1f}, new Color[]{Color.WHITE, Color.BLACK});
                ((Graphics2D) g).setPaint(gradientPaint);
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
            } else if (currColor.equals(Color.black)) {
                //Draw black pieces
                RadialGradientPaint gradientPaint = new RadialGradientPaint(currX + 25, currY + 10,
                        20, new float[]{0f, 1f}, new Color[]{Color.WHITE, Color.BLACK});
                ((Graphics2D) g).setPaint(gradientPaint);
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
            }

            // Mark the last piece with a rectangular
            Ellipse2D ellipse = new Ellipse2D.Float(currX, currY, 34, 35);
            ((Graphics2D) g).fill(ellipse);

            //Check if it is the last piece
            if (i == numPiece - 1) {
//                g.setColor(Color.red);
                g.drawRect(currX, currY, 34, 35);
            }
        }
    }


    // When press the mouse on one component
    @Override
    public void mousePressed(MouseEvent e) {
        if (!isOver) {
            String color;
            if (isBlackFirst) {
                color = "Black";
            } else {
                color = "White";
            }

            // Check when the piece cannot be put. If can be put, then add the piece.
            pieceX = (e.getX() + SPACE / 2 - MARGIN) / SPACE;
            pieceY = (e.getY() + SPACE / 2 - MARGIN) / SPACE;
            if (pieceX < 0 || pieceY < 0 || pieceX > ROWS || pieceY > COLS) {
                return;
            }
            if (pieceFound(pieceX, pieceY)) {
                return;
            }
            Piece piece = new Piece(pieceX, pieceY, isBlackFirst ? Color.black : Color.white);
            pieceList.add(numPiece++, piece);
            repaint();

            // Check if one has won
            if (isWin()) {
                String winMessage = color + " Wins!";
                JOptionPane.showMessageDialog(this, winMessage);
                isOver = true;
            }
            isBlackFirst = !isBlackFirst;
        }

    }

    // Check if one is win. I.e. If there are five same color pieces in a line
    private boolean isWin() {
        int numPieceLine = 1; // number of pieces in a line

        // Find towards left
        for (int i = pieceX - 1; i >= 0; i--) {
            Color color = isBlackFirst ? Color.black : Color.white;
            if (getPiece(i, pieceY, color) != null) {
                numPieceLine++;
            } else {
                break;
            }
        }

        //Find towards right
        for (int i = pieceX + 1; i <= COLS; i++) {
            Color color = isBlackFirst ? Color.black : Color.white;
            if (getPiece(i, pieceY, color) != null) {
                numPieceLine++;
            } else {
                break;
            }
        }

        // Determine if there are 5 pieces in a row
        if(numPieceLine >= 5) {
            return true;
        } else {
            numPieceLine = 1;
        }

        //Find up
        for (int i = pieceY - 1; i >= 0; i--) {
            Color color = isBlackFirst ? Color.black : Color.white;
            if (getPiece(pieceX, i, color) != null) {
                numPieceLine++;
            } else {
                break;
            }
        }

        // Find down
        for (int i = pieceY + 1; i <= ROWS; i++) {
            Color color = isBlackFirst ? Color.black : Color.white;
            if (getPiece(pieceX, i, color) != null) {
                numPieceLine++;
            } else {
                break;
            }
        }

        // Determine if there are 5 pieces in a column
        if(numPieceLine >= 5) {
            return true;
        } else {
            numPieceLine = 1;
        }

        // Find towards upper right
        for (int i = pieceX + 1, j = pieceY - 1; i <= COLS && j >= 0; i++, j--) {
            Color color = isBlackFirst ? Color.black : Color.white;
            if (getPiece(i, j, color) != null) {
                numPieceLine ++;
            } else {
                break;
            }
        }

        // Find towards lower left
        for (int i = pieceX - 1, j = pieceY + 1; i >= 0 && j <= ROWS; i--, j++) {
            Color color = isBlackFirst ? Color.black : Color.white;
            if (getPiece(i, j, color) != null) {
                numPieceLine ++;
            } else {
                break;
            }
        }

        // Determine if there are 5 pieces from lower left to upper right
        if(numPieceLine >= 5) {
            return true;
        } else {
            numPieceLine = 1;
        }

        // Find towards upper left
        for (int i = pieceX - 1, j = pieceY - 1; i >=0 && j >= 0; i--, j--) {
            Color color = isBlackFirst ? Color.black : Color.white;
            if (getPiece(i, j, color) != null) {
                numPieceLine ++;
            } else {
                break;
            }
        }

        // Find towards lower right
        for (int i = pieceX + 1, j = pieceY + 1; i <= COLS && j <= ROWS; i++, j++) {
            Color color = isBlackFirst ? Color.black : Color.white;
            if (getPiece(i, j, color) != null) {
                numPieceLine ++;
            } else {
                break;
            }
        }

        // Determine if there are 5 pieces from upper left to lower right
        if(numPieceLine >= 5) {
            return true;
        } else {
            numPieceLine = 1;
        }

        return false;
    }

    // Restart game
    public void restart() {
        // remove all the pieces
        for(int i = 0; i < pieceList.size(); i++) {
            pieceList.remove(i);
        }
        isBlackFirst = true;
        isOver = false;
        numPiece = 0;
        repaint();
    }

    // Go back one step
    public void backStep() {
        if (numPiece != 0) {
            pieceList.remove(numPiece - 1);
            numPiece --;
        }
        if (numPiece > 0) {
            pieceX = pieceList.get(numPiece - 1).getXCord();
            pieceY = pieceList.get(numPiece - 1).getYCord();
        }
        isBlackFirst = !isBlackFirst;
        repaint();

    }

    @Override
    public void mouseClicked(MouseEvent e) {

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


    public Dimension getPreferredSize(){
        return new Dimension(MARGIN*2+SPACE*COLS,MARGIN*2
                +SPACE*ROWS);
    }



}
