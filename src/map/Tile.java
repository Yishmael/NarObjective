package map;

import types.TileType;

public class Tile {
    // temp
    public static String process(TileType tileType, String message) {
        if (tileType == TileType.water2) {
            System.out.println("You can't swim... Yet.");
        } else if (tileType == TileType.sand) {
            if (message.equals("CMDshovel1")) {
                return "tilechange GW";
            }
        }

        return null;
    }
}
