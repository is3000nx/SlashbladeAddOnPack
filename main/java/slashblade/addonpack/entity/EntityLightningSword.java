package slashblade.addonpack.entity;

import mods.flammpfeil.slashblade.ability.StylishRankManager.AttackTypes;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorAttackable;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityLightningSword extends EntityPhantomSwordEx
{
	public static String NAME_ATTACK_TYPE = StylishRankManager.AttackTypes.registerAttackType("LightningSword", -0.5F);

	/**
     * コンストラクタ
	 *
     * @param worldIn ワールド
     */
	public EntityLightningSword(World worldIn)
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
	public EntityLightningSword(World worldIn,
								EntityLivingBase thrower,
								float attackLevel)
	{
		super(worldIn, thrower, attackLevel);
	}

	/**
	 * 攻撃.
	 *
	 * @param target 標的
	 */
	protected void attackToEntity(Entity target)
	{
		float damage = Math.max(1.0f, attackLevel_);
		target.hurtResistantTime = 0;
        
		if (!this.world.isRemote) {
			this.world.addWeatherEffect(
				new EntityNoFireLightningBolt(this.world,
											  target,
											  EntitySelectorAttackable.getInstance()));
		}
		StylishRankManager.setNextAttackType(thrower_, NAME_ATTACK_TYPE);
		StylishRankManager.doAttack(thrower_);
        
		setDead();
	}
}
