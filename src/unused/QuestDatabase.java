package unused;

public class QuestDatabase {
    /*
    private int[] ids = new int[QuestEnum.values().length];
    private String[] names = new String[QuestEnum.values().length];
    private String[][] dialogues = new String[QuestEnum.values().length][3];
    private String[] descriptions = new String[QuestEnum.values().length];
    private int questLogWidth = 240; // TODO remove this from here
    @SuppressWarnings("unchecked")
    // TODO check the type
    private ArrayList<QuestObjective>[] questObjectives = new ArrayList[QuestEnum.values().length];
    @SuppressWarnings("unchecked")
    private ArrayList<ItemType>[] rewardItemTypes = new ArrayList[QuestEnum.values().length];
    
    public QuestDatabase() {
        
        for (int i = 0; i < QuestEnum.values().length; i++) {
            questObjectives[i] = new ArrayList<>();
            rewardItemTypes[i] = new ArrayList<>();
        }
    
        for (int i = 0; i < QuestEnum.values().length; i++) {
            ids[i] = 100 + QuestEnum.values()[i].getId();
            names[i] = QuestEnum.values()[i].getName();
            dialogues[i][0] = QuestEnum.values()[i].getDialogues()[0];
            dialogues[i][1] = QuestEnum.values()[i].getDialogues()[1];
            dialogues[i][2] = QuestEnum.values()[i].getDialogues()[2];
            descriptions[i] = Text.wordWrap(QuestEnum.values()[i].getDescription(), questLogWidth);
            for (QuestObjective qo: QuestEnum.values()[i].getQuestObjectives()) {
                questObjectives[i].add(qo);
            }
            for (ItemType rewardItem: QuestEnum.values()[i].getRewardItemTypes()) {
                rewardItemTypes[i].add(rewardItem);
            }
        }
    }
    
    public Quest getQuest(int id) {
        Quest quest = new Quest(id, names[id], dialogues[id], descriptions[id]);
    
        for (QuestObjective qo: questObjectives[id]) {
            quest.addQuestObjective(qo);
        }
    
        for (ItemType item: rewardItemTypes[id]) {
            quest.addItemReward(item);
        }
    
        return quest;
    }
    
    */
}
