package dev.anullihate;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import dev.anullihate.commands.kits.KitsCmd;
import dev.anullihate.listeners.PlayerEventListener;
import dev.anullihate.objects.Kits;
import dev.anullihate.tasks.KitsCooldownTask;
import ru.nukkit.dblib.DbLib;

public class EnvyGamesCore extends PluginBase {

    public ConnectionSource connectionSource;

    public Kits kits;

    public boolean connectToDbLib() {
        if (this.getServer().getPluginManager().getPlugin("DbLib") == null) {
            this.getLogger().info(TextFormat.RED + "DbLib plugin not found");
            return false;
        }

        connectionSource = DbLib.getConnectionSource();
        if (connectionSource == null) return false;

        return true;
    }

    public void onEnable() {
        //
        connectToDbLib();

        kits = new Kits(this);

        registerCommands();
        registerListeners();

        getServer().getScheduler().scheduleRepeatingTask(new KitsCooldownTask(this), 1200);
    }

    private void registerCommands() {
        this.getServer().getCommandMap().register("envygamescore", new KitsCmd(this));
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new PlayerEventListener(this), this);
    }
}
