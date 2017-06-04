package slashblade.addonpack.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.DamageSource;
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
		super(worldIn, thrower, attackLevel);

		setColor(0xFF0000);
		setMultiHit(false);
	}

	/**
	 * 他エンティティに攻撃(?)が通った後の処理.
	 *
	 * ダメージを与えた後、
	 * 標的に着火、
	 * 標的がエンダーマンなら、テレポートキャンセル＆消極化
	 *
	 * @param target 標的
	 * @param damage ダメージ
	 * @param ds 攻撃方法
	 * @return true=刀を持って生体を攻撃した場合
	 */
	@Override
	protected boolean onImpact(Entity target, float damage, DamageSource ds)
	{
		target.setFire(5);
		
		super.onImpact(target, damage, ds);

		if (target instanceof EntityEnderman) {
			EnderTeleportCanceller.setTeleportCancel(target, 600);
			coolDownEnderman((EntityEnderman)target);
		}

		return false;	// 使わないから、どっちを返しても良い。
	}
}
