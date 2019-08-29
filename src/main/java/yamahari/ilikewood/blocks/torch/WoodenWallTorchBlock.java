package yamahari.ilikewood.blocks.torch;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yamahari.ilikewood.util.IWooden;
import yamahari.ilikewood.util.WoodType;
import yamahari.ilikewood.util.WoodenObjectType;

import java.util.Random;

public class WoodenWallTorchBlock extends WallTorchBlock implements IWooden {
    private final WoodType woodType;

    public WoodenWallTorchBlock(WoodType woodType, Block.Properties properties) {
        super(properties);
        this.woodType = woodType;
        this.setRegistryName(this.getWoodType().getModId(), this.getWoodType().getName() + "_wall_" + WoodenObjectType.TORCH.getName());
    }

    @Override
    public WoodType getWoodType() {
        return this.woodType;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        Direction direction = stateIn.get(HORIZONTAL_FACING);
        double d0 = (double) pos.getX() + 0.5D;
        double d1 = (double) pos.getY() + 0.7D;
        double d2 = (double) pos.getZ() + 0.5D;
        Direction direction1 = direction.getOpposite();
        worldIn.addParticle(ParticleTypes.SMOKE, d0 + 0.27D * (double) direction1.getXOffset(), d1 + 0.22D, d2 + 0.27D * (double) direction1.getZOffset(), 0.0D, 0.0D, 0.0D);
        // worldIn.addParticle(ParticleTypes.FLAME, d0 + 0.27D * (double)direction1.getXOffset(), d1 + 0.22D, d2 + 0.27D * (double)direction1.getZOffset(), 0.0D, 0.0D, 0.0D);
    }
}
