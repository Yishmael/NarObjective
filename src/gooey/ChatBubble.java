package gooey;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import misc.MainGame;
import misc.Text;

public class ChatBubble {

    public static final int DEFAULT = 0;
    public static final int SIGN = 1;

    private Image[] images = new Image[2];
    private float x, y;
    private int width, height;
    private int[] imageWidths = new int[2];
    private int[] imageHeights = new int[2];
    private int style = DEFAULT;
    private long lastTimeBubbleOpened;
    private boolean shown;

    private ArrayList<String> paragraphs = new ArrayList<>();

    public ChatBubble(float x, float y) throws SlickException {
        this.x = x;
        this.y = y;

        images[0] = new Image("res/images/gooey/chatbubble1.png");
        imageWidths[0] = images[0].getWidth();
        imageHeights[0] = images[0].getHeight();

        images[1] = new Image("res/images/gooey/chatbubble2.png");
        imageWidths[1] = images[1].getWidth();
        imageHeights[1] = images[1].getHeight();

    }

    public void addText(String text) {
        paragraphs.add(text);
    }

    public void clearText() {
        paragraphs.clear();
    }

    public void show() {
        shown = true;
        lastTimeBubbleOpened = MainGame.getTime();
    }

    public void hide() {
        shown = false;
    }

    public void draw(Graphics g) {

        if (shown) {
            if (MainGame.getTime() - lastTimeBubbleOpened < getDuration()) {
                g.drawImage(images[style], x, y, x + width, y + height, 0, 0, imageWidths[style],
                        imageHeights[style], new Color(255, 255, 255, 200));
                int i = 0;
                for (String paragraph: paragraphs) {
                    g.setColor(Color.yellow);
                    g.drawString(Text.wordWrap(paragraph, width), x + 5, 20 * i + y);
                    i++;
                }
            } else {
                shown = false;
            }
        }
    }

    private int getDuration() {
        if (paragraphs.size() > 0) {
            return 150 + 35 * paragraphs.get(0).length();
        }
        return 0;
    }

    public void setStyle(int style) {
        this.style = style;
        if (style == DEFAULT) {
            width = 250;
            height = 100;
        } else if (style == SIGN) {
            width = 220;
            height = 120;
        }
    }

}
