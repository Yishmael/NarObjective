package gooey;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import entities.objects.Item;
import misc.Consts;

public class TradeWindow {
    private boolean tradeOpened;
    private Image tradeImage;
    private Item[] items = new Item[9];
    private int id;
    private Rectangle[] rects = new Rectangle[9];
    private int x = 500, y = 70;

    public TradeWindow() throws SlickException {
        tradeImage = new Image("res/images/gooey/trade.png");

        for (int i = 0; i < rects.length; i++) {
            rects[i] = new Rectangle(x + Consts.TILE_WIDTH * (i % 3), y + Consts.TILE_WIDTH * (i / 3),
                    Consts.TILE_WIDTH, Consts.TILE_HEIGHT);
        }
    }

    public void draw(Graphics g) {
        if (tradeOpened) {
            g.drawImage(tradeImage, x, y);
            for (int i = 0; i < items.length; i++) {
                if (items[i] != null) {
                    items[i].drawAt(g, x + Consts.TILE_WIDTH * (i % 3), y + Consts.TILE_HEIGHT * (i / 3));
                }
            }
        }
    }

    public void removeItemAt(int index) {
        items[index] = null;
    }

    public Rectangle[] getRects() {
        return rects;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public int getTradeId() {
        return id;
    }

    public void setTradeId(int id) {
        this.id = id;
    }

    public void close() {
        tradeOpened = false;
    }

    public void open() {
        tradeOpened = true;
    }

    public boolean isTradeOpened() {
        return tradeOpened;
    }
}
