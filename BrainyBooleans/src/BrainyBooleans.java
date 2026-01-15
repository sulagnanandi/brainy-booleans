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
 * SOURCES:
 * https://www.electronics-tutorials.ws/boolean/bool_7.html
 * https://introcs.cs.princeton.edu/java/11precedence/
 * http://studytronics.weebly.com/boolean-algebra.html
 */

import hsa.Console;
import hsa.Message;
import java.awt.*;
import java.io.*;

public class BrainyBooleans {
    private static Console c; // Console object
    private char navigationChoice = 'A'; // determines where the user will go from mainMenu and other navigational points in the program
    private final File QUESTIONS = new File("AllQuestions.txt"); //file where all questions and their answers come from
    private int questionNumber; // generated randomly in questionGenerator() for quiz()
    private String[] options; // stores options of that chosen question, used for checking
    private String answer; // stores correct answer of that chosen question, used for checking
    private int[] usedQuestions; // will make sure no questions are repeated in one quiz

    // colours
    private final Color BACKGROUND = new Color(255, 102, 196);
    private final Color TITLEBOX = new Color(184, 66, 143);
    private final Color TEXTBOX = new Color(136, 0, 92);
    private final Color TEXT_COLOUR = new Color(255, 255, 255);

    // fonts
    private final Font TITLE_FONT = new Font("Calibri", Font.BOLD, 45);
    private final Font SUBTITLE_FONT = new Font("Calibri", Font.BOLD, 20);
    private final Font QUESTION_FONT = new Font("Calibri", Font.BOLD, 14);
    private final Font TEXT_FONT = new Font("Calibri", Font.BOLD, 12);


    /**
     * CONSTRUCTOR
     */
    BrainyBooleans() {
        c = new Console(25, 80);
    }


    /**
     * DISPLAYS SPLASH SCREEN WHEN OPENING PROGRAM
     */
    public void splashScreen() {
        //instantiate class with the splash screen
        Thread splash = new Thread(new SplashScreenObjects(c, -238, 640, -238, 640, 5, 15, BACKGROUND));

        /** Run splash screen */
        c.setColor(BACKGROUND);
        c.fillRect(0, 0, 640, 425);
        splash.run();
    }

    /**
     * DISPLAYS BASE BACKGROUND THAT USED IN MOST METHODS
     */
    private void background() {
        c.clear();
        c.setColor(BACKGROUND);
        c.fillRect(0, 0, 640, 425);
        c.setColor(TITLEBOX);
        c.fillRoundRect(43, 31, 555, 81, 20, 20);
        c.setColor(TEXTBOX);
        c.fillRoundRect(43, 120, 555, 274, 20, 20);
        c.setColor(TEXT_COLOUR);
        c.setFont(TITLE_FONT);
        c.drawString("Brainy Booleans", 137, 90);
    }


    /**
     * DISPLAYS THE MAIN MENU WITH ALL OPTIONS
     */
    public void mainMenu() {
        background();
        c.setFont(TEXT_FONT);
        c.drawString("Welcome to Brainy Booleans, a program that will teach you all about and make", 77, 173); //-2 on x
        c.drawString("you an expert on the core of all computers: booleans!", 156, 191);
        c.drawString("Pick where you want to go next by pressing one of the keys listed below.", 94, 224);
        c.drawString("L --> Comprehensive Lesson", 233 - 5, 263);
        c.drawString("B --> Beginner Quiz", 260 - 5, 281);
        c.drawString("I --> Intermediate Quiz", 251 - 5, 299);
        c.drawString("A --> Advanced Quiz", 258 - 5, 318);
        c.drawString("? --> Instructions", 269 - 5, 336);
        c.drawString("Any other key --> Goodbye and Exit", 212 - 5, 353);
        navigationChoice = c.getChar(); //asks for where the user wants to go; used in main(String[] args)
    }


