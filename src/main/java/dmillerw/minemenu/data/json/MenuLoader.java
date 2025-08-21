package dmillerw.minemenu.data.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dmillerw.minemenu.MineMenu;
import dmillerw.minemenu.data.click.ClickAction;
import dmillerw.minemenu.data.click.ClickActionCommand;
import dmillerw.minemenu.data.click.ClickActionKey;
import dmillerw.minemenu.data.menu.MenuItem;
import dmillerw.minemenu.data.menu.RadialMenu;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.io.*;
import java.util.Map;

public class MenuLoader {
    private static final Gson GSON;

    static {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.registerTypeAdapter(ItemStack.class, new ItemStackSerializer());
        builder.registerTypeAdapter(ClickAction.IClickAction.class, new ClickActionSerializer());
        GSON = builder.create();
    }

    public static void load(File file) {
        try {
            JsonElement element = GSON.fromJson(new FileReader(file), JsonElement.class);

            if (!element.isJsonObject()) {
                MineMenu.LOGGER.error("Failed to load minemenu.json! Improperly formatted file!");
                return;
            }

            for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
                MenuItem[] array = RadialMenu.getArray(entry.getKey());

                if (!entry.getValue().isJsonObject()) {
                    MineMenu.LOGGER.error("Failed to load {} category! Improperly formatted!", entry.getKey());
                    continue;
                }

                for (Map.Entry<String, JsonElement> entry1 : entry.getValue().getAsJsonObject().entrySet()) {
                    String key = entry1.getKey();
                    JsonElement data = entry1.getValue();

                    try {
                        int id = Integer.parseInt(key);

                        if (id < RadialMenu.MAX_ITEMS) {
                            array[id] = GSON.fromJson(data, MenuItem.class);

                            MenuItem item = array[id];

                            if (item.icon.isEmpty()) {
                                MineMenu.LOGGER.warn("Menu item in slot {} is looking for an item that no longer exists", id);
                                MenuItem newItem = new MenuItem(item.title, new ItemStack(Blocks.STONE), item.clickAction);
                                array[id] = newItem;
                            }

                            if (item.clickAction == null) {
                                MineMenu.LOGGER.error("Menu item in slot {} is missing a click action. It will be reset!", String.valueOf(id));
                                array[id] = null;
                            } else {
                                if ((item.clickAction instanceof ClickActionCommand && ((ClickActionCommand) item.clickAction).command.isEmpty())) {
                                    MineMenu.LOGGER.warn("Menu item in slot {} is defined as a command action, but is missing a command. It will be reset!", id);
                                    array[id] = null;
                                } else if (item.clickAction instanceof ClickActionKey && ((ClickActionKey) item.clickAction).getKeyBinding() == null) {
                                    MineMenu.LOGGER.warn("Menu item in slot {} is defined as a key action, but is missing a keybinding. It will be reset!", id);
                                    array[id] = null;
                                }
                            }
                        }
                    } catch (NumberFormatException ex) {
                        MineMenu.LOGGER.warn("Menu item found with invalid key. Ignoring.");
                    }
                }

                RadialMenu.replaceArray(entry.getKey(), array);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void save(File file) {
        if (file.exists()) {
            file.delete();
        }

        JsonObject object = new JsonObject();

        for (String category : RadialMenu.getCategories()) {
            JsonObject object1 = new JsonObject();
            MenuItem[] array = RadialMenu.getArray(category);

            for (int i = 0; i < RadialMenu.MAX_ITEMS; i++) {
                if (array[i] != null) {
                    object1.add(String.valueOf(i), GSON.toJsonTree(array[i]));
                }
            }

            object.add(category, object1);
        }

        try {
            FileWriter writer = new FileWriter(file);
            writer.append(GSON.toJson(object));
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}