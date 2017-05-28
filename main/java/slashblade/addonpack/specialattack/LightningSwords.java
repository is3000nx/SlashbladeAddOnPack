package slashblade.addonpack.specialattack;

import java.util.Random;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import slashblade.addonpack.entity.EntityLightningSword;
import slashblade.addonpack.entity.EntityPhantomSwordEx;

/**
 * ニシコトロアイ
 */
public class LightningSwords extends PhantomSwordsBase
{
	public static String AttackType = StylishRankManager.AttackTypes.registerAttackType("LightningSwords", 0.5F);

	@Override
	public String toString()
	{
		return "lightningswords";
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
		final World world = player.world;
		final double rad = Math.PI * 2.0 / count;
		final int targetId = target.getEntityId();

		for (int i = 0; i < count; i++) {
			EntityPhantomSwordEx entity = new EntityPhantomSwordEx(world, player, damage);

			final double ran = rad * i;
			final double x = 2.0*Math.sin(ran);
			final double y = 2.0*(1.0 + entity.getRand().nextDouble());
			final double z = 2.0*Math.cos(ran);
			final int dir = -(int)Math.toDegrees(Math.PI + ran);

			entity.setLocationAndAngles(target.posX + x,
										target.posY + y,
										target.posZ + z,
										dir,
										90.0f);

			entity.setIniPitch(90.0f);
			entity.setIniYaw(dir);
//			entity.setDriveVector(1.75f);
			entity.setLifeTime(30);
			entity.setTargetEntityId(targetId);
			entity.setColor(0x705DA8);
			entity.setInterval(7 + i / 2);

			world.spawnEntity(entity);
		}

		EntityLightningSword entity = new EntityLightningSword(world, player, damage);
		entity.setLocationAndAngles(target.posX,
									target.posY + 4.0,
									target.posZ,
									180.0f, 90.0f);
		entity.setIniPitch(90.0f);
		entity.setIniYaw(180.0f);
//		entity.setDriveVector(1.75f);
		entity.setColor(0xFFD700);
		entity.setInterval(7 + count / 2 + 10);
		entity.setLifeTime(40);
						
		entity.setTargetEntityId(targetId);

		world.spawnEntity(entity);
	}
}