    /**
     * DISPLAYS USER INSTRUCTIONS
     */
    public void instructions() {
        int indent = 80; //for formatting
        int indentAdd = 40; //for formatting
        int startY; //for formatting
        int verticalSpacing; //for formatting
        String bulletPoint; //for formatting
        String smallBullet; //for formatting
        background();

        /** General Manual */
        startY = 160;
        verticalSpacing = 17;
        bulletPoint = "◆";
        smallBullet = "○";
        c.setColor(TITLEBOX);
        c.fillRoundRect(43, 31, 555, 81, 20, 20);
        c.setColor(TEXT_COLOUR);
        c.drawString("General Manual", 140, 90);
        c.setFont(TEXT_FONT);
        c.drawString(bulletPoint + "  This program is comprised of...", indent, startY);
        c.drawString(smallBullet + "  a Comprehensive Lesson", indent + indentAdd, startY + verticalSpacing * 1);
        c.drawString(smallBullet + "  a Beginner Quiz", indent + indentAdd, startY + verticalSpacing * 2);
        c.drawString(smallBullet + "  an Intermediate Quiz", indent + indentAdd, startY + verticalSpacing * 3);
        c.drawString(smallBullet + "  an Advanced Quiz", indent + indentAdd, startY + verticalSpacing * 4);
        c.drawString(bulletPoint + "  You can enter one of the above sections only from the Main Menu.", indent, startY + verticalSpacing * 5);
        c.drawString(bulletPoint + "  Once you enter a section...", indent, startY + verticalSpacing * 6);
        c.drawString(smallBullet + "  You'll see brief instructions prior to starting that section.", indent + indentAdd, startY + verticalSpacing * 7);
        c.drawString(smallBullet + "  You cannot go back to the Main Menu until you complete that section.", indent + indentAdd, startY + verticalSpacing * 8);
        c.drawString(bulletPoint + "  In the Comprehensive and in all Quizzes...", indent, startY + verticalSpacing * 9);
        c.drawString(smallBullet + "  You will press any key to move to the next page/question.", indent + indentAdd, startY + verticalSpacing * 10);
        c.drawString(smallBullet + "  You cannot move back a page once you've moved forward.", indent + indentAdd, startY + verticalSpacing * 11);
        c.drawString(bulletPoint + "  Press any key to continue.", indent, startY + verticalSpacing * 12);

        pagePause();

        /** Lesson Manual */
        startY = 150;
        verticalSpacing = 15;
        c.drawString("Lesson Manual", 148, 90);
        c.setFont(TEXT_FONT);
        c.drawString(bulletPoint + "  This section of the program will teach you all the basics of boolean", indent, startY);
        c.drawString("     algebra and prepare you for all quizzes.", indent, startY + verticalSpacing * 1);
        c.drawString(bulletPoint + "  The topics covered are...", indent, startY + verticalSpacing * 2);
        c.drawString(smallBullet + "  True or False", indent + indentAdd, startY + verticalSpacing * 3);
        c.drawString(smallBullet + "  NOT Operator", indent + indentAdd, startY + verticalSpacing * 4);
        c.drawString(smallBullet + "  AND Gate", indent + indentAdd, startY + verticalSpacing * 5);
        c.drawString(smallBullet + "  OR Gate", indent + indentAdd, startY + verticalSpacing * 6);
        c.drawString(smallBullet + "  NOR Gate", indent + indentAdd, startY + verticalSpacing * 7);
        c.drawString(smallBullet + "  NAND Gate", indent + indentAdd, startY + verticalSpacing * 8);
        c.drawString(smallBullet + "  XOR Gate", indent + indentAdd, startY + verticalSpacing * 9);
        c.drawString(smallBullet + "  Laws of Simplification", indent + indentAdd, startY + verticalSpacing * 10);
        c.drawString(bulletPoint + "  You may go through the Comprehensive Lesson at any time and several", indent, startY + verticalSpacing * 11);
        c.drawString("     times if you wish.", indent, startY + verticalSpacing * 12);
        c.drawString(bulletPoint + "  You may wish to take notes when reading to help you prepare for the", indent, startY + verticalSpacing * 13);
        c.drawString("     quizzes.", indent, startY + verticalSpacing * 14);
        c.drawString(bulletPoint + "  Press any key to continue.", indent, startY + verticalSpacing * 15);

        pagePause();

        /** Quiz Manual */
        startY = 152;
        verticalSpacing = 17;
        c.drawString("Quiz Manual", 174, 90);
        c.setFont(TEXT_FONT);
        c.drawString(bulletPoint + "  Beginner Quiz topics are: True or False, NOT Operator, AND Gate, ", indent, startY);
        c.drawString("     and OR Gate.", indent, startY + verticalSpacing * 1);
        c.drawString(bulletPoint + "  Intermediate Quiz topics are: Everything above, NOR Gate, NAND Gate,", indent, startY + verticalSpacing * 2);
        c.drawString("     XOR Gate, and XNOR Gate.", indent, startY + verticalSpacing * 3);
        c.drawString(bulletPoint + "  Advanced Quiz topics are: Everything above and Laws of Simplification.", indent, startY + verticalSpacing * 4);
        c.drawString(bulletPoint + "  Each quiz consists of 10 random questions based on the difficulty level.", indent, startY + verticalSpacing * 5);
        c.drawString(bulletPoint + "  You will press A, B, C, or D on your keyboard to answer a question.", indent, startY + verticalSpacing * 6);
        c.drawString("     Once you press a key, you cannot change your answer.", indent, startY + verticalSpacing * 7);
        c.drawString(bulletPoint + "  Beginners get 3 chances per question, Intermediates gets 2,", indent, startY + verticalSpacing * 8);
        c.drawString("     and Advanced gets 1.", indent, startY + verticalSpacing * 9);
        c.drawString(bulletPoint + "  You will see the correct answer to a question once you've answered correctly,", indent, startY + verticalSpacing * 10);
        c.drawString("     or used up all your chances. You'll get your score at the end.", indent, startY + verticalSpacing * 11);
        c.drawString(bulletPoint + "  You may take any quiz at any time and several times if you wish.", indent, startY + verticalSpacing * 12);
        c.drawString(bulletPoint + "  Press any key to return to Main Menu.", indent, startY + verticalSpacing * 13);

        navigationChoice = c.getChar();
    }


