package engineer.skyouo.plugins.connectbestserver.storage

import engineer.skyouo.plugins.connectbestserver.util.Util
import net.md_5.bungee.config.Configuration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.File

object PluginConfig {
    private lateinit var file: File
    private lateinit var configuration: Configuration
    private lateinit var provider: ConfigurationProvider

    /**
     * Which sort type to use to connect to the best server
     */
    val sort: BestServerSort
        get() = BestServerSort.valueOf(configuration.getString("sort", BestServerSort.LeastPlayers.name))
    
    val blacklistServers: MutableList<String>
        get() = configuration.getStringList("blacklist-servers")

    fun init() {
        file = Util.getFileLocation("config.yml")
        if (!file.exists()) {
            file.createNewFile()
        }

        provider = ConfigurationProvider.getProvider(YamlConfiguration::class.java)
        configuration = provider.load(file)

        if (!configuration.contains("sort")) {
            configuration.set("sort", BestServerSort.LeastPlayers.name)
        }
        if (!configuration.contains("blacklist-servers")) {
            configuration.set("blacklist-servers", mutableListOf<String>())
        }

        save()
    }

    fun save() {
        provider.save(configuration, file)
    }
}