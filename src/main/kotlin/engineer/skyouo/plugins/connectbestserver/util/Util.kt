package engineer.skyouo.plugins.connectbestserver.util

import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.config.Configuration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.File

object Util {
    private fun getBaseDirectory(): File {
        return File("plugins/ConnectBestServer")
    }

    fun getFileLocation(fileName: String): File {
        val baseDirectory: File = getBaseDirectory()
        if (!baseDirectory.exists()) {
            baseDirectory.mkdirs()
        }

        return baseDirectory.resolve(fileName)
    }

    // The player is first join the server
    fun isFirstJoin(username: String): Boolean {
        ProxyServer.getInstance().reconnectHandler.save()
        val locations: Configuration =
            ConfigurationProvider.getProvider(YamlConfiguration::class.java).load(File("locations.yml"))

        locations.keys.forEach {
            val name = it.split(";").first()
            if (name == username) {
                return false
            }
        }

        return true
    }
}