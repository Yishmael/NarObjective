package entities.objects;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import entities.Entity;
import misc.Consts;
import types.ItemType;

public class Item extends Entity {
    private String description;
    private ItemType itemType;
    private boolean destroyedOnUse;

    public Item(ItemType itemType) throws SlickException {
        this(itemType, 0, 0);
    }

    public Item(ItemType itemType, float x, float y) throws SlickException {
        super("res/images/entities/items/" + itemType.toString() + ".png", x, y);
        this.itemType = itemType;
        this.name = itemType.getName();

        // NOTE temp
        if (itemType == ItemType.fish1) {
            destroyedOnUse = true;
        }

        description = itemType.getDescription();
    }

    public void drawAt(Graphics g, float x, float y) {
        g.drawImage(image, x, y, x + Consts.TILE_WIDTH, y + Consts.TILE_HEIGHT, 0, 0, 64, 64);
        if (Consts.DEBUG) {
            g.setColor(Color.yellow);
            g.drawRect(x, y, Consts.TILE_WIDTH, Consts.TILE_HEIGHT);
        }
    }

    public String getDescription() {
        return description;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public float getPrice() {
        return 10;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String use() {
        System.out.println("UNUSED");
        return null;
    }

    public boolean isDestroyedOnUse() {
        return destroyedOnUse;
    }

    @Override
    public ArrayList<String> process(String message) {
        ArrayList<String> result = new ArrayList<>();

        for (String msg: itemType.getCommands()) {
            result.add(msg);
        }

        return result;
    }

    @Override
    public void update(int dt) {
        // TODO Auto-generated method stub

    }

}
