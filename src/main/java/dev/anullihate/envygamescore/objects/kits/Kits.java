package dev.anullihate.envygamescore.objects.kits;

import cn.nukkit.Player;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.ServerException;
import dev.anullihate.envygamescore.EnvyGamesCore;

import java.io.File;
import java.util.*;

public class Kits {

    private EnvyGamesCore core;

    private Map<String, Kit> kits = new HashMap<>();
    private Map<String, Config> cooldowns = new HashMap<>();

    public Kits(EnvyGamesCore core) {
        this.core = core;

        this.loadKits();
    }

    private void loadKits() {
        this.core.saveResource("kits.yml");
        Config configKits = new Config(new File(this.core.getDataFolder(), "kits.yml"), Config.YAML);

        configKits.getSections().forEach((kit, kitObject) -> {
            this.kits.put(kit, new Kit(this.core, kit, (ConfigSection)kitObject));
        });
    }

    public Kit getKit(String kit) {
        Map<String, Kit> lowerKeyedKits = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        lowerKeyedKits.putAll(kits);

        if (lowerKeyedKits.containsKey(kit.toLowerCase())) {
            return lowerKeyedKits.get(kit.toLowerCase());
        }

        return null;
    }

    public Map<String, Kit> getKits() {
        return kits;
    }
}
