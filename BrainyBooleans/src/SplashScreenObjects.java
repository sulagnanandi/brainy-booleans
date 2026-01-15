/**
 * PROGRAMMER: Sulagna Nandi
 * <p>
 * DATE: 01/18/22
 * <p>
 * TEACHER: Florina Basaraba
 * <p>
 * PURPOSE: This program teaches the user the basics of boolean algebra. The user can
 * also take a beginner, intermediate, or advanced level quiz to test their knowledge.
 * <p>
 * CLASS PURPOSE: This class draws and animates the objects of the main program's
 * splash screen.
 */

import hsa.Console;

import java.awt.*;

public class SplashScreenObjects implements Runnable {
    private Console c; //Console for shapes to be drawn on
    private int startTrue; //starting X for green shape
    private int startFalse; //starting X for red shape
    private int startAnd; //starting X for purple shape
    private int startOr; //starting X for teal shape
    private int increment; //pixels moves per frame
    private int frameDelay; //pause between frames in milliseconds

    //colours
    private final Color TRUE = new Color(54, 121, 63);
    private final Color FALSE = new Color(188, 24, 35);
    private final Color AND = new Color(149, 26, 190);
    private final Color OR = new Color(2, 138, 155);
    private Color background;

    /**
     * CONSTRUCTOR
     */
    public SplashScreenObjects(Console c, int startTrue, int startFalse, int startAnd, int startOr, int increment, int frameDelay, Color background) {
        //most instance variables determined by constructor
        this.c = c;
        this.startTrue = startTrue;
        this.startFalse = startFalse;
        this.startAnd = startAnd;
        this.startOr = startOr;
        this.increment = increment;
        this.frameDelay = frameDelay;
        this.background = background;
    }

    /**
     * DRAWS ALL FOUR SHAPES
     */
    public void drawObjects(int moveX) {
        c.setColor(TRUE);
        c.fillOval(startTrue + moveX, 42, 238, 85);
        c.setColor(FALSE);
        c.fillOval(startFalse - moveX, 128, 238, 85);
        c.setColor(AND);
        c.fillOval(startAnd + moveX, 214, 238, 85);
        c.setColor(OR);
        c.fillOval(startOr - moveX, 300, 238, 85);

        c.setColor(Color.white);
        c.setFont(new Font("Calibri", Font.BOLD, 40));
        c.drawString("TRUE", startTrue + 62 + moveX, 42 + 60);
        c.drawString("FALSE", startFalse + 58 - moveX, 128 + 60);
        c.drawString("AND", startAnd + 72 + moveX, 214 + 60);
        c.drawString("OR", startOr + 88 - moveX, 300 + 60);


    }

    /**
     * ERASES TRAIL BEHIND ALL FOUR SHAPES
     */
    private void eraseObjects(int moveX) {
        c.setColor(background);
        c.fillOval(startTrue + moveX, 42, 238, 85);
        c.fillOval(startFalse - moveX, 128, 238, 85);
        c.fillOval(startAnd + moveX, 214, 238, 85);
        c.fillOval(startOr - moveX, 300, 238, 85);
    }

    /**
     * ANIMATES SPLASH SCREEN
     */
    public void run() {

        /** Animate four objects until off-screen */
        for (int i = 1; i < 200; i++) {
            drawObjects(increment * i); //draw four shapes

            try {
                Thread.sleep(frameDelay); //pause between frames
            } catch (Exception e) {
            }

            eraseObjects(increment * i); //erase trail behind four shapes
        }

    }
}