    /**
     * DISPLAYS LESSON PAGES ONE BY ONE
     */
    public void lesson() {
        background();
        int startY;
        int verticalSpacing;

        /** Welcome message */
        c.setColor(TITLEBOX);
        c.fillRoundRect(43, 31, 555, 81, 20, 20);
        c.setColor(TEXT_COLOUR);
        c.setFont(new Font("Calibri", Font.BOLD, 40));
        c.drawString("Comprehensive Lesson", 82, 90);
        c.setFont(TEXT_FONT);
        c.drawString("Welcome to the Comprehensive Lesson!", 192, 194);
        c.drawString("This covers everything on the beginner, intermediate, and advanced quizzes.", 80, 239);
        c.drawString("In the lesson, press any key to move onto the next lesson page.", 115, 284);
        c.drawString("Press any key to start the lesson now.", 195, 329);


        /** True or False Lesson */
        pagePause();
        c.drawString("True or False", 171, 90);
        c.setFont(TEXT_FONT);
        c.drawString("Boolean algebra is a branch of mathematics that deals with true", 120, 190 + 10);
        c.drawString("and false values. 1 represents true, 0 represents false, and no", 128, 230 + 10);
        c.drawString("other numbers are involved. Every single argument in and result", 120, 270 + 10);
        c.drawString("of a boolean expression or equation must be either true or false.", 119, 310 + 10);


        /** NOT Lesson */
        pagePause();
        startY = 180;
        verticalSpacing = 33;
        c.drawString("NOT Operator", 168, 90);
        c.setFont(TEXT_FONT);
        c.drawString("NOT is the logical operator of negation. Negation is when you", 125, startY);
        c.drawString("take a true or false value and reverse it. It's the same as putting", 120, startY + verticalSpacing * 1);
        c.drawString("the word \"not\" in front of \"true\" or \"false\". Not true is false, not", 123, startY + verticalSpacing * 2);
        c.drawString("false is true.  In text (and in the quizzes), it looks like this: ¬1 = 0,", 112, startY + verticalSpacing * 3);
        c.drawString("¬0 = 1. But normally you'd draw a horizontal line over the part", 122, startY + verticalSpacing * 4);
        c.drawString("you're negating.", 270, startY + verticalSpacing * 5);


        /** AND Lesson */
        pagePause();
        startY = 172;
        verticalSpacing = 20;
        c.drawString("AND Gate", 210, 90);
        c.setFont(TEXT_FONT);
        c.drawString("AND is another logical operator. It's when you have an", 152, startY);
        c.drawString("expression of two boolean values and both of those values must", 118, startY + verticalSpacing * 1);
        c.drawString("be true for the whole expression to equate to true. It's the same", 122, startY + verticalSpacing * 2);
        c.drawString("as putting the word \"and\" between each condition. For example,", 123, startY + verticalSpacing * 3);
        c.drawString("\"My shoes are pink and green\". The answer to this is true if my", 123, startY + verticalSpacing * 4);
        c.drawString("shoes are pink and green, false if they're green but not pink,", 130, startY + verticalSpacing * 5);
        c.drawString("false if they're pink but not green, and false if they're neither", 127, startY + verticalSpacing * 6);
        c.drawString("colour. The symbol used for \"and\" is • (the multiplication", 138, startY + verticalSpacing * 7);
        c.drawString("operator). For example, if P represents whether my shoes have", 124, startY + verticalSpacing * 8);
        c.drawString("pink and B represents whether my shoes have green: (P • G).", 129, startY + verticalSpacing * 9);


        /** OR Lesson */
        pagePause();
        startY = 172;
        verticalSpacing = 20;
        c.drawString("OR Gate", 225, 90);
        c.setFont(TEXT_FONT);
        c.drawString("OR is another common logical operator. It's when you have an", 126, startY);
        c.drawString("expression of two boolean values and at least one of those", 130, startY + verticalSpacing * 1);
        c.drawString("values must be true for the whole expression to equate to true.", 121, startY + verticalSpacing * 2);
        c.drawString("It's the same as putting \"and/or\" between each condition. For", 125, startY + verticalSpacing * 3);
        c.drawString("example, \"My shoes are pink and/or green\". The answer to this", 123, startY + verticalSpacing * 4);
        c.drawString("is true if my shoes are pink and green, true if they're green but", 121, startY + verticalSpacing * 5);
        c.drawString("not pink, true if they're pink but not green, but false if they're", 123, startY + verticalSpacing * 6);
        c.drawString("neither colour. The symbol used for \"and/or\" is + (the addition", 122, startY + verticalSpacing * 7);
        c.drawString("operator). For example, if P represents whether my shoes have", 123, startY + verticalSpacing * 8);
        c.drawString("pink and B represents whether my shoes have green: (P + G).", 125, startY + verticalSpacing * 9);


        /** NOR Lesson */
        pagePause();
        startY = 175;
        verticalSpacing = 25;
        c.drawString("NOR Gate", 210, 90);
        c.setFont(TEXT_FONT);
        c.drawString("NOR is similar to AND. It's when you have an expression of two", 122, startY);
        c.drawString("boolean values and all of those values must be false for the", 128, startY + verticalSpacing * 1);
        c.drawString("whole expression to equate to true. It's the same as putting \"not\"", 115, startY + verticalSpacing * 2);
        c.drawString("before and \"nor\" between two conditions. For example, \"My", 134, startY + verticalSpacing * 3);
        c.drawString("shoes are not pink nor green\". The answer to this is true if my", 123, startY + verticalSpacing * 4);
        c.drawString("shoes are neither colour, false if they're green but not pink, false", 118, startY + verticalSpacing * 5);
        c.drawString("if they're pink but not green, and false if they're both colours. You", 114, startY + verticalSpacing * 6);
        c.drawString("would write it as: (¬P • ¬G) or ¬(P + G).", 195, startY + verticalSpacing * 7);


        /** NAND Lesson */
        pagePause();
        startY = 172;
        verticalSpacing = 26;
        c.drawString("NAND Gate", 192, 90);
        c.setFont(TEXT_FONT);
        c.drawString("NAND is similar to OR. It's when you have an expression of two", 122, startY);
        c.drawString("boolean values and at least one of those values must be false", 125, startY + verticalSpacing * 1);
        c.drawString("for the whole expression to equate to true. It's the same as", 132, startY + verticalSpacing * 2);
        c.drawString("putting \"not\" before and \"and\" between two conditions. For", 132, startY + verticalSpacing * 3);
        c.drawString("example, \"My shoes are not pink and green\". The answer to this", 121, startY + verticalSpacing * 4);
        c.drawString("is true if my shoes are neither colour, true if they're green but not", 115, startY + verticalSpacing * 5);
        c.drawString("pink, true if they're pink but not green, and false if they're both", 121, startY + verticalSpacing * 6);
        c.drawString("colours. You would write it as: (¬P + ¬G) or ¬(P • G).", 157, startY + verticalSpacing * 7);


        /** XOR Lesson */
        pagePause();
        startY = 172;
        verticalSpacing = 26;
        c.drawString("XOR Gate", 210, 90);
        c.setFont(TEXT_FONT);
        c.drawString("XOR is an exclusive version of OR. It's when you have an", 142, startY);
        c.drawString("expression of two boolean values and only one of the values", 128, startY + verticalSpacing * 1);
        c.drawString("should be true for the whole expression to equate to true. It's the", 115, startY + verticalSpacing * 2);
        c.drawString("same as putting \"or\" between each condition. For example, \"My", 124, startY + verticalSpacing * 3);
        c.drawString("shoes are pink or green\". The answer to this is false if my shoes", 120, startY + verticalSpacing * 4);
        c.drawString("are both colours, true if they're green but not pink, true if they're", 119, startY + verticalSpacing * 5);
        c.drawString("pink but not green, and false if they're neither colour. You would", 119, startY + verticalSpacing * 6);
        c.drawString("write it as: (P ⊕ G).", 262, startY + verticalSpacing * 7);


        /** XNOR Lesson */
        pagePause();
        startY = 174;
        verticalSpacing = 30;
        c.drawString("XNOR Gate", 192, 90);
        c.setFont(TEXT_FONT);
        c.drawString("XNOR is exclusive-not-or. Very simply, an XNOR operation gives", 120, startY);
        c.drawString("you the opposite result that the same expression with an XOR", 125, startY + verticalSpacing * 1);
        c.drawString("operator would give you. When you evaluate longer XOR", 140, startY + verticalSpacing * 2);
        c.drawString("expressions, you'll find that an odd number of 1s gives you a", 125, startY + verticalSpacing * 3);
        c.drawString("result of 1. Since XNOR is the opposite of XOR, an XNOR", 140, startY + verticalSpacing * 4);
        c.drawString("expression with an even number of 1s gives you 1. You would", 125, startY + verticalSpacing * 5);
        c.drawString("write an XNOR expression with two arguments as: (A ⊙ B).", 140, startY + verticalSpacing * 6);


        /** Laws of Simplification Lesson */
        pagePause();
        c.drawString("Laws of Simplification", 65, 90);
        for (int i = 120 + 40; i < 394; i += 26)
            c.drawLine(43, i, 43 + 555, i);
        for (int i = 185 + 43; i < 550; i += 185)
            c.drawLine(i, 120, i, 120 + 274);
        c.setFont(TEXT_FONT);
        int indent = 55;
        c.drawString("Identity Law", indent, 151 + 26 * 1);
        c.drawString("Null Law", indent, 151 + 26 * 2);
        c.drawString("Idempotent Law", indent, 151 + 26 * 3);
        c.drawString("Inverse Law", indent, 151 + 26 * 4);
        c.drawString("Commutative Law", indent, 151 + 26 * 5);
        c.drawString("Associative Law", indent, 151 + 26 * 6);
        c.drawString("Distributive Law", indent, 151 + 26 * 7);
        c.drawString("Absorption Law", indent, 151 + 26 * 8);
        c.drawString("De Morgan's Law", indent, 151 + 26 * 9);
        indent += 185;
        c.setFont(new Font("Calibri", Font.BOLD, 10));
        c.drawString("A • 1 = A", indent, 151 + 26 * 1);
        c.drawString("A • 0 = 0", indent, 151 + 26 * 2);
        c.drawString("A • A = A", indent, 151 + 26 * 3);
        c.drawString("A • (¬A) = 0", indent, 151 + 26 * 4);
        c.drawString("A • B = B • A", indent, 151 + 26 * 5);
        c.drawString("(A • B) • C = A • (B • C)", indent, 151 + 26 * 6);
        c.drawString("A + BC = (A + B) • (A + C)", indent, 151 + 26 * 7);
        c.drawString("A • (A + B) = A", indent, 151 + 26 * 8);
        c.drawString("¬(A • B) = (¬A) + (¬B)", indent, 151 + 26 * 9);
        indent += 185;
        c.drawString("A + 0 = A", indent, 151 + 26 * 1);
        c.drawString("A + 1 = 1", indent, 151 + 26 * 2);
        c.drawString("A + A = A", indent, 151 + 26 * 3);
        c.drawString("A + (¬A) = 1", indent, 151 + 26 * 4);
        c.drawString("A + B = B + A", indent, 151 + 26 * 5);
        c.drawString("(A + B) + C = A + (B + C)", indent, 151 + 26 * 6);
        c.drawString("A • (B + C) = (A • B) + (A • C)", indent, 151 + 26 * 7);
        c.drawString("A + (A • B) = A", indent, 151 + 26 * 8);
        c.drawString("¬(A + B) = (¬A) • (¬B)", indent, 151 + 26 * 9);
        c.setFont(SUBTITLE_FONT);
        c.drawString("Name", 103, 150);
        c.drawString("AND Form", 270, 150);
        c.drawString("OR Form", 461, 150);


        /** Order of Operations Lesson */
        pagePause();
        startY = 160;
        verticalSpacing = 28;
        c.drawString("Order of Operations", 88, 90);
        c.setFont(TEXT_FONT);
        c.drawString("The Order of Operations is crucial for any level of boolean algebra", 115, startY);
        c.drawString("and is incorporated into each quiz difficulty level.", 165, startY + verticalSpacing * 1 - 10);
        c.setFont(SUBTITLE_FONT);
        c.drawString("It is as follows...", 235, startY + verticalSpacing * 2 + 5);
        c.setFont(TEXT_FONT);
        c.drawString("1. Brackets", 280, startY + verticalSpacing * 3 + 5);
        c.drawString("2. NOT", 293, startY + verticalSpacing * 4 + 5);
        c.drawString("3. XOR / XNOR (left to right)", 227, startY + verticalSpacing * 5 + 5);
        c.drawString("4. AND", 293, startY + verticalSpacing * 6 + 5);
        c.drawString("5. OR", 298, startY + verticalSpacing * 7 + 5);


        /** End of Lesson */
        pagePause();
        c.drawString("End of Lesson", 158, 90);
        c.setFont(new Font("Calibri", Font.BOLD, 30));
        c.drawString("Well done!", 240, 190 + 5);
        c.setFont(TEXT_FONT);
        c.drawString("You've learned all the basics of boolean algebra", 161, 230 + 5);
        c.drawString("and you're on your way to becoming an expert!", 163, 265 + 5);
        c.drawString("To put your new skills to the test, try out one of the quizzes!", 130, 300 + 5);
        c.drawString("Press any key to go back to Main Menu.", 196, 335 + 5);

        navigationChoice = c.getChar(); //user will go back to Main Menu
    }


