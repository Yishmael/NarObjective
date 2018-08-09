package map;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import entities.objects.GroundObject;
import entities.objects.Item;
import entities.units.NPC;
import environment.Decoration;
import misc.Consts;
import types.DecorationType;
import types.GroundObjectType;
import types.ItemType;
import types.NPCType;
import types.WorldType;

public class Spawner {

    public static ArrayList<NPC> getNpcs(WorldType worldType, int mapId) throws SlickException {
        ArrayList<NPC> npcs = new ArrayList<>();

        if (worldType == WorldType.world1) {
            if (mapId == 0) {
                npcs.add(new NPC(NPCType.tol, 7 * Consts.TILE_WIDTH, 1 * Consts.TILE_HEIGHT, 3, 80, 80));
                npcs.add(new NPC(NPCType.haki, 9 * Consts.TILE_WIDTH, 1 * Consts.TILE_HEIGHT, 3, 80, 80));
                npcs.add(new NPC(NPCType.haru, 11 * Consts.TILE_WIDTH, 1 * Consts.TILE_HEIGHT, 3, 65, 70));
                npcs.add(
                        new NPC(NPCType.farmeryi, 13 * Consts.TILE_WIDTH, 1 * Consts.TILE_HEIGHT, 3, 80, 80));
                npcs.add(new NPC(NPCType.rud, 15 * Consts.TILE_WIDTH, 1 * Consts.TILE_HEIGHT, 3, 70, 70));
                npcs.add(new NPC(NPCType.tutz, 17 * Consts.TILE_WIDTH, 1 * Consts.TILE_HEIGHT, 3, 70, 70));
                npcs.add(new NPC(NPCType.mun, 19 * Consts.TILE_WIDTH, 1 * Consts.TILE_HEIGHT, 3, 70, 70));
                npcs.add(new NPC(NPCType.az, 21 * Consts.TILE_WIDTH, 1 * Consts.TILE_HEIGHT, 3, 40, 60));

                npcs.add(new NPC(NPCType.kimi, 7 * Consts.TILE_WIDTH, 4 * Consts.TILE_HEIGHT, 3));
            } else if (mapId == 3) {
                npcs.add(new NPC(NPCType.nassi, 15 * Consts.TILE_WIDTH, 5 * Consts.TILE_HEIGHT, 3));
                npcs.add(new NPC(NPCType.pig, 17 * Consts.TILE_WIDTH, 9 * Consts.TILE_HEIGHT, 1f));
            } else if (mapId == 6) {
                npcs.add(new NPC(NPCType.atu, 10 * Consts.TILE_WIDTH, 6 * Consts.TILE_HEIGHT, 3, 85, 85));
            } else if (mapId == 11) {
            }
        } else if (worldType == WorldType.zothouse) {
            if (mapId == 0) {
                npcs.add(new NPC(NPCType.zot, 17 * Consts.TILE_WIDTH, 10 * Consts.TILE_HEIGHT, 3, 80, 80));
            }
        } else if (worldType == WorldType.farmeryihouse) {
            if (mapId == 1) {
                npcs.add(new NPC(NPCType.rat1, 8 * Consts.TILE_WIDTH, 12 * Consts.TILE_HEIGHT, 3));
                npcs.add(new NPC(NPCType.rat1, 4 * Consts.TILE_WIDTH, 10 * Consts.TILE_HEIGHT, 3));
                npcs.add(new NPC(NPCType.rat1, 4 * Consts.TILE_WIDTH, 5 * Consts.TILE_HEIGHT, 3));

            }
        }

        return npcs;
    }

