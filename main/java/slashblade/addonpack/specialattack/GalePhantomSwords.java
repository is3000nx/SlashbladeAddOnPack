package slashblade.addonpack.specialattack;

import java.util.Random;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import slashblade.addonpack.entity.EntityPhantomSwordEx;
import slashblade.addonpack.util.Math2;

/**
 * 烈風幻影剣
 */
public class GalePhantomSwords extends PhantomSwordsBase
{
	public static String AttackType = StylishRankManager.AttackTypes.registerAttackType("GalePhantomSwords", 0.5F);
  
	@Override
	public String toString()
	{
		return "galephantomswords";
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
		// 標的をぐるりと囲むように配置
		
		final World world = player.world;
		final int targetId = target.getEntityId();

		final float rotUnit = 360.0f / count;
		final Random rnd = player.getRNG();
		
		for (int i = 0; i < count; i++) {
			EntityPhantomSwordEx entity = new EntityPhantomSwordEx(world, player, damage, this);
			entity.setLifeTime(30);
			entity.setTargetEntityId(targetId);
			
			final float rot = rotUnit*i;
			
			double x = -2.0*Math2.sin(rot);
			double y =  2.0*(1.0 + rnd.nextDouble()) + 0.5;
			double z =  2.0*Math2.cos(rot);

			entity.setInitialPosition(
				target.posX + x,
				target.posY + y,
				target.posZ + z,
				rot + 180,
				90.0f,
				90.0f,
				EntityPhantomSwordEx.SPEED);

			world.spawnEntity(entity);
		}
	}

	/**
	 * 標的に当たった時の処理.
	 *
	 * @param target 標的
	 */
	public void onImpact(Entity target)
	{
		target.motionX = 0.0;
		target.motionY = 0.0;
		target.motionZ = 0.0;
	}

	/**
	 * 標的に刺さっている間の追加処理.
	 *
	 * @param target 標的
	 */
	public void onSticking(Entity target)
	{
		// 地面に縫い付けるイメージ(?)なので
		// 動きを止める。

		target.motionX = 0.0;
		target.motionY = 0.0;
		target.motionZ = 0.0;

		// ※ 向きを変えるのも止めたほうがいいかな？
		// rotationYaw = prevRotationYaw;
		// rotationPitch = prevRotationPitch;
	}
	
}
