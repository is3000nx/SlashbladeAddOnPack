package slashblade.addonpack.specialattack;

import com.google.common.base.Predicate;
import java.util.List;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorAttackable;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialattack.SpecialAttackBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * RapidPhantomSwords と GalePhantomSwords の共通部分
 */
abstract public class PhantomSwordsBase extends SpecialAttackBase
{
	abstract protected String getAttackType();
	abstract protected void resetTargetMotion(Entity target);
	abstract protected void spawnEntity(float damage, int count,
										Entity target, EntityPlayer player);

	/**
	 * 使用コスト
	 */
	private static final int COST = 40;

	/**
	 * コスト不足時の刀へのダメージ
	 */
	private static final int NO_COST_DAMAGE = 10;
	
	/**
	 * SAの使用?
	 */
	@Override
	public void doSpacialAttack(ItemStack stack, EntityPlayer player)
	{
		World world = player.world;
		if (world.isRemote)
			return;

		NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);
		Entity target = getTarget(tag, player);
		if (target == null)
			return;
		
		ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.SlashDim);
        
		if (!ItemSlashBlade.ProudSoul.tryAdd(tag, -COST, false)) 
			ItemSlashBlade.damageItem(stack, NO_COST_DAMAGE, player);

		ItemSlashBlade blade = (ItemSlashBlade)stack.getItem();
//		StylishRankManager.setNextAttackType(player, getAttackType());
//		blade.attackTargetEntity(stack, target, player, true);
//		player.onCriticalHit(target);
//		resetTargetMotion(target);

		// 弾を飛ばすタイプのSAなのに、
		// 発動と同時にダメージが入るのはオカシイと思うので、
		// 無効化
		
        
		if (target instanceof EntityLivingBase) {
			EntityLivingBase tmp = (EntityLivingBase)target;
			blade.setDaunting(tmp);
//			tmp.hurtTime = 0;
//			tmp.hurtResistantTime = 0;
					
		}

		int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
		float damage = 1.0f + ItemSlashBlade.AttackAmplifier.get(tag) * (level / 5.0F);
		int count = 3 + StylishRankManager.getStylishRank(player);

		spawnEntity(damage, count, target, player);
	}

	/**
	 * 標的を取得する。
	 *
	 * @param tag 刀の情報
	 * @param player プレイヤー
	 * @retrun 標的
	 */
	private Entity getTarget(NBTTagCompound tag, EntityPlayer player)
	{
		int id = ItemSlashBlade.TargetEntityId.get(tag);
		if (id != 0) {
			Entity entity = player.world.getEntityByID(id);
			if (entity != null && entity.getDistanceToEntity(player) < 30.0f)
				return entity;
		}
		return getEntityToWatch(player);
	}

	/**
	 * 視線方向にいる一番近い敵を取得する(?).
	 *
	 * @param player プレイヤー
	 * @return 攻撃対象となる敵
	 */
	private Entity getEntityToWatch(EntityPlayer player)
	{
		World world = player.world;
		Predicate<Entity> selector = EntitySelectorAttackable.getInstance();

		AxisAlignedBB bb = player.getEntityBoundingBox()
			.expand(2.0, 0.25, 2.0);
		Vec3d vec = player.getLookVec().normalize();
		
		for (int dist = 2; dist < 20; dist += 2) {
			AxisAlignedBB temp = bb.offset(vec.xCoord * dist,
										   vec.yCoord * dist,
										   vec.zCoord * dist);
      
			List<Entity> list = world.getEntitiesInAABBexcluding(player, temp, selector);

			Entity target = null;
			float distance = 30.0f;

			for (Entity entity : list) {
				float curDist = entity.getDistanceToEntity(player);
				if (curDist < distance) {
					target = entity;
					distance = curDist;
				}
			}

			if (target != null)
				return target;
		}

		return null;
	}
}
