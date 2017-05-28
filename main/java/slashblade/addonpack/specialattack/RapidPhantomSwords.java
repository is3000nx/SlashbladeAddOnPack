package slashblade.addonpack.specialattack;

import mods.flammpfeil.slashblade.ability.StylishRankManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import slashblade.addonpack.entity.EntityPhantomSwordEx;

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

	@Override
	protected String getAttackType()
	{
		return AttackType;
	}
	
	@Override
	protected void resetTargetMotion(Entity target)
	{
		target.motionX = 0.0D;
		target.motionY = 0.0D;
		target.motionZ = 0.0D;
		target.addVelocity(0.0D, 0.55D, 0.0D);
	}
	
	@Override
	protected void spawnEntity(float damage, int count,
							   Entity target, EntityPlayer player)
	{
		World world = player.world;
		int targetId = target.getEntityId();
		
		for (int i = 0; i < count; i++) {
			EntityPhantomSwordEx entity = new EntityPhantomSwordEx(world, player, damage);

			entity.setInterval(7 + i * 2);
			entity.setLifeTime(30);
			entity.setTargetEntityId(targetId);
              
			world.spawnEntity(entity);
		}
	}
}
