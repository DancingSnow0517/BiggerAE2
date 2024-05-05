package cn.dancingsnow.bigger_ae2.item.cell;

import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.AEKeyType;
import appeng.api.stacks.KeyCounter;
import appeng.api.storage.cells.CellState;
import appeng.api.storage.cells.ISaveProvider;
import appeng.api.storage.cells.StorageCell;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.math.BigInteger;

public class DigitalSingularityStorageCell implements StorageCell {
    private static final String KEY = "key";
    private static final String COUNT = "count";

    private final ItemStack stack;
    private final ISaveProvider container;

    private final AEKeyType type;
    @Getter
    private AEKey storedItem;
    @Getter
    private final AEKey filterItem;
    @Getter
    private BigInteger count;

    private boolean isPersisted = true;

    public DigitalSingularityStorageCell(ItemStack stack, ISaveProvider container) {
        this.stack = stack;
        this.container = container;

        DigitalSingularityCellItem cell = (DigitalSingularityCellItem) stack.getItem();

        storedItem = getTag().contains(KEY) ? cell.getKeyType().loadKeyFromTag(getTag().getCompound(KEY)) : null;
        filterItem = cell.getConfigInventory(stack).getKey(0);
        type = cell.getKeyType();

        count = !getTag().getString(COUNT).isEmpty()
            ? new BigInteger(getTag().getString(COUNT))
            : BigInteger.ZERO;
    }

    @Override
    public CellState getStatus() {
        if (filterItem != null) {
            return CellState.TYPES_FULL;
        }
        if (storedItem == null || count.signum() < 1) {
            return CellState.EMPTY;
        }
        return CellState.TYPES_FULL;
    }

    @Override
    public double getIdleDrain() {
        return 25;
    }

    @Override
    public void persist() {
        if (isPersisted) {
            return;
        }
        if (storedItem == null || count.signum() < 1) {
            getTag().remove(KEY);
            getTag().remove(COUNT);
        } else {
            getTag().put(KEY, storedItem.toTagGeneric());
            getTag().putString(COUNT, count.toString());
        }

        isPersisted = true;
    }

    @Override
    public Component getDescription() {
        return stack.getHoverName();
    }

    @Override
    public long insert(AEKey what, long amount, Actionable mode, IActionSource source) {
        if (amount == 0) {
            return 0;
        }
        if (what.getType() != type) {
            return 0;
        }
        if (filterItem != null && !filterItem.equals(what)) {
            return 0;
        }
        if (storedItem != null && !what.equals(storedItem)) {
            return 0;
        }

        BigInteger insertAmount = BigInteger.valueOf(amount);
        if (mode == Actionable.MODULATE) {
            if (storedItem == null) {
                storedItem = what;
            }
            count = count.add(insertAmount);
            saveChanges();
        }
        return amount;
    }

    @Override
    public long extract(AEKey what, long amount, Actionable mode, IActionSource source) {
        if (count.signum() < 1) {
            return 0;
        }
        if (storedItem == null) {
            return 0;
        }
        if (!what.equals(storedItem)) {
            return 0;
        }

        BigInteger extractAmount = BigInteger.valueOf(amount);
        BigInteger currentCount = count;
        if (currentCount.compareTo(extractAmount) <= 0) {
            if (mode == Actionable.MODULATE) {
                storedItem = null;
                count = BigInteger.ZERO;
                saveChanges();
            }

            return clampedLong(currentCount, Long.MAX_VALUE);
        } else {
            if (mode == Actionable.MODULATE) {
                count = count.subtract(extractAmount);
                saveChanges();
            }
            return clampedLong(extractAmount, Long.MAX_VALUE);
        }
    }

    @Override
    public void getAvailableStacks(KeyCounter out) {
        if (storedItem != null) {
            out.add(storedItem, clampedLong(count, Long.MAX_VALUE));
        }
    }

    @Override
    public boolean isPreferredStorageFor(AEKey what, IActionSource source) {
        return what.equals(storedItem) || what.equals(filterItem);
    }

    @Override
    public boolean canFitInsideCell() {
        return filterItem == null && storedItem == null && count.signum() < 1;
    }

    private void saveChanges() {
        isPersisted = false;
        if (container != null) {
            container.saveChanges();
        } else {
            persist();
        }
    }

    private CompoundTag getTag() {
        return stack.getOrCreateTag();
    }

    private long clampedLong(BigInteger toClamp, long limit) {
        return toClamp.min(BigInteger.valueOf(limit)).longValue();
    }

    public long getStoredQuantity() {
        return clampedLong(count, Long.MAX_VALUE);
    }
}
