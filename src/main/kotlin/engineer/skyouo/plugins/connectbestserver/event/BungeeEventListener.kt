package engineer.skyouo.plugins.connectbestserver.event

import engineer.skyouo.plugins.connectbestserver.ConnectBestServer
import engineer.skyouo.plugins.connectbestserver.storage.BestServerSort.LeastPlayers
import engineer.skyouo.plugins.connectbestserver.storage.BestServerSort.MostPlayers
import engineer.skyouo.plugins.connectbestserver.storage.PluginConfig
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.config.ServerInfo
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.ServerConnectEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import net.md_5.bungee.module.reconnect.yaml.YamlReconnectHandler
import java.util.logging.Level

class BungeeEventListener : Listener {
    @EventHandler
    fun onPostLogin(event: ServerConnectEvent) {
        if (event.reason != ServerConnectEvent.Reason.JOIN_PROXY) return

        val handler = ProxyServer.getInstance().reconnectHandler
        var isFirstJoin = false

        if (handler is YamlReconnectHandler) {
            val method = handler.javaClass.declaredMethods.find { it.name == "getStoredServer" }
            method?.isAccessible = true
            method?.invoke(handler, event.player).let {
                if (it == null) {
                    isFirstJoin = true
                }
            }
        }

        val player = event.player
        if (isFirstJoin) {
            val servers =
                ProxyServer.getInstance().servers.filter { !PluginConfig.blacklistServers.contains(it.value.name) }

            val server: ServerInfo = when (PluginConfig.sort) {
                MostPlayers -> servers.maxBy { it.value.players.size }.value
                LeastPlayers -> servers.minBy { it.value.players.size }.value
            }

            // If the player is already at the best server, don't move them
            if (event.target.name != server.name) {
                val thread = Thread(SetServer(server, player), "set-best-server")
                thread.start()
            }
        }
    }
}

private class SetServer(private val server: ServerInfo, private val player: ProxiedPlayer) : Runnable {
    override fun run() {
        try {
            player.connect(server)
            /// Waiting connect the player to the server.
            Thread.sleep(500)
            ProxyServer.getInstance().reconnectHandler.setServer(player)
        } catch (e: Exception) {
            ConnectBestServer.LOGGER.log(Level.SEVERE, "Failed to set the best server to player", e)
        }
    }
}