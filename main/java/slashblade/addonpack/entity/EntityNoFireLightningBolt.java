package slashblade.addonpack.entity;

import com.google.common.base.Predicate;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import slashblade.addonpack.ability.EnderTeleportCanceller;

/**
 * 延焼しない雷
 */
public class EntityNoFireLightningBolt extends EntityLightningBolt
{
	/**
	 * 落雷対象となっているエンティティ
	 */
	private final Entity target_;

	/**
	 * 雷の被害を受けるエンティティの抽出条件
	 */
	private final Predicate<Entity> selector_;

	/**
	 * 雷の状態.
	 */
	private int lightningState_;
	// 2 = 落雷
	// 0,1 = 周囲に被害発生
	// 負 = 余韻

	/**
	 * 雷の持続時間
	 */
	private int boltLivingTime_;

	/**
     * コンストラクタ
	 *
     * @param worldIn ワールド
     */
	public EntityNoFireLightningBolt(World worldIn)
	{
		super(worldIn, 0, 0, 0, true);
		target_ = null;
		selector_ = null;
		
	}

	/**
     * コンストラクタ
	 *
     * @param worldIn ワールド
	 * @param target 雷の標的
	 * @param selector 雷の被害を受けるエンティティの抽出条件 
	 */
	public EntityNoFireLightningBolt(World worldIn, Entity target, Predicate<Entity> selector)
	{
		super(worldIn, target.posX, target.posY, target.posZ, true);

		this.target_ = target;
		this.selector_ = selector;

		this.lightningState_ = 2;
		this.boltLivingTime_ = this.rand.nextInt(3) + 1;
	}

	/**
	 * 更新処理.
	 *
	 * 雷の状態を更新し、周辺に被害を与える。
	 */
	@Override
	public void onUpdate()
	{
		onEntityUpdate();
		
		if (lightningState_ == 2) {
            this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.WEATHER, 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
            this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LIGHTNING_IMPACT, SoundCategory.WEATHER, 2.0F, 0.5F + this.rand.nextFloat() * 0.2F);
		}
    
		if (--lightningState_ < 0) {

			if (boltLivingTime_ == 0) {
				setDead();
			} else if (lightningState_ < -this.rand.nextInt(10)) {
				--boltLivingTime_;
				lightningState_ = 1;

				this.boltVertex = this.rand.nextLong();
			}
		}

		if (lightningState_ < 0)
			return;

		final double AMBIT = 3.0D;
		AxisAlignedBB bb = new AxisAlignedBB(this.posX - AMBIT,
											 this.posY - AMBIT,
											 this.posZ - AMBIT,
											 this.posX + AMBIT,
											 this.posY + 6.0D + AMBIT,
											 this.posZ + AMBIT);
		List<Entity> list = world.getEntitiesInAABBexcluding(this, bb, selector_);
		if (target_.isEntityAlive())
			list.add(target_);
		// ※
		// 標的となっているエンティティには
		// 雷の効果が2回発生することにならないか？

		for (Entity entity : list) {
			EnderTeleportCanceller.setTeleportCancel(entity, 600);
			if (!this.world.isRemote &&
				!ForgeEventFactory.onEntityStruckByLightning(entity, this)) {
				
				entity.onStruckByLightning(this);
			}
		}
	}
}
