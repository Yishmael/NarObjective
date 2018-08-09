package types;

public enum GroundObjectType {
    // NOTE "any" must always be on the last place

    boat1(
            "Boat",
            new String[] { "CMDfishinig1", "any" },
            new String[] { "MSG You used fishing pole on a boat. (test)", "MSG You can't use this" },
            -1),
    sign1("Sign", new String[] {}, new String[] { "MSG PLEASE DON'T DESTROY THIS SIGN" }, -1),

    mineral1("Mineral", new String[] { "CMDpick1" }, new String[] { "addItem lapis" }, 2),
    rock1("Rock", new String[] { "CMDhammer1" }, new String[] { "addItem shard" }, 3),
    tree2("Tree2", new String[] { "CMDaxe1" }, new String[] { "addItem stick" }, 2),
    vines1("Vines", new String[] { "CMDknife1" }, new String[] { "addItem vine" }, 2),
    school1(
            "School",
            new String[] { "CMDfishinig1" },
            new String[] { "addItem fish1", "addItem fish2", "addItem shrimp" },
            4),
    bush("Bush", new String[] { "any" }, new String[] { "addItem frozenberries" }, 2),

    wheat1("Wheat", new String[] { "CMDscythe1" }, new String[] { "addItem wheat1" }, 1),
    riverfishtrap("River fish trap", new String[] { "any" }, new String[] { "addItem riverfishtrap" }, 1),
    fishtrap("Fish trap", new String[] { "any" }, new String[] { "addItem fishtrap" }, 1),
    chest1("Chest", new String[] { "any" }, new String[] { "open stash" }, -1),
    painting1("Painting", new String[] { "any" }, new String[] { "MSG An abstract painting" }, -1),
    // temp

    nil("nil", new String[] {}, new String[] {}, -1),

    ;

    private String name;
    private String[] acceptedCommands;
    private String[] responses;
    private int uses;

    GroundObjectType(String name, String[] acceptedCommands, String[] responses, int uses) {
        this.name = name;
        this.acceptedCommands = acceptedCommands;
        this.responses = responses;
        this.uses = uses;
    }

    public String[] getAcceptedCommands() {
        return acceptedCommands;
    }

    public String getName() {
        return name;
    }

    public int getUses() {
        return uses;
    }

    public String[] getResponses() {
        return responses;
    }
}
