package slashblade.addonpack.entity;

import mods.flammpfeil.slashblade.ability.StylishRankManager.AttackTypes;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorAttackable;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

/**
 * 雷を召喚する PhantomSword
 */
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
		super(worldIn, thrower, attackLevel, null);
	}

	/**
	 * 攻撃.
	 *
	 * @param target 標的
	 */
	@Override
	protected void attackToEntity(Entity target)
	{
		target.hurtResistantTime = 0;
        
		if (!world.isRemote) {
			world.addWeatherEffect(
				new EntityNoFireLightningBolt(world,
											  target,
											  EntitySelectorAttackable.getInstance()));
		}
		StylishRankManager.setNextAttackType(thrower_, NAME_ATTACK_TYPE);
		StylishRankManager.doAttack(thrower_);
        
		setDead();
	}
}
