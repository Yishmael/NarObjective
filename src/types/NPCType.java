package types;

import org.newdawn.slick.geom.Point;

import quests.QuestEnum;

public enum NPCType {

    kimi(
            "Kimi",
            new String[] {},
            new QuestEnum[] { QuestEnum.kimislostflask },
            new QuestEnum[] { QuestEnum.kimislostflask },
            new ItemType[] {},
            new Point[] { new Point(7, 4), new Point(10, 4), new Point(10, 6), new Point(7, 6) }),
    zot(
            "Zot",
            new String[] { "How can I help?" },
            new QuestEnum[] { QuestEnum.fishing },
            new QuestEnum[] { QuestEnum.fishing },
            new ItemType[] {},
            new Point[] {}),
    haru(
            "Haru",
            new String[] { "Greetings." },
            new QuestEnum[] { QuestEnum.hunting },
            new QuestEnum[] { QuestEnum.hunting },
            new ItemType[] {},
            new Point[] {}),
    tol(
            "Tol",
            new String[] { "Howdy!" },
            new QuestEnum[] { QuestEnum.drylands },
            new QuestEnum[] {},
            new ItemType[] { ItemType.fish1, ItemType.fishingpole },
            new Point[] {}),
    atu(
            "Atu",
            new String[] { "Greetings, traveler!", "Yes?" },
            new QuestEnum[] {},
            new QuestEnum[] { QuestEnum.drylands },
            new ItemType[] { ItemType.stick, ItemType.stick },
            new Point[] {}),
    nassi(
            "Nassi",
            new String[] { "Hey there!" },
            new QuestEnum[] {},
            new QuestEnum[] {},
            new ItemType[] {},
            new Point[] {}),
    haki(
            "Haki",
            new String[] { "Yeah?" },
            new QuestEnum[] { QuestEnum.forest },
            new QuestEnum[] { QuestEnum.forest },
            new ItemType[] {},
            new Point[] {}),
    pig(
            "Pig",
            new String[] { "<snuffles>" },
            new QuestEnum[] {},
            new QuestEnum[] {},
            new ItemType[] {},
            new Point[] {}),

    farmeryi(
            "Farmer Yi",
            new String[] { "I've been really busy these days." },
            new QuestEnum[] { QuestEnum.harvest },
            new QuestEnum[] { QuestEnum.harvest },
            new ItemType[] {},
            new Point[] {}),
    rud(
            "Rud",
            new String[] {},
            new QuestEnum[] { QuestEnum.help },
            new QuestEnum[] { QuestEnum.help },
            new ItemType[] {},
            new Point[] {}),
    tutz(
            "Tutz",
            new String[] { "<breathes heavily>" },
            new QuestEnum[] {},
            new QuestEnum[] {},
            new ItemType[] {},
            new Point[] {}),
    mun(
            "Mun",
            new String[] { "<stares>" },
            new QuestEnum[] {},
            new QuestEnum[] {},
            new ItemType[] {},
            new Point[] {}),
    az(
            "Az",
            new String[] { "<mumbles>" },
            new QuestEnum[] {},
            new QuestEnum[] {},
            new ItemType[] {},
            new Point[] {}),

    rat1("Rat", new String[] {}, new QuestEnum[] {}, new QuestEnum[] {}, new ItemType[] {}, new Point[] {}),

    nil(
            "nil",
            new String[] { "dummy" },
            new QuestEnum[] {},
            new QuestEnum[] {},
            new ItemType[] {},
            new Point[] {});

    private String name;
    private String[] textLines;
    private QuestEnum[] providingQuests;
    private QuestEnum[] finishingQuests;
    private ItemType[] items;
    private Point[] patrolPoints;

    NPCType(String name, String[] textLines, QuestEnum[] providingQuests, QuestEnum[] finishingQuests,
            ItemType[] items, Point[] patrolPoints) {
        this.name = name;
        this.textLines = textLines;
        this.providingQuests = providingQuests;
        this.finishingQuests = finishingQuests;
        this.items = items;
        this.patrolPoints = patrolPoints;
    }

    public Point[] getPatrolPoints() {
        return patrolPoints;
    }

    public String getName() {
        return name;
    }

    public ItemType[] getItems() {
        return items;
    }

    public String[] getTextLines() {
        return textLines;
    }

    public QuestEnum[] getProvidingQuests() {
        return providingQuests;
    }

    public QuestEnum[] getFinishingQuests() {
        return finishingQuests;
    }

}