    /**
     * WAITS FOR USER TO PRESS ANY KEY TO MOVE ONTO NEXT PAGE
     */
    private void pagePause() {
        c.getChar();

        // draws a fresh background for the new page
        c.setColor(TEXTBOX);
        c.fillRoundRect(43, 120, 555, 274, 20, 20);
        c.setColor(TITLEBOX);
        c.fillRoundRect(43, 31, 555, 81, 20, 20);
        c.setColor(TEXT_COLOUR);
        c.setFont(TITLE_FONT);
    }


    /**
     * CONDUCTS ONE QUIZ WITH QUESTIONS BASED ON USER-CHOSEN DIFFICULTY
     */
    public void quiz() {
        options = new String[5]; //each quiz starts with a fresh options array to prevent any errors
        usedQuestions = new int[11]; //each quiz starts with a fresh usedQuestions array to prevent any errors
        answer = null; //each quiz starts with no answer to prevent any errors
        questionNumber = 0; //each quiz starts without any question's number to prevent any errors
        int numChances = 0; //number of chances per question based on difficulty level

        /** Displays title of quiz and initializes numChances*/
        background();
        c.setColor(TITLEBOX);
        c.fillRoundRect(43, 31, 555, 81, 20, 20);
        c.setColor(TEXT_COLOUR);
        if (navigationChoice == 'b' || navigationChoice == 'B') {
            c.drawString("Beginner Quiz", 154, 88);
            numChances = 3;
        } else if (navigationChoice == 'i' || navigationChoice == 'I') {
            c.drawString("Intermediate Quiz", 112, 88);
            numChances = 2;
        } else if (navigationChoice == 'a' || navigationChoice == 'A') {
            c.drawString("Advanced Quiz", 146, 88);
            numChances = 1;
        }

        /** Welcome message */
        c.setFont(TEXT_FONT);
        c.drawString("Welcome to the Quiz Section!", 225, 177);
        c.drawString("Each quiz has ten questions.", 227, 202);
        c.drawString("You must press A, B, C, or D on your keyboard to answer a question.", 106, 227);
        c.drawString("You cannot go back to a question once you've answered it.", 134, 252);
        c.drawString("Correct answers to each question will be displayed immediately.", 115, 277);
        c.drawString("Your score will be shown to you after you complete the quiz.", 125, 302);
        c.drawString("You may take this quiz again any time, at any difficulty level.", 125, 327);
        c.drawString("Press any key to start the quiz now.", 205, 352);

        c.getChar();

        int indent = 75; //text-formatting variable
        int verticalStart = 155; //text-formatting variable
        int score = 0; //starting score, to be increased based on correct answers

        /** MAIN LOOP FOR QUIZ:
         * Prints 10 random, non-repeating questions.
         * Prints options to each question in a random order.
         * Checks answer and increases score accordingly.
         * After correctly answered or no more chances, prints user-selected answer, correct answer, and points gained.
         * Waits for user to press any key to move onto next question.
         */
        for (int i = 1; i <= 10; i++) {
            c.setColor(TEXTBOX);
            c.fillRect(0, 120, 640, 274);

            /** Prints a random question, and stores that question's number in an array so it's not repeated */
            c.setColor(TEXT_COLOUR);
            c.setFont(QUESTION_FONT);
            c.drawString(i + ". " + questionGenerator(), indent - 30, verticalStart);
            usedQuestions[i] = questionNumber;

            char[] abcd = {0, 'A', 'B', 'C', 'D'}; //for printing option reference letters
            String[] optionsInDisplayOrder = new String[5]; //stores options in the order shown to user, so it can be compared to the answer properly
            int[] usedRandoms = new int[5]; //ensures no repeats in displaying options

            /** Prints options in a random order */
            for (int r = 1; r <= 4; r++) {
                boolean validRandom = false;
                int random = 0;

                /** Makes sure a random sequence of 1, 2, 3, 4 with no repeats is made */
                while (!validRandom) {
                    random = (int) (Math.random() * (5 - 1) + 1);

                    for (int randIndex = 1; randIndex < usedRandoms.length; randIndex++) {
                        if (random == usedRandoms[randIndex]) {
                            validRandom = false;
                            break;
                        } else {
                            validRandom = true;
                        }
                    }
                }
                usedRandoms[r] = random;
                optionsInDisplayOrder[r] = options[random];
                c.drawString(abcd[r] + ". " + optionsInDisplayOrder[r], indent, verticalStart + 30 * r);
            }


            boolean validInput = false; //for error trapping answer to question (false if not a, b, c, or d)
            boolean correctAnswer = false;
            int spaceFromTop = 135; //formatting
            int spaceBetween = 20; //formatting
            int addIndent = -25; //formatting

            /** Gives beginners 3 chances, intermediates 2 chances, and advanceds 1 chance */
            for (int count = 1; !correctAnswer && count <= numChances; count++) {
                validInput = false;

                /** Makes sure a, b, c, or d is inputted (upper or lower case) */
                while (!validInput) {
                    char ua = c.getChar(); //user answer to question (shortened since it's used so many times)
                    c.setColor(TEXTBOX);
                    c.fillRect(0, 280, 500, 100);
                    c.setColor(TEXT_COLOUR);
                    if (ua == 'a' || ua == 'A' || ua == 'b' || ua == 'B' || ua == 'c' || ua == 'C' || ua == 'd' || ua == 'D') {
                        validInput = true;

                        c.drawString("Selected Answer: " + String.valueOf(ua).toUpperCase(), indent + addIndent, verticalStart + spaceFromTop + spaceBetween * 1);

                        /** Checks if the corresponding String of the letter they clicked matches the answer string */
                        if (optionsInDisplayOrder[1].equalsIgnoreCase(answer) && (ua == 'a' || ua == 'A')) {
                            c.drawString("Points gained: 1", indent + addIndent, verticalStart + spaceFromTop + spaceBetween * 3);
                            score++;
                            correctAnswer = true;
                        } else if (optionsInDisplayOrder[2].equalsIgnoreCase(answer) && (ua == 'b' || ua == 'B')) {
                            c.drawString("Points gained: 1", indent + addIndent, verticalStart + spaceFromTop + spaceBetween * 3);
                            score++;
                            correctAnswer = true;
                        } else if (optionsInDisplayOrder[3].equalsIgnoreCase(answer) && (ua == 'c' || ua == 'C')) {
                            c.drawString("Points gained: 1", indent + addIndent, verticalStart + spaceFromTop + spaceBetween * 3);
                            score++;
                            correctAnswer = true;
                        } else if (optionsInDisplayOrder[4].equalsIgnoreCase(answer) && (ua == 'd' || ua == 'D')) {
                            c.drawString("Points gained: 1", indent + addIndent, verticalStart + spaceFromTop + spaceBetween * 3);
                            score++;
                            correctAnswer = true;
                        } else {
                            if (count < numChances)
                                new Message("Incorrect. Try again!");
                            correctAnswer = false;
                        }

                    } else {
                        new Message("Please press A, B, C, or D on your keyboard.");
                    }
                }
            }

            /** Displays correct answers after user correctly answers or no more chances left */
            if (optionsInDisplayOrder[1].equalsIgnoreCase(answer))
                c.drawString("Correct Answer: A", indent + addIndent, verticalStart + spaceFromTop + spaceBetween * 2);
            else if (optionsInDisplayOrder[2].equalsIgnoreCase(answer))
                c.drawString("Correct Answer: B", indent + addIndent, verticalStart + spaceFromTop + spaceBetween * 2);
            else if (optionsInDisplayOrder[3].equalsIgnoreCase(answer))
                c.drawString("Correct Answer: C", indent + addIndent, verticalStart + spaceFromTop + spaceBetween * 2);
            else if (optionsInDisplayOrder[4].equalsIgnoreCase(answer))
                c.drawString("Correct Answer: D", indent + addIndent, verticalStart + spaceFromTop + spaceBetween * 2);

            if (correctAnswer == false)
                c.drawString("Points gained: 0", indent + addIndent, verticalStart + spaceFromTop + spaceBetween * 3);

            c.drawString("Press any key to continue.", indent + addIndent, verticalStart + spaceFromTop + spaceBetween * 4);

            c.getChar(); //to move onto next question
        }

        /** After the quiz, display user's score */
        background();
        c.setColor(TITLEBOX);
        c.fillRoundRect(43, 31, 555, 81, 20, 20);
        c.setColor(TEXT_COLOUR);
        c.drawString("Results", 235, 90);
        if (score == 10)
            c.drawString("SCORE: 100%", 167, 245);
        else
            c.drawString("SCORE: " + score + "0%", 181, 245);
        c.setFont(SUBTITLE_FONT);
        c.drawString("Press any key to go to Main Menu.", 143, 305);
        navigationChoice = c.getChar();
    }


