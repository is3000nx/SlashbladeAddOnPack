package slashblade.addonpack.specialattack;

import java.util.Random;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import slashblade.addonpack.entity.EntityPhantomSwordEx;
import slashblade.addonpack.util.Math2;

/**
 * 急襲幻影剣
 */
public class RapidPhantomSwords extends PhantomSwordsBase
{
	public static String AttackType = StylishRankManager.AttackTypes.registerAttackType("RapidPhantomSwords", 0.5F);
	
	@Override
	public String toString()
	{
		return "rapidphantomswords";
	}

	/**
	 * エンティティを配置する。
	 *
	 * @param damage 当たった際に与えるダメージ
	 * @param count 配置するエンティティの個数
	 * @param target 標的
	 * @param player プレイヤー
	 */
	@Override
	protected void spawnEntity(float damage, int count,
							   Entity target, EntityPlayer player)
	{
		World world = player.world;
		int targetId = target.getEntityId();

		Random rnd = player.getRNG();

		for (int i = 0; i < count; i++) {

			// プレイヤーの位置の頂点として
			// 左右に逆V字のライン上にランダムで配置
			
			EntityPhantomSwordEx entity = new EntityPhantomSwordEx(world, player, damage, this);

			entity.setInterval(7 + i * 2);
			entity.setLifeTime(30);
			entity.setTargetEntityId(targetId);

			double d = rnd.nextDouble()*2.0 - 1.0;	// -1 〜 1

			double x = 2.0 * d * Math2.cos(player.rotationYaw);
			double y = 2.0 * (1.0 - Math.abs(d)) + 0.5;
			double z = 2.0 * d * Math2.sin(player.rotationYaw);
			
			entity.setInitialPosition(
				player.posX + x,
				player.posY + y,
				player.posZ + z,
				player.rotationYaw,
				player.rotationPitch,
				90.0f,
				EntityPhantomSwordEx.SPEED);
              
			world.spawnEntity(entity);
		}
	}

	/**
	 * 標的に当たった時の処理
	 *
	 * @param target 標的
	 */
	@Override
	public void onImpact(Entity target)
	{
		target.addVelocity(-target.motionX, 0.1, -target.motionZ);

		// 上方へ飛ばす。
		// オリジナルと異なり、当たる度に加速する。
	}
}
