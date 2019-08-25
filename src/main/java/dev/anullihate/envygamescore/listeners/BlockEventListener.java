package dev.anullihate.envygamescore.listeners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockChest;
import cn.nukkit.block.BlockShulkerBox;
import cn.nukkit.block.BlockUndyedShulkerBox;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntityChest;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.inventory.ChestInventory;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.item.enchantment.EnchantmentSilkTouch;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import dev.anullihate.envygamescore.EnvyGamesCore;

import java.util.ArrayList;
import java.util.List;

public class BlockEventListener implements Listener {

    private EnvyGamesCore core;

    public BlockEventListener(EnvyGamesCore core) {
        this.core = core;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreakAutoInv(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        PlayerInventory playerInventory = player.getInventory();

        Item[] itemsToAdd = event.getDrops();
        List<Item> notAdded = new ArrayList<>();

        for (int i = 0; i < itemsToAdd.length; i++) {
            if (playerInventory.canAddItem(itemsToAdd[i])) {
                playerInventory.addItem(itemsToAdd[i]);
            } else {
                notAdded.add(itemsToAdd[i]);
            }
        }
        event.setDrops(notAdded.toArray(new Item[0]));
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

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (block instanceof BlockChest) {
            BlockEntityChest t = (BlockEntityChest) block.getLevel().getBlockEntity(block);
            if (t != null) {
                ChestInventory i = t.getRealInventory();

            }
        }
    }
}
