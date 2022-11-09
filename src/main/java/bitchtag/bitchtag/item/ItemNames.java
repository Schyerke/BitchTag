package bitchtag.bitchtag.item;

public enum ItemNames {
    START_GAME("Start Game"), GAME_LENGTH("Game Length"), COMPASS_TRACKER("Compass Tracker");

    private final String name;

    ItemNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getName(int gameLength) {
        return name + " (" + gameLength + " minutes)";
    }
}
