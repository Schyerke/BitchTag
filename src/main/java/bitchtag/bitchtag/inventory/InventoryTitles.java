package bitchtag.bitchtag.inventory;

public enum InventoryTitles {
    START_GAME_INVENTORY_TITLE("Start Game"), GAME_LENGTH_INVENTORY_TITLE("Game Length"), COMPASS_TRACKER_INVENTORY_TITLE("Compass Tracker");

    private final String title;

    InventoryTitles(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
