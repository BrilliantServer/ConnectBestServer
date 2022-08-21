package engineer.skyouo.plugins.connectbestserver.event

import engineer.skyouo.plugins.connectbestserver.storage.BestServerSort.LeastPlayers
import engineer.skyouo.plugins.connectbestserver.storage.BestServerSort.MostPlayers
import engineer.skyouo.plugins.connectbestserver.storage.PluginConfig
import engineer.skyouo.plugins.connectbestserver.util.Util
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.config.ServerInfo
import net.md_5.bungee.api.event.PlayerDisconnectEvent
import net.md_5.bungee.api.event.PostLoginEvent
import net.md_5.bungee.api.event.PreLoginEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

class BungeeEventListener : Listener {
    private val firstJoinPlayers = mutableSetOf<String>()

    @EventHandler
    fun onPreLogin(event: PreLoginEvent) {
        val connection = event.connection
        val isFirstJoin = Util.isFirstJoin(connection.name)

        if (isFirstJoin) {
            if (ProxyServer.getInstance().config.isOnlineMode && !connection.isOnlineMode) {
                return
            }
            firstJoinPlayers.add(connection.name)
        }
    }

    @EventHandler
    fun onPostLogin(event: PostLoginEvent) {
        val player = event.player
        val isFirstJoin = firstJoinPlayers.contains(player.name)
        if (isFirstJoin) {
            val servers =
                ProxyServer.getInstance().servers.filter { !PluginConfig.blacklistServers.contains(it.value.name) }

            val server: ServerInfo = when (PluginConfig.sort) {
                MostPlayers -> servers.maxBy { it.value.players.size }.value
                LeastPlayers -> servers.minBy { it.value.players.size }.value
            }

            // If the player is already at the best server, don't move them
            if (player.server?.info?.name != server.name) {
                player.connect(server)
            }

            firstJoinPlayers.remove(player.name)
        }
    }

    @EventHandler
    fun onPlayerDisconnect(event: PlayerDisconnectEvent) {
    }
}