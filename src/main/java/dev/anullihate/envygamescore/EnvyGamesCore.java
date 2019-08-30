package dev.anullihate.envygamescore;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import com.creeperface.nukkit.placeholderapi.api.PlaceholderAPI;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.massivecraft.factions.P;
import dev.anullihate.envygamescore.apis.GeneralAPI;
import dev.anullihate.envygamescore.commands.KitsCmd;
import dev.anullihate.envygamescore.datatables.User;
import dev.anullihate.envygamescore.listeners.BlockEventListener;
import dev.anullihate.envygamescore.listeners.DataPacketEventListener;
import dev.anullihate.envygamescore.listeners.PlayerEventListener;
import dev.anullihate.envygamescore.objects.kits.Kit;
import dev.anullihate.envygamescore.objects.kits.Kits;
import dev.anullihate.envygamescore.tasks.ClearLagTask;
import dev.anullihate.envygamescore.tasks.UpdaterTask;
import dev.anullihate.envygamescore.tasks.KitsCooldownTask;
import ru.nukkit.dblib.DbLib;

import java.io.IOException;
import java.sql.SQLException;

public class EnvyGamesCore extends PluginBase {

    private static EnvyGamesCore core;

    public ConnectionSource connectionSource;
    public static Dao<User, String> accountDao;

    public static Kits kits;

    // apis
    public static GeneralAPI generalAPI;
    public static PlaceholderAPI placeholderAPI;
    public static P p;

    // config
    public static Config pluginConfig;

    private boolean connectToDbLib() {
        if (this.getServer().getPluginManager().getPlugin("DbLib") == null) {
            this.getLogger().info(TextFormat.RED + "DbLib plugin not found");
            return false;
        }

        connectionSource = DbLib.getConnectionSource();
        if (connectionSource == null) return false;

        try {
            accountDao = DaoManager.createDao(connectionSource, User.class);
            TableUtils.createTableIfNotExists(connectionSource, User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static EnvyGamesCore getInstance() {
        return core;
    }

    @Override
    public void onEnable() {
        core = this;
        saveDefaultConfig();

        this.connectToDbLib();

        this.loadAPIS();

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
        try {
            connectionSource.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerCommands() {
        this.getServer().getCommandMap().register("envygamescore", new KitsCmd(this));
    }

    private void registerEventListeners() {
        this.getServer().getPluginManager().registerEvents(new PlayerEventListener(this), this);
        this.getServer().getPluginManager().registerEvents(new BlockEventListener(this), this);
        this.getServer().getPluginManager().registerEvents(new DataPacketEventListener(this), this);
    }

    private void runSchedulers() {
        this.getServer().getScheduler().scheduleRepeatingTask(new KitsCooldownTask(this), 1200);
        this.getServer().getScheduler().scheduleRepeatingTask(new ClearLagTask(this), 20 * 1200);
        this.getServer().getScheduler().scheduleRepeatingTask(new UpdaterTask(this), 20);
    }

    private void loadAPIS() {
        generalAPI = new GeneralAPI(this);
        placeholderAPI = PlaceholderAPI.getInstance();
        p = P.p;
    }
}
