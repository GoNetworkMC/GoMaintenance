package me.redth.gomaintenance

import net.md_5.bungee.api.ChatColor

object Config {
    var maintenance: Boolean = false
    lateinit var prefix: String
    lateinit var enabledMessage: String
    lateinit var disabledMessage: String
    lateinit var status: String
    lateinit var kickMessage: String
    lateinit var reloadMessage: String
    lateinit var noPermission: String

    fun load() {
        GoMaintenance.instance.saveDefaultConfig()
        val config = GoMaintenance.instance.config

        maintenance = config.getBoolean("maintenance")
        status = color(config.getString("status"))
        prefix = color(config.getString("prefix"))
        enabledMessage = prefix + color(config.getString("enabled"))
        disabledMessage = prefix + color(config.getString("disabled"))
        kickMessage = prefix + color(config.getString("kick-message"))
        reloadMessage = prefix + color(config.getString("reload"))
        noPermission = prefix + color(config.getString("no-permission"))
    }

    fun reload() {
        GoMaintenance.instance.reloadConfig()
        load()
    }

    fun toggle(): Boolean {
        maintenance = !maintenance
        GoMaintenance.instance.config.set("maintenance", maintenance)
        GoMaintenance.instance.saveConfig()
        return maintenance
    }

    private fun color(text: String): String =
        ChatColor.translateAlternateColorCodes('&', text)
}
