package top.wcpe.wcpelib.model.inventory.listener.inter;

import top.wcpe.wcpelib.model.inventory.entity.InventoryOpenEventDTO;
import top.wcpe.wcpelib.model.inventory.listener.InventoryPlus;
/**
 * {@link InventoryPlus}打开函数接口
 * @author WCPE
 * @date 2021年4月8日 下午5:14:16
 */
@FunctionalInterface
public interface InventoryOpenEventFunctional {
	void run(InventoryOpenEventDTO e);
}
