package me.deadlight.ezchestshop.listeners;

import io.papermc.paper.event.entity.ItemTransportingEntityValidateTargetEvent;
import me.deadlight.ezchestshop.data.Config;
import me.deadlight.ezchestshop.data.ShopContainer;
import me.deadlight.ezchestshop.utils.Utils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.jetbrains.annotations.NotNull;

public class ChestShopBreakPrevention implements Listener {

    //BlockBreak of this section is handled in BlockBreakListener.java

    /**
     * Entity driven explosions, such as TNT, creepers, end crystals,
     * ghast fireballs, wither skulls and the ender dragon.
     */
    @EventHandler
    public void onExplosion(EntityExplodeEvent event) {
        if (!Config.shopProtection) {
            return;
        }

        event.blockList().removeIf(ChestShopBreakPrevention::isProtected);
    }

    /**
     * Block driven explosions, such as beds detonating outside of the overworld
     * and respawn anchors detonating outside of the nether. These do not have a
     * source entity and therefore never reach {@link #onExplosion(EntityExplodeEvent)}.
     */
    @EventHandler
    public void onExplosion(BlockExplodeEvent event) {
        if (!Config.shopProtection) {
            return;
        }

        event.blockList().removeIf(ChestShopBreakPrevention::isProtected);
    }

    /**
     * Mobs that remove blocks without an explosion, most notably the wither
     * clearing a path through anything that is not wither immune.
     */
    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if (!Config.shopProtection) {
            return;
        }

        if (isProtected(event.getBlock())) {
            event.setCancelled(true);
        }
    }

    private static boolean isProtected(@NotNull Block block) {
        return ShopContainer.isShop(block.getLocation()) || Utils.isPartOfTheChestShop(block) != null;
    }

    @EventHandler
    public void onItemTransportingEntityValidateTargetEvent(ItemTransportingEntityValidateTargetEvent event) {
        if (!event.isAllowed()) {
            return;
        }

        if (ShopContainer.isShop(event.getBlock().getLocation()) || Utils.isPartOfTheChestShop(event.getBlock()) != null) {
            event.setAllowed(false);
        }
    }

    @EventHandler
    public void onBurn(BlockBurnEvent event) {
        if (!Config.shopProtection) {
            return;
        }
        if (isProtected(event.getBlock())) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent event) {
        if (!Config.shopProtection) {
            return;
        }
        for (Block block : event.getBlocks()) {
            if (isProtected(block)) {
                event.setCancelled(true);
                break;
            }
        }
    }

    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent event) {
        if (!Config.shopProtection) {
            return;
        }
        for (Block block : event.getBlocks()) {
            if (isProtected(block)) {
                event.setCancelled(true);
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryMoveItem(@NotNull InventoryMoveItemEvent event) {
        if (!Config.shopProtection) {
            return;
        }

        Location location = event.getSource().getLocation();

        if (location == null) {
            return;
        }

        if (ShopContainer.isShop(location) || Utils.isPartOfTheChestShop(location) != null) {
            event.setCancelled(true);
        }
    }
}
