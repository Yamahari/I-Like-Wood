package yamahari.ilikewood.data.recipe;

import com.google.common.collect.Lists;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import yamahari.ilikewood.items.tier.IWoodenTieredItem;
import yamahari.ilikewood.objectholders.WoodenRecipeSerializers;

import java.util.List;

public class WoodenRepairTieredItem extends SpecialRecipe {
    public WoodenRepairTieredItem(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        List<ItemStack> list = Lists.newArrayList();

        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            ItemStack itemStack = craftingInventory.getStackInSlot(i);
            if (!itemStack.isEmpty()) {
                Item item = itemStack.getItem();
                if (item instanceof IWoodenTieredItem) {
                    list.add(itemStack);
                    if (list.size() > 1) {
                        ItemStack itemStack1 = list.get(0);
                        IWoodenTieredItem tieredItem = (IWoodenTieredItem) item;
                        IWoodenTieredItem tieredItem1 = (IWoodenTieredItem) itemStack1.getItem();
                        if (tieredItem.getWoodenTieredItemType() != tieredItem1.getWoodenTieredItemType() || tieredItem.getWoodenItemTier() != tieredItem1.getWoodenItemTier()
                                || itemStack1.getCount() != 1 || itemStack.getCount() != 1 || !itemStack1.isRepairable()) {
                            return false;
                        }
                    }
                }
            }
        }

        return list.size() == 2;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public ItemStack getCraftingResult(CraftingInventory craftingInventory) {
        List<ItemStack> list = Lists.newArrayList();

        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            ItemStack itemStack = craftingInventory.getStackInSlot(i);
            if (!itemStack.isEmpty()) {
                Item item = itemStack.getItem();
                if (item instanceof IWoodenTieredItem) {
                    list.add(itemStack);
                    if (list.size() > 1) {
                        ItemStack itemStack1 = list.get(0);
                        IWoodenTieredItem tieredItem = (IWoodenTieredItem) item;
                        IWoodenTieredItem tieredItem1 = (IWoodenTieredItem) itemStack1.getItem();
                        if (tieredItem.getWoodenTieredItemType() != tieredItem1.getWoodenTieredItemType() || tieredItem.getWoodenItemTier() != tieredItem1.getWoodenItemTier()
                                || itemStack1.getCount() != 1 || itemStack.getCount() != 1 || !itemStack1.isRepairable()) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            }
        }

        if (list.size() == 2) {
            ItemStack itemStack = list.get(0);
            ItemStack itemStack1 = list.get(1);
            IWoodenTieredItem tieredItem = (IWoodenTieredItem) itemStack.getItem();
            IWoodenTieredItem tieredItem1 = (IWoodenTieredItem) itemStack1.getItem();
            if (tieredItem.getWoodenTieredItemType() == tieredItem1.getWoodenTieredItemType() && tieredItem.getWoodenItemTier() == tieredItem1.getWoodenItemTier()
                    && itemStack1.getCount() == 1 && itemStack.getCount() == 1 && itemStack1.isRepairable()) {
                int j = itemStack.getMaxDamage() - itemStack.getDamage();
                int k = itemStack.getMaxDamage() - itemStack1.getDamage();
                int l = j + k + itemStack.getMaxDamage() * 5 / 100;
                int i1 = itemStack.getMaxDamage() - l;
                if (i1 < 0) {
                    i1 = 0;
                }

                ItemStack itemStack2 = new ItemStack(itemStack.getItem());
                itemStack2.setDamage(i1);
                return itemStack2;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return WoodenRecipeSerializers.REPAIR_TIERED_ITEM;
    }
}
