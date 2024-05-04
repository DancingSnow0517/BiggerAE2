package cn.dancingsnow.bigger_ae2.item.cell;

import appeng.api.config.FuzzyMode;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.AEKeyType;
import appeng.api.stacks.GenericStack;
import appeng.api.storage.cells.ICellHandler;
import appeng.api.storage.cells.ICellWorkbenchItem;
import appeng.api.storage.cells.ISaveProvider;
import appeng.api.upgrades.IUpgradeInventory;
import appeng.api.upgrades.UpgradeInventories;
import appeng.core.AEConfig;
import appeng.core.localization.Tooltips;
import appeng.items.AEBaseItem;
import appeng.items.contents.CellConfig;
import appeng.items.storage.StorageCellTooltipComponent;
import appeng.util.ConfigInventory;
import lombok.Getter;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class DigitalSingularityCellItem extends AEBaseItem implements ICellWorkbenchItem {
    public static final Handler HANDLER = new Handler();
    private final AEKeyType keyType;

    public DigitalSingularityCellItem(Properties properties, AEKeyType keyType) {
        super(properties.stacksTo(1));
        this.keyType = keyType;
    }


    @Override
    public ConfigInventory getConfigInventory(ItemStack is) {
        return CellConfig.create(keyType.filter(), is, 1);
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
    public void setFuzzyMode(ItemStack is, FuzzyMode fzMode) {

    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> lines, TooltipFlag isAdvanced) {
        @Nullable DigitalSingularityStorageCell inv = HANDLER.getCellInventory(stack, null);
        if (inv != null) {
            AEKey storedItem = inv.getStoredItem();
            AEKey filterItem = inv.getFilterItem();

            if (storedItem != null) {
                lines.add(Component.translatable("tooltip.bigger_ae2.contains", storedItem.getDisplayName()));
                long quantity = inv.getStoredQuantity();
                lines.add(Component.translatable(
                    "tooltip.bigger_ae2.quantity",
                    quantity < Long.MAX_VALUE ? Tooltips.ofNumber(quantity) : Component.translatable("tooltip.bigger_ae2.a_lot")
                ));
            } else {
                lines.add(Component.translatable("tooltip.bigger_ae2.empty"));
            }
            if (filterItem != null) {
                if (storedItem == null) {
                    lines.add(Component.translatable("tooltip.bigger_ae2.partitioned", filterItem.getDisplayName()));
                } else if (!storedItem.equals(filterItem)) {
                    lines.add(Component.translatable("tooltip.bigger_ae2.mismatch").withStyle(ChatFormatting.DARK_RED));
                }
            } else {
                lines.add(
                    storedItem != null
                        ? Component.translatable("tooltip.bigger_ae2.mismatch").withStyle(ChatFormatting.DARK_RED)
                        : Component.translatable("tooltip.bigger_ae2.not_partitioned")
                );
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

    public static class Handler implements ICellHandler {
        @Override
        public boolean isCell(ItemStack is) {
            return is != null && is.getItem() instanceof DigitalSingularityCellItem;
        }

        @Override
        public @Nullable DigitalSingularityStorageCell getCellInventory(ItemStack is, @Nullable ISaveProvider host) {
            return isCell(is) ? new DigitalSingularityStorageCell(is, host) : null;
        }
    }
}
