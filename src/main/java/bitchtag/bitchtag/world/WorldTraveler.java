package bitchtag.bitchtag.world;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

import java.util.HashMap;
import java.util.Map;


public class WorldTraveler {
    public final String filepath;

    private final Map<World.Environment, World> worlds;

    private WorldType worldType = WorldType.NORMAL;

    public WorldTraveler(String filepath){
        this.filepath = filepath;

        this.worlds = new HashMap<>();
    }

    public void createNormalWorlds() {
        createWorld(this.worldType, World.Environment.NORMAL);
        createWorld(this.worldType, World.Environment.NETHER);
        createWorld(this.worldType, World.Environment.THE_END);
    }

    public void createWorld(final WorldType worldType, final World.Environment environment){
        WorldCreator worldCreator = new WorldCreator(filepath + environment.name() + System.nanoTime());

        worldCreator.environment(environment);
        worldCreator.type(worldType);

        this.worlds.put(environment, worldCreator.createWorld());
    }

    public World getWorldByWorldEnv(World.Environment worldEnv){
        return this.worlds.get(worldEnv);
    }

    public WorldType getWorldType() {
        return worldType;
    }

    public void setWorldType(WorldType worldType) {
        this.worldType = worldType;
    }
}
