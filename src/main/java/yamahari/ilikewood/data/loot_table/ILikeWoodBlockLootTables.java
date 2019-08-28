package yamahari.ilikewood.data.loot_table;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.*;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.IProperty;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.*;
import net.minecraft.world.storage.loot.functions.*;
import yamahari.ilikewood.ILikeWood;
import yamahari.ilikewood.objectholders.barrel.WoodenBarrelBlocks;
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
import yamahari.ilikewood.objectholders.torch.WoodenTorchBlocks;
import yamahari.ilikewood.objectholders.wall.WoodenWallBlocks;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class ILikeWoodBlockLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {
    private static final ILootCondition.IBuilder field_218573_a = MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));
    private static final ILootCondition.IBuilder field_218574_b = field_218573_a.inverted();
    private static final ILootCondition.IBuilder field_218575_c = MatchTool.builder(ItemPredicate.Builder.create().item(Items.SHEARS));
    private static final ILootCondition.IBuilder field_218576_d = field_218575_c.alternative(field_218573_a);
    private static final ILootCondition.IBuilder field_218577_e = field_218576_d.inverted();
    private static final Set<Item> field_218578_f = Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(IItemProvider::asItem).collect(ImmutableSet.toImmutableSet());
    private static final float[] field_218579_g = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
    private static final float[] field_218580_h = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};
    private final Map<ResourceLocation, LootTable.Builder> field_218581_i = Maps.newHashMap();

    private static <T> T func_218552_a(IItemProvider itemProvider, ILootFunctionConsumer<T> lootFunctionConsumer) {
        return !field_218578_f.contains(itemProvider.asItem()) ? lootFunctionConsumer.acceptFunction(ExplosionDecay.func_215863_b()) : lootFunctionConsumer.cast();
    }

    private static <T> T func_218560_a(IItemProvider itemProvider, ILootConditionConsumer<T> lootConditionConsumer) {
        return !field_218578_f.contains(itemProvider.asItem()) ? lootConditionConsumer.acceptCondition(SurvivesExplosion.builder()) : lootConditionConsumer.cast();
    }

    private static LootTable.Builder dropItemProvider(IItemProvider itemProvider) {
        return LootTable.builder().addLootPool(func_218560_a(itemProvider, LootPool.builder().name(itemProvider.asItem().toString()).rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(itemProvider))));
    }

    private static LootTable.Builder func_218494_a(Block block, ILootCondition.IBuilder lootConditionBuilder, LootEntry.Builder<?> lootEntryBuilder) {
        return LootTable.builder().addLootPool(LootPool.builder().name(block.getRegistryName().getPath()).rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(block).acceptCondition(lootConditionBuilder).func_216080_a(lootEntryBuilder)));
    }

    private static LootTable.Builder func_218519_a(Block block, LootEntry.Builder<?> builder) {
        return func_218494_a(block, field_218573_a, builder);
    }

    private static LootTable.Builder func_218511_b(Block block, LootEntry.Builder<?> builder) {
        return func_218494_a(block, field_218575_c, builder);
    }

    private static LootTable.Builder func_218535_c(Block block, LootEntry.Builder<?> builder) {
        return func_218494_a(block, field_218576_d, builder);
    }

    private static LootTable.Builder func_218515_b(Block block, IItemProvider itemProvider) {
        return func_218519_a(block, func_218560_a(block, ItemLootEntry.builder(itemProvider)));
    }

    private static LootTable.Builder func_218463_a(IItemProvider itemProvider, IRandomRange randomRange) {
        return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(func_218552_a(itemProvider, ItemLootEntry.builder(itemProvider).acceptFunction(SetCount.func_215932_a(randomRange)))));
    }

    private static LootTable.Builder func_218530_a(Block block, IItemProvider itemProvider, IRandomRange randomRange) {
        return func_218519_a(block, func_218552_a(block, ItemLootEntry.builder(itemProvider).acceptFunction(SetCount.func_215932_a(randomRange))));
    }

    private static LootTable.Builder func_218561_b(IItemProvider iItemProvider) {
        return LootTable.builder().addLootPool(LootPool.builder().acceptCondition(field_218573_a).rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(iItemProvider)));
    }

    private static LootTable.Builder func_218523_c(IItemProvider itemProvider) {
        return LootTable.builder().addLootPool(func_218560_a(Blocks.FLOWER_POT, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Blocks.FLOWER_POT)))).addLootPool(func_218560_a(itemProvider, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(itemProvider))));
    }

    private static LootTable.Builder func_218513_d(Block block) {
        return LootTable.builder().addLootPool(LootPool.builder().name(block.getRegistryName().getPath()).rolls(ConstantRange.of(1)).addEntry(func_218552_a(block, ItemLootEntry.builder(block).acceptFunction(SetCount.func_215932_a(ConstantRange.of(2)).acceptCondition(BlockStateProperty.builder(block).with(SlabBlock.TYPE, SlabType.DOUBLE))))));
    }

    private static <T extends Comparable<T>> LootTable.Builder func_218562_a(Block block, IProperty<T> property, T value) {
        return LootTable.builder().addLootPool(func_218560_a(block, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(block).acceptCondition(BlockStateProperty.builder(block).with(property, value)))));
    }

    private static LootTable.Builder dropBlockAndCopyBlockEntityName(Block block) {
        return LootTable.builder().addLootPool(func_218560_a(block, LootPool.builder().name(block.getRegistryName().getPath()).rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(block).acceptFunction(CopyName.func_215893_a(CopyName.Source.BLOCK_ENTITY)))));
    }

    private static LootTable.Builder func_218544_f(Block block) {
        return LootTable.builder().addLootPool(func_218560_a(block, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(block).acceptFunction(CopyName.func_215893_a(CopyName.Source.BLOCK_ENTITY)).acceptFunction(CopyNbt.func_215881_a(CopyNbt.Source.BLOCK_ENTITY).func_216056_a("Lock", "BlockEntityTag.Lock").func_216056_a("LootTable", "BlockEntityTag.LootTable").func_216056_a("LootTableSeed", "BlockEntityTag.LootTableSeed")).acceptFunction(SetContents.func_215920_b().func_216075_a(DynamicLootEntry.func_216162_a(ShulkerBoxBlock.field_220169_b))))));
    }

    private static LootTable.Builder func_218559_g(Block block) {
        return LootTable.builder().addLootPool(func_218560_a(block, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(block).acceptFunction(CopyName.func_215893_a(CopyName.Source.BLOCK_ENTITY)).acceptFunction(CopyNbt.func_215881_a(CopyNbt.Source.BLOCK_ENTITY).func_216056_a("Patterns", "BlockEntityTag.Patterns")))));
    }

    private static LootTable.Builder func_218476_a(Block block, Item item) {
        return func_218519_a(block, func_218552_a(block, ItemLootEntry.builder(item).acceptFunction(ApplyBonus.func_215869_a(Enchantments.FORTUNE))));
    }

    private static LootTable.Builder func_218491_c(Block block, IItemProvider itemProvider) {
        return func_218519_a(block, func_218552_a(block, ItemLootEntry.builder(itemProvider).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(-6.0F, 2.0F))).acceptFunction(LimitCount.func_215911_a(IntClamper.func_215848_a(0)))));
    }

    private static LootTable.Builder func_218570_h(Block block) {
        return func_218511_b(block, func_218552_a(block, (ItemLootEntry.builder(Items.WHEAT_SEEDS).acceptCondition(RandomChance.builder(0.125F))).acceptFunction(ApplyBonus.func_215865_a(Enchantments.FORTUNE, 2))));
    }

    private static LootTable.Builder func_218475_b(Block block, Item item) {
        return LootTable.builder().addLootPool(func_218552_a(block, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(item).acceptFunction(SetCount.func_215932_a(BinomialRange.func_215838_a(3, 0.06666667F)).acceptCondition(BlockStateProperty.builder(block).with(StemBlock.AGE, 0))).acceptFunction(SetCount.func_215932_a(BinomialRange.func_215838_a(3, 0.13333334F)).acceptCondition(BlockStateProperty.builder(block).with(StemBlock.AGE, 1))).acceptFunction(SetCount.func_215932_a(BinomialRange.func_215838_a(3, 0.2F)).acceptCondition(BlockStateProperty.builder(block).with(StemBlock.AGE, 2))).acceptFunction(SetCount.func_215932_a(BinomialRange.func_215838_a(3, 0.26666668F)).acceptCondition(BlockStateProperty.builder(block).with(StemBlock.AGE, 3))).acceptFunction(SetCount.func_215932_a(BinomialRange.func_215838_a(3, 0.33333334F)).acceptCondition(BlockStateProperty.builder(block).with(StemBlock.AGE, 4))).acceptFunction(SetCount.func_215932_a(BinomialRange.func_215838_a(3, 0.4F)).acceptCondition(BlockStateProperty.builder(block).with(StemBlock.AGE, 5))).acceptFunction(SetCount.func_215932_a(BinomialRange.func_215838_a(3, 0.46666667F)).acceptCondition(BlockStateProperty.builder(block).with(StemBlock.AGE, 6))).acceptFunction(SetCount.func_215932_a(BinomialRange.func_215838_a(3, 0.53333336F)).acceptCondition(BlockStateProperty.builder(block).with(StemBlock.AGE, 7))))));
    }

    private static LootTable.Builder func_218486_d(IItemProvider itemProvider) {
        return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).acceptCondition(field_218575_c).addEntry(ItemLootEntry.builder(itemProvider)));
    }

    private static LootTable.Builder func_218540_a(Block block, Block block1, float... values) {
        return func_218535_c(block, func_218560_a(block, ItemLootEntry.builder(block1)).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, values))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).acceptCondition(field_218577_e).addEntry(func_218552_a(block, ItemLootEntry.builder(Items.STICK).acceptFunction(SetCount.func_215932_a(RandomValueRange.func_215837_a(1.0F, 2.0F)))).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
    }

    private static LootTable.Builder func_218526_b(Block block, Block block1, float... values) {
        return func_218540_a(block, block1, values).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).acceptCondition(field_218577_e).addEntry(func_218560_a(block, ItemLootEntry.builder(Items.APPLE)).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
    }

    private static LootTable.Builder func_218541_a(Block block, Item item, Item item1, ILootCondition.IBuilder builder) {
        return func_218552_a(block, LootTable.builder().addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(item).acceptCondition(builder).func_216080_a(ItemLootEntry.builder(item1)))).addLootPool(LootPool.builder().acceptCondition(builder).addEntry(ItemLootEntry.builder(item1).acceptFunction(ApplyBonus.func_215870_a(Enchantments.FORTUNE, 0.5714286F, 3)))));
    }

    public static LootTable.Builder func_218482_a() {
        return LootTable.builder();
    }

    private static Block getLootFrom(String name, Class<?> objectHolder) {
        try {
            final Field field = objectHolder.getDeclaredField(name);
            return (Block) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            ILikeWood.logger.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("ConstantConditions")
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        List<Block> blocks = new LinkedList<>();

        Stream.of(WoodenBookshelfBlocks.ACACIA, WoodenBookshelfBlocks.BIRCH, WoodenBookshelfBlocks.DARK_OAK, WoodenBookshelfBlocks.JUNGLE, WoodenBookshelfBlocks.OAK, WoodenBookshelfBlocks.SPRUCE)
                .forEach(woodenBookshelfBlock -> {
                    blocks.add(woodenBookshelfBlock);
                    this.registerLootTable(woodenBookshelfBlock, (block) -> func_218530_a(block, Items.BOOK, ConstantRange.of(3)));
                });

        Stream.of(WoodenPanelsSlabBlocks.ACACIA, WoodenPanelsSlabBlocks.BIRCH, WoodenPanelsSlabBlocks.DARK_OAK, WoodenPanelsSlabBlocks.JUNGLE, WoodenPanelsSlabBlocks.OAK, WoodenPanelsSlabBlocks.SPRUCE)
                .forEach(woodenPanelsSlabBlock -> {
                    blocks.add(woodenPanelsSlabBlock);
                    this.registerLootTable(woodenPanelsSlabBlock, ILikeWoodBlockLootTables::func_218513_d);
                });

        // TODO this doesn't work, saves under torch not wall_torch
        /*Stream.of(WoodenWallTorchBlocks.ACACIA, WoodenWallTorchBlocks.BIRCH, WoodenWallTorchBlocks.DARK_OAK, WoodenWallTorchBlocks.JUNGLE, WoodenWallTorchBlocks.OAK, WoodenWallTorchBlocks.SPRUCE)
                .forEach(woodenWallTorchBlock -> {
                    blocks.add(woodenWallTorchBlock);
                    this.dropItself(getLootFrom(woodenWallTorchBlock.getWoodType().getName().toUpperCase(), WoodenTorchBlocks.class));
                });*/

        Stream.of(WoodenComposterBlocks.class, WoodenCraftingTableBlocks.class, WoodenLadderBlocks.class, WoodenLogPileBlocks.class, WoodenPanelsBlocks.class, WoodenPanelsStairsBlocks.class, WoodenPostBlocks.class, WoodenScaffoldingBlocks.class, WoodenTorchBlocks.class, WoodenWallBlocks.class).forEach(
                blockClass -> Arrays.stream(blockClass.getDeclaredFields())
                        .forEach(field -> {
                            try {
                                Block block = (Block) field.get(null);
                                blocks.add(block);
                                this.dropItself(block);
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
                                blocks.add(block);
                                this.registerLootTable(block, ILikeWoodBlockLootTables::dropBlockAndCopyBlockEntityName);
                            } catch (ClassCastException | IllegalAccessException e) {
                                ILikeWood.logger.error(e.getMessage());
                                e.printStackTrace();
                            }
                        }));

        Set<ResourceLocation> set = Sets.newHashSet();

        blocks.stream().forEach(block -> {
            ResourceLocation resourceLocation = block.getLootTable();
            if (resourceLocation != LootTables.EMPTY && set.add(resourceLocation)) {
                LootTable.Builder builder = this.field_218581_i.remove(resourceLocation);
                if (builder == null) {
                    ILikeWood.logger.error(String.format("Missing loot_table '%s' for '%s'", resourceLocation, block.getRegistryName()));
                } else {
                    consumer.accept(resourceLocation, builder);
                }
            }
        });
    }

    public void func_218547_a(Block block) {
        this.registerLootTable(block, (p_218524_0_) -> func_218523_c(((FlowerPotBlock) p_218524_0_).func_220276_d()));
    }

    public void func_218564_a(Block block, Block block1) {
        this.registerLootTable(block, func_218561_b(block1));
    }

    public void func_218493_a(Block block, IItemProvider itemProvider) {
        this.registerLootTable(block, dropItemProvider(itemProvider));
    }

    public void func_218466_b(Block block) {
        this.func_218564_a(block, block);
    }

    public void dropItself(Block block) {
        this.func_218493_a(block, block);
    }

    private void registerLootTable(Block block, Function<Block, LootTable.Builder> builderFunction) {
        this.registerLootTable(block, builderFunction.apply(block));
    }

    private void registerLootTable(Block block, LootTable.Builder builder) {
        this.field_218581_i.put(block.getLootTable(), builder);
    }
}
