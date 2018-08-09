package quests;

import types.EventType;
import types.ItemType;

public class QuestObjective {
    private String text;
    private int current, needed;
    private ItemType itemType;
    private EventType eventType;
    private String name;

    public QuestObjective(String text, EventType eventType, ItemType itemType, String name, int needed) {
        this.text = text;
        this.eventType = eventType;
        this.itemType = itemType;
        this.name = name;
        this.needed = needed;
    }

    public int getCurrent() {
        return current;
    }

    public EventType getEventType() {
        return eventType;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public String getName() {
        return name;
    }

    public int getNeeded() {
        return needed;
    }

    public String getProgress() {
        return text + ": " + current + "/" + needed;
    }

    public String getText() {
        return text;
    }

    public boolean isObjectiveComplete() {
        return current == needed;
    }

    public void next() {
        current = Math.min(current + 1, needed);
    }

    public void prev() {
        current = Math.max(current - 1, 0);
    }

    public void setCurrent(int current) {
        this.current = current;
    }

}
