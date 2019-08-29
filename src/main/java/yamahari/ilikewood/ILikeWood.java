package yamahari.ilikewood;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yamahari.ilikewood.blocks.*;
import yamahari.ilikewood.blocks.post.WoodenPostBlock;
import yamahari.ilikewood.blocks.post.WoodenStrippedPostBlock;
import yamahari.ilikewood.blocks.torch.WoodenTorchBlock;
import yamahari.ilikewood.blocks.torch.WoodenWallTorchBlock;
import yamahari.ilikewood.config.ILikeWoodConfig;
import yamahari.ilikewood.container.WoodenLecternContainer;
import yamahari.ilikewood.container.WoodenWorkbenchContainer;
import yamahari.ilikewood.data.loot_table.ILikeWoodLootTableProvider;
import yamahari.ilikewood.data.recipe.ILikeWoodRecipeProvider;
import yamahari.ilikewood.data.recipe.WoodenDyeBedRecipe;
import yamahari.ilikewood.data.recipe.WoodenRepairTieredItem;
import yamahari.ilikewood.items.*;
import yamahari.ilikewood.items.tier.WoodenHoeItem;
import yamahari.ilikewood.items.tier.WoodenSwordItem;
import yamahari.ilikewood.items.tier.tool.WoodenAxeItem;
import yamahari.ilikewood.items.tier.tool.WoodenPickaxeItem;
import yamahari.ilikewood.items.tier.tool.WoodenShovelItem;
import yamahari.ilikewood.objectholders.WoodenTileEntityTypes;
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
import yamahari.ilikewood.objectholders.torch.wall.WoodenWallTorchBlocks;
import yamahari.ilikewood.objectholders.wall.WoodenWallBlocks;
import yamahari.ilikewood.proxy.ClientProxy;
import yamahari.ilikewood.proxy.CommonProxy;
import yamahari.ilikewood.proxy.IProxy;
import yamahari.ilikewood.tilenentities.WoodenBarrelTileEntity;
import yamahari.ilikewood.tilenentities.WoodenChestTileEntity;
import yamahari.ilikewood.tilenentities.WoodenLecternTileEntity;
import yamahari.ilikewood.tilenentities.renderer.WoodenChestItemStackTileEntityRenderer;
import yamahari.ilikewood.util.Constants;
import yamahari.ilikewood.util.WoodTypes;
import yamahari.ilikewood.util.WoodenItemTiers;
import yamahari.ilikewood.util.WoodenObjectType;

import java.util.Arrays;
import java.util.stream.Stream;

@Mod(Constants.MOD_ID)
public class ILikeWood {
    @SuppressWarnings("unused")
    public static final Logger logger = LogManager.getLogger(Constants.MOD_ID);

    private static final IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public static boolean COMMON_CONFIG_LOADED = false;
    public static boolean CLIENT_CONFIG_LOADED = false;
    public static boolean SERVER_CONFIG_LOADED = false;

