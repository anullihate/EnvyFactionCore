package dev.anullihate.envygamescore;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import com.creeperface.nukkit.placeholderapi.api.PlaceholderAPI;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import dev.anullihate.envygamescore.apis.GeneralAPI;
import dev.anullihate.envygamescore.commands.KitsCmd;
import dev.anullihate.envygamescore.datatables.Account;
import dev.anullihate.envygamescore.listeners.BlockEventListener;
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

    public ConnectionSource connectionSource;
    public static Dao<Account, String> accountDao;

    public static Kits kits;

    // apis
    public static GeneralAPI generalAPI;
    public static PlaceholderAPI placeholderAPI;

    // config
    public static Config pluginConfig;

    public boolean connectToDbLib() {
        if (this.getServer().getPluginManager().getPlugin("DbLib") == null) {
            this.getLogger().info(TextFormat.RED + "DbLib plugin not found");
            return false;
        }

        connectionSource = DbLib.getConnectionSource();
        if (connectionSource == null) return false;

        try {
            accountDao = DaoManager.createDao(connectionSource, Account.class);
            TableUtils.createTableIfNotExists(connectionSource, Account.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        pluginConfig = getConfig();
        connectToDbLib();

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
    }

    private void runSchedulers() {
        this.getServer().getScheduler().scheduleRepeatingTask(new KitsCooldownTask(this), 1200);
        this.getServer().getScheduler().scheduleRepeatingTask(new ClearLagTask(this), 20 * 1200);
        this.getServer().getScheduler().scheduleRepeatingTask(new UpdaterTask(this), 20);
    }

    private void loadAPIS() {
        generalAPI = new GeneralAPI(this);
        placeholderAPI = PlaceholderAPI.getInstance();
    }
}
