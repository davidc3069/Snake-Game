import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Panel extends JPanel implements ActionListener {

    static final int WIDTH = 850;
    static final int HEIGHT = 750;
    static final int BLOCK = 50;
    static final int BOARD = (HEIGHT * WIDTH) / (BLOCK * BLOCK);
    final Timer timer = new Timer(150, this);
    final Font font = new Font("TimesRoman", Font.BOLD, 30);

    int[] snakeX = new int[BOARD];
    int[] snakeY = new int[BOARD];
    int snakeSize;
    char direction = 'R';
    boolean isMoving = false;
    Food food;
    int foodScore;




    public Panel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (isMoving) {

                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                            if (direction != 'R') {
                                direction = 'L';
                            }
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (direction != 'L') {
                                direction = 'R';
                            }
                            break;
                        case KeyEvent.VK_UP:
                            if (direction != 'D') {
                                direction = 'U';
                            }
                            break;
                        case KeyEvent.VK_DOWN:
                            if (direction != 'U') {
                                direction = 'D';
                            }
                            break;
                    }
                }
                else {
                    gameStart();
                }
            }
        });

        gameStart();
    }

    protected void gameStart() {
        snakeY = new int[BOARD];
        snakeX = new int[BOARD];
        snakeSize = 5;
        foodScore = 0;
        direction = 'R';
        isMoving = true;
        spawnFood();
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(isMoving) {
            g.setColor(Color.RED);
            g.fillRect(food.getPosX(), food.getPosY(), BLOCK, BLOCK);
            g.setColor(Color.GREEN);
            for (int i = 0; i < snakeSize; ++i) {
                g.fillRect(snakeX[i], snakeY[i], BLOCK, BLOCK);
            }
        }
        else {
            String text = String.format("GAME OVER | SCORE: %d | Press Any Key to Play Again!", foodScore);
            g.setColor(Color.GREEN);
            g.setFont(font);
            g.drawString(text, (WIDTH - getFontMetrics(g.getFont()).stringWidth(text)) / 2, HEIGHT / 2);
        }
    }

    protected void move() {
        for (int i = snakeSize; i > 0; --i) {
            snakeX[i] = snakeX[i-1];
            snakeY[i] = snakeY[i-1];
        }
        switch (direction) {
            case 'U' -> snakeY[0] -= BLOCK;
            case 'D' -> snakeY[0] += BLOCK;
            case 'L' -> snakeX[0] -= BLOCK;
            case 'R' -> snakeX[0] += BLOCK;
        }
    }

    protected void spawnFood() {
        food = new Food();
    }

    protected void resetFood() {
        if ((snakeX[0] == food.getPosX()) && (snakeY[0] == food.getPosY())) {
            ++snakeSize;
            ++foodScore;
            spawnFood();
        }
    }

    protected void checkCrash() {
        for (int i = snakeSize; i > 0; --i) {
            if ((snakeX[0] == snakeX[i]) && (snakeY[0] == snakeY[i])) {
                isMoving = false;
                break;
            }
        }
        if (snakeX[0] < 0 || snakeX[0] > WIDTH - BLOCK || snakeY[0] <0 || snakeY[0] > HEIGHT - BLOCK) {
            isMoving = false;

        }

        if(!isMoving) {
            timer.stop();
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {

        if (isMoving) {
            move();
            checkCrash();
            resetFood();
        }
        repaint();
    }
}
