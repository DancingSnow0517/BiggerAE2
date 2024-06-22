package cn.dancingsnow.bigger_ae2.item.cell;

import cn.dancingsnow.bigger_ae2.util.NumberUtil;

import appeng.api.config.FuzzyMode;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.AEKeyType;
import appeng.api.stacks.GenericStack;
import appeng.api.storage.StorageCells;
import appeng.api.storage.cells.ICellHandler;
import appeng.api.storage.cells.ICellWorkbenchItem;
import appeng.api.storage.cells.ISaveProvider;
import appeng.api.upgrades.IUpgradeInventory;
import appeng.api.upgrades.UpgradeInventories;
import appeng.core.AEConfig;
import appeng.core.localization.PlayerMessages;
import appeng.items.AEBaseItem;
import appeng.items.contents.CellConfig;
import appeng.items.storage.StorageCellTooltipComponent;
import appeng.util.ConfigInventory;
import appeng.util.InteractionUtil;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Getter
public class DigitalSingularityCellItem extends AEBaseItem implements ICellWorkbenchItem {
    public static final Handler HANDLER = new Handler();
    private final AEKeyType keyType;

    private final ItemLike coreItem;
    private final ItemLike housingItem;

    public DigitalSingularityCellItem(
            Properties properties, AEKeyType keyType, ItemLike coreItem, ItemLike housingItem) {
        super(properties.stacksTo(1));
        this.keyType = keyType;
        this.coreItem = coreItem;
        this.housingItem = housingItem;
    }

    @Override
    public ConfigInventory getConfigInventory(ItemStack is) {
        return CellConfig.create(Set.of(keyType), is, 1);
    }

    @Override
    public IUpgradeInventory getUpgrades(ItemStack stack) {
        return UpgradeInventories.forItem(stack, 1);
    }

    @Override
    public FuzzyMode getFuzzyMode(ItemStack is) {
        return null;
    }

    @Override
    public void setFuzzyMode(ItemStack is, FuzzyMode fzMode) {}

    @Override
    public void appendHoverText(
            ItemStack stack, TooltipContext context, List<Component> lines, TooltipFlag flag) {
        @Nullable DigitalSingularityStorageCell inv = HANDLER.getCellInventory(stack, null);
        if (inv != null) {
            AEKey storedItem = inv.getStoredItem();
            AEKey filterItem = inv.getFilterItem();

            if (storedItem != null) {
                lines.add(
                        Component.translatable("tooltip.bigger_ae2.contains", storedItem.getDisplayName()));
                lines.add(Component.translatable(
                        "tooltip.bigger_ae2.quantity", NumberUtil.numberText(inv.getCount())));
            } else {
                lines.add(Component.translatable("tooltip.bigger_ae2.empty"));
            }
            if (filterItem != null) {
                if (storedItem == null) {
                    lines.add(Component.translatable(
                            "tooltip.bigger_ae2.partitioned", filterItem.getDisplayName()));
                }
            }
        }
    }

    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack stack) {
        @Nullable DigitalSingularityStorageCell inv = HANDLER.getCellInventory(stack, null);
        if (inv == null) {
            return Optional.empty();
        }
        //noinspection MismatchedQueryAndUpdateOfCollection
        List<ItemStack> upgrades = new ArrayList<>();
        List<GenericStack> content = new ArrayList<>();
        if (AEConfig.instance().isTooltipShowCellContent()) {
            if (inv.getStoredItem() != null) {
                content.add(new GenericStack(inv.getStoredItem(), inv.getStoredQuantity()));
            } else if (inv.getFilterItem() != null) {
                content.add(new GenericStack(inv.getFilterItem(), 0));
            }
        }
        return Optional.of(new StorageCellTooltipComponent(upgrades, content, false, true));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(
            Level level, Player player, InteractionHand usedHand) {
        this.disassembleDrive(player.getItemInHand(usedHand), level, player);
        return new InteractionResultHolder<>(
                InteractionResult.sidedSuccess(level.isClientSide()), player.getItemInHand(usedHand));
    }

    private boolean disassembleDrive(ItemStack stack, Level level, Player player) {
        if (InteractionUtil.isInAlternateUseMode(player)) {
            if (level.isClientSide()) {
                return false;
            }

            final Inventory playerInventory = player.getInventory();
            var inv = StorageCells.getCellInventory(stack, null);
            if (inv != null && playerInventory.getSelected() == stack) {
                var list = inv.getAvailableStacks();
                if (list.isEmpty()) {
                    playerInventory.setItem(playerInventory.selected, ItemStack.EMPTY);

                    // drop core
                    playerInventory.placeItemBackInInventory(new ItemStack(coreItem));

                    // drop upgrades
                    for (var upgrade : this.getUpgrades(stack)) {
                        playerInventory.placeItemBackInInventory(upgrade);
                    }

                    // drop empty storage cell case
                    playerInventory.placeItemBackInInventory(new ItemStack(housingItem));

                    return true;
                } else {
                    player.displayClientMessage(PlayerMessages.OnlyEmptyCellsCanBeDisassembled.text(), true);
                }
            }
        }
        return false;
    }

    public static class Handler implements ICellHandler {
        @Override
        public boolean isCell(ItemStack is) {
            return is != null && is.getItem() instanceof DigitalSingularityCellItem;
        }

        @Override
        public @Nullable DigitalSingularityStorageCell getCellInventory(
                ItemStack is, @Nullable ISaveProvider host) {
            return isCell(is) ? new DigitalSingularityStorageCell(is, host) : null;
        }
    }
}
