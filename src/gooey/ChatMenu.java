package gooey;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import misc.Text;

public class ChatMenu {

    public static final int DEFAULT = 0;
    public static final int SIGN = 1;

    private Image image;
    private float x, y;
    private int width, height, imageWidth, imageHeight;
    private ArrayList<Rectangle> rects = new ArrayList<>();
    private boolean shown;
    private int highlightedIndex = -1;

    private ArrayList<String> lines = new ArrayList<>();
    private ArrayList<String> commands = new ArrayList<>();

    public ChatMenu(float x, float y) throws SlickException {
        this.x = x;
        this.y = y;

        width = 250;
        height = 100;

        image = new Image("res/images/gooey/chatmenu1.png");
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();

    }

    public String getDialogueData(int i) {
        if (commands.size() > i) {
            return commands.get(i);
        } else {
            return null;
        }
        // TODO fix to only return one dialogue line even if the same unit completes and provides
        // the quest
    }

    public void highlightOption(int i) {
        highlightedIndex = i;
    }

    public void clearLines() {
        lines.clear();
        commands.clear();
    }

    public ArrayList<Rectangle> getRects() {
        return shown ? rects : new ArrayList<Rectangle>();
    }

    public void addDialogueData(String data) {
        rects.add(new Rectangle(x + 5, 20 * lines.size() + y, width - 5, 15));

        lines.add(data.split(":")[0]);
        commands.add(data.split(":")[1]);
    }

    public void show() {
        shown = true;
    }

    public void hide() {
        shown = false;
    }

    public void draw(Graphics g) {
        if (shown) {
            g.drawImage(image, x, y, x + width, y + height, 0, 0, imageWidth, imageHeight,
                    new Color(255, 255, 255, 200));
            int i = 0;
            for (String line: lines) {
                if (i == highlightedIndex) {
                    g.setColor(Color.yellow);
                } else {
                    g.setColor(Color.gray);
                }
                g.drawString(Text.wordWrap(line, width), x + 5, 20 * i + y);
                i++;
            }
        }
    }
}
