package tw.brilliant.server.plugins.connectbestserver.event

import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.config.ServerInfo
import net.md_5.bungee.api.event.ServerConnectEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import net.md_5.bungee.module.reconnect.yaml.YamlReconnectHandler
import tw.brilliant.server.plugins.connectbestserver.storage.BestServerSort.LeastPlayers
import tw.brilliant.server.plugins.connectbestserver.storage.BestServerSort.MostPlayers
import tw.brilliant.server.plugins.connectbestserver.storage.PluginConfig

class BungeeEventListener : Listener {
    @EventHandler
    fun onServerConnect(event: ServerConnectEvent) {
        if (event.reason != ServerConnectEvent.Reason.JOIN_PROXY || event.isCancelled) return

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

        if (isFirstJoin) {
            val servers =
                ProxyServer.getInstance().servers.filter { !PluginConfig.blacklistServers.contains(it.value.name) }

            val server: ServerInfo = when (PluginConfig.sort) {
                MostPlayers -> servers.maxBy { it.value.players.size }.value
                LeastPlayers -> servers.minBy { it.value.players.size }.value
            }

            event.target = server
        }
    }
}