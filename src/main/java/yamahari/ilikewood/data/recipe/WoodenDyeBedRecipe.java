package yamahari.ilikewood.data.recipe;

import com.google.common.collect.ImmutableMap;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import yamahari.ilikewood.blocks.WoodenBedBlock;
import yamahari.ilikewood.items.WoodenBedItem;
import yamahari.ilikewood.objectholders.WoodenRecipeSerializers;
import yamahari.ilikewood.tags.WoodenItemTags;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

public class WoodenDyeBedRecipe extends SpecialRecipe {
    private static final Map<DyeColor, Tag<Item>> BED_TAGS =
            new ImmutableMap.Builder<DyeColor, Tag<Item>>()
                    .put(DyeColor.BLACK, WoodenItemTags.BLACK_BEDS)
                    .put(DyeColor.BLUE, WoodenItemTags.BLUE_BEDS)
                    .put(DyeColor.BROWN, WoodenItemTags.BROWN_BEDS)
                    .put(DyeColor.CYAN, WoodenItemTags.CYAN_BEDS)
                    .put(DyeColor.GRAY, WoodenItemTags.GRAY_BEDS)
                    .put(DyeColor.GREEN, WoodenItemTags.GREEN_BEDS)
                    .put(DyeColor.LIGHT_BLUE, WoodenItemTags.LIGHT_BLUE_BEDS)
                    .put(DyeColor.LIGHT_GRAY, WoodenItemTags.LIGHT_GRAY_BEDS)
                    .put(DyeColor.LIME, WoodenItemTags.LIME_BEDS)
                    .put(DyeColor.MAGENTA, WoodenItemTags.MAGENTA_BEDS)
                    .put(DyeColor.ORANGE, WoodenItemTags.ORANGE_BEDS)
                    .put(DyeColor.PINK, WoodenItemTags.PINK_BEDS)
                    .put(DyeColor.PURPLE, WoodenItemTags.PURPLE_BEDS)
                    .put(DyeColor.RED, WoodenItemTags.RED_BEDS)
                    .put(DyeColor.WHITE, WoodenItemTags.WHITE_BEDS)
                    .put(DyeColor.YELLOW, WoodenItemTags.YELLOW_BEDS)
                    .build();

    public WoodenDyeBedRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean matches(CraftingInventory craftingInventory, World world) {
        ItemStack bed = ItemStack.EMPTY;
        ItemStack dye = ItemStack.EMPTY;

        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            ItemStack itemStack = craftingInventory.getStackInSlot(i);
            if (!itemStack.isEmpty()) {
                if (itemStack.getItem() instanceof WoodenBedItem) {
                    if (!bed.isEmpty()) {
                        return false;
                    }
                    bed = itemStack;
                } else if (itemStack.getItem() instanceof DyeItem) {
                    if (!dye.isEmpty()) {
                        return false;
                    }
                    dye = itemStack;
                } else {
                    return false;
                }
            }
        }
        if (!bed.isEmpty() && !dye.isEmpty()) {
            WoodenBedBlock woodenBedBlock = (WoodenBedBlock) ((WoodenBedItem) bed.getItem()).getBlock();
            DyeItem dyeItem = (DyeItem) dye.getItem();
            return (woodenBedBlock.getDyeColor() == DyeColor.WHITE) == (dyeItem.getDyeColor() != DyeColor.WHITE);
        }
        return false;
    }

    @Override
    @ParametersAreNonnullByDefault
    @Nonnull
    public ItemStack getCraftingResult(CraftingInventory craftingInventory) {
        ItemStack bed = ItemStack.EMPTY;
        ItemStack dye = ItemStack.EMPTY;

        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            ItemStack itemStack1 = craftingInventory.getStackInSlot(i);
            if (!itemStack1.isEmpty()) {
                if (itemStack1.getItem() instanceof WoodenBedItem) {
                    if (!bed.isEmpty()) {
                        return ItemStack.EMPTY;
                    }
                    bed = itemStack1;

                } else if (itemStack1.getItem() instanceof DyeItem) {
                    if (!dye.isEmpty()) {
                        return ItemStack.EMPTY;
                    }
                    dye = itemStack1;
                } else {
                    return ItemStack.EMPTY;
                }
            }
        }
        ItemStack result = ItemStack.EMPTY;
        if (!bed.isEmpty() && !dye.isEmpty()) {
            WoodenBedItem input = (WoodenBedItem) bed.getItem();
            WoodenBedBlock woodenBedBlock = (WoodenBedBlock) input.getBlock();
            DyeItem dyeItem = (DyeItem) dye.getItem();

            if ((woodenBedBlock.getDyeColor() == DyeColor.WHITE) == (dyeItem.getDyeColor() != DyeColor.WHITE)) {
                for (Item item : BED_TAGS.get(dyeItem.getDyeColor()).getAllElements()) {
                    if (item instanceof WoodenBedItem) {
                        WoodenBedItem output = (WoodenBedItem) item;
                        if (input.getWoodType() == output.getWoodType()) {
                            result = new ItemStack(output);
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return WoodenRecipeSerializers.DYE_BED;
    }
}
