package yamahari.ilikewood.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.DyeColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import yamahari.ilikewood.util.IWooden;
import yamahari.ilikewood.util.WoodType;
import yamahari.ilikewood.util.WoodenObjectType;

import javax.annotation.Nullable;

public class WoodenBedBlock extends BedBlock implements IWooden {
    private final WoodType woodType;
    private final DyeColor dyeColor;

    public WoodenBedBlock(WoodType woodType, DyeColor dyeColor) {
        super(dyeColor, Block.Properties.create(Material.WOOL).sound(SoundType.WOOD).hardnessAndResistance(0.2f));
        this.woodType = woodType;
        this.dyeColor = dyeColor;
        this.setRegistryName(this.getWoodType().getModId(), this.getDyeColor().getName() + "_" + this.getWoodType().getName() + "_" + WoodenObjectType.BED.getName());
    }

    public DyeColor getDyeColor() {
        return this.dyeColor;
    }

    @Override
    public WoodType getWoodType() {
        return woodType;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return null;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean isBed(BlockState state, IBlockReader world, BlockPos pos, @Nullable Entity player) {
        return true;
    }
}
