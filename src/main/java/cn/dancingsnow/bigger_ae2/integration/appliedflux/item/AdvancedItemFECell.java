package cn.dancingsnow.bigger_ae2.integration.appliedflux.item;

import cn.dancingsnow.bigger_ae2.integration.appliedflux.AppliedFluxItems;

import appeng.api.stacks.KeyCounter;
import appeng.api.storage.StorageCells;
import appeng.api.storage.cells.StorageCell;
import appeng.core.localization.PlayerMessages;
import appeng.util.InteractionUtil;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

import com.glodblock.github.appflux.common.items.ItemFECell;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class AdvancedItemFECell extends ItemFECell {
    private final ItemLike coreItem;

    public AdvancedItemFECell(ItemLike coreItem, int kilobytes, double idleDrain) {
        super(coreItem, kilobytes, idleDrain);
        this.coreItem = coreItem;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(
            Level level, Player player, InteractionHand hand) {
        this.disassembleDrive(player.getItemInHand(hand), level, player);
        return new InteractionResultHolder<>(
                InteractionResult.sidedSuccess(level.isClientSide()), player.getItemInHand(hand));
    }

    private boolean disassembleDrive(ItemStack stack, Level level, Player player) {
        if (InteractionUtil.isInAlternateUseMode(player)) {
            if (level.isClientSide()) {
                return false;
            }

            Inventory playerInventory = player.getInventory();
            StorageCell inv = StorageCells.getCellInventory(stack, null);
            if (inv != null && playerInventory.getSelected() == stack) {
                KeyCounter list = inv.getAvailableStacks();
                if (list.isEmpty()) {
                    playerInventory.setItem(playerInventory.selected, ItemStack.EMPTY);
                    playerInventory.placeItemBackInInventory(new ItemStack(this.coreItem));
                    for (ItemStack upgrade : this.getUpgrades(stack)) {
                        playerInventory.placeItemBackInInventory(upgrade);
                    }
                    playerInventory.placeItemBackInInventory(
                            new ItemStack(AppliedFluxItems.ADVANCED_FLUX_CELL_HOUSING.asItem()));
                    return true;
                }

                player.displayClientMessage(PlayerMessages.OnlyEmptyCellsCanBeDisassembled.text(), true);
            }
        }

        return false;
    }
}
