package yamahari.ilikewood.proxy;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import yamahari.ilikewood.tilenentities.WoodenChestTileEntity;
import yamahari.ilikewood.tilenentities.WoodenLecternTileEntity;
import yamahari.ilikewood.tilenentities.renderer.WoodenChestTileEntityRenderer;
import yamahari.ilikewood.tilenentities.renderer.WoodenLecternTileEntityRenderer;

public class ClientProxy implements IProxy {
    @Override
    public void onFMLClientSetup(FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntitySpecialRenderer(WoodenChestTileEntity.class, new WoodenChestTileEntityRenderer<>());
        ClientRegistry.bindTileEntitySpecialRenderer(WoodenLecternTileEntity.class, new WoodenLecternTileEntityRenderer());
    }

    @Override
    public void onFMLCommonSetup(FMLCommonSetupEvent event) {

    }
}
