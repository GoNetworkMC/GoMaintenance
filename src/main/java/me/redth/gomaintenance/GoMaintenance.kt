package me.redth.gomaintenance

import org.bukkit.plugin.java.JavaPlugin

class GoMaintenance : JavaPlugin() {
    override fun onEnable() {
        instance = this
        Config.load()
        server.pluginManager.registerEvents(Core, this)
        getCommand("maintenance").executor = GoCommand
    }

    companion object {
        lateinit var instance: GoMaintenance
    }
}
