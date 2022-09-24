
import java.util.Random;


public class Food {

    private final int posX;
    private final int posY;

    public Food() {
        posX = getPos(Panel.WIDTH);
        posY = getPos(Panel.HEIGHT);

    }

    private int getPos(int size) {
        Random rand = new Random();
        return rand.nextInt(size / Panel.BLOCK) * Panel.BLOCK;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
