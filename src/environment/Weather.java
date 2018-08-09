package environment;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import misc.Consts;

public class Weather {

    // add other density constants and types
    public static final int rain = 1;
    public static final int snow = 2;

    private int weatherType;
    private boolean enabled;
    private Rectangle[] particles;
    private Image image;
    private int width, height;
    private float speed;

    public Weather(int weatherType, int density) throws SlickException {
        this.weatherType = weatherType;
        particles = new Rectangle[density];

        if (weatherType == rain) {
            image = new Image("res/images/environment/weather/rain.png");
            width = 5;
            height = 20;
            speed = 25;
        } else if (weatherType == snow) {
            image = new Image("res/images/environment/weather/snow.png");
            width = 25;
            height = 25;
            speed = 5;
        }

        for (int i = 0; i < particles.length; i++) {
            particles[i] = new Rectangle((int) (width + Math.random() * (Consts.SCREEN_WIDTH - 35)), 50,
                    width, height);
        }
    }

    public void disable() {
        enabled = false;
    }

    public void draw(Graphics g) {
        if (!enabled) {
            return;
        }
        if (weatherType == rain || weatherType == snow) {
            for (Rectangle particle: particles) {
                g.drawImage(image, particle.getX(), particle.getY(), particle.getX() + width,
                        particle.getY() + height, 0, 0, 32, 32);
                if (Consts.DEBUG) {
                    // g.draw(particle);
                }
            }
        } else if (weatherType == -3) {

        }
    }

    public void enable() {
        enabled = true;
    }

    // TODO make particles move sideways (especially snow)
    public void update(int dt) {
        for (Rectangle particle: particles) {
            if (particle.getY() > Consts.SCREEN_HEIGHT / 1.5f && Math.random() < 0.003) {
                particle.setX((int) (width + Math.random() * (Consts.SCREEN_WIDTH - 25)));
                particle.setY(5);
                continue;
            }
            particle.setY((float) (particle.getY() + (speed / 2 + Math.random() * speed) * 30 * dt / 1000f));
            if (particle.getY() > Consts.SCREEN_HEIGHT - height) {
                particle.setX((int) (10 + Math.random() * (Consts.SCREEN_WIDTH - 25)));
                particle.setY(5);
            }
        }
    }

}
