package yamahari.ilikewood.data.loot_table;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.state.properties.BedPart;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.BlockStateProperty;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraft.world.storage.loot.conditions.MatchTool;
import net.minecraft.world.storage.loot.conditions.SurvivesExplosion;
import net.minecraft.world.storage.loot.functions.CopyName;
import net.minecraft.world.storage.loot.functions.ExplosionDecay;
import net.minecraft.world.storage.loot.functions.SetCount;
import yamahari.ilikewood.ILikeWood;
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
import yamahari.ilikewood.objectholders.post.stripped.WoodenStrippedPostBlocks;
import yamahari.ilikewood.objectholders.scaffolding.WoodenScaffoldingBlocks;
import yamahari.ilikewood.objectholders.torch.WoodenTorchBlocks;
import yamahari.ilikewood.objectholders.wall.WoodenWallBlocks;
import yamahari.ilikewood.util.Constants;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class ILikeWoodBlockLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {
    private static final ILootCondition.IBuilder hasSilktouch = MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));
    private final Map<ResourceLocation, LootTable.Builder> lootTables = Maps.newHashMap();

    private static <T> T explosionDecay(ILootFunctionConsumer<T> lootFunctionConsumer) {
        return lootFunctionConsumer.acceptFunction(ExplosionDecay.func_215863_b());
    }

    private static <T> T survivesExplosion(ILootConditionConsumer<T> lootConditionConsumer) {
        return lootConditionConsumer.acceptCondition(SurvivesExplosion.builder());
    }

    private static LootTable.Builder dropItemProvider(IItemProvider itemProvider) {
        return LootTable.builder().addLootPool(survivesExplosion(LootPool.builder().name(itemProvider.asItem().toString()).rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(itemProvider))));
    }

    private static LootTable.Builder dropBlockSilktouch(Block block, LootEntry.Builder<?> lootEntryBuilder) {
        return LootTable.builder().addLootPool(LootPool.builder().name(Objects.requireNonNull(block.getRegistryName()).getPath()).rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(block).acceptCondition(ILikeWoodBlockLootTables.hasSilktouch).func_216080_a(lootEntryBuilder)));
    }

    private static LootTable.Builder dropBooks(Block block, IRandomRange randomRange) {
        return dropBlockSilktouch(block, explosionDecay(ItemLootEntry.builder(Items.BOOK).acceptFunction(SetCount.func_215932_a(randomRange))));
    }

    private static LootTable.Builder dropSlapTypeDouble(Block block) {
        return LootTable.builder().addLootPool(LootPool.builder().name(Objects.requireNonNull(block.getRegistryName()).getPath()).rolls(ConstantRange.of(1)).addEntry(explosionDecay(ItemLootEntry.builder(block).acceptFunction(SetCount.func_215932_a(ConstantRange.of(2)).acceptCondition(BlockStateProperty.builder(block).with(SlabBlock.TYPE, SlabType.DOUBLE))))));
    }

    private static LootTable.Builder dropCopyBlockEntityName(Block block) {
        return LootTable.builder().addLootPool(survivesExplosion(LootPool.builder().name(Objects.requireNonNull(block.getRegistryName()).getPath()).rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(block).acceptFunction(CopyName.func_215893_a(CopyName.Source.BLOCK_ENTITY)))));
    }

    private static LootTable.Builder dropBedPartHead(Block block) {
        return LootTable.builder().addLootPool(survivesExplosion(LootPool.builder().name(Objects.requireNonNull(block.getRegistryName()).getPath()).rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(block).acceptCondition(BlockStateProperty.builder(block).with(BedBlock.PART, BedPart.HEAD)))));
    }

    @SuppressWarnings({"ConstantConditions", "deprecation"})
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        Stream.of(WoodenBookshelfBlocks.ACACIA, WoodenBookshelfBlocks.BIRCH, WoodenBookshelfBlocks.DARK_OAK, WoodenBookshelfBlocks.JUNGLE, WoodenBookshelfBlocks.OAK, WoodenBookshelfBlocks.SPRUCE)
                .forEach(woodenBookshelfBlock -> this.registerLootTable(woodenBookshelfBlock, (block) -> dropBooks(block, ConstantRange.of(3))));

        Stream.of(WoodenPanelsSlabBlocks.ACACIA, WoodenPanelsSlabBlocks.BIRCH, WoodenPanelsSlabBlocks.DARK_OAK, WoodenPanelsSlabBlocks.JUNGLE, WoodenPanelsSlabBlocks.OAK, WoodenPanelsSlabBlocks.SPRUCE)
                .forEach(woodenPanelsSlabBlock -> this.registerLootTable(woodenPanelsSlabBlock, ILikeWoodBlockLootTables::dropSlapTypeDouble));

        Stream.of(WoodenBlackBedBlocks.class, WoodenBlueBedBlocks.class, WoodenBrownBedBlocks.class, WoodenCyanBedBlocks.class, WoodenGrayBedBlocks.class, WoodenGreenBedBlocks.class, WoodenLightBlueBedBlocks.class, WoodenLightGrayBedBlocks.class, WoodenLimeBedBlocks.class, WoodenMagentaBedBlocks.class, WoodenOrangeBedBlocks.class, WoodenPinkBedBlocks.class, WoodenPurpleBedBlocks.class, WoodenRedBedBlocks.class, WoodenWhiteBedBlocks.class, WoodenYellowBedBlocks.class)
                .forEach(bedClass -> Arrays.stream(bedClass.getDeclaredFields())
                        .forEach(field -> {
                            try {
                                Block block = (Block) field.get(null);
                                this.registerLootTable(block, ILikeWoodBlockLootTables::dropBedPartHead);
                            } catch (IllegalAccessException | ClassCastException e) {
                                ILikeWood.logger.error(e.getMessage());
                                e.printStackTrace();
                            }
                        }));

        Stream.of(WoodenComposterBlocks.class, WoodenCraftingTableBlocks.class, WoodenLadderBlocks.class, WoodenLogPileBlocks.class, WoodenPanelsBlocks.class, WoodenPanelsStairsBlocks.class, WoodenPostBlocks.class, WoodenScaffoldingBlocks.class, WoodenTorchBlocks.class, WoodenWallBlocks.class, WoodenStrippedPostBlocks.class).forEach(
                blockClass -> Arrays.stream(blockClass.getDeclaredFields())
                        .forEach(field -> {
                            try {
                                Block block = (Block) field.get(null);
                                this.registerLootTable(block, dropItemProvider(block));
                            } catch (ClassCastException | IllegalAccessException e) {
                                ILikeWood.logger.error(e.getMessage());
                                e.printStackTrace();
                            }
                        }));

        Stream.of(WoodenBarrelBlocks.class, WoodenChestBlocks.class, WoodenLecternBlocks.class).forEach(
                blockClass -> Arrays.stream(blockClass.getDeclaredFields())
                        .forEach(field -> {
                            try {
                                Block block = (Block) field.get(null);
                                this.registerLootTable(block, ILikeWoodBlockLootTables::dropCopyBlockEntityName);
                            } catch (ClassCastException | IllegalAccessException e) {
                                ILikeWood.logger.error(e.getMessage());
                                e.printStackTrace();
                            }
                        }));

        Set<ResourceLocation> set = Sets.newHashSet();

        Registry.BLOCK.stream().forEach(block -> {
            ResourceLocation resourceLocation = block.getLootTable();
            if (resourceLocation.getNamespace().equals(Constants.MOD_ID) && set.add(resourceLocation)) {
                LootTable.Builder builder = this.lootTables.remove(resourceLocation);
                if (builder == null) {
                    ILikeWood.logger.error(String.format("Missing loot_table '%s' for '%s'", resourceLocation, block.getRegistryName()));
                } else {
                    consumer.accept(resourceLocation, builder);
                }
            }
        });
    }

    private void registerLootTable(Block block, Function<Block, LootTable.Builder> builderFunction) {
        this.registerLootTable(block, builderFunction.apply(block));
    }

    private void registerLootTable(Block block, LootTable.Builder builder) {
        this.lootTables.put(block.getLootTable(), builder);
    }
}
