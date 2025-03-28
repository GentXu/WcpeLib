package top.wcpe.wcpelib.common.command.v2

import top.wcpe.wcpelib.common.Message
import top.wcpe.wcpelib.common.command.v2.extend.simpleJoinToString
import top.wcpe.wcpelib.common.utils.string.StringUtil
import kotlin.math.max

/**
 * 由 WCPE 在 2023/7/22 11:20 创建
 * <p>
 * Created by WCPE on 2023/7/22 11:20
 * <p>
 * <p>
 * GitHub  : <a href="https://github.com/wcpe">wcpe 's GitHub</a>
 * <p>
 * QQ      : 1837019522
 * @author : WCPE
 * @since  : v1.2.0-alpha-dev-1
 */
abstract class ParentCommand @JvmOverloads constructor(
    name: String,
    description: String = "",
    aliases: List<String> = listOf(),
    playerOnly: Boolean = false,
    playerOnlyMessage: String = "",
    opOnly: Boolean = false,
    opOnlyMessage: String = "",
    usageMessage: String = "",
    permission: String = "",
    permissionMessage: String = "",
    private val opVisibleHelp: Boolean = false,
    parentCommandBuilder: ParentCommandBuilder = ParentCommandBuilder(
        name = name,
        description = description,
        aliases = aliases,
        playerOnly = playerOnly,
        playerOnlyMessage = playerOnlyMessage,
        opOnly = opOnly,
        opOnlyMessage = opOnlyMessage,
        usageMessage = usageMessage,
        permission = permission,
        permissionMessage = permissionMessage,
        opVisibleHelp = opVisibleHelp
    ),
) : AbstractCommand(
    name = parentCommandBuilder.name,
    description = parentCommandBuilder.description,
    permission = parentCommandBuilder.permission,
    permissionMessage = parentCommandBuilder.permissionMessage,
    aliases = parentCommandBuilder.aliases,
    arguments = emptyList(),
    usageMessage = parentCommandBuilder.usageMessage,
    playerOnly = parentCommandBuilder.playerOnly,
    playerOnlyMessage = parentCommandBuilder.playerOnlyMessage,
    opOnly = parentCommandBuilder.opOnly,
    opOnlyMessage = parentCommandBuilder.opOnlyMessage
), CommandExecutor, TabCompleter {

    private val childCommandMap: MutableMap<String, ChildCommand> = mutableMapOf()
    private val childAliasCommandMap: MutableMap<String, ChildCommand> = mutableMapOf()

    fun addChildCommand(childCommand: ChildCommand) {
        childCommandMap[childCommand.name] = childCommand
        for (alias in childCommand.aliases) {
            childAliasCommandMap[alias] = childCommand
        }
    }

    @JvmOverloads
    fun childCommand(
        name: String,
        description: String = "",
        aliases: List<String> = listOf(),
        arguments: List<Argument> = listOf(),
        playerOnly: Boolean = false,
        playerOnlyMessage: String = "",
        opOnly: Boolean = false,
        opOnlyMessage: String = "",
        usageMessage: String = "",
        permission: String = "",
        permissionMessage: String = "",
        shouldDisplay: Boolean = false,
        commandExecutor: CommandExecutor? = null,
        tabCompleter: TabCompleter? = null,
    ): ChildCommand {
        return object : ChildCommand(
            this,
            name,
            description,
            aliases = aliases,
            arguments = arguments,
            playerOnly = playerOnly,
            playerOnlyMessage = playerOnlyMessage,
            opOnly = opOnly,
            opOnlyMessage = opOnlyMessage,
            usageMessage = usageMessage,
            permission = permission,
            permissionMessage = permissionMessage,
            shouldDisplay = shouldDisplay
        ) {
            init {
                this.commandExecutor = commandExecutor
                this.tabCompleter = tabCompleter
            }
        }
    }

    private val pageSize = 5

    private fun sendHelper(commandSender: CommandSender<*>, page: Int) {
        if (opVisibleHelp && !commandSender.isOp()) {
            return
        }
        val childCommands = childCommandMap.filter { (_, command) ->
            command.shouldDisplay || command.permission == "*" || commandSender.hasPermission(command.permission)
        }.map { it.value }

        val totalPages = max(1, (childCommands.size + pageSize - 1) / pageSize)
        val pageNumber = page.coerceIn(1, totalPages)
        val startIndex = (pageNumber - 1) * pageSize
        val endIndex = minOf(startIndex + pageSize, childCommands.size)


        val builder = StringBuilder()
        builder.appendLine(
            Message.CommandHelpTop.toLocalization("%command_name%" to name,
                "%page%" to pageNumber,
                "%total_page%" to totalPages,
                "%aliases%" to aliases.joinToString("") {
                    Message.AliasFormat.toLocalization("%alias%" to it)
                })
        )

        val space = StringUtil.getRepeatString(" ", name.length)

        for (command in childCommands.subList(startIndex, endIndex)) {
            builder.appendLine(
                Message.CommandHelpFormat.toLocalization(
                    "%space%" to space,
                    "%command_name%" to command.name,
                    "%arguments%" to command.arguments.simpleJoinToString(),
                    "%description%" to command.description,
                    "%permission%" to command.permission
                )
            )
        }

        builder.appendLine(
            Message.CommandHelpBottom.toLocalization(
                "%command_name%" to name, "%argument_tip%" to Message.ArgumentTip.toLocalization()
            )
        )

        commandSender.sendMessage(builder.toString())
    }

    final override fun execute(commandSender: CommandSender<*>, args: Array<String?>) {
        if (args.isEmpty() || args[0] == "help") {
            val pageNumber = args.getOrNull(1)?.toIntOrNull() ?: 1
            sendHelper(commandSender, pageNumber)
            return
        }
        val commandName = args[0]
        val childCommand = childCommandMap[commandName] ?: childAliasCommandMap[commandName]
        if (childCommand == null) {
            commandSender.sendMessage(Message.CommandNotExist.toLocalization("%command_name%" to name))
            return
        }
        childCommand.handleExecute(commandSender, args.copyOfRange(1, args.size))
    }

    override fun tabComplete(commandSender: CommandSender<*>, args: Array<String>): List<String> {
        if (args.size == 1) {
            return childCommandMap.filter { (name, command) ->
                (command.shouldDisplay || commandSender.hasPermission(command.permission)) && name.startsWith(args[0])
            }.map { it.key }
        }
        val commandName = args[0]
        val childCommand = childCommandMap[commandName] ?: childAliasCommandMap[commandName] ?: return emptyList()
        if (childCommand.playerOnly && !commandSender.isPlayer()) {
            return emptyList()
        }
        return childCommand.handleTabComplete(commandSender, args.copyOfRange(1, args.size))
    }
}