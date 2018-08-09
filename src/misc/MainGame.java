package misc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.Sys;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

import entities.Entity;
import entities.objects.GroundObject;
import entities.objects.Item;
import entities.units.NPC;
import entities.units.Player;
import environment.DayCycle;
import environment.Weather;
import gooey.BottomMenu;
import gooey.ChatBubble;
import gooey.ChatMenu;
import gooey.InventoryWindow;
import gooey.TradeWindow;
import map.GlobalLocation;
import map.ScreenMap;
import map.Tile;
import map.World;
import quests.QuestEnum;
import quests.QuestManager;
import quests.QuestObjective;
import types.EventType;
import types.ItemType;
import types.TileType;
import types.WorldType;

public class MainGame extends BasicGame {

    public static long getTime() {
        return (long) (Sys.getTime() * 1000f / Sys.getTimerResolution());
    }

    public static void main(String argv[]) throws SlickException {
        AppGameContainer appgc = new AppGameContainer(new MainGame());
        appgc.setDisplayMode(Consts.SCREEN_WIDTH, Consts.SCREEN_HEIGHT, Consts.FULLSCREEN);
        appgc.setTargetFrameRate(Consts.FPS_LIMIT);
        // appgc.setIcon("res/images/other/icon.png");
        appgc.setShowFPS(true);
        appgc.setAlwaysRender(true);
        appgc.setUpdateOnlyWhenVisible(true);
        appgc.setClearEachFrame(false);
        appgc.start();
    }

    // GUI
    private InventoryWindow inventoryWindow;
    private TradeWindow tradeWindow;
    private QuestManager questManager;
    private BottomMenu bottomMenu;
    private ChatBubble bubble;
    private ChatMenu menu;
    // /GUI

    private Input input;
    private Player player;
    private World world;
    private ScreenMap map;
    private boolean gameRunning = true;
    // TODO move tradewindow to NPCs
    public static int dt;
    private int mouseX, mouseY;
    @SuppressWarnings("unused")
    private long gameTime;
    private ArrayList<Event> events = new ArrayList<>();
    private GlobalLocation globalLocation, nextGlobalLocation;
    private BlackScreen blackScreen = new BlackScreen();

    private World[] worlds = new World[WorldType.values().length];

    private Weather weather;
    private DayCycle dayCycle;

