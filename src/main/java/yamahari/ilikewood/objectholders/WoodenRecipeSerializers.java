package yamahari.ilikewood.objectholders;

import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.registries.ObjectHolder;
import yamahari.ilikewood.data.recipe.WoodenDyeBedRecipe;
import yamahari.ilikewood.data.recipe.WoodenRepairTieredItem;
import yamahari.ilikewood.util.Constants;

@ObjectHolder(Constants.MOD_ID)
public class WoodenRecipeSerializers {
    @ObjectHolder("wooden_repair_tiered_item")
    public static final SpecialRecipeSerializer<WoodenRepairTieredItem> REPAIR_TIERED_ITEM = null;

    @ObjectHolder("wooden_dye_bed")
    public static final SpecialRecipeSerializer<WoodenDyeBedRecipe> DYE_BED = null;
}
