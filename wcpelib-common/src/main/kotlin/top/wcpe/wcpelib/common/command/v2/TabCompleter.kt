package top.wcpe.wcpelib.common.command.v2

/**
 * 由 WCPE 在 2023/7/24 13:17 创建
 * <p>
 * Created by WCPE on 2023/7/24 13:17
 * <p>
 * <p>
 * GitHub  : <a href="https://github.com/wcpe">wcpe 's GitHub</a>
 * <p>
 * QQ      : 1837019522
 * @author : WCPE
 * @since  : v1.2.0-alpha-dev-1
 */
fun interface TabCompleter {
    fun tabComplete(commandSender: CommandSender<*>, args: Array<String>): List<String>
}