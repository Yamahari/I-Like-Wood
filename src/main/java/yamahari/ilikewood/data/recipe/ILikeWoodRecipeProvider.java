package yamahari.ilikewood.data.recipe;

import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import yamahari.ilikewood.ILikeWood;
import yamahari.ilikewood.blocks.WoodenBedBlock;
import yamahari.ilikewood.items.tier.WoodenHoeItem;
import yamahari.ilikewood.items.tier.WoodenSwordItem;
import yamahari.ilikewood.items.tier.tool.WoodenAxeItem;
import yamahari.ilikewood.items.tier.tool.WoodenPickaxeItem;
import yamahari.ilikewood.items.tier.tool.WoodenShovelItem;
import yamahari.ilikewood.objectholders.WoodenRecipeSerializers;
import yamahari.ilikewood.objectholders.barrel.WoodenBarrelBlocks;
import yamahari.ilikewood.objectholders.bed.black.WoodenBlackBedBlocks;
import yamahari.ilikewood.objectholders.bed.blue.WoodenBlueBedBlocks;
import yamahari.ilikewood.objectholders.bed.brown.WoodenBrownBedBlocks;
import yamahari.ilikewood.objectholders.bed.cyan.WoodenCyanBedBlocks;
import yamahari.ilikewood.objectholders.bed.gray.WoodenGrayBedBlocks;
import yamahari.ilikewood.objectholders.bed.green.WoodenGreenBedBlocks;
import yamahari.ilikewood.objectholders.bed.light_blue.WoodenLightBlueBedBlocks;
import yamahari.ilikewood.objectholders.bed.light_gray.WoodenLightGrayBedBlocks;
import yamahari.ilikewood.objectholders.bed.lime.WoodenLimeBedBlocks;
import yamahari.ilikewood.objectholders.bed.magenta.WoodenMagentaBedBlocks;
import yamahari.ilikewood.objectholders.bed.orange.WoodenOrangeBedBlocks;
import yamahari.ilikewood.objectholders.bed.pink.WoodenPinkBedBlocks;
import yamahari.ilikewood.objectholders.bed.purple.WoodenPurpleBedBlocks;
import yamahari.ilikewood.objectholders.bed.red.WoodenRedBedBlocks;
import yamahari.ilikewood.objectholders.bed.white.WoodenWhiteBedBlocks;
import yamahari.ilikewood.objectholders.bed.yellow.WoodenYellowBedBlocks;
import yamahari.ilikewood.objectholders.bookshelf.WoodenBookshelfBlocks;
import yamahari.ilikewood.objectholders.chest.WoodenChestBlocks;
import yamahari.ilikewood.objectholders.composter.WoodenComposterBlocks;
import yamahari.ilikewood.objectholders.crafting_table.WoodenCraftingTableBlocks;
import yamahari.ilikewood.objectholders.ladder.WoodenLadderBlocks;
import yamahari.ilikewood.objectholders.lectern.WoodenLecternBlocks;
import yamahari.ilikewood.objectholders.log_pile.WoodenLogPileBlocks;
import yamahari.ilikewood.objectholders.panels.WoodenPanelsBlocks;
import yamahari.ilikewood.objectholders.panels.slab.WoodenPanelsSlabBlocks;
import yamahari.ilikewood.objectholders.panels.stairs.WoodenPanelsStairsBlocks;
import yamahari.ilikewood.objectholders.post.WoodenPostBlocks;
import yamahari.ilikewood.objectholders.scaffolding.WoodenScaffoldingBlocks;
import yamahari.ilikewood.objectholders.stick.WoodenStickItems;
import yamahari.ilikewood.objectholders.tiered.WoodenHoeItems;
import yamahari.ilikewood.objectholders.tiered.WoodenSwordItems;
import yamahari.ilikewood.objectholders.tiered.tools.WoodenAxeItems;
import yamahari.ilikewood.objectholders.tiered.tools.WoodenPickaxeItems;
import yamahari.ilikewood.objectholders.tiered.tools.WoodenShovelItems;
import yamahari.ilikewood.objectholders.torch.WoodenTorchBlocks;
import yamahari.ilikewood.objectholders.wall.WoodenWallBlocks;
import yamahari.ilikewood.util.Constants;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ILikeWoodRecipeProvider extends RecipeProvider {
    public ILikeWoodRecipeProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    private static IItemProvider getIngredient(String name, Class<?> objectHolder) {
        try {
            Field block = objectHolder.getDeclaredField(name);
            return (IItemProvider) block.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            ILikeWood.logger.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private InventoryChangeTrigger.Instance hasItem(Ingredient ingredientIn) {
        return InventoryChangeTrigger.Instance.forItems(Arrays.stream(ingredientIn.getMatchingStacks()).map(ItemStack::getItem).toArray(Item[]::new));
    }


    @Override
    @ParametersAreNonnullByDefault
    @SuppressWarnings("ConstantConditions")
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        // TODO smelting iron/gold tools
        CustomRecipeBuilder.func_218656_a(WoodenRecipeSerializers.REPAIR_TIERED_ITEM).build(consumer, Constants.MOD_ID + ":repair_tiered_item");
        CustomRecipeBuilder.func_218656_a(WoodenRecipeSerializers.DYE_BED).build(consumer, Constants.MOD_ID + ":dye_bed");

        Stream.of(WoodenBarrelBlocks.ACACIA, WoodenBarrelBlocks.BIRCH, WoodenBarrelBlocks.DARK_OAK, WoodenBarrelBlocks.JUNGLE, WoodenBarrelBlocks.OAK, WoodenBarrelBlocks.SPRUCE)
                .forEach(block -> {
                    final IItemProvider ingredient = getIngredient(block.getWoodType().getName().toUpperCase(), WoodenPanelsBlocks.class);
                    ShapedRecipeBuilder.shapedRecipe(block).key('P', ingredient).key('S', getIngredient(block.getWoodType().getName().toUpperCase(), WoodenPanelsSlabBlocks.class)).patternLine("PSP").patternLine("P P").patternLine("PSP").addCriterion("has_panels", this.hasItem(ingredient)).build(consumer);
                });

        Stream.of(WoodenChestBlocks.ACACIA, WoodenChestBlocks.BIRCH, WoodenChestBlocks.DARK_OAK, WoodenChestBlocks.JUNGLE, WoodenChestBlocks.OAK, WoodenChestBlocks.SPRUCE)
                .forEach(block -> {
                    final IItemProvider ingredient = getIngredient(block.getWoodType().getName().toUpperCase(), WoodenPanelsBlocks.class);
                    ShapedRecipeBuilder.shapedRecipe(block).key('#', ingredient).patternLine("###").patternLine("# #").patternLine("###").addCriterion("has_planks", this.hasItem(ingredient)).build(consumer);
                });

        Stream.of(WoodenLecternBlocks.ACACIA, WoodenLecternBlocks.BIRCH, WoodenLecternBlocks.DARK_OAK, WoodenLecternBlocks.JUNGLE, WoodenLecternBlocks.OAK, WoodenLecternBlocks.SPRUCE)
                .forEach(block -> ShapedRecipeBuilder.shapedRecipe(block).key('S', getIngredient(block.getWoodType().getName().toUpperCase(), WoodenPanelsSlabBlocks.class)).key('B', getIngredient(block.getWoodType().getName().toUpperCase(), WoodenBookshelfBlocks.class)).patternLine("SSS").patternLine(" B ").patternLine(" S ").addCriterion("has_book", this.hasItem(Items.BOOK)).build(consumer));

        Stream.of(WoodenBookshelfBlocks.ACACIA, WoodenBookshelfBlocks.BIRCH, WoodenBookshelfBlocks.DARK_OAK, WoodenBookshelfBlocks.JUNGLE, WoodenBookshelfBlocks.OAK, WoodenBookshelfBlocks.SPRUCE)
                .forEach(block -> ShapedRecipeBuilder.shapedRecipe(block).key('#', getIngredient(block.getWoodType().getName().toUpperCase(), WoodenPanelsBlocks.class)).key('X', getIngredient(block.getWoodType().getName().toUpperCase(), WoodenBookshelfBlocks.class)).patternLine("###").patternLine("XXX").patternLine("###").addCriterion("has_book", this.hasItem(Items.BOOK)).build(consumer));

        Stream.of(WoodenComposterBlocks.ACACIA, WoodenComposterBlocks.BIRCH, WoodenComposterBlocks.DARK_OAK, WoodenComposterBlocks.JUNGLE, WoodenComposterBlocks.OAK, WoodenComposterBlocks.SPRUCE)
                .forEach(block -> {
                    final IItemProvider ingredient = getIngredient(block.getWoodType().getName().toUpperCase() + "_FENCE", Blocks.class);
                    ShapedRecipeBuilder.shapedRecipe(block).key('F', ingredient).key('#', getIngredient(block.getWoodType().getName().toUpperCase(), WoodenPanelsBlocks.class)).patternLine("F F").patternLine("F F").patternLine("###").addCriterion("has_fence", this.hasItem(ingredient)).build(consumer);
                });

        Stream.of(WoodenLadderBlocks.ACACIA, WoodenLadderBlocks.BIRCH, WoodenLadderBlocks.DARK_OAK, WoodenLadderBlocks.JUNGLE, WoodenLadderBlocks.OAK, WoodenLadderBlocks.SPRUCE)
                .forEach(block -> {
                    final IItemProvider ingredient = getIngredient(block.getWoodType().getName().toUpperCase(), WoodenStickItems.class);
                    ShapedRecipeBuilder.shapedRecipe(block).key('I', ingredient).patternLine("I I").patternLine("III").patternLine("I I").addCriterion("has_stick", this.hasItem(ingredient)).build(consumer);
                });

        Stream.of(WoodenScaffoldingBlocks.ACACIA, WoodenScaffoldingBlocks.BIRCH, WoodenScaffoldingBlocks.DARK_OAK, WoodenScaffoldingBlocks.JUNGLE, WoodenScaffoldingBlocks.OAK, WoodenScaffoldingBlocks.SPRUCE)
                .forEach(block -> {
                    final IItemProvider ingredient = getIngredient(block.getWoodType().getName().toUpperCase(), WoodenStickItems.class);
                    ShapedRecipeBuilder.shapedRecipe(block).key('I', ingredient).key('~', Items.STRING).patternLine("I~I").patternLine("I I").patternLine("I I").addCriterion("has_stick", this.hasItem(ingredient)).build(consumer);
                });

        Stream.of(WoodenWallBlocks.ACACIA, WoodenWallBlocks.BIRCH, WoodenWallBlocks.DARK_OAK, WoodenWallBlocks.JUNGLE, WoodenWallBlocks.OAK, WoodenWallBlocks.SPRUCE)
                .forEach(block -> {
                    final IItemProvider ingredient = getIngredient(block.getWoodType().getName().toUpperCase() + "_LOG", Blocks.class);
                    ShapedRecipeBuilder.shapedRecipe(block, 6).key('#', ingredient).patternLine("###").patternLine("###").addCriterion("has_log", this.hasItem(ingredient)).build(consumer);
                });

        Stream.of(WoodenPostBlocks.ACACIA, WoodenPostBlocks.BIRCH, WoodenPostBlocks.DARK_OAK, WoodenPostBlocks.JUNGLE, WoodenPostBlocks.OAK, WoodenPostBlocks.SPRUCE)
                .forEach(block -> {
                    final IItemProvider ingredient = getIngredient(block.getWoodType().getName().toUpperCase() + "_LOG", Blocks.class);
                    ShapedRecipeBuilder.shapedRecipe(block, 6).key('#', ingredient).patternLine("#").patternLine("#").patternLine("#").addCriterion("has_log", this.hasItem(ingredient)).build(consumer);
                });

        Stream.of(WoodenCraftingTableBlocks.ACACIA, WoodenCraftingTableBlocks.BIRCH, WoodenCraftingTableBlocks.DARK_OAK, WoodenCraftingTableBlocks.JUNGLE, WoodenCraftingTableBlocks.OAK, WoodenCraftingTableBlocks.SPRUCE)
                .forEach(block -> {
                    final IItemProvider ingredient = getIngredient(block.getWoodType().getName().toUpperCase(), WoodenPanelsBlocks.class);
                    ShapedRecipeBuilder.shapedRecipe(block).key('#', ingredient).patternLine("##").patternLine("##").addCriterion("has_planks", this.hasItem(ingredient)).build(consumer);
                });

        Stream.of(WoodenLogPileBlocks.ACACIA, WoodenLogPileBlocks.BIRCH, WoodenLogPileBlocks.DARK_OAK, WoodenLogPileBlocks.JUNGLE, WoodenLogPileBlocks.OAK, WoodenLogPileBlocks.SPRUCE)
                .forEach(block -> {
                    final IItemProvider ingredient = getIngredient(block.getWoodType().getName().toUpperCase(), WoodenPostBlocks.class);
                    ShapedRecipeBuilder.shapedRecipe(block).key('#', ingredient).patternLine("##").patternLine("##").addCriterion("has_post", this.hasItem(ingredient)).build(consumer);
                    ShapelessRecipeBuilder.shapelessRecipe(ingredient, 4).addIngredient(block).addCriterion("has_log_pile", this.hasItem(block)).build(consumer, Constants.MOD_ID + ":" + ingredient.asItem().getRegistryName().getPath() + "_from_" + block.getRegistryName().getPath());
                });

        Stream.of(WoodenBlackBedBlocks.class, WoodenBlueBedBlocks.class, WoodenBrownBedBlocks.class, WoodenCyanBedBlocks.class, WoodenGrayBedBlocks.class, WoodenGreenBedBlocks.class, WoodenLightBlueBedBlocks.class, WoodenLightGrayBedBlocks.class, WoodenLimeBedBlocks.class, WoodenMagentaBedBlocks.class, WoodenOrangeBedBlocks.class, WoodenPinkBedBlocks.class, WoodenPurpleBedBlocks.class, WoodenRedBedBlocks.class, WoodenWhiteBedBlocks.class, WoodenYellowBedBlocks.class)
                .forEach(bedClass -> Arrays.stream(bedClass.getDeclaredFields())
                        .forEach(field -> {
                            try {
                                final WoodenBedBlock bed = (WoodenBedBlock) field.get(null);
                                final IItemProvider ingredient = getIngredient(bed.getDyeColor().getName().toUpperCase() + "_WOOL", Items.class);
                                ShapedRecipeBuilder.shapedRecipe(bed).key('X', ingredient).key('#', getIngredient(bed.getWoodType().getName().toUpperCase(), WoodenPanelsBlocks.class)).patternLine("XXX").patternLine("###").addCriterion("has_wool", this.hasItem(ingredient)).build(consumer);
                            } catch (IllegalAccessException | ClassCastException e) {
                                ILikeWood.logger.error(e.getMessage());
                                e.printStackTrace();
                            }
                        }));

        Stream.of(WoodenPanelsBlocks.ACACIA, WoodenPanelsBlocks.BIRCH, WoodenPanelsBlocks.DARK_OAK, WoodenPanelsBlocks.JUNGLE, WoodenPanelsBlocks.OAK, WoodenPanelsBlocks.SPRUCE)
                .forEach(block -> {
                    final IItemProvider ingredient = getIngredient(block.getWoodType().getName().toUpperCase() + "_SLAB", Blocks.class);
                    ShapedRecipeBuilder.shapedRecipe(block).key('#', ingredient).patternLine("#").patternLine("#").addCriterion("has_planks", this.hasItem(ingredient)).build(consumer);
                });

        Stream.of(WoodenPanelsStairsBlocks.ACACIA, WoodenPanelsStairsBlocks.BIRCH, WoodenPanelsStairsBlocks.DARK_OAK, WoodenPanelsStairsBlocks.JUNGLE, WoodenPanelsStairsBlocks.OAK, WoodenPanelsStairsBlocks.SPRUCE)
                .forEach(block -> {
                    final IItemProvider ingredient = getIngredient(block.getWoodType().getName().toUpperCase(), WoodenPanelsBlocks.class);
                    ShapedRecipeBuilder.shapedRecipe(block, 4).key('#', ingredient).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_panels", this.hasItem(ingredient)).build(consumer);
                });

        Stream.of(WoodenPanelsSlabBlocks.ACACIA, WoodenPanelsSlabBlocks.BIRCH, WoodenPanelsSlabBlocks.DARK_OAK, WoodenPanelsSlabBlocks.JUNGLE, WoodenPanelsSlabBlocks.OAK, WoodenPanelsSlabBlocks.SPRUCE)
                .forEach(block -> {
                    final IItemProvider ingredient = getIngredient(block.getWoodType().getName().toUpperCase(), WoodenPanelsBlocks.class);
                    final IItemProvider planks = getIngredient(block.getWoodType().getName().toUpperCase() + "_PLANKS", Blocks.class);
                    ShapedRecipeBuilder.shapedRecipe(block, 6).key('#', ingredient).patternLine("###").addCriterion("has_panels", this.hasItem(ingredient)).build(consumer);
                    ShapedRecipeBuilder.shapedRecipe(planks, 1).key('S', block).patternLine("S").patternLine("S").addCriterion("has_panels_slab", this.hasItem(block)).build(consumer, Constants.MOD_ID + ":" + planks.asItem().getRegistryName().getPath() + "_from_" + block.getRegistryName().getPath());
                });

        Stream.of(WoodenStickItems.ACACIA, WoodenStickItems.BIRCH, WoodenStickItems.DARK_OAK, WoodenStickItems.JUNGLE, WoodenStickItems.OAK, WoodenStickItems.SPRUCE)
                .forEach(item -> {
                    final IItemProvider ingredient = getIngredient(item.getWoodType().getName().toUpperCase(), WoodenPanelsBlocks.class);
                    ShapedRecipeBuilder.shapedRecipe(item, 4).key('#', ingredient).patternLine("#").patternLine("#").addCriterion("has_planks", this.hasItem(ingredient)).build(consumer);
                });

        Stream.of(WoodenTorchBlocks.ACACIA, WoodenTorchBlocks.BIRCH, WoodenTorchBlocks.DARK_OAK, WoodenTorchBlocks.JUNGLE, WoodenTorchBlocks.OAK, WoodenTorchBlocks.SPRUCE)
                .forEach(block -> {
                    final IItemProvider ingredient = getIngredient(block.getWoodType().getName().toUpperCase(), WoodenStickItems.class);
                    ShapedRecipeBuilder.shapedRecipe(block, 4).key('#', Ingredient.fromItems(Items.COAL, Items.CHARCOAL)).key('I', ingredient).patternLine("#").patternLine("I").addCriterion("has_coal", this.hasItem(Ingredient.fromItems(Items.COAL, Items.CHARCOAL))).build(consumer);
                });

        Arrays.stream(WoodenAxeItems.class.getDeclaredFields())
                .forEach(field -> {
                    try {
                        final WoodenAxeItem axe = (WoodenAxeItem) field.get(null);
                        final Ingredient ingredient = axe.getWoodenItemTier().getRepairMaterial();
                        ShapedRecipeBuilder.shapedRecipe(axe).key('#', ingredient).key('I', getIngredient(axe.getWoodType().getName().toUpperCase(), WoodenStickItems.class)).patternLine("##").patternLine("#I").patternLine(" I").addCriterion("has_repair_material", this.hasItem(ingredient)).build(consumer);
                    } catch (IllegalAccessException | ClassCastException e) {
                        ILikeWood.logger.error(e.getMessage());
                        e.printStackTrace();
                    }
                });

        Arrays.stream(WoodenPickaxeItems.class.getDeclaredFields())
                .forEach(field -> {
                    try {
                        final WoodenPickaxeItem pickaxe = (WoodenPickaxeItem) field.get(null);
                        final Ingredient ingredient = pickaxe.getWoodenItemTier().getRepairMaterial();
                        ShapedRecipeBuilder.shapedRecipe(pickaxe).key('#', ingredient).key('I', getIngredient(pickaxe.getWoodType().getName().toUpperCase(), WoodenStickItems.class)).patternLine("###").patternLine(" I ").patternLine(" I ").addCriterion("has_repair_material", this.hasItem(ingredient)).build(consumer);
                    } catch (IllegalAccessException | ClassCastException e) {
                        ILikeWood.logger.error(e.getMessage());
                        e.printStackTrace();
                    }
                });

        Arrays.stream(WoodenShovelItems.class.getDeclaredFields())
                .forEach(field -> {
                    try {
                        final WoodenShovelItem shovel = (WoodenShovelItem) field.get(null);
                        final Ingredient ingredient = shovel.getWoodenItemTier().getRepairMaterial();
                        ShapedRecipeBuilder.shapedRecipe(shovel).key('#', ingredient).key('I', getIngredient(shovel.getWoodType().getName().toUpperCase(), WoodenStickItems.class)).patternLine("#").patternLine("I").patternLine("I").addCriterion("has_repair_material", this.hasItem(ingredient)).build(consumer);
                    } catch (IllegalAccessException | ClassCastException e) {
                        ILikeWood.logger.error(e.getMessage());
                        e.printStackTrace();
                    }
                });

        Arrays.stream(WoodenSwordItems.class.getDeclaredFields())
                .forEach(field -> {
                    try {
                        final WoodenSwordItem sword = (WoodenSwordItem) field.get(null);
                        final Ingredient ingredient = sword.getWoodenItemTier().getRepairMaterial();
                        ShapedRecipeBuilder.shapedRecipe(sword).key('#', ingredient).key('I', getIngredient(sword.getWoodType().getName().toUpperCase(), WoodenStickItems.class)).patternLine("#").patternLine("#").patternLine("I").addCriterion("has_repair_material", this.hasItem(ingredient)).build(consumer);
                    } catch (IllegalAccessException | ClassCastException e) {
                        ILikeWood.logger.error(e.getMessage());
                        e.printStackTrace();
                    }
                });

        Arrays.stream(WoodenHoeItems.class.getDeclaredFields())
                .forEach(field -> {
                    try {
                        final WoodenHoeItem hoe = (WoodenHoeItem) field.get(null);
                        final Ingredient ingredient = hoe.getWoodenItemTier().getRepairMaterial();
                        ShapedRecipeBuilder.shapedRecipe(hoe).key('#', ingredient).key('I', getIngredient(hoe.getWoodType().getName().toUpperCase(), WoodenStickItems.class)).patternLine("##").patternLine(" I").patternLine(" I").addCriterion("has_repair_material", this.hasItem(ingredient)).build(consumer);
                    } catch (IllegalAccessException | ClassCastException e) {
                        ILikeWood.logger.error(e.getMessage());
                        e.printStackTrace();
                    }
                });
    }
}
