package gooey;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import entities.objects.Item;
import misc.Consts;

public class InventoryWindow {
    private Rectangle[] rects = new Rectangle[9];
    private float x, y;
    private float width, height;
    private int imageWidth, imageHeight;
    private Image image;
    private boolean shown, showItemInfo;
    private Item[] items = new Item[9];
    private int shownItemIndex = -1, gold;
    private String itemName, itemDescription;

    public InventoryWindow(float x, float y, float width, float height) throws SlickException {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        image = new Image("res/images/gooey/inventory.png");
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();

        for (int i = 0; i < rects.length; i++) {
            rects[i] = new Rectangle(10 + (Consts.TILE_WIDTH * (i % 3)),
                    10 + y + Consts.TILE_HEIGHT * (i / 3), Consts.TILE_WIDTH, Consts.TILE_HEIGHT);
        }
    }

    public void draw(Graphics g) {
        if (!shown) {
            return;
        }
        g.drawImage(image, x, y, x + width, y + height, 0, 0, imageWidth, imageHeight);
        for (int i = 0; i < rects.length; i++) {
            g.setColor(Color.magenta);
            g.drawRect(rects[i].getX(), rects[i].getY(), Consts.TILE_WIDTH, Consts.TILE_HEIGHT);
        }

        g.setColor(Color.orange);
        for (int i = 0; i < 9; i++) {
            Item item = items[i];
            if (item != null) {
                float x = 10 + (Consts.TILE_WIDTH * (i % 3));
                float y = 10 + (Consts.SCREEN_HEIGHT - 320) + Consts.TILE_HEIGHT * (i / 3);
                item.drawAt(g, x, y);
                g.setColor(Color.blue);
                g.drawRect(x, y, Consts.TILE_WIDTH, Consts.TILE_HEIGHT);
                g.setColor(Color.black);
                g.drawString(i + 1 + "", x, y);
                if (showItemInfo) {
                    if (i == shownItemIndex) {
                        g.drawString(itemName, x, y - 30);
                        g.drawString(itemDescription, x, y - 10);
                    }
                }
            }

        }
        g.setColor(Color.black);
        g.drawString("Gold: " + gold, 10, Consts.SCREEN_HEIGHT - 180);
    }

    public void setShowItemInfo(boolean display) {
        this.showItemInfo = display;
    }

    public void setDisplayedItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setDisplayedItemIndex(int i) {
        this.shownItemIndex = i;
    }

    public void setDisplayedItemName(String name) {
        this.itemName = name;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void open() {
        shown = true;
    }

    public void close() {
        shown = true;
    }

    public Rectangle[] getRects() {
        return shown ? rects : new Rectangle[] {};
    }

    public void toggle() {
        shown = !shown;
    }

}
