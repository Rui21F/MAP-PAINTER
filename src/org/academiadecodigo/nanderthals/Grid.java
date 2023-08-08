package org.academiadecodigo.nanderthals;

import org.academiadecodigo.simplegraphics.graphics.*;

public class Grid {

    /**
     * Class to create the grid
     * Instance the cols and rows in Main
     */

    public static final int PADDING = 10;
    public static final int CELLSIZE = 20;
    private Player player;
    private Rectangle[][] gridArr;
    private int col;
    private int row;

    public Grid(int col, int row) {
        this.col = col;
        this.row = row;
        this.gridArr = new Rectangle[col][row];
        this.player = new Player(col, row, gridArr, this);
        myGrid();
    }

    public void myGrid() {

        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                Rectangle rectangle = new Rectangle(col * i + PADDING, row * j + PADDING, CELLSIZE, CELLSIZE);
                rectangle.draw();
                gridArr[i][j] = rectangle;
            }
        }
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}