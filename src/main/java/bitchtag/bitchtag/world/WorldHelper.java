package bitchtag.bitchtag.world;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

import java.time.Instant;
import java.time.temporal.ChronoField;

import static bitchtag.bitchtag.world.WorldConstants.*;


public enum WorldHelper {
    INSTANCE;
    private World currentOverWorld;
    private World currentNetherWorld;
    private World currentEndWorld;

    public void init(){
        currentPrefixWorld = String.valueOf(Instant.now().get(ChronoField.MILLI_OF_SECOND));
        createNewWorld();
    }

    public void createNewWorld() {
        createOverWorld();
        createNetherWorld();
        createEndWorld();
    }

    private void createOverWorld(){
        WorldCreator worldCreator = new WorldCreator(FILEPATH + WORLD_SUFFIX + currentPrefixWorld);

        worldCreator.environment(World.Environment.NORMAL);
        worldCreator.type(WorldType.NORMAL);

        this.currentOverWorld = worldCreator.createWorld();
    }

    private void createNetherWorld(){
        WorldCreator worldCreator = new WorldCreator(FILEPATH + NETHER_SUFFIX + currentPrefixWorld);

        worldCreator.environment(World.Environment.NETHER);
        worldCreator.type(WorldType.NORMAL);

        this.currentNetherWorld = worldCreator.createWorld();
    }

    private void createEndWorld(){
        WorldCreator worldCreator = new WorldCreator(FILEPATH + END_SUFFIX + currentPrefixWorld);

        worldCreator.environment(World.Environment.THE_END);
        worldCreator.type(WorldType.NORMAL);

        this.currentEndWorld = worldCreator.createWorld();
    }

    public World getCurrentOverWorld() {
        return currentOverWorld;
    }

    public World getCurrentEndWorld() {
        return currentEndWorld;
    }

    public World getCurrentNetherWorld() {
        return currentNetherWorld;
    }
}
