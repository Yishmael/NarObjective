package quests;

import types.EventType;
import types.ItemType;

public enum QuestEnum {
    help(
            "Help",
            new String[] { "Find my brother in the cave and help him escape.", "Hurry!",
                    "Thank you! He's safe at last!" },

            "Mut has asked you to find his brother in a cave to the west and help him get out to safety.",
            new QuestObjective[] { new QuestObjective("Brother helped", EventType.talking, null, "Pig", 1) },
            new ItemType[] {},
            new int[] {}),

    fishing(
            "Fishing",
            new String[] { "Find a fishing pole and catch a fish.", "No luck yet, eh?", "Good job." },
            "You need to find a fishing pole and catch a fish.",
            new QuestObjective[] {
                    new QuestObjective("Fishing pole found", EventType.obtaining, ItemType.fishingpole, null,
                            1),
                    new QuestObjective("Fish caught", EventType.obtaining, ItemType.fish1, null, 1) },
            new ItemType[] {},
            new int[] {}),

    drylands(
            "Dry lands",
            new String[] { "Find out if there's anyone in the desert.", "...I'm thirsty...",
                    "We better get him some water." },
            "<name> has asked you to see if there's anyone in the desert.",
            new QuestObjective[] { new QuestObjective("Found someone", EventType.talking, null, "Atu", 1) },
            new ItemType[] { ItemType.lapis },
            new int[] {}),

    hunting(
            "Hunting",
            new String[] { "Go find a deer, skin it, and bring me its hide.", "Keep trying.", "Well done!" },
            "<name> has asked you to go to the nearby forest in search of deer. If you find one, kill it, skin it, and bring its hide to <name>.For your work, <name> will reward you with a hot cup of tea.",
            new QuestObjective[] {
                    new QuestObjective("Dear skinned", EventType.using, ItemType.knife1, null, 1),
                    new QuestObjective("Deer's hide", EventType.obtaining, ItemType.hide1, null, 1) },
            new ItemType[] { ItemType.tea },
            new int[] {}),

    forest(
            "Forest!",
            new String[] { "Go east until you reach the forest.", "Back already?",
                    "I hope it wasn't too scary." },
            "Venture of the forest in the east.",
            new QuestObjective[] {
                    new QuestObjective("Forest discovered", EventType.exploring, null, "Forest", 1) },
            new ItemType[] { ItemType.axe1 },
            new int[] {}),
    kimislostflask(
            "Kimi's lost flask",
            new String[] {
                    "I've lost my lucky flask somewhere in the Eastern Forest as I was running from a pack of wolves two few nights ago. Please return it to me if you find it.",
                    "I should've been more careful...", "Thank you! Enjoy this fish." },
            "Kimi has asked you to find his lost flask in the Eastern Forest. Beware the wolves and other beasts that luck the area. It is especially dangerous to go there at night, as it reduces your vision and increases a chance of a hostile creature encounter. Once you've found the flask, return to Kimi for a reward.",
            new QuestObjective[] {
                    new QuestObjective("Kimi's lucky flask", EventType.obtaining, ItemType.flask1, null, 1) },
            new ItemType[] { ItemType.fish2 },
            new int[] {}),

    legendofthedeep(
            "Legend of the Deep",
            new String[] {
                    "I've heard of a legend about the sea monter. The legend says, if you catch some of the rarest fish around, the mighty moster of the Deep will resurface in the ocean.",
                    "ND", "ND" },
            "Judgding by Zot's words, those who succeed in catching three of the rarest fish in this world will get to face the great beast of the deep, Ylggur, if they dare to venture to the far seas. You must be very careful, as the beast can't be easily beaten, but can you outsmart it?",
            new QuestObjective[] {
                    new QuestObjective("Rare fish", EventType.obtaining, ItemType.fish3, null, 3) },
            new ItemType[] {},
            new int[] {}),

    harvest(
            "Wheatful thinking",
            new String[] {
                    "My wheat field is in need of harvesting, but today I'm not feeling well, hopefully it'll pass soon. Could you do me a favor and harvest the wheat for me? The scythe is in the farm house right to the east of here. I promise I will make your work worthwhile, <player>.",
                    "I should really have some rest...",
                    "Thank you for your help. I hope I'll be back on my feel before the next harvest." },
            "<name> has asked you to harvest wheat for him. The scythe needed can be found in his farm house to the east of his home. Once you've gathered 10 wheat, return the scythe where you've found it and then return to <name> for a reward.",
            new QuestObjective[] {
                    new QuestObjective("Wheat harvested", EventType.obtaining, ItemType.wheat1, null, 10),
                    new QuestObjective("Scythe returned", EventType.using, ItemType.scythe1, null, 1), },
            new ItemType[] { ItemType.bread1 },
            new int[] {}),

    dummy(
            "",
            new String[] { "", "", "" },
            "",
            new QuestObjective[] {
                    new QuestObjective("Rare fish", EventType.obtaining, ItemType.fish3, null, 3), },
            new ItemType[] {},
            new int[] {}),

    ;

    private String name, description;
    private String[] dialogues = new String[3];
    private QuestObjective[] questObjectives;
    private ItemType[] rewardItemTypes;
    private int[] requiredQuestsIds;

    QuestEnum(String name, String[] dialogues, String description, QuestObjective[] questObjectives,
            ItemType[] rewardItemTypes, int[] requiredQuestsIds) {
        this.name = name;
        this.dialogues = dialogues;
        this.description = description;
        this.questObjectives = questObjectives;
        this.rewardItemTypes = rewardItemTypes;
        this.requiredQuestsIds = requiredQuestsIds;
    }

    public int[] getRequiredQuestsIds() {
        return requiredQuestsIds;
    }

    public int getId() {
        return ordinal();
    }

    public ItemType[] getRewardItemTypes() {
        return rewardItemTypes;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String[] getDialogues() {
        return dialogues;
    }

    public QuestObjective[] getQuestObjectives() {
        return questObjectives;
    }
}
