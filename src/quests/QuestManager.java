package quests;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import misc.Text;
import types.EventType;
import types.ItemType;
import types.NPCType;
import unused.QuestLog;

//states: 0 - quest not taken, 1 - quest taken, 2 - quest objective achieved, 3 - quest completed
//Quests: event -> handle the event -> update quests' objectives
public class QuestManager {
    private int[] questStates = new int[QuestEnum.values().length];
    // private int activeQuestsNumber = 0;

    @SuppressWarnings("unchecked")
    private ArrayList<QuestObjective>[] questObjectives = new ArrayList[QuestEnum.values().length];

    private int highlightedQuestIndex;
    private Rectangle[] rects = new Rectangle[QuestEnum.values().length];
    private Image[] images = new Image[2];
    private boolean shown;

    public QuestManager() throws SlickException {

        for (int i = 0; i < QuestEnum.values().length; i++) {
            questObjectives[i] = new ArrayList<>();
            rects[i] = new Rectangle(QuestLog.x + 30 + 60 * (i % 5), QuestLog.y - 45 + 20 * (i / 5), 50, 20);
        }

        images[0] = new Image("res/images/gooey/questlog.png");
        images[1] = new Image("res/images/gooey/bookmark.png");
    }

    public void addQuest(int questId) {
        // TODO make it use actual quest ID
        questStates[questId] = 1;
        // activeQuestsNumber++;
        highlightedQuestIndex = questId;
        for (QuestObjective qo: QuestEnum.values()[questId].getQuestObjectives()) {
            questObjectives[questId].add(qo);
        }
        System.out.println("Quest '" + QuestEnum.values()[questId].getName() + "' taken");
    }
    // TODO join advanceObj and regressObj into one more general method

    // TODO check inventory items when taking a quest
    public void advanceObjectives(int questId, EventType eventType, String eventData) {
        if (questStates[questId] == 1) { // quest is taken
            for (QuestObjective qo: questObjectives[questId]) {
                if (qo.getEventType() == eventType) {
                    if (qo.getEventType() == EventType.obtaining || qo.getEventType() == EventType.using) {
                        ItemType itemType = ItemType.valueOf(eventData);
                        if (qo.getItemType() == itemType) {
                            qo.next();
                        }
                    } else if (qo.getEventType() == EventType.talking) {
                        NPCType npcType = NPCType.valueOf(eventData);
                        if (qo.getName().equals(npcType.getName())) {
                            qo.next();
                        }
                    } else if (qo.getEventType() == EventType.exploring) {
                        if (qo.getName().equals(eventData)) {
                            qo.next();
                        }
                    }
                }
            }
            int i;
            for (i = 0; i < questObjectives[questId].size(); i++) {
                if (!questObjectives[questId].get(i).isObjectiveComplete()) {
                    break;
                }
            }
            if (i == questObjectives[questId].size()) {
                advanceState(questId);
            }
        } else if (questStates[questId] == 2) { // quest objective achieved
            advanceState(questId);
        } else {
            System.out.println("ERROR: Quest '" + questId + "' already completed");
        }
    }

    // TODO check total number of quest items in the inventory when dropping them
    public void regressObjectives(int questId, EventType eventType, String eventData) {
        for (QuestObjective qo: questObjectives[questId]) {
            // TODO get the opposite event
            if (qo.getEventType().getOpposite() == eventType) {
                // TODO update objectives for all possible types
                if (eventType == EventType.dropping || eventType == EventType.obtaining) {
                    ItemType itemType = ItemType.valueOf(eventData);
                    if (qo.getItemType() == itemType) {
                        qo.prev();
                    }
                }
            }
        }
        if (questStates[questId] == 2) { // quest objective no longer achieved
            regressState(questId);
        }
    }

    private void regressState(int questId) {
        questStates[questId]--;

        System.out.println("Quest '" + QuestEnum.values()[questId].getName() + "' downgraded to state "
                + questStates[questId]);
    }

    private void advanceState(int questId) {
        questStates[questId]++;

        if (questStates[questId] == 3) { // quest completed
            // activeQuestsNumber--;
            highlightedQuestIndex = 0;

            System.out.println("Quest '" + QuestEnum.values()[questId].getName() + "' complete!");
        } else {
            System.out.println("Quest '" + QuestEnum.values()[questId].getName() + "' advanced to state "
                    + questStates[questId]);
        }
    }

    public void draw(Graphics g) {
        if (!shown) {
            return;
        }
        g.drawImage(images[0], QuestLog.x, QuestLog.y, QuestLog.x + QuestLog.width,
                QuestLog.y + QuestLog.height, 0, 0, 300, 250);

        g.setColor(Color.green);
        for (int i = 0; i < QuestEnum.values().length; i++) {
            g.drawImage(images[1], QuestLog.x + 30 + 60 * (i % 5), QuestLog.y - 45 + 20 * (i / 5));
        }

        for (int i = 0; i < QuestEnum.values().length; i++) {
            if (questStates[i] != 0) {
                QuestEnum quest = QuestEnum.values()[i];
                g.setColor(Color.white);
                g.drawString(quest.getName(), QuestLog.x + 30 + 60 * (i % 5), QuestLog.y - 45 + 20 * (i / 5));
                if (i == highlightedQuestIndex) {
                    g.setColor(Color.black);
                    g.drawString(Text.wordWrap(quest.getDescription(), QuestLog.width), QuestLog.x + 20,
                            20 + QuestLog.y);

                    ItemType[] itemRewards = QuestEnum.values()[highlightedQuestIndex].getRewardItemTypes();
                    for (int j = 0; j < itemRewards.length; j++) {
                        g.drawString("Quest reward: " + itemRewards[j].getName(), QuestLog.x + 20,
                                QuestLog.y + QuestLog.height - 15);
                    }
                    for (int j = 0; j < questObjectives[highlightedQuestIndex].size(); j++) {
                        g.drawString(questObjectives[highlightedQuestIndex].get(j).getProgress(),
                                QuestLog.x + 20, QuestLog.y + QuestLog.height - 60 + 20 * j);
                    }
                }
            }
        }
    }

    public ArrayList<String> getDataOfQuest(int questId) {
        ArrayList<String> result = new ArrayList<>();
        if (questStates[questId] == 3) { // give reward if quest is complete
            for (ItemType rewardItemType: QuestEnum.values()[questId].getRewardItemTypes()) {
                result.add("addItem " + rewardItemType);
            }
        }
        return result;
    }

    public Rectangle[] getRects() {
        return shown ? rects : new Rectangle[] {};
    }

    public void toggle() {
        shown = !shown;
    }

    public int getStateOfQuest(int questId) {
        return questStates[questId];
    }

    public void highlightQuest(int i) {
        if (QuestEnum.values().length > i) {
            highlightedQuestIndex = i;
        }
    }
}
