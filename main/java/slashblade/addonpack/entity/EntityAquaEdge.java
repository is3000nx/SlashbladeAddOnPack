package slashblade.addonpack.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
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
	 */
    public EntityAquaEdge(World worldIn,
						  EntityLivingBase thrower,
						  float attackLevel)
	{
		super(worldIn, thrower, attackLevel);

		setMultiHit(true);
		setColor(0x0000FF);
	}

	/**
	 * エンティティの初期位置を設定する.
	 *
	 * エンティティのインスタンスを作成後は
	 * 必ずコレで初期化すること。
	 *
	 * @param x 位置(X座標)
	 * @param y 位置(Y座標)
	 * @param z 位置(Z座標)
	 * @param yaw 向き(ヨー)(単位：度)
	 * @param pitch 向き(ピッチ)(単位：度)
	 * @param roll 傾き(ロール)(単位：度)
	 * @param speed 移動速度(移動方向はエンティティの向きと同じ)
	 */
	@Override
	public void setInitialPosition(double x, double y, double z,
								   float yaw, float pitch, float roll,
								   float speed)
	{
		super.setInitialPosition(x, y, z, yaw, pitch, roll, speed);

		setPosition(posX + motionX, posY + motionY, posZ + motionZ);
	}
	
	/**
	 * 他エンティティに攻撃(?)が通った後の処理.
	 *
	 * @param target 標的
	 * @param damage ダメージ
	 * @return true=刀を持って生体を攻撃した場合
	 */
	@Override
	protected boolean onImpact(Entity target, float damage)
	{
		return onImpact(target, damage, "drown");
	}

	/**
	 * 他エンティティに攻撃(?)が通った後の処理.
	 *
	 * ダメージを与えた後、
	 * 標的を水中にいる扱いにする、
	 * 燃えている場合は消火、
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
		spawnParticle(target);

		super.onImpact(target, damage, ds);
    
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
					"setFlag", "func_70052_a",
					int.class, boolean.class).invoke(target, 0, false);
			} catch (IllegalAccessException ex) {
				throw new RuntimeException(ex);
			} catch (IllegalArgumentException ex) {
				throw new RuntimeException(ex);
			} catch (java.lang.reflect.InvocationTargetException ex) {
				throw new RuntimeException(ex);
			}
		}
    
		if (target instanceof EntityEnderman) {
			EnderTeleportCanceller.setTeleportCancel(target, 100);
			coolDownEnderman((EntityEnderman)target);
		}

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
