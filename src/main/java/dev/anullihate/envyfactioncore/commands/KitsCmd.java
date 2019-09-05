package dev.anullihate.envyfactioncore.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import dev.anullihate.envyfactioncore.EnvyFactionCore;
import dev.anullihate.envyfactioncore.guis.kits.KitsSelectionGui;

public class KitsCmd extends Command {

    private EnvyFactionCore core;

    public KitsCmd(EnvyFactionCore core) {
        super("kits", "open kits ui", "/kits");
        this.core = core;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(TextFormat.RED + "Please use this command in-game.");
            return false;
        }

        Player player = (Player) sender;

        player.showFormWindow(new KitsSelectionGui(player));

        return true;
    }
}
