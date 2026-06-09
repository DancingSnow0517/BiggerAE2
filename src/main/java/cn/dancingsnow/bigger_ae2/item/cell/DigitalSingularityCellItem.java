package cn.dancingsnow.bigger_ae2.item.cell;

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
import appeng.recipes.game.StorageCellDisassemblyRecipe;
import appeng.util.ConfigInventory;
import appeng.util.InteractionUtil;
import cn.dancingsnow.bigger_ae2.util.NumberUtil;
import lombok.Getter;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

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
    public void setFuzzyMode(ItemStack is, FuzzyMode fzMode) {
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(
        @NonNull ItemStack stack,
        @NonNull TooltipContext context,
        @NonNull TooltipDisplay tooltipDisplay,
        @NonNull Consumer<Component> lines,
        @NonNull TooltipFlag tooltipFlags
    ) {
        @Nullable DigitalSingularityStorageCell inv = HANDLER.getCellInventory(stack, null);
        if (inv != null) {
            AEKey storedItem = inv.getStoredItem();
            AEKey filterItem = inv.getFilterItem();

            if (storedItem != null) {
                lines.accept(Component.translatable("tooltip.bigger_ae2.contains", storedItem.getDisplayName()));
                lines.accept(Component.translatable(
                    "tooltip.bigger_ae2.quantity", NumberUtil.numberText(inv.getCount())));
            } else {
                lines.accept(Component.translatable("tooltip.bigger_ae2.empty"));
            }
            if (filterItem != null) {
                if (storedItem == null) {
                    lines.accept(Component.translatable("tooltip.bigger_ae2.partitioned", filterItem.getDisplayName()));
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
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level instanceof ServerLevel serverLevel) {
            this.disassembleDrive(player.getItemInHand(hand), serverLevel, player);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if (context.getLevel() instanceof ServerLevel serverLevel
            && this.disassembleDrive(stack, serverLevel, context.getPlayer())) {
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }


    private boolean disassembleDrive(ItemStack stack, ServerLevel level, Player player) {
        if (!InteractionUtil.isInAlternateUseMode(player)) {
            return false;
        }

        var disassembledStacks = StorageCellDisassemblyRecipe.getDisassemblyResult(level, stack.getItem());
        if (disassembledStacks.isEmpty()) {
            return false;
        }

        var playerInventory = player.getInventory();
        if (playerInventory.getSelectedItem() != stack) {
            return false;
        }

        var inv = StorageCells.getCellInventory(stack, null);
        if (inv != null && !inv.getAvailableStacks().isEmpty()) {
            player.sendOverlayMessage(PlayerMessages.OnlyEmptyCellsCanBeDisassembled.text());
            return false;
        }

        playerInventory.setItem(playerInventory.getSelectedSlot(), ItemStack.EMPTY);

        // Drop items from the recipe.
        for (var disassembledStack : disassembledStacks) {
            playerInventory.placeItemBackInInventory(disassembledStack.copy());
        }

        // Drop upgrades
        getUpgrades(stack).forEach(playerInventory::placeItemBackInInventory);

        return true;
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
