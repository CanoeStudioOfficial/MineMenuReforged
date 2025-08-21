package dmillerw.minemenu.gui.config;

import dmillerw.minemenu.handler.ConfigHandler;
import dmillerw.minemenu.Tags;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.DefaultGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

public class MineMenuGuiFactory extends DefaultGuiFactory {

    public MineMenuGuiFactory() {
        super(Tags.MOD_ID, GuiConfig.getAbridgedConfigPath(ConfigHandler.config.toString()));
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        return new GuiConfig(parentScreen, getElements(), modid, false, false, title);
    }

    private static List<IConfigElement> getElements() {
        List<IConfigElement> list = new ArrayList<>();
        list.addAll((new ConfigElement(ConfigHandler.config.getCategory(ConfigHandler.CATEGORY_VISUAL))).getChildElements());
        list.addAll((new ConfigElement(ConfigHandler.config.getCategory(Configuration.CATEGORY_GENERAL))).getChildElements());
        return list;
    }
}