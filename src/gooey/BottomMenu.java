package gooey;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import misc.Consts;

public class BottomMenu {

    private boolean shown = !!false;

    public void draw(Graphics g, String worldName, String areaName) {
        if (!shown) {
            return;
        }
        g.setColor(Color.darkGray);
        g.fillRect(0, Consts.SCREEN_HEIGHT - 100, 800, 100);

        // g.setColor(Color.red);
        // g.drawRect(200, Consts.SCREEN_HEIGHT - 70, 100, 50);
        // g.fillRect(200, Consts.SCREEN_HEIGHT - 70, 100f * player.getHealth() /
        // player.getMaxHealth(), 50);

        // g.setColor(Color.cyan);
        // g.drawRect(320, Consts.SCREEN_HEIGHT - 70, 100, 50);
        // g.fillRect(320, Consts.SCREEN_HEIGHT - 70, 100f * player.getMana() / player.getMaxMana(),
        // 50);

        g.setColor(Color.yellow);
        g.drawRect(440, Consts.SCREEN_HEIGHT - 70, 100, 50);
        g.drawString("stat1: " + 0, 440, Consts.SCREEN_HEIGHT - 70);
        g.drawString("stat2: " + 0, 440, Consts.SCREEN_HEIGHT - 55);
        g.drawString("stat3: " + 0, 440, Consts.SCREEN_HEIGHT - 40);

        g.setColor(Color.white);
        g.drawString(worldName, Consts.SCREEN_WIDTH - 200, Consts.SCREEN_HEIGHT - 70);
        g.drawString(areaName, Consts.SCREEN_WIDTH - 200, Consts.SCREEN_HEIGHT - 55);
    }
}
