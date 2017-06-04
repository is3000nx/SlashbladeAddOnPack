package slashblade.addonpack.specialattack;

import java.util.Random;
import mods.flammpfeil.slashblade.ability.StylishRankManager.AttackTypes;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialattack.SpecialAttackBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

/**
 * カムイノミ
 */
public class None extends SpecialAttackBase
{
	public static String AttackType = StylishRankManager.AttackTypes.registerAttackType("Provoke", -1.0f);

	/**
	 * 使用コスト
	 */
	private static final int COST = 10;
	
	@Override
	public String toString()
	{
		return "none";
	}
  
	/**
	 * SAの発動
	 */
	@Override
	public void doSpacialAttack(ItemStack stack, EntityPlayer player)
	{
		NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);
		if (ItemSlashBlade.ProudSoul.tryAdd(tag, -COST, false)) {

			spawnParticle(EnumParticleTypes.SPELL_WITCH, player, 20, 1.0);
			StylishRankManager.setNextAttackType(player, AttackType);
			StylishRankManager.doAttack(player);
			// SAランクを上げるだけ

		} else {
			spawnParticle(EnumParticleTypes.SMOKE_LARGE, player, 20, 1.0);
		}
	}

	/**
	 * パーティクルの表示.
	 *
	 * プレイヤの周辺にランダムで発生
	 *
	 * @param type パーティクルの種類
	 * @param player プレイヤ
	 * @param num パーティクルの個数
	 */
	public static void spawnParticle(EnumParticleTypes type, EntityPlayer player, int num, double rate)
	{
		// EntityBase.spawnExplodeParticle() とほぼ同じだが
		// こっちは、Y座標が固定
		
		World world = player.world;
		Random rand = player.getRNG();
		
		for (int i = 0; i < num; i++) {
			double xSpeed = rand.nextGaussian() * 0.02;
			double ySpeed = rand.nextGaussian() * 0.02;
			double zSpeed = rand.nextGaussian() * 0.02;

			double rx = rand.nextDouble();
//			double ry = rand.nextDouble();
			double rz = rand.nextDouble();
			
			world.spawnParticle(
				type,
				player.posX + ((rx*2 - 1)*player.width  - xSpeed * 10.0)*rate,
				player.posY,
				player.posZ + ((rz*2 - 1)*player.width  - zSpeed * 10.0)*rate,
				xSpeed, ySpeed, zSpeed);
		}
	}
}
