package javaproject.Control;

import javaproject.Entity.MovingEntity.Player;
import javaproject.Entity.MovingEntity.Enemy;
import javaproject.Entity.StillEntity.Doctor;
import javaproject.Entity.StillEntity.BlueHerbs;
import javaproject.Entity.StillEntity.Mountains;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javaproject.Entity.StillEntity.Herbs;
import javaproject.Entity.StillEntity.PoisonousHerbs;
import javaproject.Entity.StillEntity.GoldHerbs;

/**
    Board which holds all elements and visuals of the game
*/
public class Board extends JPanel implements ActionListener, KeyListener {

    private final int DELAY = 25; //The delay for the timer
    public static final int TILE_SIZE = 30; //Tile size
    public static final int ROWS = 12; //Number of rows on the board
    public static final int COLUMNS = 18; //Number of columns on the board
    
    public static final int NUM_HERBS = 5; //Number of herbs on the board
    public static final int NUM_MOUNTAINS = 10; //Number of mountains/barriers on the board

    // keep a reference to the timer object that triggers actionPerformed() in
    // case we need access to it in another method
    private Timer timer;
    // objects that appear on the game board
    private Player player;
    private ArrayList<Enemy> enemies;
    private Doctor doctor;
    private ArrayList<Herbs> herbs;
    private ArrayList<Mountains> mountains;
    private BufferedImage grass;
    private BufferedImage mountain;

    //check variables for game completion and enemy movement
    private int blueHerbsCounter = 0;
    private int blueHerbsCollected = 0;
    private boolean moved = false;
    private boolean gameWin = false;
    private boolean gameLose = false;

