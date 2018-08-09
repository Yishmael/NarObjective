package entities.units;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;

import entities.objects.Item;
import misc.Consts;
import misc.MainGame;
import quests.QuestEnum;
import types.NPCType;

public class NPC extends Unit {
    private int pointIndex = 0;
    private NPCType npcType;
    private long lastTimeOfArrival;
    private float buyingPriceModifier = 0.8f;
    private float sellingPriceModifier = 1f;
    private ArrayList<Point> patrolPoints = new ArrayList<>();

    public NPC(NPCType npcType, float x, float y, float speed, int width, int height) throws SlickException {
        super("res/images/entities/npcs/" + npcType.toString() + ".png", x, y, speed, width, height);
        name = npcType.getName();
        this.npcType = npcType;

        for (Point p: npcType.getPatrolPoints()) {
            patrolPoints.add(p);
        }

        for (int i = 0; i < npcType.getItems().length; i++) {
            inventory[i] = new Item(npcType.getItems()[i]);
        }

        if (npcType == NPCType.pig) {
            damage = 30;
            attackCooldown = 880;
        } else if (npcType == NPCType.rat1) {
            damage = 12;
            attackCooldown = 420;
        }

        ss = new SpriteSheet("res/images/entities/npcs/" + npcType.toString() + ".png", 32, 32);
        animation = new Animation(ss, 0, 0, 3, 0, true, 750, true);
    }

    public NPC(NPCType npcType, float x, float y, float speed) throws SlickException {
        this(npcType, x, y, speed, (int) Consts.TILE_WIDTH, (int) Consts.TILE_HEIGHT);
    }

    public void addPoint(Point point) {
        patrolPoints.add(point);
    }

    public void clearPaths() {
        patrolPoints.clear();
        pointIndex = 0;
    }

    public String getNextTextLine() {
        if (npcType.getTextLines().length > 0) {
            return npcType.getTextLines()[(int) (Math.random() * (npcType.getTextLines().length - 1))];
        }
        return "ND";
    }

    public float getBuyingPriceModifier() {
        return buyingPriceModifier;
    }

    public float getSellingPriceModifier() {
        return sellingPriceModifier;
    }

    public ArrayList<Point> getPatrolPoints() {
        return patrolPoints;
    }

    @Override
    public ArrayList<String> process(String message) {
        ArrayList<String> result = new ArrayList<>();

        for (QuestEnum qe: npcType.getProvidingQuests()) {
            result.add("quest " + qe.getId() + " A");
        }
        for (QuestEnum qe: npcType.getFinishingQuests()) {
            result.add("quest " + qe.getId() + " Z");
        }
        if (npcType.getItems().length > 0) {
            result.add("open trade " + hashCode());
        } else if (npcType == NPCType.pig || npcType == NPCType.rat1) {
            if (message.equals("CMDaxe1")) {
                damage(35);
            }
        }
        if (npcType.getTextLines().length > 0) {
            result.add("chat");
        }

        return result;
    }

    // TODO clear this mess...
    @Override
    public void update(int dt) {
        if (speed > 0 && alive && patrolPoints.size() > 0) {
            if (MainGame.getTime() - lastTimeOfArrival > 1000f / speed) {
                if (Consts.SNAPPING) {
                    if ((int) (x / Consts.TILE_WIDTH) != patrolPoints.get(pointIndex).getX()) {
                        move(Consts.TILE_WIDTH * dt / 1000f
                                * Math.signum(patrolPoints.get(pointIndex).getX() - x / Consts.TILE_WIDTH),
                                0);
                    }
                    if ((int) (y / Consts.TILE_HEIGHT) != patrolPoints.get(pointIndex).getY()) {
                        move(0, Consts.TILE_HEIGHT * dt / 1000f
                                * Math.signum(patrolPoints.get(pointIndex).getY() - y / Consts.TILE_HEIGHT));
                    } else if ((int) (x / Consts.TILE_WIDTH) == patrolPoints.get(pointIndex).getX()
                            && (int) (y / Consts.TILE_HEIGHT) == patrolPoints.get(pointIndex).getY()) {
                        lastTimeOfArrival = MainGame.getTime();
                        pointIndex++;
                        pointIndex %= patrolPoints.size();
                    }
                } else {
                    if (Math.abs(x - patrolPoints.get(pointIndex).getX() * Consts.TILE_WIDTH) > 1) {
                        move(Consts.TILE_WIDTH * dt / 1000f
                                * Math.signum(patrolPoints.get(pointIndex).getX() - x / Consts.TILE_WIDTH),
                                0);
                    }
                    if (Math.abs(y - patrolPoints.get(pointIndex).getY() * Consts.TILE_HEIGHT) > 1) {
                        move(0, Consts.TILE_HEIGHT * dt / 1000f
                                * Math.signum(patrolPoints.get(pointIndex).getY() - y / Consts.TILE_HEIGHT));
                    } else if (Math.abs(x - patrolPoints.get(pointIndex).getX() * Consts.TILE_WIDTH) <= 1
                            && Math.abs(y - patrolPoints.get(pointIndex).getY() * Consts.TILE_HEIGHT) <= 1) {
                        lastTimeOfArrival = 0;
                        pointIndex++;
                        pointIndex %= patrolPoints.size();
                    }
                }
            }
        }
    }

}
