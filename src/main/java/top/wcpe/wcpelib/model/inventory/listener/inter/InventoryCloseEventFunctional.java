package top.wcpe.wcpelib.model.inventory.listener.inter;

import top.wcpe.wcpelib.model.inventory.entity.InventoryCloseEventDTO;
import top.wcpe.wcpelib.model.inventory.listener.InventoryPlus;

/**
 * {@link InventoryPlus}关闭函数接口
 * @author WCPE
 * @date 2021年4月8日 下午5:13:57
 */
@FunctionalInterface
public interface InventoryCloseEventFunctional {
	void run(InventoryCloseEventDTO e);
}
