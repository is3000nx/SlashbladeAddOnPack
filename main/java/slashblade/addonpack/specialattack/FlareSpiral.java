package slashblade.addonpack.specialattack;

import java.util.List;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorAttackable;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialattack.SpecialAttackBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slashblade.addonpack.entity.EntityFlareEdge;

/**
 * キムンリムセ
 */
public class FlareSpiral extends SpecialAttackBase
{
	public static String AttackType = StylishRankManager.AttackTypes.registerAttackType("FlareSpiral", 0.5F);

	/**
	 * 使用コスト
	 */
	private static final int COST = 40;

	/**
	 * コスト不足時の刀へのダメージ
	 */
	private static final int NO_COST_DAMAGE = 10;

	@Override
	public String toString()
	{
		return "flarespiral";
	}
  
	@Override
	public void doSpacialAttack(ItemStack stack, EntityPlayer player)
	{
		World world = player.world;
    
		world.playEvent(player, 1009,
						new BlockPos(player.posX, player.posY, player.posZ),
						0);
		// ※ 消火の時と同じイベントだが？

		None.spawnParticle(EnumParticleTypes.FLAME, player, 20, 5.0);
   
		NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);
		if (!world.isRemote) {

			if (!ItemSlashBlade.ProudSoul.tryAdd(tag, -COST, false))
				ItemSlashBlade.damageItem(stack, NO_COST_DAMAGE, player);
      
			ItemSlashBlade blade = (ItemSlashBlade)stack.getItem();
      
			AxisAlignedBB bb = player.getEntityBoundingBox()
				.expand(5.0, 0.25, 5.0);

			List<Entity> list = world.getEntitiesInAABBexcluding(player, bb, EntitySelectorAttackable.getInstance());

			if (!list.isEmpty())
				StylishRankManager.setNextAttackType(player, AttackType);
			
			for (Entity curEntity : list) {
				blade.attackTargetEntity(stack, curEntity, player, true);
				player.onCriticalHit(curEntity);
			}
      
			int rank = StylishRankManager.getStylishRank(player);

			float damage = blade.getBaseAttackModifiers(tag) / 2.0f;
			if (rank >= 5) {
				int level = Math.max(1, EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack));
				damage += ItemSlashBlade.AttackAmplifier.get(tag) * (0.25f + level / 5.0f);
			}
      
			final int maxCount = 5 + rank;
			final double radBaseRot = Math.toRadians(player.rotationYaw);
			final double radRot = 2.0*Math.PI / maxCount;

			for (int i = 0; i < maxCount; i++) {
				EntityFlareEdge entity = new EntityFlareEdge(world, player, damage);

				double x = player.posX + Math.cos(radBaseRot + radRot*i);
				double y = player.posY + 0.7*Math.sin(-Math.PI/3.0 + radRot*i);
				double z = player.posZ + Math.sin(radBaseRot + radRot*i);
				float yaw = player.rotationYaw +(float)Math.toDegrees(radRot*i);
				float pitch = (float)(Math.cos(-Math.PI/3.0 + radRot*i));
				if (pitch < 0.0f)
					pitch = 1.0f;
				else
					pitch *= -30.0f;

				entity.setLocationAndAngles(x, y, z, yaw, pitch);

				entity.setColor(0xFF0000);
				entity.setDriveVector(0.3F);
				entity.setLifeTime(15);
				entity.setIsMultiHit(false);
				entity.setRoll(90.0f - 30.0f*(float)Math.cos(Math.PI/6 + radRot*i));
				world.spawnEntity(entity);
			}
		}

		ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.Battou);

		// ※ 上記処理 SpiralEdge と、ほぼ同じ
	}
}
