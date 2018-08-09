package environment;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import misc.Consts;
import misc.MainGame;
import types.DecorationType;

public class Decoration {

    private float x, y;
    private Image image;
    private SpriteSheet ss;
    private Animation animation;
    private int width, height;
    private boolean collidable;
    private DecorationType decorationType;

    public Decoration(DecorationType decorationType, float x, float y, int width, int height)
            throws SlickException {
        this.decorationType = decorationType;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        ss = new SpriteSheet("res/images/sprites.png", decorationType.getImageWidth(),
                decorationType.getImageHeight());
        image = ss.getSubImage(decorationType.getSpriteX(), decorationType.getSpriteY(),
                decorationType.getImageWidth(), decorationType.getImageHeight());
        // TODO add animations to some decorations
    }

    public Decoration(DecorationType decorationType, float x, float y) throws SlickException {
        this(decorationType, x, y, decorationType.getImageWidth(), decorationType.getImageHeight());
    }

    public boolean hasCollision() {
        return collidable;
    }

    public void toggleCollision() {
        collidable = !collidable;
    }

    public DecorationType getdecorationType() {
        return decorationType;
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
            animation.getCurrentFrame().draw(drawX, drawY, width, height);
            animation.update(MainGame.dt);
        } else {
            g.setColor(Color.yellow);
            g.drawImage(image, drawX, drawY, drawX + width, drawY + height, 0, 0,
                    decorationType.getImageWidth(), decorationType.getImageHeight());
            if (Consts.DEBUG) {
                g.drawRect(drawX + (width - Consts.TILE_WIDTH) / 2, drawY + (height - Consts.TILE_HEIGHT),
                        Consts.TILE_WIDTH, Consts.TILE_HEIGHT);
            }
        }
        // g.draw(new Rectangle(drawX, drawY, width, height));
    }
}
