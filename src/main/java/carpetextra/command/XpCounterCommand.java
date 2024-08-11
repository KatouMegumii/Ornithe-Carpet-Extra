package carpetextra.command;

import carpet.commands.CarpetAbstractCommand;
import carpetextra.CarpetExtraSettings;
import carpetextra.logging.logHelpers.XpCounter;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.exception.CommandException;
import net.minecraft.server.command.exception.IncorrectUsageException;
import net.minecraft.server.command.source.CommandSource;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class XpCounterCommand extends CarpetAbstractCommand {
    @Override
    public String getName() {
        return "xpcounter";
    }

    @Override
    public String getUsage(CommandSource source) {
        return "Usage: xpcounter <armorStand|playerName> <reset>";
    }

    @Override
    public boolean canUse(MinecraftServer server, CommandSource source) {
        return !"off".equals(CarpetExtraSettings.xpCounter);
    }

    @Override
    public void run(MinecraftServer server, CommandSource source, String[] args) throws CommandException {
        if (args.length == 0) {
            msg(source, XpCounter.formatAll(server, false, true));
            return;
        }
        if ("reset".equals(args[0]) && args.length == 1) {
            for (XpCounter counter : XpCounter.COUNTERS.values()) {
                counter.reset(server);
            }
            return;
        }

        XpCounter counter = XpCounter.getCounter(args[0]);
        if (counter == null) {
            throw new IncorrectUsageException("XpCounter not found");
        }
        if (args.length == 1) {
            msg(source, counter.format(server, false,false));
            return;
        }
        if (args.length == 2 && "reset".equals(args[1])) {
            counter.reset(server);
            return;
        }
        throw new IncorrectUsageException(getUsage(source));
    }

    @Override
    public List<String> getSuggestions(MinecraftServer server, CommandSource source, String[] args, @Nullable BlockPos pos) {
        if (args.length == 1) {
            List<String> suggestion = new ArrayList<>();
            suggestion.add("armorStand");
            suggestion.add("reset");
            suggestion.addAll(Arrays.asList(server.getPlayerNames()));
            return suggestMatching(args, suggestion);
        }
        if (args.length == 2) {
            return suggestMatching(args, "reset");
        }

        return Collections.emptyList();
    }
}
