package entities;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;

import misc.Consts;
import misc.MainGame;

public abstract class Entity {
    protected float x, y;
    protected String name;
    protected Image image;

    protected int facing;
    protected SpriteSheet ss;
    protected Animation animation;
    protected int imageWidth, imageHeight;
    protected int width, height;
    protected boolean collidable = true;

    public Entity(String imagePath, float x, float y, int width, int height) throws SlickException {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        image = new Image(imagePath);
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
        image.draw(0, 0, 0, 0, null); // miraculous fix for center of rotation
        image.setCenterOfRotation(width / 2, height / 2);
    }

    public Entity(String imagePath, float x, float y) throws SlickException {
        this(imagePath, x, y, (int) Consts.TILE_WIDTH, (int) Consts.TILE_HEIGHT);
    }

    public boolean hasCollision() {
        return collidable;
    }

    public void toggleCollision() {
        collidable = !collidable;
    }

    public void draw(Graphics g) {

        float drawX, drawY;

        if (Consts.SNAPPING) {
            drawX = x - x % Consts.TILE_WIDTH;
            drawY = y - y % Consts.TILE_HEIGHT;
        } else {
            drawX = x;
            drawY = y;
        }

        drawX -= (width - Consts.TILE_WIDTH) / 2;
        drawY -= (height - Consts.TILE_HEIGHT);

        if (animation != null) {
            animation.getCurrentFrame().setCenterOfRotation(Consts.TILE_WIDTH / 2, Consts.TILE_HEIGHT / 2);
            animation.getCurrentFrame().setRotation(facing * -90);
            animation.getCurrentFrame().draw(drawX, drawY, width, height);
            animation.update(MainGame.dt);
        } else {
            g.setColor(Color.yellow);
            g.drawImage(image, drawX, drawY, drawX + width, drawY + height, 0, 0, imageWidth, imageHeight);
            if (Consts.DEBUG) {
                g.drawRect(drawX + (width - Consts.TILE_WIDTH) / 2, drawY + (height - Consts.TILE_HEIGHT),
                        Consts.TILE_WIDTH, Consts.TILE_HEIGHT);
            }
        }
        if (Consts.SHOW_NAMES) {
            g.setColor(Color.black);
            g.drawString(name, drawX, drawY - 20);
        }
        // g.draw(new Rectangle(drawX, drawY, width, height));
    }

    public int getFacing() {
        return facing;
    }

    public String getName() {
        return name;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public abstract ArrayList<String> process(String message);

    public void setFacing(int facing) {
        this.facing = facing;

        switch (facing) {
        case 0:
            image.setRotation(0);
            break;
        case 1:
            image.setRotation(-90);
            break;
        case 2:
            image.setRotation(180);
            break;
        case 3:
            image.setRotation(90);
            break;
        }
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setLocation(Point point) {
        x = point.getX();
        y = point.getY();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public abstract void update(int dt);
}
