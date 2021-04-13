package top.wcpe.wcpelib.model.inventory.listener.inter;

import top.wcpe.wcpelib.model.inventory.entity.SlotDTO;
import top.wcpe.wcpelib.model.inventory.entity.Slot;
import top.wcpe.wcpelib.model.inventory.listener.InventoryPlus;
/**
 * {@link InventoryPlus}中的{@link Slot}点击函数接口
 * @author WCPE
 * @date 2021年4月8日 下午5:14:32
 */
@FunctionalInterface
public interface SlotEventFunctional {
	void run(SlotDTO e);
}
