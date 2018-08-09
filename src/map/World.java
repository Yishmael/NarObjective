package map;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import misc.Consts;
import types.TileType;
import types.WorldType;

public class World {
    private ScreenMap[][] maps;
    private WorldType worldType;
    private int[] tileTypeCount = new int[TileType.values().length];
    int worldWidth, worldHeight;

    public World(WorldType worldType) throws SlickException, IOException {
        this.worldType = worldType;

        // TODO make mapWidth and mapHeight independent of resolution and screen ratio
        BufferedImage bi = ImageIO.read(new File("res/images/worlds/" + worldType.toString() + ".bmp"));
        int mapWidth = Consts.MAP_WIDTH;
        int mapHeight = Consts.MAP_HEIGHT;
        worldWidth = bi.getWidth() / mapWidth;
        worldHeight = bi.getHeight() / mapHeight;
        WorldType.valueOf(worldType + "").setWidth(worldWidth);
        // System.out.println(worldtype + " " + worldWidth + " by " + worldHeight);

        maps = new ScreenMap[worldHeight][worldWidth];
        int[][][][] world = new int[worldHeight][worldWidth][mapHeight][mapWidth];

        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j++) {
                for (int k = 0; k < world[0][0].length; k++) {
                    for (int m = 0; m < world[0][0][0].length; m++) {
                        int pixel = bi.getRGB(j * mapWidth + m, i * mapHeight + k);
                        Color color = new Color(pixel);
                        for (TileType tileType: TileType.values()) {
                            if (tileType.getColor().equals(color)) {
                                tileTypeCount[tileType.ordinal()]++;
                                world[i][j][k][m] = tileType.ordinal();
                            }
                        }
                        if (j * mapWidth + m == 0
                                || j * mapWidth + m == (worldWidth - 1) * mapWidth + mapWidth - 1
                                || i * mapHeight + k == 0
                                || i * mapHeight + k == (worldHeight - 1) * mapHeight + mapHeight - 1) {
                            world[i][j][k][m] = TileType.edge.ordinal();
                        }
                    }
                }
                int index = 0;
                for (int n = 0; n < tileTypeCount.length; n++) {
                    if (tileTypeCount[n] > tileTypeCount[index]) {
                        index = n;
                    }
                }
                TileType dominantTileType = TileType.values()[index];
                maps[i][j] = new ScreenMap(world[i][j], dominantTileType, worldType, i * worldWidth + j);
                tileTypeCount = new int[TileType.values().length];
            }
        }
    }

    public World(WorldType worldType, boolean binary) throws IOException, SlickException {
        this.worldType = worldType;

        // TODO make mapWidth and mapHeight independent of resolution and screen ratio
        FileInputStream fis = new FileInputStream("res/images/worlds/" + worldType.toString() + ".bin");

        while (fis.read() != 0x2a);

        int fileWidth = fis.read();
        // System.out.println(fileWidth);
        fis.read(); // skip new 0d
        fis.read(); // skip new 0a
        fis.read(); // skip new 0a
        int fileHeight = fis.read();
        // System.out.println(fileHeight);
        fis.read(); // skip new 0d
        fis.read(); // skip new 0a
        fis.read(); // skip new 0a
        int mapWidth = Consts.MAP_WIDTH;
        int mapHeight = Consts.MAP_HEIGHT;
        worldWidth = fileWidth / mapWidth;
        worldHeight = fileHeight / mapHeight;
        WorldType.valueOf(worldType + "").setWidth(worldWidth);
        // System.out.println(worldtype + " " + worldWidth + " by " + worldHeight);

        maps = new ScreenMap[worldHeight][worldWidth];
        int[][][][] world = new int[worldHeight][worldWidth][mapHeight][mapWidth];

        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j++) {
                for (int k = 0; k < world[0][0].length; k++) {
                    for (int m = 0; m < world[0][0][0].length; m++) {
                        int b = fis.read();
                        b %= 255;
                        TileType tileType = TileType.values()[b];
                        tileTypeCount[tileType.ordinal()]++;
                        world[i][j][k][m] = tileType.ordinal();
                        if (j * mapWidth + m == 0
                                || j * mapWidth + m == (worldWidth - 1) * mapWidth + mapWidth - 1
                                || i * mapHeight + k == 0
                                || i * mapHeight + k == (worldHeight - 1) * mapHeight + mapHeight - 1) {
                            world[i][j][k][m] = TileType.edge.ordinal();
                        }
                    }
                }
                int index = 0;
                for (int n = 0; n < tileTypeCount.length; n++) {
                    if (tileTypeCount[n] > tileTypeCount[index]) {
                        index = n;
                    }
                }
                TileType dominantTileType = TileType.values()[index];
                maps[i][j] = new ScreenMap(world[i][j], dominantTileType, worldType, i * worldWidth + j);
                tileTypeCount = new int[TileType.values().length];
            }
        }

        fis.close();
    }

    public int getHeight() {
        return worldHeight;
    }

    public ScreenMap getMap(int i, int j) {
        return maps[i][j];
    }

    public ScreenMap getMap(Point mapCoords) {
        return maps[(int) mapCoords.getX()][(int) mapCoords.getY()];
    }

    public ScreenMap getMap(int mapId) {
        return maps[mapId / worldWidth][mapId % worldWidth];
    }

    public int getWidth() {
        return worldWidth;
    }

    public WorldType getWorldType() {
        return worldType;
    }

    public String getAreaName(int mapId) {
        int i = mapId / worldWidth;
        int j = mapId % worldWidth;

        if (worldType == WorldType.world1) {
            if (i == 0 && j == 0) {
                return "Debug town";
            }
            if (i == 2 && j == 2) { // bottom right
                return "River";
            } else if (i == 0 && j == 2) { // top right
                return "Snowy valley";
            } else if (i == 1 && j == 2) {
                return "Forest";
            }
        } else if (worldType == WorldType.zothouse) {
            return "Kimi's house";
        } else if (worldType == WorldType.farmeryihouse) {
            return "Abandoned house";
        } else if (worldType == WorldType.tower) {
            return "Tower";
        } else if (worldType == WorldType.mill) {
            return "The Mill";
        }

        return "";
    }
}
