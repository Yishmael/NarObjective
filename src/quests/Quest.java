package quests;

import java.util.ArrayList;

import types.ItemType;

public class Quest {
    private int id;
    private String name, description;
    private String[] dialogues;
    private ArrayList<QuestObjective> questObjectives = new ArrayList<>();
    private ArrayList<ItemType> rewardItemTypes = new ArrayList<>();

    public Quest(int id, String name, String[] dialogues, String description) {
        super();
        this.id = id;
        this.name = name;
        this.dialogues = dialogues;
        this.description = description;
    }

    public void addItemReward(ItemType itemRewardType) {
        this.rewardItemTypes.add(itemRewardType);
    }

    public void addQuestObjective(QuestObjective questObjective) {
        this.questObjectives.add(questObjective);
    }

    public String getDescription() {
        return description;
    }

    public String getDialogue(int state) {
        return dialogues[state];
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public QuestObjective getQuestObjective(int i) {
        if (questObjectives.size() > i) {
            return questObjectives.get(i);
        }
        return null;
    }

    public ArrayList<QuestObjective> getQuestObjectives() {
        return questObjectives;
    }

    public ItemType getRewardItemType(int i) {
        if (rewardItemTypes.size() > i) {
            return rewardItemTypes.get(i);
        }

        return null;
    }

    public ArrayList<ItemType> getRewardItemTypes() {
        return rewardItemTypes;
    }
}