    /**
     * GENERATES A RANDOM QUESTION TO BE PRINTED
     */
    public String questionGenerator() {
        int QLN = 0; //question line number (shortened because questionLineNumber is long)
        boolean validQuestionNumber = false;

        /** Keeps running until a random, unused question number is generated */
        while (!validQuestionNumber) {

            /** Generates random question's line number based on difficulty level */
            if (navigationChoice == 'B' || navigationChoice == 'b')
                QLN = (int) (Math.random() * (18 - 1) + 1) * 6; //[1 to 17] | Generates beginner questions based on NOT, AND, OR
            else if (navigationChoice == 'I' || navigationChoice == 'i')
                QLN = (int) (Math.random() * (35 - 18) + 18) * 6; //[18 to 34] | Generates intermediate questions based on above, NAND, NOR, XOR, XNOR
            else if (navigationChoice == 'A' || navigationChoice == 'a')
                QLN = (int) (Math.random() * (52 - 35) + 35) * 6; //[35 to 51] | Generates advanced questions based on above, and Rules and Laws of Simplification

            /** Makes sure question is not repeating itself */
            for (int i = 0; i < usedQuestions.length; i++) {
                if (usedQuestions[i] == (QLN / 6)) {
                    validQuestionNumber = false;
                    break;
                } else {
                    validQuestionNumber = true;
                }
            }
        }

        questionNumber = QLN / 6; //to be stored in usedQuestions[]

        FileReader f;
        BufferedReader b;
        String question = "";
        boolean noCrash = false;

        /** Searches file for all that's needed and tries again in case an error occurs */
        while (!noCrash) {
            try {
                f = new FileReader(QUESTIONS);
                b = new BufferedReader(f);
                int counter = 0;

                /** Read file until question is found */
                do {
                    question = b.readLine();
                    counter++;
                } while (counter < QLN);

                /** Store options of that question in an array */
                for (int i = 1; i <= 4; i++) {
                    options[i] = b.readLine();
                }

                /** Store the answer of that question in a String */
                answer = b.readLine();
                noCrash = true;
            } catch (IOException io) {
                noCrash = false; //for clarity
            }
        }

        return question; //for printing in quiz()
    }


