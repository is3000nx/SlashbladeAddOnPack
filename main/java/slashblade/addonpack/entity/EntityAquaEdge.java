package slashblade.addonpack.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import slashblade.addonpack.ability.EnderTeleportCanceller;

public class EntityAquaEdge extends EntityDriveEx
{
	/**
	 * コンストラクタ
	 *
	 * @param worldIn ワールド
	 */
	public EntityAquaEdge(World worldIn)
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
    public EntityAquaEdge(World worldIn,
						  EntityLivingBase thrower,
						  float attackLevel,
						  boolean multiHit,
						  float roll)
	{
		super(worldIn, thrower, attackLevel, multiHit, roll);
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
		EnderTeleportCanceller.setTeleportCancel(target, 100);
    
		spawnParticle(target);

		if (!target.world.isRemote)
			super.onImpact(target, damage, "drown");
    
		ReflectionHelper.setPrivateValue(Entity.class,
										 target,
										 true,
										 "field_70171_ac", "inWater");
    
		if (target.isBurning()) {
			// 消火
			target.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE,
							 0.7f,
							 1.2f + 0.8f*this.rand.nextFloat());
			target.extinguish();

			try {
				ReflectionHelper.findMethod(
					Entity.class,
					target,
					new String[]{"setFlag"},
					int.class, boolean.class).invoke(target, 0, false);
			} catch (IllegalAccessException ex) {
				throw new RuntimeException(ex);
			} catch (IllegalArgumentException ex) {
				throw new RuntimeException(ex);
			} catch (java.lang.reflect.InvocationTargetException ex) {
				throw new RuntimeException(ex);
			}
		}
    
		if (target instanceof EntityEnderman)
			toPassiveEnderman((EntityEnderman)target);

		return false;	// 使わないから、どっちを返しても良い。
	}

	/**
	 * 命中した時のパーティクルを出す.
	 *
	 * @param target 標的
	 */
	private void spawnParticle(Entity target)
	{
		target.world.spawnParticle(
			EnumParticleTypes.EXPLOSION_LARGE,
			target.posX,
			target.posY + target.height,
			target.posZ,
			3.0, 3.0, 3.0);

		target.world.spawnParticle(
			EnumParticleTypes.EXPLOSION_LARGE,
			target.posX + 1.0,
			target.posY + target.height + 1.0,
			target.posZ,
			3.0, 3.0, 3.0);

		target.world.spawnParticle(
			EnumParticleTypes.EXPLOSION_LARGE,
			target.posX,
			target.posY + target.height + 0.5,
			target.posZ + 1.0,
			3.0, 3.0, 3.0);
	}
}
