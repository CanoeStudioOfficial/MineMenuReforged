package dmillerw.minemenu.network;

import dmillerw.minemenu.MineMenu;
import dmillerw.minemenu.data.session.ActionSessionData;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber
public class NetworkEventHandler {

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
        }
    }

    @SubscribeEvent
    public static void onClientConnect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if (!event.isLocal()) {
            // Disable server specific options, which will be re-enabled if the server replies
            MineMenu.LOGGER.info("CLIENT: Connected to server. Disabling server-side click actions until server replies");
            ActionSessionData.activateClientValues();
        } else {
            ActionSessionData.activateAll();
        }
    }

    @SubscribeEvent
    public static void onClientDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        MineMenu.LOGGER.info("CLIENT: Disconnected from server, enabling all click actions");
        ActionSessionData.activateAll();
    }
}