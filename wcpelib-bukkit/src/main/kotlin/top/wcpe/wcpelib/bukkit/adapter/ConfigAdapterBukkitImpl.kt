package top.wcpe.wcpelib.bukkit.adapter

import com.google.common.base.Charsets
import org.bukkit.configuration.file.YamlConfiguration
import top.wcpe.wcpelib.bukkit.WcpeLib
import top.wcpe.wcpelib.common.adapter.ConfigAdapter
import java.io.File
import java.io.InputStreamReader
import java.nio.file.Path

/**
 * 由 WCPE 在 2022/1/3 22:57 创建
 *
 * Created by WCPE on 2022/1/3 22:57
 *
 * Github: https://github.com/wcpe
 *
 * QQ: 1837019522
 *
 * @author WCPE
 */
class ConfigAdapterBukkitImpl(private val file: File) : ConfigAdapter {

    private var config: YamlConfiguration = file.let {
        saveDefaultConfig()
        YamlConfiguration.loadConfiguration(it)
    }

    override fun saveDefaultConfig() {
        if (!file.exists()) {
            WcpeLib.getInstance().saveResource(file.name, false)
        }
    }

    override fun reloadConfig() {
        this.config = YamlConfiguration.loadConfiguration(file)
        this.config.defaults = YamlConfiguration.loadConfiguration(
            InputStreamReader(
                WcpeLib.getInstance().getResource("config.yml"),
                Charsets.UTF_8
            )
        )
    }

    override fun getString(key: String): String {
        return config.getString(key)
    }

    override fun getBoolean(key: String): Boolean {
        return config.getBoolean(key)
    }

    override fun getInt(key: String): Int {
        return config.getInt(key)
    }

    override fun getLong(key: String): Long {
        return config.getLong(key)
    }

    override fun getDouble(key: String): Double {
        return config.getDouble(key)
    }

    override fun getPath(): Path {
        return file.toPath()
    }
}