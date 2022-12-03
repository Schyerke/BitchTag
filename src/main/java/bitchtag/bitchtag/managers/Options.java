package bitchtag.bitchtag.managers;

import org.bukkit.WorldType;

public class Options {
    private static boolean WEIRD_KNOCKBACK = false;
    private static boolean EXPLOSIVE_ARROW = false;
    private static boolean TELEPORT_ARROW = false;
    private static boolean EXPLOSIVE_SNOWS = false;
    private static boolean EXPLOSIVE_EGGS = false;
    private static boolean DOUBLE_JUMP = false;
    private static boolean ELYTRA = false;
    private static WorldType WORLD_TYPE_GENERATION = WorldType.NORMAL;

    public static boolean isWeirdKnockback() {
        return WEIRD_KNOCKBACK;
    }

    public static void setWeirdKnockback(boolean weirdKnockback) {
        WEIRD_KNOCKBACK = weirdKnockback;
    }

    public static void toggleWeirdKnockback() {
        WEIRD_KNOCKBACK = !WEIRD_KNOCKBACK;
    }

    public static boolean isExplosiveArrow() {
        return EXPLOSIVE_ARROW;
    }

    public static void setExplosiveArrow(boolean explosiveArrow) {
        EXPLOSIVE_ARROW = explosiveArrow;
    }

    public static void toggleExplosiveArrow() {
        EXPLOSIVE_ARROW = !EXPLOSIVE_ARROW;
    }

    public static boolean isTeleportArrow() {
        return TELEPORT_ARROW;
    }

    public static void setTeleportArrow(boolean teleportArrow) {
        TELEPORT_ARROW = teleportArrow;
    }

    public static void toggleTeleportArrow() {
        TELEPORT_ARROW = !TELEPORT_ARROW;
    }

    public static boolean isExplosiveSnows() {
        return EXPLOSIVE_SNOWS;
    }

    public static void setExplosiveSnows(boolean explosiveSnows) {
        EXPLOSIVE_SNOWS = explosiveSnows;
    }

    public static void toggleExplosiveSnows() {
        EXPLOSIVE_SNOWS = !EXPLOSIVE_SNOWS;
    }

    public static boolean isExplosiveEggs() {
        return EXPLOSIVE_EGGS;
    }

    public static void setExplosiveEggs(boolean explosiveEggs) {
        EXPLOSIVE_EGGS = explosiveEggs;
    }

    public static void toggleExplosiveEggs() {
        EXPLOSIVE_EGGS = !EXPLOSIVE_EGGS;
    }

    public static boolean isDoubleJump() {
        return DOUBLE_JUMP;
    }

    public static void setDoubleJump(boolean doubleJump) {
        DOUBLE_JUMP = doubleJump;
    }

    public static void toggleDoubleJump() {
        DOUBLE_JUMP = !DOUBLE_JUMP;
    }

    public static boolean isElytra() {
        return ELYTRA;
    }

    public static void setELYTRA(boolean ELYTRA) {
        Options.ELYTRA = ELYTRA;
    }

    public static void toggleElytra() {
        ELYTRA = !ELYTRA;
    }

    public static WorldType getWorldTypeGeneration() {
        return WORLD_TYPE_GENERATION;
    }

    public static void setWorldTypeGeneration(WorldType worldTypeGeneration) {
        WORLD_TYPE_GENERATION = worldTypeGeneration;
    }
}
