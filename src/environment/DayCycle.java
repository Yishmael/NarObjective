package environment;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import misc.Consts;

public class DayCycle {
    private Image image;
    private boolean enabled;

    public DayCycle() throws SlickException {
        image = new Image("res/images/environment/night.png");

    }

    public void draw(Graphics g) {
        if (enabled) {
            g.drawImage(image, 0, 0, Consts.SCREEN_WIDTH, Consts.SCREEN_HEIGHT, 0, 0, 800, 600);
        }
    }

    public void drawCenteredAt(Graphics g, float x, float y) {
        // TODO fix this
        if (enabled) {
            g.drawImage(image, x - Consts.SCREEN_WIDTH / 2, y - Consts.SCREEN_HEIGHT / 2,
                    x + Consts.SCREEN_WIDTH / 2, y + Consts.SCREEN_HEIGHT / 2, 0, 0, 1300, 1300);
        }
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }
}
