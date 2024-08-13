package carpetextra.logging.logHelpers;

import carpet.utils.Messenger;
import carpetextra.CarpetExtraServer;
import carpetextra.CarpetExtraSettings;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;

import java.util.*;

public class XpCounter {
    public static final XpCounter armorStandCounter = new XpCounter("armorStand");
    public static final Map<String, XpCounter> COUNTERS = new HashMap<>();

    static {
        // what if someone called armorStand
        COUNTERS.put("armorStand", armorStandCounter);
    }

    private long totalXp;
    private long startTick;
    private long startMillis;
    private String name;

    private XpCounter(String name) {
        this.name = name;
        this.totalXp = 0;
    }

    public long getTotalXp() {
        return totalXp;
    }

    public void add(MinecraftServer server, int xp) {
        if (startTick == 0) {
            startTick = server.getTicks();
            startMillis = MinecraftServer.getTimeMillis();
        }

        this.totalXp += xp;
    }

    public void reset(MinecraftServer server) {
        totalXp = 0;
        startTick = server.getTicks();
        startMillis = MinecraftServer.getTimeMillis();
    }

    public static List<Text> formatAll(MinecraftServer server, boolean relTime, boolean brief) {
        List<Text> result = new ArrayList<>();
        List<XpCounter> xpCounters = new ArrayList<>(COUNTERS.values());

        for (XpCounter counter : xpCounters) {
            if (counter.getTotalXp() > 0) {
                result.addAll(counter.format(server, relTime, brief));
            }
        }
        return result;
    }

    public List<Text> format(MinecraftServer server, boolean realTime, boolean brief) {
        long ticks = Math.max(realTime ? (MinecraftServer.getTimeMillis() - startMillis) / 50 : server.getTicks() - startTick, 1);
        List<Text> result = new ArrayList<>();

        if (this.totalXp == 0) {
            result.add(Messenger.c(String.format("g No xp for %s yet", this.name)));
            return result;
        }

        if (brief) {
            result.add(Messenger.c(String.format("w xp of %s: %d, %d/h, %.1fmin", name, totalXp, totalXp * (20 * 60 * 60) / ticks, ticks / (20.0 * 60.0))));
            return result;
        }

        result.add(Messenger.c(
                String.format("w XpCounter: %s (%.1f min), total: %d (%.1f/h)", name, ticks / (20.0 * 60.0), totalXp, totalXp * (20.0 * 60.0 * 60.0) / ticks),
                "nb  [X]", "^g reset", "!/xpcounter " + name + " reset"
        ));
        return result;
    }

    public static XpCounter getCounter(String name) {
        if (!COUNTERS.containsKey(name)) {
            COUNTERS.put(name, new XpCounter(name));
        }
        return COUNTERS.get(name);
    }

}
