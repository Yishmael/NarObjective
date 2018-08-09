package map;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import entities.objects.GroundObject;
import entities.objects.Item;
import entities.units.NPC;
import entities.units.Player;
import environment.Decoration;
import misc.Consts;
import types.TileType;
import types.WorldType;

public class ScreenMap {
    private Image[] images = new Image[TileType.values().length];
    private Image backgroundImage;
    private int[][] map;
    private ArrayList<NPC> npcs = new ArrayList<>();
    private ArrayList<GroundObject> groundObjects = new ArrayList<>();
    private ArrayList<Item> droppedItems = new ArrayList<>();
    private ArrayList<Decoration> doodads = new ArrayList<>();
    private int mapId;
    private TileType dominantTileType;

    public ScreenMap(int[][] map, TileType dominantTileType, WorldType worldType, int mapId)
            throws SlickException {
        this.map = map;
        this.dominantTileType = dominantTileType;
        this.mapId = mapId;
        for (int i = 0; i < images.length; i++) {
            images[i] = new Image("res/images/terrain/" + TileType.values()[i] + ".png");
        }

        backgroundImage = new Image("res/images/terrain/backgrounds/" + dominantTileType.toString() + ".png");

        npcs.addAll(Spawner.getNpcs(worldType, mapId));
        groundObjects.addAll(Spawner.getGroundObjects(worldType, mapId));
        droppedItems.addAll(Spawner.getItems(worldType, mapId));
        doodads.addAll(Spawner.getDecorations(worldType, mapId));
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, Consts.SCREEN_WIDTH - Consts.SCREEN_WIDTH % Consts.TILE_WIDTH,
                Consts.SCREEN_HEIGHT - Consts.SCREEN_HEIGHT % Consts.TILE_HEIGHT, 0, 0, 800, 600);

