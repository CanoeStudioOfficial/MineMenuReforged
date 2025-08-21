package dmillerw.minemenu.network;

import dmillerw.minemenu.network.packet.client.PacketServerResponse;
import dmillerw.minemenu.network.packet.server.PacketUseItem;
import dmillerw.minemenu.Tags;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Tags.MOD_NAME);

    public static void initialize() {
        INSTANCE.registerMessage(PacketServerResponse.class, PacketServerResponse.class, 0, Side.CLIENT);
        INSTANCE.registerMessage(PacketUseItem.class, PacketUseItem.class, 1, Side.SERVER);
    }
}