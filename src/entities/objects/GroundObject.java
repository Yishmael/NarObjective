package entities.objects;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import entities.Entity;
import misc.Consts;
import types.GroundObjectType;

// make entity superclass of NPC, Unit and game objects (trees, rocks, etc.)
public class GroundObject extends Entity {

    private GroundObjectType groundObjectType = GroundObjectType.nil;
    private int uses;

    public GroundObject(GroundObjectType groundObjectType, float x, float y, int width, int height)
            throws SlickException {

        super("res/images/entities/objects/" + groundObjectType.toString() + ".png", x, y, width, height);

        // TODO temp
        this.groundObjectType = groundObjectType;
        name = groundObjectType.getName();
        uses = groundObjectType.getUses();
    }

    public GroundObject(GroundObjectType groundObjectType, float x, float y) throws SlickException {
        this(groundObjectType, x, y, (int) Consts.TILE_WIDTH, (int) Consts.TILE_HEIGHT);
    }

    public GroundObjectType getgroundObjectType() {
        return groundObjectType;
    }

    @Override
    public ArrayList<String> process(String message) {
        ArrayList<String> result = new ArrayList<>();

        if (uses > 0) {
            if (groundObjectType.getAcceptedCommands().length > 0) {
                for (int i = 0; i < groundObjectType.getAcceptedCommands().length; i++) {
                    String acceptedCommand = groundObjectType.getAcceptedCommands()[i];
                    if (message.equals(acceptedCommand) || acceptedCommand.equals("any")) {
                        result.add(groundObjectType.getResponses()[i]);
                        uses = Math.max(0, uses - 1);
                        break;
                    }
                }
            }
        } else if (uses == -1) { // infinite uses
            if (groundObjectType.getAcceptedCommands().length == 0) { // accepts anything
                if (groundObjectType.getResponses().length > 0) {
                    result.add(groundObjectType.getResponses()[(int) Math
                            .round(Math.random() * (groundObjectType.getResponses().length - 1))]);
                }
            } else { // return the response to the corresponding command
                for (int i = 0; i < groundObjectType.getAcceptedCommands().length; i++) {
                    String acceptedCommand = groundObjectType.getAcceptedCommands()[i];
                    if (message.equals(acceptedCommand)) {
                        result.add(groundObjectType.getResponses()[i]);
                        break;
                    } else if (acceptedCommand.equals("any")) {
                        result.add(groundObjectType.getResponses()[i]);
                    }
                }

            }
        }
        if (uses == 0) {
            try {
                for (GroundObjectType got: GroundObjectType.values()) {
                    if (got.getName().equals(name)) {
                        setImage(new Image("res/images/entities/objects/" + got.toString() + "dead.png"));
                        break;
                    }
                }
            } catch (SlickException e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    @Override
    public void update(int dt) {
        System.out.println("UNUSED");
    }

}
