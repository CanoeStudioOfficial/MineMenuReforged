package dmillerw.minemenu;

import dmillerw.minemenu.handler.ConfigHandler;
import dmillerw.minemenu.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = Tags.MOD_ID,
     name = Tags.MOD_NAME,
     version = Tags.VERSION,
     guiFactory = "dmillerw.minemenu.gui.config.MineMenuGuiFactory")
public class MineMenu {

    @SidedProxy(serverSide = "dmillerw.minemenu.proxy.CommonProxy",
                clientSide = "dmillerw.minemenu.proxy.ClientProxy")
    public static CommonProxy proxy;

    public static File menuFile;

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_ID);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        File configDir = event.getModConfigurationDirectory();

        File modConfigDir = new File(configDir, Tags.MOD_ID);
        if (!modConfigDir.exists() && !modConfigDir.mkdirs()) {
            throw new RuntimeException("无法创建配置目录: " + modConfigDir.getAbsolutePath());
        }

        File configFile = new File(modConfigDir, Tags.MOD_NAME + ".cfg");
        menuFile = new File(modConfigDir, "minemenu.json");

        ConfigHandler.init(configFile);

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}