package dev.anullihate.envygamescore.listeners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockShulkerBox;
import cn.nukkit.block.BlockUndyedShulkerBox;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.item.enchantment.EnchantmentSilkTouch;
import dev.anullihate.envygamescore.EnvyGamesCore;

public class BlockEventListener implements Listener {

    private EnvyGamesCore core;

    public BlockEventListener(EnvyGamesCore core) {
        this.core = core;
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();
        PlayerInventory playerInventory = player.getInventory();

        Item item = playerInventory.getItemInHand();
        Block block = event.getBlock();

        if (item.hasEnchantments()) {
            for (Enchantment enchantment : item.getEnchantments()) {
                if (enchantment instanceof EnchantmentSilkTouch) {
                    if (block instanceof BlockUndyedShulkerBox) {
                        player.sendMessage("You can't break Shulker Box using Silk Touch here!");
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
