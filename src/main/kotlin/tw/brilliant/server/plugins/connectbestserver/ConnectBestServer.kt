package tw.brilliant.server.plugins.connectbestserver

import tw.brilliant.server.plugins.connectbestserver.event.BungeeEventListener
import tw.brilliant.server.plugins.connectbestserver.storage.PluginConfig
import net.md_5.bungee.api.plugin.Plugin
import java.util.logging.Logger

@Suppress("unused")
class ConnectBestServer : Plugin() {
    companion object {
        lateinit var INSTANCE: Plugin
        val LOGGER: Logger
            get() = INSTANCE.logger
    }

    override fun onEnable() {
        INSTANCE = this
        PluginConfig.init()
        proxy.pluginManager.registerListener(this, BungeeEventListener())

        LOGGER.info("ConnectBestServer is enabled")
    }

    override fun onDisable() {
        LOGGER.info("ConnectBestServer is disabled")
    }
}