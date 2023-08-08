package org.academiadecodigo.nanderthals;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

import java.io.*;

/**
 * CLASS USED TO IMPLEMENT ALL THE MOVEMENTS AND ACTIONS
 * MOVE WITH NARROW KEYS;
 * SAVE WITH S
 * CLEAR WITH C
 * LOAD WITH L
 * PAINT WITH SPACE
 */
public class Movement implements KeyboardHandler {

    private Keyboard keyboard;
    private Rectangle rect;
    private Rectangle[][] rectArr;
    private boolean flag;
    private Grid grid;


    public Movement(Rectangle rect, Rectangle[][] rectArr, Grid grid) {
        this.rect = rect;
        this.rectArr = rectArr;
        this.grid = grid;
        keyboard = new Keyboard(this);
        addKeyboard();
    }


    private void addKeyboard() {
        KeyboardEvent moveRight = new KeyboardEvent();
        moveRight.setKey(KeyboardEvent.KEY_RIGHT);
        moveRight.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(moveRight);

        KeyboardEvent moveLeft = new KeyboardEvent();
        moveLeft.setKey(KeyboardEvent.KEY_LEFT);
        moveLeft.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(moveLeft);

        KeyboardEvent moveUp = new KeyboardEvent();
        moveUp.setKey(KeyboardEvent.KEY_UP);
        moveUp.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(moveUp);

        KeyboardEvent moveDown = new KeyboardEvent();
        moveDown.setKey(KeyboardEvent.KEY_DOWN);
        moveDown.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(moveDown);

        KeyboardEvent clear = new KeyboardEvent();
        clear.setKey(67);
        clear.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(clear);

        KeyboardEvent save = new KeyboardEvent();
        save.setKey(KeyboardEvent.KEY_S);
        save.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(save);

        KeyboardEvent load = new KeyboardEvent();
        load.setKey(KeyboardEvent.KEY_L);
        load.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(load);

        KeyboardEvent paint = new KeyboardEvent();
        paint.setKey(KeyboardEvent.KEY_SPACE);
        paint.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(paint);

        KeyboardEvent stopPainting = new KeyboardEvent();
        stopPainting.setKey((KeyboardEvent.KEY_SPACE));
        stopPainting.setKeyboardEventType(KeyboardEventType.KEY_RELEASED);
        keyboard.addEventListener(stopPainting);
    }


    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {

        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_RIGHT:
                moveRight();
                if (flag) {
                    paint();
                }
                break;

            case KeyboardEvent.KEY_LEFT:
                moveLeft();
                if (flag) {
                    paint();
                }
                break;

            case KeyboardEvent.KEY_UP:
                moveUp();
                if (flag) {
                    paint();
                }
                break;

            case KeyboardEvent.KEY_DOWN:
                moveDown();
                if (flag) {
                    paint();
                }
                break;

            case KeyboardEvent.KEY_SPACE:
                flag = true;
                if (flag) {
                    paint();
                }
                break;

            case KeyboardEvent.KEY_C:
                clear();
                break;

            case KeyboardEvent.KEY_S:
                save();
                break;

            case KeyboardEvent.KEY_L:
                load();
                break;
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        int keyReleased = keyboardEvent.getKey();

        if (keyReleased == KeyboardEvent.KEY_SPACE) {
            flag = false;
        }
    }

    public void moveRight() {
        if (rect.getX() < Grid.CELLSIZE * grid.getCol() - Grid.PADDING) {
            rect.translate(Grid.CELLSIZE, 0);
        }
    }

    public void moveLeft() {
        if (rect.getX() > Grid.PADDING) {
            rect.translate(-Grid.CELLSIZE, 0);
        }
    }

    public void moveUp() {
        if (rect.getY() > Grid.PADDING) {
            rect.translate(0, -Grid.CELLSIZE);
        }
    }

    public void moveDown() {
        if (rect.getY() < Grid.CELLSIZE * grid.getRow() - Grid.PADDING) {
            rect.translate(0, Grid.CELLSIZE);
        }
    }

    public void paint() {

        Rectangle[][] gridRectangles = rectArr;

        for (Rectangle[] rectangles : gridRectangles) {
            for (Rectangle rectangle : rectangles) {
                if (rect.getX() == rectangle.getX() && rect.getY() == rectangle.getY()) {
                    if (rectangle.isFilled()) {
                        rectangle.setColor(Color.BLACK);
                        isFlag();
                        rectangle.draw();
                    } else {
                        isFlag();
                        rectangle.fill();
                    }
                }
            }
        }
    }

    public void clear() {

        Rectangle[][] gridRectanglesClear = rectArr;

        for (Rectangle[] rectangles : gridRectanglesClear) {
            for (Rectangle rectangle : rectangles) {
                rectangle.draw();
            }
        }
    }

    public void save() {
        String fileName = "arrFile.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            int colorIndex = 0;
            for (Rectangle[] row : rectArr) {
                for (Rectangle rectangle : row) {
                    if (rectangle.isFilled()) {
                        writer.write(rectangle.getX() + "," + rectangle.getY() + "," + rectangle.getWidth() + "," + rectangle.getHeight());
                        writer.newLine();
                    }
                }
            }
            System.out.println("Array successfully saved to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean isFlag() {
        return !flag;
    }

    public void load() {

        String fileName = "arrFile.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                int width = Integer.parseInt(parts[2]);
                int height = Integer.parseInt(parts[3]);
                for (Rectangle[] rects : rectArr) {
                    for (Rectangle rect : rects) {
                        if (x == rect.getX() && y == rect.getY()) {

                            rect.setColor(Color.BLACK);
                            rect.fill();
                        }
                    }
                }
            }
            System.out.println("Rectangles successfully loaded from the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while loading the file: " + e.getMessage());

            e.printStackTrace();
        }
    }
}


