package types;

public enum ItemType {

    stick("Stick", "Just a stick...", new String[] {}),
    bread1("Bread", "Keeps your warm.", new String[] {}),
    leg("Chicken leg", "Yummy...", new String[] {}),
    flower1("Flower", "Smells great.", new String[] {}),
    lapis("Lapis", "Not exactly shiny", new String[] {}),
    flask1("Kimi's lucky flask", "I wonder what he used this for...", new String[] {}),
    hide1("Hide", "Feels warm to the touch", new String[] {}),
    tea("Tea", "It's boiling hot!", new String[] {}),
    fish1("Fish", "It's alive!", new String[] {}),
    fish2("Common flatfish", "It's so thin!", new String[] {}),
    fish3("Flying fish", "Can it really fly?", new String[] {}),
    shrimp("Common shrimp", "Too small to be useful", new String[] {}),
    lobster("Common lobster", "Is it still fresh?", new String[] {}),
    vine("Common vine", "Nothing impressive", new String[] {}),
    wheat1("Wheat", "Einkorn species", new String[] {}),
    riverfishtrap("River fish trap", "Seems functional", new String[] {}),
    fishtrap("Fish trap", "Seems functional", new String[] {}),
    frozenberries("Frozen berries", "They're frozen!", new String[] {}),

    pick1("Pick", "Allows gathering precious minerals", new String[] { "CMDpick1" }),
    scythe1("Scythe", "Allows gathering wheat and similar plants", new String[] { "CMDscythe1" }),
    hammer1("Hammer", "It's hammer time!", new String[] { "CMDhammer1" }),
    axe1("Axe", "Useful for gathering wood.", new String[] { "CMDaxe1" }),
    knife1("Knife", "Can be used for skinning", new String[] { "CMDknife1" }),
    fishingpole("Fishing pole", "Teach a man to fish...", new String[] { "CMDfishinig1" }),
    shovel1("Shovel", "Regular shovel", new String[] { "CMDshovel1" }),
    letter("It's unintelligible", "", new String[] { "quest 0 A" }),
    //

    nil("NN", "ND", new String[] {});

    private String name, description;
    private String[] commands;

    ItemType(String name, String description, String[] commands) {
        this.name = name;
        this.description = description;
        this.commands = commands;
    }

    public String[] getCommands() {
        return commands;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
