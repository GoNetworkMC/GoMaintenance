package me.redth.gomaintenance

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object GoCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (!sender.hasPermission("gomaintenance")) {
            sender.sendMessage(Config.noPermission)
            return false
        }
        if (args.firstOrNull() == "reload") {
            Config.reload()
            sender.sendMessage(Config.reloadMessage)
            return true
        }
        sender.sendMessage(if (Config.toggle()) Config.enabledMessage else Config.disabledMessage)
        return true
    }
}