    public static ArrayList<GroundObject> getGroundObjects(WorldType worldType, int mapId)
            throws SlickException {
        ArrayList<GroundObject> groundObjects = new ArrayList<>();
        if (worldType == WorldType.world1) {
            if (mapId == 0) {
                groundObjects.add(new GroundObject(GroundObjectType.tree2, 14 * Consts.TILE_WIDTH,
                        6 * Consts.TILE_HEIGHT, 64, 128));
            } else if (mapId == 1) {
                groundObjects.add(new GroundObject(GroundObjectType.sign1, 22 * Consts.TILE_WIDTH,
                        7 * Consts.TILE_HEIGHT));
                groundObjects.add(new GroundObject(GroundObjectType.school1, 7 * Consts.TILE_WIDTH,
                        10 * Consts.TILE_HEIGHT));
                groundObjects.add(new GroundObject(GroundObjectType.vines1, 21 * Consts.TILE_WIDTH,
                        6 * Consts.TILE_HEIGHT));
                groundObjects.add(new GroundObject(GroundObjectType.fishtrap, 12 * Consts.TILE_WIDTH,
                        11 * Consts.TILE_HEIGHT));
            } else if (mapId == 2) {
                groundObjects.add(new GroundObject(GroundObjectType.bush, 22 * Consts.TILE_WIDTH,
                        3 * Consts.TILE_HEIGHT));
                // groundObjects.add(new GroundObject(GroundObjectType.tree3, 19 *
                // Consts.TILE_WIDTH,
                // 9 * Consts.TILE_HEIGHT));
            } else if (mapId == 3) {
                groundObjects.add(new GroundObject(GroundObjectType.boat1, 10 * Consts.TILE_WIDTH,
                        9 * Consts.TILE_HEIGHT));
                groundObjects.add(new GroundObject(GroundObjectType.mineral1, 4 * Consts.TILE_WIDTH,
                        3 * Consts.TILE_HEIGHT));

            } else if (mapId == 4) {
                for (int i = 18; i < 23; i++) {
                    for (int j = 2; j < 4; j++) {
                        groundObjects.add(new GroundObject(GroundObjectType.wheat1, i * Consts.TILE_WIDTH,
                                j * Consts.TILE_HEIGHT, 40, 70));
                    }
                }
            } else if (mapId == 5) {
                for (int i = 0; i < 40; i++) {
                    // groundObjects.add(new GroundObject(GroundObjectType.tree1,
                    // (float) ((Math.random() * 12 + 8) * Consts.TILE_WIDTH),
                    // (float) ((Math.random() * 17 + 1) * Consts.TILE_HEIGHT), 64, 128));
                }
            } else if (mapId == 7) {

            }
        } else if (worldType == WorldType.farmeryihouse) {
            if (mapId == 0) {
                groundObjects.add(new GroundObject(GroundObjectType.chest1, 10 * Consts.TILE_WIDTH,
                        5 * Consts.TILE_HEIGHT, 50, 50));
            } else if (mapId == 1) {
            }
        } else if (worldType == WorldType.tower) {
        } else if (worldType == WorldType.zothouse) {
            if (mapId == 0) {
                groundObjects.add(new GroundObject(GroundObjectType.painting1, 10 * Consts.TILE_WIDTH,
                        3 * Consts.TILE_HEIGHT, 30, 30));
            }
        }

        return groundObjects;
    }

    public static ArrayList<Item> getItems(WorldType worldType, int mapId) throws SlickException {
        ArrayList<Item> items = new ArrayList<>();

        if (worldType == WorldType.world1) {
            if (mapId == 0) {
                items.add(new Item(ItemType.knife1, 9 * Consts.TILE_WIDTH, 5 * Consts.TILE_HEIGHT));

            } else if (mapId == 3) {
            } else if (mapId == 4) {
                items.add(new Item(ItemType.flask1, 19 * Consts.TILE_WIDTH, 6 * Consts.TILE_HEIGHT));
            }
        } else if (worldType == WorldType.zothouse) {
            if (mapId == 0) {
                items.add(new Item(ItemType.shovel1, 7 * Consts.TILE_WIDTH, 7 * Consts.TILE_HEIGHT));
                items.add(new Item(ItemType.axe1, 12 * Consts.TILE_WIDTH, 7 * Consts.TILE_HEIGHT));
            }
        } else if (worldType == WorldType.farmeryihouse) {
            if (mapId == 1) {

                items.add(new Item(ItemType.scythe1, 10 * Consts.TILE_WIDTH, 7 * Consts.TILE_HEIGHT));
            }
        }

        return items;
    }

    public static ArrayList<Decoration> getDecorations(WorldType worldType, int mapId) throws SlickException {
        ArrayList<Decoration> decorations = new ArrayList<>();
        if (worldType == WorldType.world1) {
            if (mapId == 0) {
                decorations.add(new Decoration(DecorationType.bug1, 600, 300));
            } else if (mapId == 1) {
            } else if (mapId == 3) {
                // doodads.add(new Decoration(DecorationType.fence1, 17 * Consts.TILE_WIDTH,
                // 10 * Consts.TILE_HEIGHT));
            } else if (mapId == 4) {
                // doodads.add(new Decoration(DecorationType.farmeryihouse, 10 * Consts.TILE_WIDTH,
                // 7 * Consts.TILE_HEIGHT, 370, 260));
            } else if (mapId == 6) {

            } else if (mapId == 7) {
                // doodads.add(new Decoration(DecorationType.tower, 19 * Consts.TILE_WIDTH, 14 *
                // Consts.TILE_HEIGHT, 220,
                // 420));

            } else if (mapId == 8) {

                // doodads.add(new Decoration(DecorationType.mill, 11 * Consts.TILE_WIDTH, 13 *
                // Consts.TILE_HEIGHT, 320,
                // 350));
            } else if (mapId == 11) {
            }
        } else if (worldType == WorldType.zothouse) {
            if (mapId == 0) {
                decorations.add(new Decoration(DecorationType.desk1, 5 * Consts.TILE_WIDTH,
                        4 * Consts.TILE_HEIGHT, 150, 75));
                decorations.add(new Decoration(DecorationType.chair1, 5 * Consts.TILE_WIDTH,
                        5 * Consts.TILE_HEIGHT, 70, 85));
                decorations.add(new Decoration(DecorationType.oven1, 14 * Consts.TILE_WIDTH,
                        4 * Consts.TILE_HEIGHT, 80, 75));
            }
        }

        return decorations;
    }

}