    /**
     * DISPLAYS A GOODBYE MESSAGE TO THE USER BEFORE EXITING
     */
    public void goodbye() {
        background();
        int startY = 205; //formatting
        int verticalSpacing = 40; //formatting
        c.setFont(new Font("Calibri", Font.BOLD, 17));
        c.drawString("Thank you so much for using Brainy Booleans!", 115, startY);
        c.drawString("Press M to go back to the Main Menu.", 153, startY+verticalSpacing*1);
        c.drawString("Press any other key to close the program.", 137, startY+verticalSpacing*2);
        c.drawString("Programmed By: Sulagna Nandi",185,startY+verticalSpacing*3);
        navigationChoice = c.getChar(); //Goes back to mainMenu() if M/m, otherwise closes Console
    }


    /**
     * MAIN
     */
    public static void main(String[] args) {
        BrainyBooleans b = new BrainyBooleans(); //instantiate

        b.splashScreen(); //show splash screen

        /** Runs entire program until user decides to leave */
        while (b.navigationChoice != 0) {
            b.mainMenu(); //program will always come back here

            /** Controls which page the user will go to */
            switch (b.navigationChoice) {
                case 'L':
                case 'l':
                    b.lesson(); //go through lesson
                    break;
                case 'B':
                case 'b':
                case 'I':
                case 'i':
                case 'A':
                case 'a':
                    b.quiz(); //go through quiz
                    break;
                case '?':
                case '/':
                    b.instructions(); //go through instructions
                    break;
                default:
                    b.goodbye(); //display goodbye
                    if (b.navigationChoice == 'M' || b.navigationChoice == 'm') { // M pressed = back to Main Menu
                    } else if (b.navigationChoice != 0) {
                        c.close(); //any other key pressed = exit
                    }
                    break;
            }
        }
    }
}