        for (int i = 0; i < map[0].length; i++) {
            for (int j = 0; j < map.length; j++) {
                int tileX = (i);
                int tileY = (j);

                if (map[j][i] != dominantTileType.ordinal()) {
                    g.drawImage(images[map[j][i]], Consts.TILE_WIDTH * tileX, Consts.TILE_HEIGHT * tileY,
                            Consts.TILE_WIDTH * tileX + Consts.TILE_WIDTH,
                            Consts.TILE_HEIGHT * tileY + Consts.TILE_HEIGHT, 0, 0, 64, 64);
                }

                try {
                    if (!(Math.random() < 1)) {
                        if (j - 1 > -1 && map[j][i] == TileType.grass.ordinal()
                                && map[j - 1][i] == TileType.water1.ordinal()) {
                            g.drawImage(new Image("res/images/terrain/watergrassNS.png"),
                                    Consts.TILE_WIDTH * tileX, Consts.TILE_HEIGHT * tileY,
                                    Consts.TILE_WIDTH * tileX + Consts.TILE_WIDTH,
                                    Consts.TILE_HEIGHT * tileY + Consts.TILE_HEIGHT, 0, 0, 64, 64);
                        } else if (j + 1 < map.length && map[j][i] == TileType.grass.ordinal()
                                && map[j + 1][i] == TileType.water1.ordinal()) {
                            g.drawImage(new Image("res/images/terrain/watergrassSN.png"),
                                    Consts.TILE_WIDTH * tileX, Consts.TILE_HEIGHT * tileY,
                                    Consts.TILE_WIDTH * tileX + Consts.TILE_WIDTH,
                                    Consts.TILE_HEIGHT * tileY + Consts.TILE_HEIGHT, 0, 0, 64, 64);
                        }
                    }
                } catch (SlickException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void drawItems(Graphics g) {
        g.setColor(Color.green);
        for (Item item: droppedItems) {
            item.draw(g);
        }
    }

    // make it a function that draws NPC's and groundObjects
    public void drawObjects(Graphics g) {

        for (Decoration doo: doodads) {
            doo.draw(g);
        }

        g.setColor(Color.green);
        for (NPC npc: npcs) {
            npc.draw(g);
        }
        for (GroundObject ent: groundObjects) {
            ent.draw(g);
        }

    }

    public void drawObjectsByY(Graphics g, float lowerLimit, float upperLimit) {

        g.setColor(Color.green);
        for (NPC npc: npcs) {
            if (npc.getY() >= lowerLimit && npc.getY() <= upperLimit) {
                npc.draw(g);
            }
        }
        for (GroundObject ent: groundObjects) {
            if (ent.getY() >= lowerLimit && ent.getY() <= upperLimit) {
                ent.draw(g);
            }
        }

    }

    public void dropItemAt(Item item, float x, float y) {
        if (Consts.SNAPPING) {
            item.setX((int) x - (int) x % Consts.TILE_WIDTH);
            item.setY((int) y - (int) y % Consts.TILE_HEIGHT);
        } else {
            item.setX(x);
            item.setY(y);
        }
        droppedItems.add(item);
    }

    public ArrayList<Item> getDroppedItems() {
        return droppedItems;
    }

    public GroundObject getEntityAt(float x, float y) {
        int i = (int) x;
        int j = (int) y;
        for (GroundObject ent: groundObjects) {
            if ((int) (ent.getX() / Consts.TILE_WIDTH) == i
                    && ((int) (ent.getY() / Consts.TILE_HEIGHT) == j)) {
                return ent;
            }
        }

        return null;
    }

    public int[][] getMap() {
        return map;
    }

    public int getMapId() {
        return mapId;
    }

    public NPC getNpcAt(float x, float y) {
        int i = (int) x;
        int j = (int) y;

        for (NPC npc: npcs) {
            if ((int) (npc.getX() / Consts.TILE_WIDTH) == i
                    && ((int) (npc.getY() / Consts.TILE_HEIGHT) == j)) {
                return npc;
            }
        }

        return null;
    }

    public ArrayList<NPC> getNpcs() {
        return npcs;
    }

    public TileType getTileType(float x, float y) {
        int tileX = (int) (x);/// Consts.TILE_SIZE);
        int tileY = (int) (y);/// Consts.TILE_SIZE);
        if (x < 0 || tileX >= map[0].length || y < 0 || tileY >= map.length) {
            return TileType.nil;
        }

        return TileType.values()[map[tileY][tileX]];
    }

    public void interact(Player player) {
        for (NPC npc: npcs) {
            if (isAdjecant(npc.getX(), npc.getY(), player.getX(), player.getY())) {
                if (npc.isAttackReady()) {
                    player.process(npc.attack());
                }
            } else {
                if (npc.getDamage() > 0) {
                    npc.clearPaths();
                    npc.addPoint(new Point((int) (player.getX() / Consts.TILE_WIDTH),
                            (int) (player.getY() / Consts.TILE_HEIGHT)));
                }
            }
        }
    }

    private boolean isAdjecant(double x1, double y1, double x2, double y2) {
        // TODO fix the distance calculation due to variable tile widths and heights
        if (Consts.SNAPPING) {
            return Math
                    .sqrt(Math.pow((getTileX(x1, y1) - getTileX(x2, y2)), 2) * Consts.TILE_WIDTH
                            / Consts.TILE_HEIGHT + Math.pow((getTileY(x1, y1) - getTileY(x2, y2)), 2))
                    * Consts.TILE_HEIGHT / Consts.TILE_WIDTH <= 1.5f;
        } else {
            return Math.sqrt(Math.pow(x1 - x2, 2)
                    + Math.pow(y1 - y2, 2)) <= (Consts.TILE_WIDTH + Consts.TILE_HEIGHT) / 2 + 5;
        }
    }

    private int getTileX(double x, double y) {
        return (int) (y / Consts.TILE_WIDTH);
    }

    private int getTileY(double x, double y) {
        return (int) (x / Consts.TILE_HEIGHT);
    }

    public boolean isWalkable(int tileX, int tileY) {
        if (tileX < 0 || tileX >= map[0].length || tileY < 0 || tileY >= map.length) {
            return false;
        }

        TileType tile = TileType.values()[map[tileY][tileX]];

        if (!tile.isWalkable()) {
            return false;
        }

        for (NPC npc: npcs) {
            if (npc.hasCollision()) {
                // if (npc.getRectangle().includes(tileX * Consts.TILE_WIDTH, tileY *
                // Consts.TILE_HEIGHT)) {
                // return false;
                // }
                if ((int) (npc.getX() / Consts.TILE_WIDTH) == tileX
                        && (int) (npc.getY() / Consts.TILE_HEIGHT) == tileY) {
                    return false;
                }
            }
        }

        for (GroundObject ent: groundObjects) {
            if (ent.hasCollision()) {
                // if (ent.getRectangle().includes(tileX * Consts.TILE_WIDTH, tileY *
                // Consts.TILE_HEIGHT)) {
                // return false;
                // }
                if ((int) (ent.getX() / Consts.TILE_WIDTH) == tileX
                        && (int) (ent.getY() / Consts.TILE_HEIGHT) == tileY) {
                    return false;
                }
            }
        }

        return true;
    }

    public void setTileType(int tileX, int tileY, TileType tileType) {
        map[tileY][tileX] = tileType.ordinal();
    }

    public void update(int dt) {
        System.out.println("UNUSED");
    }

    public void updateNpcs(int dt) {
        for (NPC npc: npcs) {
            npc.update(dt);
        }
    }

}
