package cn.dancingsnow.bigger_ae2.integration.appliedmekanistics.item;

import appeng.api.stacks.AEKey;
import appeng.items.storage.BasicStorageCell;

import net.minecraft.world.item.ItemStack;

import me.ramidzkh.mekae2.ae2.MekanismKey;
import me.ramidzkh.mekae2.ae2.MekanismKeyType;
import mekanism.api.chemical.attribute.ChemicalAttributeValidator;

public class AdvancedChemicalStorageCell extends BasicStorageCell {
    public AdvancedChemicalStorageCell(
            Properties properties, double idleDrain, int kilobytes, int bytesPerType, int totalTypes) {
        super(properties, idleDrain, kilobytes, bytesPerType, totalTypes, MekanismKeyType.TYPE);
    }

    public boolean isBlackListed(ItemStack cellItem, AEKey requestedAddition) {
        if (requestedAddition instanceof MekanismKey key) {
            return !ChemicalAttributeValidator.DEFAULT.process(key.getStack());
        } else {
            return true;
        }
    }
}
