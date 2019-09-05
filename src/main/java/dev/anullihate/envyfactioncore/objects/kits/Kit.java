package dev.anullihate.envyfactioncore.objects.kits;

import cn.nukkit.Player;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.ServerException;
import cn.nukkit.utils.TextFormat;
import dev.anullihate.envyfactioncore.EnvyFactionCore;
import dev.anullihate.envyfactioncore.utils.EnchantmentUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Kit {

    private EnvyFactionCore core;

    private String kitName;
    private String kitDisplayName;
    private ConfigSection kitSection;

    private int cost = 0;
    private int cooldown;

    private Config cooldownsConfig;
    private final ConfigSection playerCooldowns;

    public Kit(EnvyFactionCore core, String kitName, ConfigSection kitSection) {
        this.core = core;
        this.kitName = kitName;
        this.kitDisplayName = TextFormat.colorize(kitSection.getString("display-name"));
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

        ArrayList<Item> kitItems = new ArrayList<>();

        for (String itemDataString : kitSection.getStringList("items")) {
            Item item = itemLoader(itemDataString);
            kitItems.add(item);
        }

        String helmetStringData = kitSection.getString("helmet");
        String chestplateStringData = kitSection.getString("chestplate");
        String leggingsStringData = kitSection.getString("leggings");
        String bootsStringData = kitSection.getString("boots");

        if (kitSection.containsKey("helmet") && !helmetStringData.isEmpty()) {
            Item helmet = itemLoader(helmetStringData);
            kitItems.add(helmet);
        }

        if (kitSection.containsKey("chestplate") && !kitSection.getString("chestplate").isEmpty()) {
            Item chestplate = itemLoader(chestplateStringData);
            kitItems.add(chestplate);
        }

        if (kitSection.containsKey("leggings") && !kitSection.getString("leggings").isEmpty()) {
            Item leggings = itemLoader(leggingsStringData);
            kitItems.add(leggings);
        }

        if (kitSection.containsKey("boots") && !kitSection.getString("boots").isEmpty()) {
            Item boots = itemLoader(bootsStringData);
            kitItems.add(boots);
        }

        CompoundTag tag = new CompoundTag();

        ListTag<CompoundTag> items = new ListTag<>("Items");
        int index = 0;
        for (Item item : kitItems) {
            CompoundTag d = NBTIO.putItemHelper(item, index++);
            items.add(d);
        }

        tag.putList(items);

        Item chest = Item.fromString("chest");
        chest.setCustomName(kitDisplayName);
        chest.setCustomBlockData(tag);

        playerInventory.setItem(playerInventory.firstEmpty(chest), chest);

        if (kitSection.containsKey("effects") && !kitSection.getStringList("effects").isEmpty()) {
            for (String effectDataString: kitSection.getStringList("effects")) {
                player.addEffect(effectLoader(effectDataString));
            }
        }

        if (kitSection.containsKey("commands") && !kitSection.getStringList("commands").isEmpty()) {
            for (String commandsDataString : kitSection.getStringList("commands")) {
                this.core.getServer().dispatchCommand(new ConsoleCommandSender(),
                        TextFormat.colorize(commandsDataString
                                .replace("{player}", player.getName())
                                .replace("{kit}", kitDisplayName)
                        ));
            }
        }

        if (this.cooldown > 0) {
            this.playerCooldowns.set(player.getName().toLowerCase(), this.cooldown);
        }
    }

    private Item itemLoader(String itemDataString) {
        String[] itemDataStringArray = itemDataString.split(":");

        String itemData = String.format("%s:%s", itemDataStringArray[0], itemDataStringArray[1]);

        Item item = Item.fromString(itemData);
        item.setCount(Integer.parseInt(itemDataStringArray[2]));

        if (itemDataStringArray.length > 3 && !itemDataStringArray[3].equals("default")) {
            item.setCustomName(TextFormat.colorize(itemDataStringArray[3]));
        }

        for (int i = 4; i <= itemDataStringArray.length - 2; i += 2) {
            Enchantment enchantment = Enchantment.getEnchantment(EnchantmentUtils.getIdByName(itemDataStringArray[i]));
            enchantment.setLevel(Integer.parseInt(itemDataStringArray[i + 1]));
            item.addEnchantment(enchantment);
        }

        return item;
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
            System.out.println(time);

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
