package types;

public enum WorldType {

    world1("Great Meadows", 3),
    world2("Western Desert", 3),
    world3("Icy Lakes", 3),

    zothouse("Great Meadows", 1),
    farmeryihouse("Great Meadows", 1),
    tower("Great Meadows", 1),
    mill("Great Meadows", 1),

    nil("FORBIDDEN AREA", 0);

    private String worldName;
    private int worldWidth;

    WorldType(String worldName, int worldWidth) {
        this.worldName = worldName;
        this.worldWidth = worldWidth;
    }

    public String getWorldName() {
        return worldName;
    }

    public int getWidth() {
        return worldWidth;
    }

    public void setWidth(int worldWidth) {
        this.worldWidth = worldWidth;
    }
}
