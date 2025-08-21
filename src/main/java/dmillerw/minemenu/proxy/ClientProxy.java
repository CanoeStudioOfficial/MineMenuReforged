package dmillerw.minemenu.proxy;

import dmillerw.minemenu.MineMenu;
import dmillerw.minemenu.data.json.MenuLoader;
import dmillerw.minemenu.handler.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraftforge.client.resource.IResourceType;
import net.minecraftforge.client.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public class ClientProxy extends CommonProxy implements ISelectiveResourceReloadListener {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        KeyboardHandler.register();

        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(this);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);

        if (!MineMenu.menuFile.exists()) {
            MenuLoader.save(MineMenu.menuFile);
        }
        MenuLoader.load(MineMenu.menuFile);
    }

    @Override
    public void onResourceManagerReload(@Nonnull IResourceManager manager, @Nonnull Predicate<IResourceType> predicate) {
        MenuLoader.load(MineMenu.menuFile);
    }
}