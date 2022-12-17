package engineer.skyouo.plugins.connectbestserver.util

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
}