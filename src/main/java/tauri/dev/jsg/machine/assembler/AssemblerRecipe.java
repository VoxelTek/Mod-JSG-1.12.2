package tauri.dev.jsg.machine.assembler;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public abstract class AssemblerRecipe {
    public abstract int getWorkingTime(); // in ticks

    public abstract int getEnergyPerTick();

    public abstract Item getSchematic();

    public abstract ArrayList<ItemStack> getPattern();

    public abstract ItemStack getSubItemStack();

    public abstract ItemStack getResult();

    public abstract String getUnlocalizedName();

    public boolean removeSubItem() {
        return true;
    }

    public boolean removeDurabilitySubItem() {
        return false;
    }

    public boolean isOk(int energyStored, Item schematic, ArrayList<ItemStack> stacks, ItemStack subStack) {
        if (energyStored < getEnergyPerTick()) return false;
        if (getSchematic() != schematic) return false;
        int i = 0;
        for (ItemStack s : getPattern()) {
            if (s != null) {
                if (stacks.get(i).isEmpty()) return false;
                if (stacks.get(i).getItem() != s.getItem()) return false;
                if (stacks.get(i).getCount() < s.getCount()) return false;
            } else {
                if (!(stacks.get(i).isEmpty())) return false;
            }
            i++;
        }
        if (subStack.isEmpty() || subStack.getItem() != getSubItemStack().getItem()) return false;
        return subStack.getCount() >= getSubItemStack().getCount();
    }
}
