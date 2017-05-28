package slashblade.addonpack.ability;

import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * Enderman のテレポートをキャンセルする
 */
public class EnderTeleportCanceller
{
	// ※
	// キャンセル期間をパラメータで変更出来る点以外、
	// mods.flammpfeil.slashblade.ability.TeleportCanceller と一緒。
	// （ただ作り的に共通化は無理）
	
	/** テレポート無効期間の登録名 */
	public static final String CancelingTimesStr = "SB.Kamuy.WarpCancel";

	/**
	 * テレポートを無効化する期間を設定する。
	 *
	 * @param target 設定対象(=Enderman)
	 * @param ticks テレポート無効期間 (単位:ticks)
	 */
	public static void setTeleportCancel(Entity target, int ticks)
	{
		if (!(target instanceof EntityEnderman))
			return;

		target.getEntityData().setLong(CancelingTimesStr, target.world.getTotalWorldTime() + ticks);
	}

	/**
	 * テレポート可能かどうかの判定
	 * @param entity 対象
	 * @return true=可能 / false=無効期間中
	 */
    static private boolean canTeleport(Entity entity)
	{
		World world = entity.world;

        if (entity != null && !world.isRemote) {

            long current = world.getTotalWorldTime();
            long timeout = entity.getEntityData().getLong(CancelingTimesStr);

			// ※
			// Enderman以外でココが呼ばれることは考慮しなくて良い？

            return timeout < current;
        }

        return false;
    }
	
	/**
	 * Endermanがテレポートする時に呼ばれるイベント
	 */
    @SubscribeEvent
    public void onEnderTeleportEvent(EnderTeleportEvent event)
	{
        if (!canTeleport(event.getEntityLiving()))
            event.setCanceled(true);
    }
}
