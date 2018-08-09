package map;

import org.newdawn.slick.geom.Point;

import misc.Consts;
import types.WorldType;

public class GlobalLocation {
    private WorldType worldType;
    private int mapId;
    private int tileX, tileY;
    private Point worldCoords;

    public GlobalLocation(WorldType worldType, int mapId, float x, float y) {
        this.worldType = worldType;
        this.mapId = mapId;
        this.tileX = (int) (x / Consts.TILE_WIDTH);
        this.tileY = (int) (y / Consts.TILE_HEIGHT);

        worldCoords = new Point(mapId / worldType.getWidth(), mapId % worldType.getWidth());
    }

    public static GlobalLocation teleportFrom(GlobalLocation currentLocation) {

        // NOTE tileX and tileY are swapped
        int temp = currentLocation.getTileX();
        currentLocation.setTileX(currentLocation.getTileY());
        currentLocation.setTileY(temp);

        WorldType worldType = currentLocation.getWorldType();
        int mapId = currentLocation.getMapId();
        int tileX = currentLocation.getTileX();
        int tileY = currentLocation.getTileY();
        
        if (worldType == WorldType.world1) {
            if (mapId == 0) {
                if (tileX == 3 && tileY == 2) {
                    return new GlobalLocation(WorldType.zothouse, 0, 12 * Consts.TILE_WIDTH,
                            14 * Consts.TILE_HEIGHT);
                } else if (tileX == 18 && tileY == 5) {
                    return new GlobalLocation(WorldType.world2, 0, 10 * Consts.TILE_WIDTH,
                            10 * Consts.TILE_HEIGHT);
                }
            } else if (mapId == 4) {
                if (tileX == 10 && tileY == 7) {
                    return new GlobalLocation(WorldType.farmeryihouse, 0, 12 * Consts.TILE_WIDTH,
                            14 * Consts.TILE_HEIGHT);
                }
            } else if (mapId == 7) {
                if (tileX == 19 && tileY == 14) {
                    return new GlobalLocation(WorldType.tower, 0, 16 * Consts.TILE_WIDTH,
                            12 * Consts.TILE_HEIGHT);
                }
            } else if (mapId == 8) {
                if (tileX == 11 && tileY == 13) {
                    return new GlobalLocation(WorldType.mill, 0, 12 * Consts.TILE_WIDTH,
                            14 * Consts.TILE_HEIGHT);
                }
            }
        } else if (worldType == WorldType.world2) {
            if (mapId == 0) {
                if (tileX == 17 && tileY == 9) {
                    return new GlobalLocation(WorldType.world1, 0, 17 * Consts.TILE_WIDTH,
                            11 * Consts.TILE_HEIGHT);
                }
            } else if (mapId == 1) {
                if (tileX == 12 && tileY == 1) {
                    return new GlobalLocation(WorldType.world3, 1, 16 * Consts.TILE_WIDTH,
                            6 * Consts.TILE_HEIGHT);
                }
            }
        } else if (worldType == WorldType.world3) {
            if (mapId == 0) {
                if (tileX == 17 && tileY == 9) {
                    return new GlobalLocation(WorldType.world2, 1, 16 * Consts.TILE_WIDTH,
                            6 * Consts.TILE_HEIGHT);
                }
            }
        } else if (worldType == WorldType.zothouse) {
            if (mapId == 0) {
                if (tileX == 12 && tileY == 15) {
                    return new GlobalLocation(WorldType.world1, 0, 3 * Consts.TILE_WIDTH,
                            3 * Consts.TILE_HEIGHT);
                }
            }
        } else if (worldType == WorldType.farmeryihouse) {
            if (mapId == 0) {
                if (tileX == 12 && tileY == 15) {
                    return new GlobalLocation(WorldType.world1, 4, 10 * Consts.TILE_WIDTH,
                            8 * Consts.TILE_HEIGHT);
                } else if (tileX == 21 && tileY == 8) {
                    return new GlobalLocation(WorldType.farmeryihouse, 1, 21 * Consts.TILE_WIDTH,
                            7 * Consts.TILE_HEIGHT);
                }
            } else if (mapId == 1) {
                if (tileX == 21 && tileY == 8) {
                    return new GlobalLocation(WorldType.farmeryihouse, 0, 21 * Consts.TILE_WIDTH,
                            9 * Consts.TILE_HEIGHT);
                }
            }
        } else if (worldType == WorldType.tower) {
            if (mapId == 0) {
                if (tileX == 16 && tileY == 13) {
                    return new GlobalLocation(WorldType.world1, 7, 19 * Consts.TILE_WIDTH,
                            15 * Consts.TILE_HEIGHT);
                } else if (tileX == 18 && tileY == 9) {
                    return new GlobalLocation(WorldType.tower, 1, 18 * Consts.TILE_WIDTH,
                            9 * Consts.TILE_HEIGHT);
                }
            } else if (mapId == 1) {
                if (tileX == 18 && tileY == 10) {
                    return new GlobalLocation(WorldType.tower, 2, 14 * Consts.TILE_WIDTH,
                            10 * Consts.TILE_HEIGHT);
                } else if (tileX == 18 && tileY == 12) {
                    return new GlobalLocation(WorldType.tower, 0, 18 * Consts.TILE_WIDTH,
                            10 * Consts.TILE_HEIGHT);
                }
            } else if (mapId == 2) {
                if (tileX == 14 && tileY == 7) {
                    return new GlobalLocation(WorldType.tower, 1, 16 * Consts.TILE_WIDTH,
                            10 * Consts.TILE_HEIGHT);
                }
            }
        } else if (worldType == WorldType.mill) {
            if (mapId == 0) {
                if (tileX == 12 && tileY == 15) {
                    return new GlobalLocation(WorldType.world1, 8, 11 * Consts.TILE_WIDTH,
                            14 * Consts.TILE_HEIGHT);
                }
            }
        }
        return null;
    }

    public Point getWorldCoordinates() {
        return worldCoords;
    }

    public void setWorldCoordinates(Point point) {
        this.worldCoords = point;
    }

    public void setWorldType(WorldType worldType) {
        this.worldType = worldType;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    public WorldType getWorldType() {
        return worldType;
    }

    public int getMapId() {
        return mapId;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

}