    public ILikeWood() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ILikeWoodConfig.SERVER_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ILikeWoodConfig.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ILikeWoodConfig.CLIENT_SPEC);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(proxy::onFMLClientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(proxy::onFMLCommonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventHandler {
        @SubscribeEvent
        public static void onRegisterBlock(final RegistryEvent.Register<Block> event) {
            final IForgeRegistry<Block> blockRegistry = event.getRegistry();

            Stream.of(WoodTypes.ACACIA, WoodTypes.BIRCH, WoodTypes.DARK_OAK, WoodTypes.JUNGLE, WoodTypes.OAK, WoodTypes.SPRUCE)
                    .forEach(woodType -> {
                        Arrays.stream(DyeColor.values()).forEach(dyeColor -> blockRegistry.register(new WoodenBedBlock(woodType, dyeColor)));
                        blockRegistry.registerAll(
                                new WoodenBarrelBlock(woodType, () -> WoodenTileEntityTypes.BARREL),
                                new WoodenChestBlock(woodType, () -> WoodenTileEntityTypes.CHEST),
                                new WoodenLecternBlock(woodType, () -> WoodenTileEntityTypes.LECTERN),
                                new WoodenBookshelfBlock(woodType),
                                new WoodenComposterBlock(woodType),
                                new WoodenWallBlock(woodType),
                                new WoodenLadderBlock(woodType),
                                new WoodenScaffoldingBlock(woodType),
                                new WoodenStrippedPostBlock(woodType).setRegistryName(String.format(WoodenObjectType.STRIPPED_POST.getName(), woodType.getName())),
                                new WoodenCraftingTableBlock(woodType),
                                new WoodenLogPileBlock(woodType)
                        );
                    });

            final Block acacia_panels = new WoodenBlock(WoodTypes.ACACIA, Block.Properties.from(Blocks.ACACIA_PLANKS)).setRegistryName(WoodTypes.ACACIA.getName() + "_" + WoodenObjectType.PANELS.getName());
            final Block birch_panels = new WoodenBlock(WoodTypes.BIRCH, Block.Properties.from(Blocks.BIRCH_PLANKS)).setRegistryName(WoodTypes.BIRCH.getName() + "_" + WoodenObjectType.PANELS.getName());
            final Block dark_oak_panels = new WoodenBlock(WoodTypes.DARK_OAK, Block.Properties.from(Blocks.DARK_OAK_PLANKS)).setRegistryName(WoodTypes.DARK_OAK.getName() + "_" + WoodenObjectType.PANELS.getName());
            final Block jungle_panels = new WoodenBlock(WoodTypes.JUNGLE, Block.Properties.from(Blocks.JUNGLE_PLANKS)).setRegistryName(WoodTypes.JUNGLE.getName() + "_" + WoodenObjectType.PANELS.getName());
            final Block oak_panels = new WoodenBlock(WoodTypes.OAK, Block.Properties.from(Blocks.OAK_PLANKS)).setRegistryName(WoodTypes.OAK.getName() + "_" + WoodenObjectType.PANELS.getName());
            final Block spruce_panels = new WoodenBlock(WoodTypes.SPRUCE, Block.Properties.from(Blocks.SPRUCE_PLANKS)).setRegistryName(WoodTypes.SPRUCE.getName() + "_" + WoodenObjectType.PANELS.getName());

            final Block acacia_torch = new WoodenTorchBlock(WoodTypes.ACACIA);
            final Block birch_torch = new WoodenTorchBlock(WoodTypes.BIRCH);
            final Block dark_oak_torch = new WoodenTorchBlock(WoodTypes.DARK_OAK);
            final Block jungle_torch = new WoodenTorchBlock(WoodTypes.JUNGLE);
            final Block oak_torch = new WoodenTorchBlock(WoodTypes.OAK);
            final Block spruce_torch = new WoodenTorchBlock(WoodTypes.SPRUCE);
            
            blockRegistry.registerAll(
                    acacia_torch, birch_torch, dark_oak_torch, jungle_torch, oak_torch, spruce_torch,
                    new WoodenWallTorchBlock(WoodTypes.ACACIA, Block.Properties.from(acacia_torch).lootFrom(acacia_torch)),
                    new WoodenWallTorchBlock(WoodTypes.BIRCH, Block.Properties.from(birch_torch).lootFrom(birch_torch)),
                    new WoodenWallTorchBlock(WoodTypes.DARK_OAK, Block.Properties.from(dark_oak_torch).lootFrom(dark_oak_torch)),
                    new WoodenWallTorchBlock(WoodTypes.JUNGLE, Block.Properties.from(jungle_torch).lootFrom(jungle_torch)),
                    new WoodenWallTorchBlock(WoodTypes.OAK, Block.Properties.from(oak_torch).lootFrom(oak_torch)),
                    new WoodenWallTorchBlock(WoodTypes.SPRUCE, Block.Properties.from(spruce_torch).lootFrom(spruce_torch)),
                    new WoodenPostBlock(WoodTypes.ACACIA, () -> WoodenStrippedPostBlocks.ACACIA),
                    new WoodenPostBlock(WoodTypes.BIRCH, () -> WoodenStrippedPostBlocks.BIRCH),
                    new WoodenPostBlock(WoodTypes.DARK_OAK, () -> WoodenStrippedPostBlocks.DARK_OAK),
                    new WoodenPostBlock(WoodTypes.JUNGLE, () -> WoodenStrippedPostBlocks.JUNGLE),
                    new WoodenPostBlock(WoodTypes.OAK, () -> WoodenStrippedPostBlocks.OAK),
                    new WoodenPostBlock(WoodTypes.SPRUCE, () -> WoodenStrippedPostBlocks.SPRUCE),
                    acacia_panels, birch_panels, dark_oak_panels, jungle_panels, oak_panels, spruce_panels,
                    new WoodenStairsBlock(WoodTypes.ACACIA, acacia_panels.getDefaultState(), Block.Properties.from(acacia_panels)).setRegistryName(WoodTypes.ACACIA.getName() + "_" + WoodenObjectType.PANELS.getName() + "_" + WoodenObjectType.STAIRS.getName()),
                    new WoodenStairsBlock(WoodTypes.BIRCH, birch_panels.getDefaultState(), Block.Properties.from(birch_panels)).setRegistryName(WoodTypes.BIRCH.getName() + "_" + WoodenObjectType.PANELS.getName() + "_" + WoodenObjectType.STAIRS.getName()),
                    new WoodenStairsBlock(WoodTypes.DARK_OAK, dark_oak_panels.getDefaultState(), Block.Properties.from(dark_oak_panels)).setRegistryName(WoodTypes.DARK_OAK.getName() + "_" + WoodenObjectType.PANELS.getName() + "_" + WoodenObjectType.STAIRS.getName()),
                    new WoodenStairsBlock(WoodTypes.JUNGLE, jungle_panels.getDefaultState(), Block.Properties.from(jungle_panels)).setRegistryName(WoodTypes.JUNGLE.getName() + "_" + WoodenObjectType.PANELS.getName() + "_" + WoodenObjectType.STAIRS.getName()),
                    new WoodenStairsBlock(WoodTypes.OAK, oak_panels.getDefaultState(), Block.Properties.from(oak_panels)).setRegistryName(WoodTypes.OAK.getName() + "_" + WoodenObjectType.PANELS.getName() + "_" + WoodenObjectType.STAIRS.getName()),
                    new WoodenStairsBlock(WoodTypes.SPRUCE, spruce_panels.getDefaultState(), Block.Properties.from(spruce_panels)).setRegistryName(WoodTypes.SPRUCE.getName() + "_" + WoodenObjectType.PANELS.getName() + "_" + WoodenObjectType.STAIRS.getName()),
                    new WoodenSlabBlock(WoodTypes.ACACIA, Block.Properties.from(acacia_panels)).setRegistryName(WoodTypes.ACACIA.getName() + "_" + WoodenObjectType.PANELS.getName() + "_" + WoodenObjectType.SLAB.getName()),
                    new WoodenSlabBlock(WoodTypes.BIRCH, Block.Properties.from(birch_panels)).setRegistryName(WoodTypes.BIRCH.getName() + "_" + WoodenObjectType.PANELS.getName() + "_" + WoodenObjectType.SLAB.getName()),
                    new WoodenSlabBlock(WoodTypes.DARK_OAK, Block.Properties.from(dark_oak_panels)).setRegistryName(WoodTypes.DARK_OAK.getName() + "_" + WoodenObjectType.PANELS.getName() + "_" + WoodenObjectType.SLAB.getName()),
                    new WoodenSlabBlock(WoodTypes.JUNGLE, Block.Properties.from(jungle_panels)).setRegistryName(WoodTypes.JUNGLE.getName() + "_" + WoodenObjectType.PANELS.getName() + "_" + WoodenObjectType.SLAB.getName()),
                    new WoodenSlabBlock(WoodTypes.OAK, Block.Properties.from(oak_panels)).setRegistryName(WoodTypes.OAK.getName() + "_" + WoodenObjectType.PANELS.getName() + "_" + WoodenObjectType.SLAB.getName()),
                    new WoodenSlabBlock(WoodTypes.SPRUCE, Block.Properties.from(spruce_panels)).setRegistryName(WoodTypes.SPRUCE.getName() + "_" + WoodenObjectType.PANELS.getName() + "_" + WoodenObjectType.SLAB.getName())
            );
        }

        @SuppressWarnings("ConstantConditions")
        @SubscribeEvent
        public static void onRegisterItem(final RegistryEvent.Register<Item> event) {
            final IForgeRegistry<Item> itemRegistry = event.getRegistry();

            Stream.of(WoodenBarrelBlocks.ACACIA, WoodenBarrelBlocks.BIRCH, WoodenBarrelBlocks.DARK_OAK, WoodenBarrelBlocks.JUNGLE, WoodenBarrelBlocks.OAK, WoodenBarrelBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBlockItem(block, WoodenObjectType.BARREL, (new Item.Properties()).group(ItemGroup.DECORATIONS))));

            Stream.of(WoodenChestBlocks.ACACIA, WoodenChestBlocks.BIRCH, WoodenChestBlocks.DARK_OAK, WoodenChestBlocks.JUNGLE, WoodenChestBlocks.OAK, WoodenChestBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBlockItem(block, WoodenObjectType.CHEST, (new Item.Properties()).group(ItemGroup.DECORATIONS).setTEISR(() -> WoodenChestItemStackTileEntityRenderer::new))));

            Stream.of(WoodenLecternBlocks.ACACIA, WoodenLecternBlocks.BIRCH, WoodenLecternBlocks.DARK_OAK, WoodenLecternBlocks.JUNGLE, WoodenLecternBlocks.OAK, WoodenLecternBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBlockItem(block, WoodenObjectType.LECTERN, (new Item.Properties()).group(ItemGroup.REDSTONE))));

            Stream.of(WoodenPanelsBlocks.ACACIA, WoodenPanelsBlocks.BIRCH, WoodenPanelsBlocks.DARK_OAK, WoodenPanelsBlocks.JUNGLE, WoodenPanelsBlocks.OAK, WoodenPanelsBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBlockItem(block, WoodenObjectType.PANELS, (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS))));

            Stream.of(WoodenBookshelfBlocks.ACACIA, WoodenBookshelfBlocks.BIRCH, WoodenBookshelfBlocks.DARK_OAK, WoodenBookshelfBlocks.JUNGLE, WoodenBookshelfBlocks.OAK, WoodenBookshelfBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBlockItem(block, WoodenObjectType.BOOKSHELF, (new Item.Properties()).group(ItemGroup.DECORATIONS))));

            Stream.of(WoodenComposterBlocks.ACACIA, WoodenComposterBlocks.BIRCH, WoodenComposterBlocks.DARK_OAK, WoodenComposterBlocks.JUNGLE, WoodenComposterBlocks.OAK, WoodenComposterBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBlockItem(block, WoodenObjectType.COMPOSTER, (new Item.Properties()).group(ItemGroup.MISC))));

            Stream.of(WoodenWallBlocks.ACACIA, WoodenWallBlocks.BIRCH, WoodenWallBlocks.DARK_OAK, WoodenWallBlocks.JUNGLE, WoodenWallBlocks.OAK, WoodenWallBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBlockItem(block, WoodenObjectType.WALL, (new Item.Properties()).group(ItemGroup.DECORATIONS))));

            Stream.of(WoodenLadderBlocks.ACACIA, WoodenLadderBlocks.BIRCH, WoodenLadderBlocks.DARK_OAK, WoodenLadderBlocks.JUNGLE, WoodenLadderBlocks.OAK, WoodenLadderBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBlockItem(block, WoodenObjectType.WALL, (new Item.Properties()).group(ItemGroup.DECORATIONS))));

            Stream.of(WoodenScaffoldingBlocks.ACACIA, WoodenScaffoldingBlocks.BIRCH, WoodenScaffoldingBlocks.DARK_OAK, WoodenScaffoldingBlocks.JUNGLE, WoodenScaffoldingBlocks.OAK, WoodenScaffoldingBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenScaffoldingItem(block)));

            Stream.of(WoodenPostBlocks.ACACIA, WoodenPostBlocks.BIRCH, WoodenPostBlocks.DARK_OAK, WoodenPostBlocks.JUNGLE, WoodenPostBlocks.OAK, WoodenPostBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBlockItem(block, WoodenObjectType.POST, (new Item.Properties()).group(ItemGroup.DECORATIONS))));

            Stream.of(WoodenStrippedPostBlocks.ACACIA, WoodenStrippedPostBlocks.BIRCH, WoodenStrippedPostBlocks.DARK_OAK, WoodenStrippedPostBlocks.JUNGLE, WoodenStrippedPostBlocks.OAK, WoodenStrippedPostBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBlockItem(block, WoodenObjectType.STRIPPED_POST, (new Item.Properties()).group(ItemGroup.DECORATIONS))));

            Stream.of(WoodenCraftingTableBlocks.ACACIA, WoodenCraftingTableBlocks.BIRCH, WoodenCraftingTableBlocks.DARK_OAK, WoodenCraftingTableBlocks.JUNGLE, WoodenCraftingTableBlocks.OAK, WoodenCraftingTableBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBlockItem(block, WoodenObjectType.CRAFTING_TABLE, (new Item.Properties()).group(ItemGroup.DECORATIONS))));

            Stream.of(WoodenLogPileBlocks.ACACIA, WoodenLogPileBlocks.BIRCH, WoodenLogPileBlocks.DARK_OAK, WoodenLogPileBlocks.JUNGLE, WoodenLogPileBlocks.OAK, WoodenLogPileBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBlockItem(block, WoodenObjectType.LOG_PILE, (new Item.Properties()).group(ItemGroup.DECORATIONS))));

            Stream.of(WoodenPanelsStairsBlocks.ACACIA, WoodenPanelsStairsBlocks.BIRCH, WoodenPanelsStairsBlocks.DARK_OAK, WoodenPanelsStairsBlocks.JUNGLE, WoodenPanelsStairsBlocks.OAK, WoodenPanelsStairsBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBlockItem(block, WoodenObjectType.STAIRS, (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS))));

            Stream.of(WoodenPanelsSlabBlocks.ACACIA, WoodenPanelsSlabBlocks.BIRCH, WoodenPanelsSlabBlocks.DARK_OAK, WoodenPanelsSlabBlocks.JUNGLE, WoodenPanelsSlabBlocks.OAK, WoodenPanelsSlabBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBlockItem(block, WoodenObjectType.SLAB, (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS))));

            Stream.of(WoodTypes.ACACIA, WoodTypes.BIRCH, WoodTypes.DARK_OAK, WoodTypes.JUNGLE, WoodTypes.OAK, WoodTypes.SPRUCE)
                    .forEach(woodType -> {
                        itemRegistry.register(new WoodenItem(woodType, WoodenObjectType.STICK, (new Item.Properties()).group(ItemGroup.MATERIALS)).setRegistryName(woodType.getName() + "_" + WoodenObjectType.STICK.getName()));
                        Stream.of(WoodenItemTiers.ACACIA, WoodenItemTiers.BIRCH, WoodenItemTiers.DARK_OAK, WoodenItemTiers.JUNGLE, WoodenItemTiers.OAK, WoodenItemTiers.SPRUCE, WoodenItemTiers.STONE, WoodenItemTiers.IRON, WoodenItemTiers.DIAMOND, WoodenItemTiers.GOLD)
                                .filter(woodenItemTier -> !woodenItemTier.isWood() || woodType.getName().equals(woodenItemTier.getName()))
                                .forEach(woodenItemTier -> itemRegistry.registerAll(new WoodenAxeItem(woodType, woodenItemTier), new WoodenHoeItem(woodType, woodenItemTier), new WoodenPickaxeItem(woodType, woodenItemTier), new WoodenShovelItem(woodType, woodenItemTier), new WoodenSwordItem(woodType, woodenItemTier)));
                    });

            Stream.of(WoodenBlackBedBlocks.ACACIA, WoodenBlackBedBlocks.BIRCH, WoodenBlackBedBlocks.DARK_OAK, WoodenBlackBedBlocks.JUNGLE, WoodenBlackBedBlocks.OAK, WoodenBlackBedBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBedItem(block)));

            Stream.of(WoodenBlueBedBlocks.ACACIA, WoodenBlueBedBlocks.BIRCH, WoodenBlueBedBlocks.DARK_OAK, WoodenBlueBedBlocks.JUNGLE, WoodenBlueBedBlocks.OAK, WoodenBlueBedBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBedItem(block)));

            Stream.of(WoodenBrownBedBlocks.ACACIA, WoodenBrownBedBlocks.BIRCH, WoodenBrownBedBlocks.DARK_OAK, WoodenBrownBedBlocks.JUNGLE, WoodenBrownBedBlocks.OAK, WoodenBrownBedBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBedItem(block)));

            Stream.of(WoodenCyanBedBlocks.ACACIA, WoodenCyanBedBlocks.BIRCH, WoodenCyanBedBlocks.DARK_OAK, WoodenCyanBedBlocks.JUNGLE, WoodenCyanBedBlocks.OAK, WoodenCyanBedBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBedItem(block)));

            Stream.of(WoodenGrayBedBlocks.ACACIA, WoodenGrayBedBlocks.BIRCH, WoodenGrayBedBlocks.DARK_OAK, WoodenGrayBedBlocks.JUNGLE, WoodenGrayBedBlocks.OAK, WoodenGrayBedBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBedItem(block)));

            Stream.of(WoodenGreenBedBlocks.ACACIA, WoodenGreenBedBlocks.BIRCH, WoodenGreenBedBlocks.DARK_OAK, WoodenGreenBedBlocks.JUNGLE, WoodenGreenBedBlocks.OAK, WoodenGreenBedBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBedItem(block)));

            Stream.of(WoodenLightBlueBedBlocks.ACACIA, WoodenLightBlueBedBlocks.BIRCH, WoodenLightBlueBedBlocks.DARK_OAK, WoodenLightBlueBedBlocks.JUNGLE, WoodenLightBlueBedBlocks.OAK, WoodenLightBlueBedBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBedItem(block)));

            Stream.of(WoodenLightGrayBedBlocks.ACACIA, WoodenLightGrayBedBlocks.BIRCH, WoodenLightGrayBedBlocks.DARK_OAK, WoodenLightGrayBedBlocks.JUNGLE, WoodenLightGrayBedBlocks.OAK, WoodenLightGrayBedBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBedItem(block)));

            Stream.of(WoodenLimeBedBlocks.ACACIA, WoodenLimeBedBlocks.BIRCH, WoodenLimeBedBlocks.DARK_OAK, WoodenLimeBedBlocks.JUNGLE, WoodenLimeBedBlocks.OAK, WoodenLimeBedBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBedItem(block)));

            Stream.of(WoodenMagentaBedBlocks.ACACIA, WoodenMagentaBedBlocks.BIRCH, WoodenMagentaBedBlocks.DARK_OAK, WoodenMagentaBedBlocks.JUNGLE, WoodenMagentaBedBlocks.OAK, WoodenMagentaBedBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBedItem(block)));

            Stream.of(WoodenOrangeBedBlocks.ACACIA, WoodenOrangeBedBlocks.BIRCH, WoodenOrangeBedBlocks.DARK_OAK, WoodenOrangeBedBlocks.JUNGLE, WoodenOrangeBedBlocks.OAK, WoodenOrangeBedBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBedItem(block)));

            Stream.of(WoodenPinkBedBlocks.ACACIA, WoodenPinkBedBlocks.BIRCH, WoodenPinkBedBlocks.DARK_OAK, WoodenPinkBedBlocks.JUNGLE, WoodenPinkBedBlocks.OAK, WoodenPinkBedBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBedItem(block)));

            Stream.of(WoodenPurpleBedBlocks.ACACIA, WoodenPurpleBedBlocks.BIRCH, WoodenPurpleBedBlocks.DARK_OAK, WoodenPurpleBedBlocks.JUNGLE, WoodenPurpleBedBlocks.OAK, WoodenPurpleBedBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBedItem(block)));

            Stream.of(WoodenRedBedBlocks.ACACIA, WoodenRedBedBlocks.BIRCH, WoodenRedBedBlocks.DARK_OAK, WoodenRedBedBlocks.JUNGLE, WoodenRedBedBlocks.OAK, WoodenRedBedBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBedItem(block)));

            Stream.of(WoodenWhiteBedBlocks.ACACIA, WoodenWhiteBedBlocks.BIRCH, WoodenWhiteBedBlocks.DARK_OAK, WoodenWhiteBedBlocks.JUNGLE, WoodenWhiteBedBlocks.OAK, WoodenWhiteBedBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBedItem(block)));

            Stream.of(WoodenYellowBedBlocks.ACACIA, WoodenYellowBedBlocks.BIRCH, WoodenYellowBedBlocks.DARK_OAK, WoodenYellowBedBlocks.JUNGLE, WoodenYellowBedBlocks.OAK, WoodenYellowBedBlocks.SPRUCE)
                    .forEach(block -> itemRegistry.register(new WoodenBedItem(block)));

            itemRegistry.registerAll(
                    new WoodenWallOrFloorItem(WoodenTorchBlocks.ACACIA, WoodenWallTorchBlocks.ACACIA, WoodenObjectType.TORCH, (new Item.Properties()).group(ItemGroup.DECORATIONS)),
                    new WoodenWallOrFloorItem(WoodenTorchBlocks.BIRCH, WoodenWallTorchBlocks.BIRCH, WoodenObjectType.TORCH, (new Item.Properties()).group(ItemGroup.DECORATIONS)),
                    new WoodenWallOrFloorItem(WoodenTorchBlocks.DARK_OAK, WoodenWallTorchBlocks.DARK_OAK, WoodenObjectType.TORCH, (new Item.Properties()).group(ItemGroup.DECORATIONS)),
                    new WoodenWallOrFloorItem(WoodenTorchBlocks.JUNGLE, WoodenWallTorchBlocks.JUNGLE, WoodenObjectType.TORCH, (new Item.Properties()).group(ItemGroup.DECORATIONS)),
                    new WoodenWallOrFloorItem(WoodenTorchBlocks.OAK, WoodenWallTorchBlocks.OAK, WoodenObjectType.TORCH, (new Item.Properties()).group(ItemGroup.DECORATIONS)),
                    new WoodenWallOrFloorItem(WoodenTorchBlocks.SPRUCE, WoodenWallTorchBlocks.SPRUCE, WoodenObjectType.TORCH, (new Item.Properties()).group(ItemGroup.DECORATIONS))
            );
        }

        @SuppressWarnings("ConstantConditions")
        @SubscribeEvent
        public static void onRegisterTileEntityType(final RegistryEvent.Register<TileEntityType<?>> event) {
            event.getRegistry().registerAll(
                    TileEntityType.Builder.create(
                            () -> new WoodenBarrelTileEntity(WoodenTileEntityTypes.BARREL),
                            WoodenBarrelBlocks.ACACIA, WoodenBarrelBlocks.BIRCH, WoodenBarrelBlocks.DARK_OAK, WoodenBarrelBlocks.JUNGLE, WoodenBarrelBlocks.OAK, WoodenBarrelBlocks.SPRUCE
                    ).build(null).setRegistryName("wooden_barrel"),
                    TileEntityType.Builder.create(
                            () -> new WoodenChestTileEntity(WoodenTileEntityTypes.CHEST, Constants.TEXTURES_CHEST, Constants.TEXTURES_CHEST_DOUBLE),
                            WoodenChestBlocks.ACACIA, WoodenChestBlocks.BIRCH, WoodenChestBlocks.DARK_OAK, WoodenChestBlocks.JUNGLE, WoodenChestBlocks.OAK, WoodenChestBlocks.SPRUCE
                    ).build(null).setRegistryName("wooden_chest"),
                    TileEntityType.Builder.create(
                            () -> new WoodenLecternTileEntity(WoodenTileEntityTypes.LECTERN),
                            WoodenLecternBlocks.ACACIA, WoodenLecternBlocks.BIRCH, WoodenLecternBlocks.DARK_OAK, WoodenLecternBlocks.JUNGLE, WoodenLecternBlocks.OAK, WoodenLecternBlocks.SPRUCE
                    ).build(null).setRegistryName("wooden_lectern")
            );
        }

        @SubscribeEvent
        public static void onRegisterContainerType(final RegistryEvent.Register<ContainerType<?>> event) {
            event.getRegistry().registerAll(
                    new ContainerType<>((IContainerFactory<WoodenLecternContainer>) (windowId, inv, data) -> new WoodenLecternContainer(windowId)).setRegistryName("wooden_lectern"),
                    new ContainerType<>(WoodenWorkbenchContainer::new).setRegistryName("wooden_workbench")
            );
        }

        @SubscribeEvent
        public static void onRegisterRecipeSerializer(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
            event.getRegistry().registerAll(
                    new SpecialRecipeSerializer<>(WoodenRepairTieredItem::new).setRegistryName("wooden_repair_tiered_item"),
                    new SpecialRecipeSerializer<>(WoodenDyeBedRecipe::new).setRegistryName("wooden_dye_bed")
            );
        }

        @SubscribeEvent
        public static void onModConfigLoading(final ModConfig.Loading event) {
            switch (event.getConfig().getType()) {
                case COMMON:
                    COMMON_CONFIG_LOADED = true;
                    break;
                case CLIENT:
                    CLIENT_CONFIG_LOADED = true;
                    break;
                case SERVER:
                    SERVER_CONFIG_LOADED = true;
                    break;
            }
        }

        @SubscribeEvent
        public static void onGatherData(final GatherDataEvent event) {
            DataGenerator dataGenerator = event.getGenerator();
            if (event.includeServer()) {
                dataGenerator.addProvider(new ILikeWoodRecipeProvider(dataGenerator));
                dataGenerator.addProvider(new ILikeWoodLootTableProvider(dataGenerator));
            }
        }
    }
}