    public MainGame() {
        super("NarObjective");
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        loadWorlds();
        inventoryWindow = new InventoryWindow(0, Consts.SCREEN_HEIGHT - 320, 3 * Consts.TILE_WIDTH + 20,
                3 * Consts.TILE_HEIGHT + 70);

        world = worlds[0];
        map = world.getMap(0, 0);
        globalLocation = new GlobalLocation(world.getWorldType(), map.getMapId(), 2 * Consts.TILE_WIDTH,
                9 * Consts.TILE_HEIGHT);
        events.add(new Event(EventType.exploring, world.getAreaName(map.getMapId())));
        bubble = new ChatBubble(500, 300);
        menu = new ChatMenu(500, 180);
        tradeWindow = new TradeWindow();
        bottomMenu = new BottomMenu();

        player = new Player("Gil", globalLocation.getTileX() * Consts.TILE_WIDTH,
                globalLocation.getTileY() * Consts.TILE_HEIGHT, 15, 40, 40);

        questManager = new QuestManager();

        weather = new Weather(Weather.snow, 20);
        // weather.enable();

        dayCycle = new DayCycle();
        // dayCycle.enable();

        // blackScreen.activate();
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {

        // drawGrid(g);

        map.draw(g);
        map.drawObjects(g);
        map.drawItems(g);

        player.draw(g);

        weather.draw(g);
        dayCycle.drawCenteredAt(g, player.getX(), player.getY());

        // draw equipped item
        if (player.getEquippedItemIndex() != -1) {
            player.getItemAt(player.getEquippedItemIndex()).drawAt(g, player.getX(), player.getY());
        }

        bubble.draw(g);
        menu.draw(g);

        drawGUI(g);

        blackScreen.draw(g);

        g.setColor(Color.white);
        g.drawString("0.1.3", Consts.SCREEN_WIDTH - 70, 10);
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        gameTime = getTime();
        dt = delta;
        input = gc.getInput();
        if (!gameRunning) {
            gc.exit();
        }

        if (dt > 16) {
            dt = 16;
        }

        mouseX = input.getMouseX();
        mouseY = input.getMouseY();

        handleInput();

        nextGlobalLocation = GlobalLocation.teleportFrom(globalLocation);
        if (nextGlobalLocation != null) {
            globalLocation = nextGlobalLocation;
            world = worlds[globalLocation.getWorldType().ordinal()];
            map = world.getMap(globalLocation.getMapId());
            player.setLocation(globalLocation.getTileX() * Consts.TILE_WIDTH,
                    globalLocation.getTileY() * Consts.TILE_HEIGHT);
        }

        map.updateNpcs(dt);
        map.interact(player);

        for (Iterator<Item> iter = map.getDroppedItems().iterator(); iter.hasNext();) {
            Item item = iter.next();
            if ((int) (player.getX() / Consts.TILE_WIDTH) == (int) (item.getX() / Consts.TILE_WIDTH)
                    && (int) (player.getY() / Consts.TILE_HEIGHT) == (int) (item.getY()
                            / Consts.TILE_HEIGHT)) {
                if (!player.isInventoryFull()) {
                    iter.remove();
                    player.addItem(item);
                    inventoryWindow.setItems(player.getItems());
                    events.add(new Event(EventType.obtaining, item.getItemType().toString()));
                }
                break;
            }
        }

        globalLocation.setTileX((int) (player.getY() / Consts.TILE_HEIGHT));
        globalLocation.setTileY((int) (player.getX() / Consts.TILE_WIDTH));

        weather.update(dt);

        handleEvents();

        blackScreen.update(dt);

        input.clearKeyPressedRecord();
    }

    private void handleEvents() {
        if (events.size() == 0) {
            return;
        }
        // handle event
        for (int i = 0; i < QuestEnum.values().length; i++) {
            int state = questManager.getStateOfQuest(i);
            for (int j = 0; j < QuestEnum.values()[i].getQuestObjectives().length; j++) {
                for (int k = 0; k < events.size(); k++) {
                    if (state == 1 || state == 2) {
                        EventType qEvent = QuestEnum.values()[i].getQuestObjectives()[j].getEventType();
                        QuestObjective qObj = QuestEnum.values()[i].getQuestObjectives()[j];
                        if (qEvent == events.get(k).getEventType()) {
                            if (state == 1) {
                                if (qEvent == EventType.obtaining || qEvent == EventType.using) {
                                    if (qObj.getItemType() == ItemType.valueOf(events.get(k).getData())) {
                                        questManager.advanceObjectives(i, events.get(k).getEventType(),
                                                events.get(k).getData());
                                    }
                                } else if (qEvent == EventType.exploring) {
                                    if (qObj.getName().equals(events.get(k).getData())) {
                                        questManager.advanceObjectives(i, events.get(k).getEventType(),
                                                events.get(k).getData());
                                    }
                                }
                            }
                        } else { // downgrading the objective
                            if (qEvent == EventType.obtaining
                                    && events.get(k).getEventType() == EventType.dropping) {
                                if (qObj.getItemType() == ItemType.valueOf(events.get(k).getData())) {
                                    // System.out.println(events.get(k) + " " +
                                    // events.get(k).getData());
                                    questManager.regressObjectives(i, events.get(k).getEventType(),
                                            events.get(k).getData());
                                }
                            }
                        }
                    }
                }
            }
        }
        events.clear();
    }

    private void handleInput() throws SlickException {
        {
            int i;
            for (i = 0; i < inventoryWindow.getRects().length; i++) {
                if (inventoryWindow.getRects()[i].contains(mouseX, mouseY)) {
                    if (player.getItemAt(i) != null) {
                        inventoryWindow.setDisplayedItemName(player.getItemAt(i).getName());
                        inventoryWindow.setDisplayedItemDescription(player.getItemAt(i).getDescription());
                        inventoryWindow.setDisplayedItemIndex(i);
                        inventoryWindow.setShowItemInfo(true);
                        break;
                    }
                }
            }
            if (i == 9) {
                inventoryWindow.setShowItemInfo(false);
            }
        }
        {
            int i;
            for (i = 0; i < menu.getRects().size(); i++) {
                if (menu.getRects().get(i).contains(mouseX, mouseY)) {
                    menu.highlightOption(i);
                    break;
                }
            }
            if (i == menu.getRects().size()) {
                menu.highlightOption(-1);
            }
        }
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            gameRunning = false;
        } else if (input.isKeyPressed(Input.KEY_1)) {
            Item item = player.getItemAt(0);
            if (item != null) {
                process(item);
                events.add(new Event(EventType.using, item.getItemType().toString()));
                if (item.isDestroyedOnUse()) {
                    player.destroyItemAt(0);
                    events.add(new Event(EventType.dropping, item.getItemType().toString()));
                }
                // TODO add handler to monitor inventory changes
            }
        } else if (input.isMousePressed(Input.MOUSE_MIDDLE_BUTTON)) {
            player.setX(mouseX);
            player.setY(mouseY);
            System.out.println(mouseX + ":" + mouseY + "("
                    + (int) ((player.getX() - player.getX() % Consts.TILE_WIDTH) / Consts.TILE_WIDTH) + ":"
                    + (int) ((player.getY() - player.getY() % Consts.TILE_HEIGHT) / Consts.TILE_HEIGHT) + ")"
                    + " W: " + world.getWorldType() + " M: " + map.getMapId());
        } else if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
            if (!tradeWindow.isTradeOpened()) {
                for (int i = 0; i < inventoryWindow.getRects().length; i++) {
                    if (inventoryWindow.getRects()[i].contains(mouseX, mouseY)) {
                        Item item = player.getItemAt(i);
                        if (item != null) {
                            player.destroyItemAt(i);
                            events.add(new Event(EventType.dropping, item.getItemType().toString()));

                            // TODO drop it in front of the player
                            map.dropItemAt(item, player.getX() + Consts.TILE_WIDTH, player.getY());
                        }
                        break;
                    }
                }
            }
        } else if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            for (int i = 0; i < menu.getRects().size(); i++) {
                if (menu.getRects().get(i).contains(mouseX, mouseY)) {
                    processMessage(menu.getDialogueData(i));
                    break;
                }
            }
            for (int i = 0; i < questManager.getRects().length; i++) {
                if (questManager.getRects()[i].contains(mouseX, mouseY)) {
                    questManager.highlightQuest(i);
                    break;
                }
            }
            if (!tradeWindow.isTradeOpened()) {
                for (int i = 0; i < inventoryWindow.getRects().length; i++) {
                    if (inventoryWindow.getRects()[i].contains(mouseX, mouseY)) {
                        player.equipItem(i);
                        break;
                    }
                }
            }
            if (tradeWindow.isTradeOpened()) {
                for (int j = 0; j < tradeWindow.getRects().length; j++) {
                    if (tradeWindow.getRects()[j].contains(mouseX, mouseY)) {
                        for (NPC npc: map.getNpcs()) {
                            if (npc.hashCode() == tradeWindow.getTradeId()) {
                                Item item = npc.getItemAt(j);
                                if (item != null) {
                                    if (player.getGold() >= item.getPrice() * npc.getSellingPriceModifier()
                                            && !player.isInventoryFull()) {
                                        player.setGold(player.getGold()
                                                - (int) (item.getPrice() * npc.getSellingPriceModifier()));
                                        player.addItem(item);
                                        inventoryWindow.setGold(player.getGold());
                                        inventoryWindow.setItems(player.getItems());
                                        events.add(new Event(EventType.obtaining,
                                                item.getItemType().toString()));
                                        npc.destroyItemAt(j);
                                        tradeWindow.setItems(npc.getItems());
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                    } else {
                        for (int i = 0; i < inventoryWindow.getRects().length; i++) {
                            Rectangle rect2 = inventoryWindow.getRects()[i];
                            if (rect2.contains(mouseX, mouseY)) {
                                Item item = player.getItems()[i];
                                if (item != null) {
                                    for (NPC npc: map.getNpcs()) {
                                        if (npc.hashCode() == tradeWindow.getTradeId()) {
                                            player.setGold(player.getGold() + (int) ((item.getPrice()
                                                    * npc.getBuyingPriceModifier())));
                                            player.destroyItemAt(i);
                                            events.add(new Event(EventType.dropping,
                                                    item.getItemType().toString()));
                                            inventoryWindow.setGold(player.getGold());
                                            inventoryWindow.setItems(player.getItems());
                                            npc.addItem(item);
                                            tradeWindow.setItems(npc.getItems());
                                            break;
                                        }
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
        } else if (input.isKeyPressed(Input.KEY_Q)) {
            questManager.toggle();
        } else if (input.isKeyPressed(Input.KEY_I)) {
            inventoryWindow.setGold(player.getGold());
            inventoryWindow.toggle();
        } else if (input.isKeyDown(Input.KEY_COMMA)) {
            player.setFacing(2);
            if (map.isWalkable((int) (player.getX() / Consts.TILE_WIDTH),
                    (int) (player.getY() / Consts.TILE_HEIGHT) - 1)) {
                player.moveUp(Consts.TILE_HEIGHT * dt / 1000f);
                menu.hide();
                tradeWindow.close();
            } else if (map.getTileType((int) (player.getX() / Consts.TILE_WIDTH),
                    (int) (player.getY() / Consts.TILE_HEIGHT) - 1) == TileType.nil) {
                player.setY(Consts.SCREEN_HEIGHT - Consts.SCREEN_HEIGHT % Consts.TILE_HEIGHT);
                globalLocation.setTileY((int) (player.getY() / Consts.TILE_HEIGHT));
                map = world.getMap((int) (globalLocation.getWorldCoordinates().getX() - 1),
                        (int) globalLocation.getWorldCoordinates().getY());
                globalLocation.setWorldCoordinates(new Point(globalLocation.getWorldCoordinates().getX() - 1,
                        globalLocation.getWorldCoordinates().getY()));
                globalLocation.setMapId(map.getMapId());
                events.add(new Event(EventType.exploring, world.getAreaName(map.getMapId())));
            }
        } else if (input.isKeyDown(Input.KEY_O)) {
            player.setFacing(0);
            if (map.isWalkable((int) (player.getX() / Consts.TILE_WIDTH),
                    (int) (player.getY() / Consts.TILE_HEIGHT) + 1)) {
                player.moveDown(Consts.TILE_HEIGHT * dt / 1000f);
                menu.hide();
                tradeWindow.close();
            } else if (map.getTileType((int) (player.getX() / Consts.TILE_WIDTH),
                    (int) (player.getY() / Consts.TILE_HEIGHT) + 1) == TileType.nil) {
                player.setY(1);
                map = world.getMap((int) (globalLocation.getWorldCoordinates().getX() + 1),
                        (int) globalLocation.getWorldCoordinates().getY());
                globalLocation.getWorldCoordinates()
                        .setX((int) (globalLocation.getWorldCoordinates().getX() + 1));
                globalLocation.setMapId(map.getMapId());
                events.add(new Event(EventType.exploring, world.getAreaName(map.getMapId())));
            }
        } else if (input.isKeyDown(Input.KEY_A)) {
            player.setFacing(3);
            if (map.isWalkable((int) (player.getX() / Consts.TILE_WIDTH) - 1,
                    (int) (player.getY() / Consts.TILE_HEIGHT))) {
                player.moveLeft(Consts.TILE_WIDTH * dt / 1000f);
                menu.hide();
                tradeWindow.close();
            } else if (map.getTileType((int) (player.getX() / Consts.TILE_WIDTH) - 1,
                    (int) (player.getY() / Consts.TILE_HEIGHT)) == TileType.nil) {
                player.setX(Consts.SCREEN_WIDTH);
                map = world.getMap((int) (globalLocation.getWorldCoordinates().getX()),
                        (int) (globalLocation.getWorldCoordinates().getY() - 1));
                globalLocation.getWorldCoordinates()
                        .setY((int) (globalLocation.getWorldCoordinates().getY() - 1));
                globalLocation.setMapId(map.getMapId());
                events.add(new Event(EventType.exploring, world.getAreaName(map.getMapId())));
            }
        } else if (input.isKeyDown(Input.KEY_E)) {
            player.setFacing(1);
            if (map.isWalkable((int) (player.getX() / Consts.TILE_WIDTH) + 1,
                    (int) (player.getY() / Consts.TILE_HEIGHT))) {
                player.moveRight(Consts.TILE_WIDTH * dt / 1000f);
                menu.hide();
                tradeWindow.close();
            } else if (map.getTileType((int) (player.getX() / Consts.TILE_WIDTH) + 1,
                    (int) (player.getY() / Consts.TILE_HEIGHT)) == TileType.nil) {
                player.setX(1);
                map = world.getMap((int) (globalLocation.getWorldCoordinates().getX()),
                        (int) (globalLocation.getWorldCoordinates().getY() + 1));
                globalLocation.getWorldCoordinates()
                        .setY((int) (globalLocation.getWorldCoordinates().getY() + 1));
                globalLocation.setMapId(map.getMapId());
                events.add(new Event(EventType.exploring, world.getAreaName(map.getMapId())));
            }
        } else if (input.isKeyPressed(Input.KEY_SPACE)) {
            switch (player.getFacing()) {
            case 0: // facing downwards
                interactAt(player.getX() / Consts.TILE_WIDTH, player.getY() / Consts.TILE_HEIGHT + 1);
                break;
            case 1: // facing right
                interactAt(player.getX() / Consts.TILE_WIDTH + 1, player.getY() / Consts.TILE_HEIGHT);
                break;
            case 2: // facing up
                interactAt(player.getX() / Consts.TILE_WIDTH, player.getY() / Consts.TILE_HEIGHT - 1);
                break;
            case 3: // facing left
                interactAt(player.getX() / Consts.TILE_WIDTH - 1, player.getY() / Consts.TILE_HEIGHT);
                break;
            }
        }
        if (input.isKeyPressed(Input.KEY_TAB)) {
            if (world.getWorldType() == WorldType.world1) {
                world = worlds[1];
            } else {
                world = worlds[0];
            }
            map = world.getMap(0, 0);
            globalLocation.setWorldCoordinates(new Point(0, 0));
            globalLocation.setMapId(map.getMapId());
            globalLocation.setWorldType(world.getWorldType());
        }
    }

    // TODO fix calculations when snapping is off
    private void interactAt(float tileX, float tileY) throws SlickException {
        NPC npc = map.getNpcAt(tileX, tileY);
        if (npc != null) {
            process(npc);
            events.add(new Event(EventType.talking, npc.getName()));
        } else {
            GroundObject ent = map.getEntityAt(tileX, tileY);
            if (ent != null) {
                process(ent);
            } else {
                TileType tileType = map.getTileType(tileX, tileY);

                // TODO generalize interactions

                for (String message: player.getEquippedItem().getItemType().getCommands()) {
                    String result = Tile.process(tileType, message);
                    if (result != null && result.equals("tilechange GW")) {
                        map.setTileType((int) tileX, (int) tileY, TileType.water1);
                    }
                }
            }
        }
    }

    private void loadWorlds() throws SlickException {
        // long startTime = getTime();
        // int counter = 0;
        for (int i = 0; i < worlds.length; i++) {
            if (i == 2) {
                continue;
            }
            try {
                worlds[i] = new World(WorldType.values()[i]);
                // worlds[i] = new World(WorldType.values()[i], true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // counter += worlds[i].getWidth() * worlds[i].getHeight();

        }
        // System.out.println(
        // "Loaded " + counter + " maps in " + (MainGame.getTime() - startTime) / 1000f + "
        // seconds.");
    }

    private void processMessage(String message) throws SlickException {
        if (message == null) {
            return;
        }
        String cmd = message.split(" ")[0];
        String msg;

        if (message.length() > cmd.length()) {
            msg = message.substring(cmd.length() + 1);
        } else {
            msg = "";
        }
        if (cmd.equals("addItem")) {
            map.dropItemAt(new Item(ItemType.valueOf(msg)), player.getX(), player.getY());
        } else if (cmd.equals("MSG")) {
            bubble.setStyle(ChatBubble.SIGN);
            bubble.clearText();
            bubble.addText(message.substring(4));
            bubble.show();
        } else if (cmd.equals("open")) { // open a gui window
            if (msg.split(" ")[0].equals("trade")) {
                tradeWindow.setTradeId(Integer.parseInt(message.split(" ")[2]));
                for (NPC npc: map.getNpcs()) {
                    if (npc.hashCode() == tradeWindow.getTradeId()) {
                        tradeWindow.setItems(npc.getItems());
                        break;
                    }
                }
                tradeWindow.open();
            } else if (msg.equals("stash")) {
                System.out.println("displaying stash items");
            }
        } else if (cmd.equals("chat")) { // only NPCs send "chat" message
            bubble.setStyle(ChatBubble.DEFAULT);
            bubble.clearText();
            bubble.addText(msg);
            bubble.show();
        } else if (cmd.equals("quest")) {
            String mode = msg.split(" ")[1];
            int questId = Integer.parseInt(msg.split(" ")[0]);
            int state = questManager.getStateOfQuest(questId);
            bubble.clearText();
            bubble.setStyle(ChatBubble.DEFAULT);
            if (state == 0 && mode.equals("A")) {
                {
                    int i;
                    for (i = 0; i < QuestEnum.values()[questId].getRequiredQuestsIds().length; i++) {
                        if (questManager.getStateOfQuest(
                                QuestEnum.values()[questId].getRequiredQuestsIds()[i]) != 2) {
                            break;
                        }
                    }
                    if (i == QuestEnum.values()[questId].getRequiredQuestsIds().length) {
                        questManager.addQuest(questId);
                        bubble.addText(QuestEnum.values()[questId].getDialogues()[state]);
                        bubble.show();
                    } else {
                        System.out.println("Cannot accept " + QuestEnum.values()[questId].getName() + "!");
                    }
                }
            } else if (state == 1) {
                bubble.addText(QuestEnum.values()[questId].getDialogues()[state]);
                bubble.show();
            } else if (state == 2 && mode.equals("Z")) {
                questManager.advanceObjectives(questId, null, null);
                bubble.addText(QuestEnum.values()[questId].getDialogues()[state]);
                if (questManager.getDataOfQuest(questId).size() > 0) {
                    for (String data: questManager.getDataOfQuest(questId)) {
                        if (data.split(" ")[0].equals("addItem")) {
                            player.addItem(new Item(ItemType.valueOf(data.split(" ")[1])));
                            inventoryWindow.setItems(player.getItems());
                            events.add(new Event(EventType.obtaining,
                                    ItemType.valueOf(data.split(" ")[1]).toString()));
                        }
                    }
                }
                bubble.show();
            }
        }
    }

    private void process(Entity entity) throws SlickException {
        Item item = player.getEquippedItem();
        ArrayList<String> messages;
        if (item.getItemType() != ItemType.nil) {
            messages = new ArrayList<>();
            for (String commands: item.getItemType().getCommands()) {
                messages.addAll(entity.process(commands));
            }
        } else {
            messages = entity.process("");
        }
        menu.clearLines();
        for (String message: messages) {
            if (message == null) {
                continue;
            }
            String cmd = message.split(" ")[0];
            String msg;

            if (message.length() > cmd.length()) {
                msg = message.substring(cmd.length() + 1);
            } else {
                msg = "";
            }
            if (cmd.equals("addItem")) {
                map.dropItemAt(new Item(ItemType.valueOf(msg)), player.getX(), player.getY());
            } else if (cmd.equals("MSG")) {
                bubble.setStyle(ChatBubble.SIGN);
                bubble.clearText();
                bubble.addText(message.substring(4));
                bubble.show();
            } else if (cmd.equals("open")) { // open a gui window
                menu.addDialogueData("Trade:" + message);
            } else if (cmd.equals("chat")) { // only NPCs send "chat" message
                String nextLine = ((NPC) entity).getNextTextLine();
                menu.addDialogueData("Chat:" + message + " " + nextLine);
            } else if (cmd.equals("quest")) {
                int questId = Integer.parseInt(msg.split(" ")[0]);
                menu.addDialogueData(QuestEnum.values()[questId].getName() + ":" + message);
            }
        }
        if (menu.getDialogueData(0) != null) {
            menu.show();
        }
    }

    private void drawGUI(Graphics g) {
        inventoryWindow.draw(g);

        tradeWindow.draw(g);

        questManager.draw(g);

        bottomMenu.draw(g, world.getWorldType().getWorldName(), world.getAreaName(map.getMapId()));
    }

    @SuppressWarnings("unused")
    private void drawGrid(Graphics g) {

        g.setColor(Color.black);
        for (int i = 0; i < Consts.SCREEN_WIDTH / Consts.TILE_WIDTH; i++) {
            g.drawLine(Consts.TILE_WIDTH * i, 0, Consts.TILE_WIDTH * i, 600);
        }
        for (int i = 0; i < Consts.SCREEN_HEIGHT / Consts.TILE_HEIGHT; i++) {
            g.drawLine(0, Consts.TILE_HEIGHT * i, 800, Consts.TILE_HEIGHT * i);
        }
    }

}
