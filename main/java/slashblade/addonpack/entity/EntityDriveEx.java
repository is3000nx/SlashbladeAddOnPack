package slashblade.addonpack.entity;

import java.util.List;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorAttackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityDriveEx extends EntityBase
{

	/** 当たり判定の大きさ */
	private static final double AMBIT = 1.5;
	
	/** パラメータ：多段Hitの有無 */
    private static final DataParameter<Boolean> IS_MULTI_HIT = EntityDataManager.<Boolean>createKey(EntityDriveEx.class, DataSerializers.BOOLEAN);
	
	/**
	 * コンストラクタ
	 *
	 * @param worldIn ワールド
	 */
    public EntityDriveEx(World worldIn)
    {
        super(worldIn);
    }

	/**
	 * コンストラクタ
	 *
	 * @param worldIn ワールド
	 * @param thrower 撃った人
	 * @param attackLevel 攻撃レベル
	 * @param multiHit true=多段Hit有り
	 * @param roll エンティティのロール
	 */
    public EntityDriveEx(World worldIn,
						 EntityLivingBase thrower,
						 float attackLevel,
						 boolean multiHit,
						 float roll)
	{
		super(worldIn, thrower, attackLevel, roll);
        setIsMultiHit(multiHit);

		setInitialPosition();
    }

	/**
	 * デフォルトでの初期表示位置
	 */
	private void setInitialPosition()
	{
        setSize(1.0f, 2.0f);
		setInitialSpeed(0.75f);

		setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
    }

    /**
     * エンティティの初期化処理
     */
    @Override
    protected void entityInit()
	{
		super.entityInit();

		EntityDataManager manager = getDataManager();
        manager.register(IS_MULTI_HIT, false);
    }

	/**
	 * 多段Hitの有無
	 */
    public boolean getIsMultiHit(){
        return this.getDataManager().get(IS_MULTI_HIT);
    }
	/**
	 * 多段Hitの有無
	 */
    public void setIsMultiHit(boolean isMultiHit){
        this.getDataManager().set(IS_MULTI_HIT,isMultiHit);
    }

    public void setDriveVector(float speed)
	{
		setDriveVector(rotationYaw, rotationPitch, speed, true);
	}

	public void setInitialSpeed(float speed)
	{
        setLocationAndAngles(thrower_.posX,
							 thrower_.posY + getYOffset(),
							 thrower_.posZ,
							 thrower_.rotationYaw,
							 thrower_.rotationPitch);

        setDriveVector(speed);
	}

    @Override
    public void onUpdate()
    {
		AxisAlignedBB bb = new AxisAlignedBB(this.posX - AMBIT,
											 this.posY - AMBIT,
											 this.posZ - AMBIT,
											 this.posX + AMBIT,
											 this.posY + AMBIT,
											 this.posZ + AMBIT);

		intercept(bb, false);	// 射撃物の迎撃

		final boolean isMultiHit = getIsMultiHit();
		
		if (!isMultiHit || this.ticksExisted % 2 == 0) {

			List<Entity> list = world.getEntitiesInAABBexcluding(thrower_, bb, EntitySelectorAttackable.getInstance());
			list.removeAll(alreadyHitEntity_);
			if (!isMultiHit)
				alreadyHitEntity_.addAll(list);

			float damage = Math.max(1.0f, attackLevel_);
			StylishRankManager.setNextAttackType(
				thrower_ ,
				isMultiHit ?
				StylishRankManager.AttackTypes.QuickDrive :
				StylishRankManager.AttackTypes.Drive);

			for (Entity target : list) {
				onImpact(target, damage);
			}
		}

		if (!world.getCollisionBoxes(this, getEntityBoundingBox()).isEmpty()) {
			// 障害物に当たった？
			setDead();
			return;
		}

		updatePosition();

		if (this.ticksExisted >= getLifeTime())
			setDead();
	}

	/**
	 * 移動.
	 */
	private void updatePosition()
	{
		this.lastTickPosX = this.posX;
		this.lastTickPosY = this.posY;
		this.lastTickPosZ = this.posZ;

        this.motionX *= 1.05f;
        this.motionY *= 1.05f;
        this.motionZ *= 1.05f;

		setPosition(posX + motionX, posY + motionY, posZ + motionZ);
	}

	@Override
    public double getYOffset()
    {
        return thrower_.getEyeHeight() * 0.5;
    }

}
