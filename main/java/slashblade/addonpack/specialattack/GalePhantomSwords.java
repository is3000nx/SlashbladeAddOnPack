package slashblade.addonpack.specialattack;

import java.util.Random;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import slashblade.addonpack.entity.EntityPhantomSwordEx;

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
	}

	@Override
	protected void spawnEntity(float damage, int count,
							   Entity target, EntityPlayer player)
	{
		World world = player.world;
		double rad = Math.PI * 2.0 / count;
		int targetId = target.getEntityId();

		for (int i = 0; i < count; i++) {
			EntityPhantomSwordEx entity = new EntityPhantomSwordEx(world, player, damage);

			double ran = rad * i;
			double x = 2.0*Math.sin(ran);
			double y = 2.0*(1.0 + entity.getRand().nextDouble());
			double z = 2.0*Math.cos(ran);
			int dir = -(int)Math.toDegrees(Math.PI + ran);

			entity.setLocationAndAngles(target.posX + x,
										target.posY + y,
										target.posZ + z,
										dir,
										90.0f);

			entity.setIniPitch(90.0f);
			entity.setIniYaw(dir);
//			entity.setDriveVector(1.75f);
//			entity.setInterval(7);
			entity.setLifeTime(30);
			entity.setTargetEntityId(targetId);

			world.spawnEntity(entity);
		}
	}
}
