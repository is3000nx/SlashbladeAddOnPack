package slashblade.addonpack.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.world.World;
import slashblade.addonpack.ability.EnderTeleportCanceller;

public class EntityFlareEdge extends EntityDriveEx
{
	/**
	 * コンストラクタ
	 *
	 * @param worldIn ワールド
	 */
	public EntityFlareEdge(World worldIn)
    {
        super(worldIn);
	}
  
	/**
	 * コンストラクタ
	 *
	 * @param worldIn ワールド
	 * @param thrower 撃った人
	 * @param attackLevel 攻撃レベル
	 * @param multiHit true=多段Hit有り
	 * @param roll エンティティのロール
	 */
	public EntityFlareEdge(World worldIn,
						  EntityLivingBase thrower,
						  float attackLevel)
	{
		super(worldIn, thrower, attackLevel, false, 0.0f);
	}

	/**
	 * 攻撃が当たった処理.
	 *
	 * @param target 当たった対象
	 * @param damage 与えるダメージ
	 * @param source 攻撃手段
	 * @return true=刀を持って生体を攻撃した場合
	 */
	@Override
	protected boolean onImpact(Entity target, float damage, String source)
	{
		EnderTeleportCanceller.setTeleportCancel(target, 600);

		target.setFire(5);
		
		if (!this.world.isRemote)
			super.onImpact(target, damage, source);

		if (target instanceof EntityEnderman)
			toPassiveEnderman((EntityEnderman)target);

		return false;	// 使わないから、どっちを返しても良い。
	}
}
