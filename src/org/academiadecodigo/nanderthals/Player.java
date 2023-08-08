package org.academiadecodigo.nanderthals;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Player {

    /**
     * Class used to create the player rectangle in the grid
     */

    public Rectangle playerRect;
    private Rectangle[][] rectArr;
    private int width;
    private int height;
    private Movement movement;
    private Grid grid;


    public Player(int width, int height, Rectangle[][] rectArr, Grid grid) {
        this.rectArr = rectArr;
        this.width = width;
        this.height = height;
        this.grid = grid;
        createPlayerCursor();
    }

    public void createPlayerCursor() {
        this.playerRect = new Rectangle(Grid.PADDING, Grid.PADDING, Grid.CELLSIZE, Grid.CELLSIZE);
        playerRect.setColor(Color.GRAY);
        playerRect.fill();
        this.movement = new Movement(playerRect, rectArr, grid);

    }

}
