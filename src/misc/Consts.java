package misc;

public final class Consts {

    public static final int SCREEN_WIDTH = 800, SCREEN_HEIGHT = 600;
    // public static final int SCREEN_WIDTH = 1366, SCREEN_HEIGHT = 768;
    public static final int FPS_LIMIT = 9999;
    public static final boolean FULLSCREEN = false;

    // public static final int MAP_WIDTH = 21, MAP_HEIGHT = 11;
    public static final int MAP_WIDTH = 25, MAP_HEIGHT = 19;

    public static final float TILE_WIDTH = SCREEN_WIDTH / MAP_WIDTH;
    public static final float TILE_HEIGHT = SCREEN_HEIGHT / MAP_HEIGHT;

    // public static final int TILE_WIDTH = 32;
    // public static final int TILE_HEIGHT = 32;

    public static final boolean SNAPPING = true;
    public static final boolean DEBUG = true;

    public static boolean SHOW_NAMES = !false;
    // TODO utilize this
    public static boolean SHOW_ITEM_NAMES = SHOW_NAMES && !false;
    public static boolean SHOW_NPC_NAMES = SHOW_NAMES && true;
    public static boolean SHOW_PLAYER_NAME = SHOW_NAMES && !false;

    public Consts() throws Exception {
        throw new Exception("Nope.");
    }

}
