package dmillerw.minemenu.network.packet.client;

import dmillerw.minemenu.MineMenu;
import dmillerw.minemenu.data.session.ActionSessionData;
import dmillerw.minemenu.network.packet.Packet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class PacketServerResponse extends Packet<PacketServerResponse> {

    @Override
    protected void handleClientSide(EntityPlayer player) {
        MineMenu.LOGGER.info("CLIENT: Received response from server, activating server-side click actions");
        ActionSessionData.activateAll();
    }

    @Override
    protected void handleServerSide(EntityPlayerMP player) {
    }

    @Override
    protected void toBytes(PacketBuffer buffer) {
        buffer.writeBoolean(true);
    }

    @Override
    protected void fromBytes(PacketBuffer buffer) throws IOException {
    }
}