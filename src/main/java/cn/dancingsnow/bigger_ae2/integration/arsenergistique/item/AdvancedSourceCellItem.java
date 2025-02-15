package cn.dancingsnow.bigger_ae2.integration.arsenergistique.item;

import appeng.items.storage.StorageTier;

import gripe._90.arseng.item.SourceCellItem;

public class AdvancedSourceCellItem extends SourceCellItem {
    private final double idleDrain;
    private final long totalBytes;

    public AdvancedSourceCellItem(Properties properties, double idleDrain, int kilobytes) {
        super(properties, StorageTier.SIZE_1K);

        this.idleDrain = idleDrain;
        this.totalBytes = kilobytes * 1024L;
    }

    @Override
    public double getIdleDrain() {
        return idleDrain;
    }

    @Override
    public long getTotalBytes() {
        return totalBytes;
    }
}
