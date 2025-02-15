package cn.dancingsnow.bigger_ae2.integration.appliedexperienced.item;

import appeng.items.storage.StorageTier;

import es.degrassi.appexp.item.ExperienceStorageCell;

public class AdvancedExperienceStorageCell extends ExperienceStorageCell {
    private final double idleDrain;
    private final long totalBytes;

    public AdvancedExperienceStorageCell(Properties properties, double idleDrain, int kilobytes) {
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
