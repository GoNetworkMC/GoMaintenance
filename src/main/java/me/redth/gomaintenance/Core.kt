package me.redth.gomaintenance

import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPipeline
import io.netty.channel.ChannelPromise
import net.minecraft.server.v1_8_R3.MinecraftServer
import net.minecraft.server.v1_8_R3.NetworkManager
import net.minecraft.server.v1_8_R3.PacketStatusOutServerInfo
import net.minecraft.server.v1_8_R3.ServerPing
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.event.server.ServerListPingEvent
import java.net.InetSocketAddress


object Core : Listener {
    private val networkManagers: List<NetworkManager> = MinecraftServer.getServer().aq().getField("h")

    @EventHandler
    fun onLogin(event: PlayerLoginEvent) {
        if (!Config.maintenance) return
        if (event.player.hasPermission("gomaintenance")) return
        event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Config.kickMessage)
    }

    @EventHandler
    fun onPing(event: ServerListPingEvent) {
        if (!Config.maintenance) return
        val networkManager = networkManagers.find {
            (it.socketAddress as? InetSocketAddress)?.address == event.address
        } ?: return
        val pipeline = networkManager.channel.pipeline()
        if ("packet_handler" !in pipeline) return
        if ("go_maintenance" in pipeline) return
        pipeline.addBefore("packet_handler", "go_maintenance", Handler())
    }

    class Handler : ChannelDuplexHandler() {
        override fun write(channel: ChannelHandlerContext, packet: Any, promise: ChannelPromise) {
            if (packet is PacketStatusOutServerInfo) {
                val serverPing: ServerPing = packet.getField("b")
                serverPing.setServerInfo(ServerPing.ServerData(Config.status, -1))
            }
            super.write(channel, packet, promise)
        }
    }

    private operator fun ChannelPipeline.contains(name: String) = get(name) != null

    private fun <T> Any.getField(name: String): T {
        val field = this::class.java.getDeclaredField(name)
        field.isAccessible = true
        return field.get(this) as T
    }
}