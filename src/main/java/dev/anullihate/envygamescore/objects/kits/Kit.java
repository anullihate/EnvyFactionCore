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
import java.util.LinkedHashMap;

public class Kit {

    private EnvyGamesCore core;

    private String kitName;
    private ConfigSection kitSection;

    private int cost = 0;
    private int cooldown;

    private Config cooldownsConfig;
    private final ConfigSection playerCooldowns;

    public Kit(EnvyGamesCore core, String kitName, ConfigSection kitSection) {
        this.core = core;
        this.kitName = kitName;
        this.kitSection = kitSection;

        this.cooldown = this.getCooldownInMinutes();

        if (kitSection.containsKey("cost") && kitSection.getInt("cost") != 0) {
            this.cost = kitSection.getInt("cost");
        }

        cooldownsConfig = new Config(new File(this.core.getDataFolder(), "cooldowns/" + this.kitName.toLowerCase() + ".yml"),
                Config.YAML,
                new ConfigSection(new LinkedHashMap<String, Object>() {
                    {
                        put("players", new ConfigSection());
                    }
                }));
        playerCooldowns = this.cooldownsConfig.getSection("players");
    }

    public boolean requestHandler(Player player) {
        if (testPermission(player)) {
            if (!this.playerCooldowns.exists(player.getName().toLowerCase())) {
                if (this.cost > 0) {

                } else {
                    this.addTo(player);
                    player.sendMessage("got kit");
                    return true;
                }
            } else {
                player.sendMessage("still on cooldown");
            }
        } else {
            player.sendMessage("You have no perms to get this kit!");
        }
        return false;
    }

    public void addTo(Player player) {
        PlayerInventory playerInventory = player.getInventory();

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
                player.addEffect(effectLoader(effectDataString));
            }
        }

        if (kitSection.containsKey("commands") && !kitSection.getStringList("commands").isEmpty()) {
            for (String commandsDataString: kitSection.getStringList("commands")) {
                this.core.getServer().dispatchCommand(new ConsoleCommandSender(),
                        commandsDataString
                                .replace("{player}", player.getName()));
            }
        }

        if (this.cooldown > 0) {
            this.playerCooldowns.set(player.getName().toLowerCase(), this.cooldown);
        }
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

    private int getCooldownInMinutes() {
        int min = 0;
        if (kitSection.containsKey("cooldown")) {
            ConfigSection cooldown = kitSection.getSection("cooldown");
            if (cooldown.containsKey("minutes")) {
                min += cooldown.getInt("minutes");
            }
            if (cooldown.containsKey("hours")) {
                min += cooldown.getInt("hours") * 60;
            }
        } else {
            min = 24 * 60;
        }

        return min;
    }

    public void processCooldown() {
        this.playerCooldowns.getSections().forEach((player, time) -> {
            int remainingMinute = (int)time - 1;

            this.playerCooldowns.set(player, remainingMinute);
            if (remainingMinute <= 0) {
                this.playerCooldowns.remove(player);
            }
        });
    }

    public boolean testPermission(Player player) {
        return player.hasPermission("envykits." + this.kitName.toLowerCase());
    }

    public void save() {
        this.cooldownsConfig.save();
    }
}
