package top.wcpe.wcpelib.nukkit.inventory

import cn.nukkit.item.Item
import cn.nukkit.item.enchantment.Enchantment
import top.wcpe.wcpelib.nukkit.inventory.action.ClickListener

class SlotPlusBuilder {

    var id: Int = 0

    var data = 0

    var name: String = " "

    var lores: MutableList<String> = mutableListOf()

    var amount = 1

    var enchantments: MutableList<Enchantment> = mutableListOf()

    var unbreakable = false

    var listener: ClickListener? = null

    constructor(item: Item) {
        if (item == null) {
            return
        }
        id = item.id
        data = item.damage
        name = item.customName
        amount = item.count
        lores = item.lore.toMutableList()
        enchantments = item.enchantments.toMutableList()
        unbreakable = item.isUnbreakable
    }


    fun name(name: String): SlotPlusBuilder {
        this.name = name
        return this
    }


    fun lore(lores: MutableList<String>): SlotPlusBuilder {
        if (lores == null) return this
        this.lores = lores
        return this
    }


    fun lore(vararg lore: String): SlotPlusBuilder {
        if (lore == null) return this
        this.lores = lore.toMutableList()
        return this
    }

    fun id(id: Int): SlotPlusBuilder {
        this.id = id
        return this
    }


    fun amount(amount: Int): SlotPlusBuilder {
        this.amount = amount
        return this
    }

    fun addEnchantments(
        enchantments: MutableList<Enchantment>
    ): SlotPlusBuilder {
        this.enchantments = enchantments
        return this
    }

    fun onClick(listener: ClickListener): SlotPlusBuilder {
        this.listener = listener
        return this
    }

    fun unbreakable(unbreakable: Boolean): SlotPlusBuilder {
        this.unbreakable = unbreakable
        return this
    }

    fun build(): SlotPlus {
        return SlotPlus(this)
    }
}