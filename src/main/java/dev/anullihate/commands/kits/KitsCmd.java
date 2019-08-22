package dev.anullihate.commands.kits;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import dev.anullihate.EnvyGamesCore;

public class KitsCmd extends Command {

    private EnvyGamesCore core;

    public KitsCmd(EnvyGamesCore core) {
        super("kits", "open kits ui", "/kits");
        this.core = core;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(TextFormat.RED + "Please use this command in-game.");
            return true;
        }

        Player player = this.core.getServer().getPlayer(sender.getName());

        return true;
    }
}
