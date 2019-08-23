package dev.anullihate.envygamescore;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import com.j256.ormlite.support.ConnectionSource;
import dev.anullihate.envygamescore.commands.KitsCmd;
import dev.anullihate.envygamescore.listeners.BlockEventListener;
import dev.anullihate.envygamescore.listeners.PlayerEventListener;
import dev.anullihate.envygamescore.objects.kits.Kit;
import dev.anullihate.envygamescore.objects.kits.Kits;
import dev.anullihate.envygamescore.tasks.ClearLagTask;
import dev.anullihate.envygamescore.tasks.KitsCooldownTask;
import ru.nukkit.dblib.DbLib;

public class EnvyGamesCore extends PluginBase {

    public ConnectionSource connectionSource;

    public static Kits kits;

    public boolean connectToDbLib() {
        if (this.getServer().getPluginManager().getPlugin("DbLib") == null) {
            this.getLogger().info(TextFormat.RED + "DbLib plugin not found");
            return false;
        }

        connectionSource = DbLib.getConnectionSource();
        if (connectionSource == null) return false;

        return true;
    }

    @Override
    public void onEnable() {
        //
        connectToDbLib();

        kits = new Kits(this);

        this.registerCommands();
        this.registerEventListeners();
        this.runSchedulers();
    }

    @Override
    public void onDisable() {
        for (Kit kit : kits.getKits().values()) {
            kit.save();
        }
    }

    private void registerCommands() {
        this.getServer().getCommandMap().register("envygamescore", new KitsCmd(this));
    }

    private void registerEventListeners() {
        this.getServer().getPluginManager().registerEvents(new PlayerEventListener(this), this);
        this.getServer().getPluginManager().registerEvents(new BlockEventListener(this), this);
    }

    private void runSchedulers() {
        this.getServer().getScheduler().scheduleRepeatingTask(new KitsCooldownTask(this), 1200);
        this.getServer().getScheduler().scheduleRepeatingTask(new ClearLagTask(this), 20 * 1200);
    }
}
