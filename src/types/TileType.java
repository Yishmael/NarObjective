package types;

import org.newdawn.slick.Color;

public enum TileType {

    grass(new Color(0, 255, 0), true), //
    snow(new Color(255, 255, 255), true), //
    redbrickwall(new Color(255, 150, 100), false), //
    wood1(new Color(225, 150, 80), true), //
    sand(new Color(200, 200, 0), true), //
    wood2(new Color(200, 80, 80), true), //
    dirt(new Color(150, 100, 0), true), //
    graybrickwall(new Color(128, 128, 128), false), //
    ice(new Color(100, 255, 255), true), //
    rubble(new Color(100, 100, 100), false),
    leaves(new Color(0, 200, 0), true),
    water1(new Color(0, 100, 255), false), //
    swamp(new Color(0, 180, 80), true), //
    mud(new Color(160, 100, 30), true), //
    edge(new Color(1, 1, 1), false),
    water2(new Color(0, 0, 255), false), //
    nil(new Color(0, 0, 0), false);

    private Color color;
    private boolean walkable;

    TileType(Color color, boolean walkable) {
        this.color = color;
        this.walkable = walkable;
    }

    public Color getColor() {
        return color;
    }

    public boolean isWalkable() {
        return walkable;
    }

}
