package dmillerw.minemenu.mixin;

import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyBinding.class)
public interface IKeyBindingAccessor {
    @Accessor("pressTime")
    void setPressTime(int value);

    @Accessor("pressTime")
    void addPressTime(int value);
}