    /**
        Board constructor
    */
    public Board() {
        setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS));
        //intializing all visuals
        player = new Player();
        enemies = new ArrayList<Enemy>();
        enemies.add(new Enemy(10, 10));
        enemies.add(new Enemy(7, 7));
        doctor = new Doctor();
        herbs = populateHerbs();
        mountains = populateMountains();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    /**
        actionPerformed method
    */
    public void actionPerformed(ActionEvent e) {
        player.tick();
        collectHerbs();
        blocked();
        reachDoctor();
        gameOver();
        repaint();
    }

    /**
        Override method to draw the board
    */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!gameWin && !gameLose) {
            drawBackground(g);
            drawScore(g);
            player.draw(g, this);
            for (Enemy enemy : enemies) {
                enemy.draw(g, this);
            }
            doctor.draw(g, this);
            for (Herbs herb : herbs) {
                herb.draw(g, this);
            }
            for (Mountains mountain : mountains) {
                mountain.draw(g, this);
            }
        }
        else if (gameWin) {
            drawBackground(g);
            //FOR GAME WIN END SCREEN: Can draw text saying "Congratulations!" Code needs to be written below in this else if block



        }
        else { //gamelose
            drawBackground(g);
            //FOR GAME LOSE END SCREEN: Can draw text saying "Game Over!" Code needs to be written below in this else block
        }
    }

    @Override
    /**
        key typed method
    */
    public void keyTyped(KeyEvent e) {
        //Does nothing
    }

    @Override
    /**
        Key pressed method for player movement and enemy movement (Each tick of the game is a key press)
    */
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
        //Enemy will move every other key click since this makes the game more fun and less difficult
        //Enemy and knight are blocked by mountains and enemy moves towards the knight
        if (!moved) {
            enemyMove();
        }
        if (!moved) {
            moved = true;
        }
        else {
            moved = false;
        }
    }

    @Override
    /**
        Key released method
    */
    public void keyReleased(KeyEvent e) {
        //Does nothing
    }

    /**
        Method for drawing the background of the board
        @param g The graphics
    */
    private void drawBackground(Graphics g) {
        try {
            grass = ImageIO.read(new File("javaproject/src/main/java/javaproject/images/grass.png"));
        }
        catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            mountain = ImageIO.read(new File("javaproject/src/main/java/javaproject/images/001-mountain.png"));
        }
        catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        //Draw grass everywhere on the game board
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                g.drawImage(grass, col * TILE_SIZE, row * TILE_SIZE, this);
            }
        }

        //Draw mountains on edges of the game board
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (row == 0 || col == 0 || row == (ROWS - 1) || col == (COLUMNS - 1)) {
                    g.drawImage(mountain, col * TILE_SIZE, row * TILE_SIZE, this);
                }
            }
        }
    }

    /**
        Method for drawing the score
        @param g The graphics
    */
    private void drawScore(Graphics g) {
        String text = player.getScore();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(
            RenderingHints.KEY_FRACTIONALMETRICS,
            RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        // set the text color and font
        g2d.setColor(new Color(30, 201, 139));
        g2d.setFont(new Font("Lato", Font.BOLD, 25));
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        Rectangle rect = new Rectangle(0, TILE_SIZE * (ROWS - 1), TILE_SIZE * COLUMNS, TILE_SIZE);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g2d.drawString(text, x, y);
    }

    /**
        Method for populating the board with herbs
        @return Returns an arraylist of herbs
    */
    private ArrayList<Herbs> populateHerbs() {
        ArrayList<Herbs> herbsList = new ArrayList<Herbs>();
        Random rand = new Random();
        boolean flag = true;

        // create the given number of herbs in random positions on the board.
        // we also check to make sure that each entity holds a unique position on the board
        // ie two different entities will not share the same spot when the game is run
        for (int i = 0; i < NUM_HERBS; i++) {
            int StillEntityX = rand.nextInt(COLUMNS-2)+1;
            int StillEntityY = rand.nextInt(ROWS-2)+1;
            Point position = new Point(StillEntityX, StillEntityY);
            for (Herbs herb : herbsList) {
                if (herb.getPos().equals(position)) {
                    flag = false;
                }
            }
            if (player.getPos().equals(position)) {
                flag = false;
            }
            for (Enemy enemy : enemies) {
                if (enemy.getPos().equals(position)) {
                    flag = false;
                }
            }
            if (doctor.getPos().equals(position)) {
                flag = false;
            }
            if (flag) {
                herbsList.add(new BlueHerbs(StillEntityX, StillEntityY));
                blueHerbsCounter++;
            }
            flag = true;
        }

        for (int i = 0; i < NUM_HERBS; i++) {
            int StillEntityX = rand.nextInt(COLUMNS-2)+1;
            int StillEntityY = rand.nextInt(ROWS-2)+1;
            Point position = new Point(StillEntityX, StillEntityY);
            for (Herbs herb : herbsList) {
                if (herb.getPos().equals(position)) {
                    flag = false;
                }
            }
            if (player.getPos().equals(position)) {
                flag = false;
            }
            for (Enemy enemy : enemies) {
                if (enemy.getPos().equals(position)) {
                    flag = false;
                }
            }
            if (doctor.getPos().equals(position)) {
                flag = false;
            }
            if (flag) {
                herbsList.add(new PoisonousHerbs(StillEntityX, StillEntityY));
            }
            flag = true;
        }

        for (int i = 0; i < 1; i++) {
            int StillEntityX = rand.nextInt(COLUMNS-2)+1;
            int StillEntityY = rand.nextInt(ROWS-2)+1;
            Point position = new Point(StillEntityX, StillEntityY);
            for (Herbs herb : herbsList) {
                if (herb.getPos().equals(position)) {
                    flag = false;
                }
            }
            if (player.getPos().equals(position)) {
                flag = false;
            }
            for (Enemy enemy : enemies) {
                if (enemy.getPos().equals(position)) {
                    flag = false;
                }
            }
            if (doctor.getPos().equals(position)) {
                flag = false;
            }
            if (flag) {
                herbsList.add(new GoldHerbs(StillEntityX, StillEntityY));
            }
            flag = true;
        }
        return herbsList;
    }

    /**
        Method for populating the board with mountains
        @return Returns an arraylist of mountains
    */
    private ArrayList<Mountains> populateMountains() {
        ArrayList<Mountains> mountainsList = new ArrayList<Mountains>();
        Random rand = new Random();
        boolean flag = true;

        // create the given number of mountains/barriers in random positions on the board.
        // we also check to make sure that each entity holds a unique position on the board
        // ie two different entities will not share the same spot when the game is run
        for (int i = 0; i < NUM_MOUNTAINS; i++) {
            int StillEntityX = rand.nextInt(COLUMNS-2)+1;
            int StillEntityY = rand.nextInt(ROWS-2)+1;
            Point position = new Point(StillEntityX, StillEntityY);
            for (Herbs herb : herbs) {
                if (herb.getPos().equals(position)) {
                    flag = false;
                }
            }
            for (Mountains mountain : mountainsList) {
                if (mountain.getPos().equals(position)) {
                    flag = false;
                }
            }
            if (player.getPos().equals(position)) {
                flag = false;
            }
            for (Enemy enemy : enemies) {
                if (enemy.getPos().equals(position)) {
                    flag = false;
                }
            }
            if (doctor.getPos().equals(position)) {
                flag = false;
            }
            if (flag) {
                mountainsList.add(new Mountains(StillEntityX, StillEntityY));
            }
            flag = true;
        }
        return mountainsList;
    }

    /**
        Method for collecting different herbs
    */
    private void collectHerbs() {
        // allow player to pickup herbs
        ArrayList<Herbs> collectedHerbs = new ArrayList<Herbs>();
        for (Herbs herb : herbs) {
            // if the player is on the same tile as a herb, collect it
            if (player.getPos().equals(herb.getPos())) {
                // check which herb the player collected and give them points. Increment the regular reward (blue herb) counter by 1
                if (herb.getValue() == 10) {
                    player.addScore(10);
                    blueHerbsCollected++;
                }
                else if (herb.getValue() == 50) {
                    player.addScore(50);
                }
                else {
                    player.addScore(-10);
                }
                collectedHerbs.add(herb);
            }
        }
        // remove collected herbs from the board
        herbs.removeAll(collectedHerbs);
    }

    /**
        Method for game completion
    */
    private void reachDoctor() {
        if (player.getPos().equals(doctor.getPos()) && blueHerbsCollected == blueHerbsCounter) {
            gameWin = true;
        }
    }

    /**
        Method for game over by losing too many points are being reached by an enemy
    */
    private void gameOver() {
        for (Enemy enemy : enemies) {
            if (player.getScoreValue() < 0 || player.getPos().equals(enemy.getPos())) {
                gameLose = true;
            }
        }
    }

    /**
        Method for blocking the player by mountains within the game board (Edge case done inside player class)
    */
    private void blocked() {
        for (Mountains mountain : mountains) {
            if (player.getPos().equals(mountain.getPos())) {
                if (player.getKey() == KeyEvent.VK_UP) {
                    player.translate(0, 1);
                }
                if (player.getKey() == KeyEvent.VK_RIGHT) {
                    player.translate(-1, 0);
                }
                if (player.getKey() == KeyEvent.VK_DOWN) {
                    player.translate(0, -1);
                }
                if (player.getKey() == KeyEvent.VK_LEFT) {
                    player.translate(1, 0);
                }
            }
        }
    }

    /**
        Method for moving the enemy. Enemies are blocked by mountains, move toward the player and move once every other key press
    */
    private void enemyMove() {
        for (Enemy enemy : enemies) {
            boolean flag = true; //Enemies need to be blocked by mountains
            boolean enemyAlreadyMoved = false; //Enemy moves once per key press (We'll change it to every other key press within caller method)
            Point position = new Point();
            position.x = enemy.getX();
            position.y = enemy.getY();

            //Check if player is to the left of the enemy and if there is no mountain
            position.x = position.x - 1;
            for (Mountains mountain : mountains) {
                if (position.equals(mountain.getPos())) {
                    flag = false;
                }
            }
            if (flag && !enemyAlreadyMoved && player.getX() < enemy.getX()) {
                enemy.translate(-1, 0);
                enemyAlreadyMoved = true;
            }
            flag = true;

            //Otherwise check if player is to the right of the enemy and if there is no mountain
            position.x = enemy.getX();
            position.y = enemy.getY();
            position.x = position.x + 1;
            for (Mountains mountain : mountains) {
                if (position.equals(mountain.getPos())) {
                    flag = false;
                }
            }
            if (flag && !enemyAlreadyMoved && player.getX() > enemy.getX()) {
                enemy.translate(1, 0);
                enemyAlreadyMoved = true;
            }
            flag = true;

            //Otherwise check if player is above the enemy and if there is no mountain
            position.x = enemy.getX();
            position.y = enemy.getY();
            position.y = position.y - 1;
            for (Mountains mountain : mountains) {
                if (position.equals(mountain.getPos())) {
                    flag = false;
                }
            }
            if (flag && !enemyAlreadyMoved && player.getY() < enemy.getY()) {
                enemy.translate(0, -1);
                enemyAlreadyMoved = true;
            }
            flag = true;

            //Otherwise check if player is below the enemy and if there is no mountain
            position.x = enemy.getX();
            position.y = enemy.getY();
            position.y = position.y + 1;
            for (Mountains mountain : mountains) {
                if (position.equals(mountain.getPos())) {
                    flag = false;
                }
            }
            if (flag && !enemyAlreadyMoved && player.getY() > enemy.getY()) {
                enemy.translate(0, 1);
                enemyAlreadyMoved = true;
            }
        }
    }
}