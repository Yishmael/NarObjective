package entities.units;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import misc.Consts;

public class Player extends Unit {
    private int gold = 100;

    public Player(String name, float x, float y, float speed, int width, int height) throws SlickException {
        super("res/images/entities/player/player1.png", x, y, speed, width, height);

        maxHealth = 5000;
        health = 5000;

        this.name = name;
        ss = new SpriteSheet("res/images/entities/player/player1.png", 32, 32);
        animation = new Animation(ss, 550);
    }

    public Player(String name, float x, float y, float speed) throws SlickException {
        this(name, x, y, speed, (int) Consts.TILE_WIDTH, (int) Consts.TILE_HEIGHT);
    }

    public int getGold() {
        return gold;
    }

    @Override
    public ArrayList<String> process(String message) {
        if (message == null) {
            return null;
        }
        String cmd = message.split(" ")[0];
        String msg = message.split(" ")[1];

        if (cmd.equals("damage")) {
            int amount = Integer.parseInt(msg);
            damage(amount);
        }
        return null;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    @Override
    public void update(int dt) {
    }

}
