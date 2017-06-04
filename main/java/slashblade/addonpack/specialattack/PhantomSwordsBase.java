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
 * PhantomSwordを召喚するSAの共通部分
 */
abstract public class PhantomSwordsBase extends SpecialAttackBase
{
	/**
	 * 使用コスト
	 */
	private static final int COST = 40;

	/**
	 * コスト不足時の刀へのダメージ
	 */
	private static final int NO_COST_DAMAGE = 10;
	
	/**
	 * SAの発動
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
		if (target instanceof EntityLivingBase) {
			EntityLivingBase tmp = (EntityLivingBase)target;
			blade.setDaunting(tmp);
		}

		int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
		float damage = 1.0f + ItemSlashBlade.AttackAmplifier.get(tag) * (level / 5.0F);
		int count = 3 + StylishRankManager.getStylishRank(player);

		spawnEntity(damage, count, target, player);
	}

	/**
	 * 標的を取得する。
	 *
	 * 事前に設定してあれば、その標的。
	 * なければ視線方向に居る敵。
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
	 * 視線方向にいる一番近い敵を取得する.
	 *
	 * @param player プレイヤー
	 * @return 標的
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

	// =====================================================
	// 以下、必要に応じで書き換える

	/**
	 * 召喚したエンティティが標的に当たった時の追加処理.
	 *
	 * @param target 標的
	 */
	public void onImpact(Entity target)
	{
	}

	/**
	 * 召喚したエンティティが標的に刺さっている間の追加処理.
	 *
	 * @param target 標的
	 */
	public void onSticking(Entity target)
	{
	}

	/**
	 * エンティティを配置する。
	 *
	 * @param damage 当たった際に与えるダメージ
	 * @param count 配置するエンティティの個数
	 * @param target 標的
	 * @param player プレイヤー
	 */
	abstract protected void spawnEntity(float damage, int count,
										Entity target, EntityPlayer player);

}
