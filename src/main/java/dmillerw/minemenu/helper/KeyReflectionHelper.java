package dmillerw.minemenu.helper;

import dmillerw.minemenu.mixin.IKeyBindingAccessor;
import net.minecraft.client.settings.KeyBinding;

public class KeyReflectionHelper {

    public static void updatePressTime(KeyBinding keyBinding, int pressTime) {
        IKeyBindingAccessor accessor = (IKeyBindingAccessor) keyBinding;
        if (pressTime == 0) {
            accessor.setPressTime(0);
        } else {
            accessor.addPressTime(pressTime);
        }
    }
}