package misc;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class BlackScreen {
    int transitionTime = 700;
    private Color color = new Color(0f, 0f, 0f, 1f);
    private float upperLimit = 1f;
    private float lowerLimit = 0f;
    private boolean activated;
    private Rectangle rect = new Rectangle(0, 0, Consts.SCREEN_WIDTH, Consts.SCREEN_HEIGHT);
    private int direction = 1;

    public void update(int dt) {
        // TOOD pause the game while black screen in progress
        if (activated) {
            color.a = direction == 1 ? Math.max(lowerLimit, color.a - direction * (float) dt / transitionTime)
                    : Math.min(upperLimit, color.a - direction * (float) dt / transitionTime);

            direction = color.a == lowerLimit ? -1 : color.a == upperLimit ? 1 : direction;
        }
    }

    public void draw(Graphics g) {
        if (activated) {
            g.setColor(color);
            g.fill(rect);
        }
    }

    public void setTransitionTime(int transitionTime) {
        this.transitionTime = transitionTime;
    }

    public int getTransitiontime() {
        return transitionTime;
    }

    public void activate() {
        activated = true;
    }
}
