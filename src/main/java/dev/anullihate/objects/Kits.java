package dev.anullihate.objects;

import cn.nukkit.Player;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.ServerException;
import dev.anullihate.EnvyGamesCore;

import java.io.File;
import java.util.*;

public class Kits {

    private EnvyGamesCore core;

    private Map<String, ConfigSection> kits = new HashMap<>();
    private Map<String, Config> cooldowns = new HashMap<>();

    public Kits(EnvyGamesCore core) {
        this.core = core;

        loadKits();
    }

    private void loadKits() {
        Config configKits = new Config(new File(this.core.getDataFolder(), "kits.yml"), Config.YAML);

        configKits.getSections().forEach((kit, kitObject) -> {
            this.kits.put(kit, configKits.getSection(kit));

            cooldowns.put(kit, new Config(new File(this.core.getDataFolder(), "cooldowns/" + kit.toLowerCase() + ".yml"),
                    Config.YAML,
                    new ConfigSection(new LinkedHashMap<String, Object>() {
                {
                    put("players", new ConfigSection());
                }
            })));
        });
    }

    private ConfigSection getKitSection(String kit) {
        Map<String, ConfigSection> lowerKeyedKits = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        lowerKeyedKits.putAll(kits);

        if (lowerKeyedKits.containsKey(kit.toLowerCase())) {
            return lowerKeyedKits.get(kit.toLowerCase());
        }

        return null;
    }

    public void sendKit(Player to, String kit) {
        ConfigSection kitSection = getKitSection(kit);
        int cooldownInMinutes = 0;

        PlayerInventory playerInventory = to.getInventory();

        if (kitSection == null) return;

        if (kitSection.containsKey("cooldown")) {
            ConfigSection cooldown = kitSection.getSection("cooldown");
            if (cooldown.containsKey("minutes")) {
                cooldownInMinutes += cooldown.getInt("minutes");
            }
            if (cooldown.containsKey("hours")) {
                cooldownInMinutes += cooldown.getInt("hours") * 60;
            }
        } else {
            cooldownInMinutes = 24 * 60;
        }

        for (String itemDataString : kitSection.getStringList("items")) {
            Item item = itemLoader(itemDataString);
            playerInventory.addItem(item);
        }

        String helmetStringData = kitSection.getString("helmet");
        String chestplateStringData = kitSection.getString("helmet");
        String leggingsStringData = kitSection.getString("helmet");
        String bootsStringData = kitSection.getString("helmet");

        if (kitSection.containsKey("helmet") && !helmetStringData.isEmpty()) {
            Item helmet = itemLoader(helmetStringData);
            playerInventory.setHelmet(helmet);
        }

        if (kitSection.containsKey("chestplate") && !kitSection.getString("chestplate").isEmpty()) {
            Item chestplate = itemLoader(chestplateStringData);
            playerInventory.setBoots(chestplate);
        }

        if (kitSection.containsKey("leggings") && !kitSection.getString("leggings").isEmpty()) {
            Item leggings = itemLoader(leggingsStringData);
            playerInventory.setHelmet(leggings);
        }

        if (kitSection.containsKey("boots") && !kitSection.getString("boots").isEmpty()) {
            Item boots = itemLoader(bootsStringData);
            playerInventory.setBoots(boots);
        }

        if (kitSection.containsKey("effects") && !kitSection.getStringList("effects").isEmpty()) {
            for (String effectDataString: kitSection.getStringList("effects")) {
                to.addEffect(effectLoader(effectDataString));
            }
        }

        if (kitSection.containsKey("commands") && !kitSection.getStringList("commands").isEmpty()) {
            for (String commandsDataString: kitSection.getStringList("commands")) {
                this.core.getServer().dispatchCommand(new ConsoleCommandSender(),
                        commandsDataString
                                .replace("{player}", to.getName()));
            }
        }

        // cooldowns
        cooldowns.get(kit).getSection("player").set(to.getName().toLowerCase(), cooldownInMinutes);
    }

    private Item itemLoader(String itemDataString) {
        String[] itemDataStringArray = itemDataString.split(":");

        String itemData = String.format("%s:%s", itemDataStringArray[0], itemDataStringArray[1]);

        try {
            Item item = Item.fromString(itemData);
            item.setCount(Integer.parseInt(itemDataStringArray[2]));

            if (itemDataStringArray.length > 3 && !itemDataStringArray[3].equals("default")) {
                item.setCustomName(itemDataStringArray[3]);
            }

            for (int i = 4; i <= itemDataStringArray.length - 2; i += 2) {
                Enchantment enchantment = Enchantment.getEnchantment(Integer.parseInt(itemDataStringArray[i]));
                enchantment.setLevel(Integer.parseInt(itemDataStringArray[i + 1]));
                item.addEnchantment(enchantment);
            }

            return item;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Effect effectLoader(String effectDataString) {
        String[] effectDataStringArray = effectDataString.split(":");
        Effect effect;
        try {
            effect = Effect.getEffect(Integer.parseInt(effectDataStringArray[0]));
        } catch (NumberFormatException | ServerException a) {
            try {
                effect = Effect.getEffectByName(effectDataStringArray[0]);
            } catch (Exception e) {
                return null;
            }
        }

        if (effect != null) {
            return effect.setDuration(Integer.parseInt(effectDataStringArray[1]) * 20)
                    .setAmplifier(Integer.parseInt(effectDataStringArray[2]));
        }

        return null;
    }

    public void cooldownProcessor() {
        kits.forEach((kit, kitSection) -> {
            ConfigSection playerCooldowns = cooldowns.get(kit).getSection("players");

            playerCooldowns.getSections().forEach((player, remainingTime) -> {
                int playerCooldown = playerCooldowns.getInt(player);

                playerCooldowns.set(player, playerCooldown);

                if (playerCooldown <= 0) {
                    playerCooldowns.remove(player);
                }
            });
        });
    }

    public void save() {
        kits.forEach((kit, kitSection) -> {
            cooldowns.get(kit).save();
        });
    }

}
