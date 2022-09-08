package top.wcpe.wcpelib.bukkit.command

import top.wcpe.wcpelib.bukkit.command.intel.TabCompleterFunctional
import top.wcpe.wcpelib.common.command.Command

/**
 * 由 WCPE 在 2022/9/6 19:48 创建
 *
 * Created by WCPE on 2022/9/6 19:48
 *
 * GitHub  : https://github.com/wcpe
 * QQ      : 1837019522
 * @author : WCPE
 * @since  : v
 */
interface BukkitCommand : Command {
    val tabCompleter: TabCompleterFunctional?

}