/*
 * GoBang piece class
 */

import java.awt.*;

public class Piece {
    private Color color;
    private int xCord, yCord; // x and y coordinate on the board

    public static final int DIM = 30; // The diameter of the piece

    //Constructor
    public Piece(int xCord, int yCord, Color color) {
        this.xCord = xCord;
        this.yCord = yCord;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getXCord() {
        return xCord;
    }

    public int getYCord() {
        return yCord;
    }
}
