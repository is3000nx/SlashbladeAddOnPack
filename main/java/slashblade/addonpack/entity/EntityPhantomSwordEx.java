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

public class EntityPhantomSwordEx extends EntityBase
{
	/**
	 * 初期ヨー
	 */
    protected float iniYaw_ = Float.NaN;

	/**
	 * 初期ピッチ
	 */
    protected float iniPitch_ = Float.NaN;

	/**
	 * 刺さっているエンティティ
	 */
    private Entity stuckEntity_ = null;	

	/* 標的に刺さった際の、標的との相対位置と向き */
    private double hitX_;
    private double hitY_;
    private double hitZ_;
    private float hitYaw_;
    private float hitPitch_;

	/** 移動スピード */
	private static final float SPEED = 1.75f;

	/** 当たり判定の大きさ */
	private static final double AMBIT = 0.75;
	
	/** パラメータ：標的 */
    private static final DataParameter<Integer> TARGET_ENTITY_ID = EntityDataManager.<Integer>createKey(EntityPhantomSwordEx.class, DataSerializers.VARINT);
	
	/** パラメータ：移動開始までのインターバル */
    private static final DataParameter<Integer> INTERVAL = EntityDataManager.<Integer>createKey(EntityPhantomSwordEx.class, DataSerializers.VARINT);

	/** パラメータ：初期ヨー */
    private static final DataParameter<Float> INI_YAW = EntityDataManager.<Float>createKey(EntityPhantomSwordEx.class, DataSerializers.FLOAT);

	/** パラメータ：初期ピッチ */
    private static final DataParameter<Float> INI_PITCH = EntityDataManager.<Float>createKey(EntityPhantomSwordEx.class, DataSerializers.FLOAT);

    /**
     * コンストラクタ
	 *
     * @param worldIn ワールド
     */
	public EntityPhantomSwordEx(World worldIn)
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
	public EntityPhantomSwordEx(World worldIn,
								EntityLivingBase thrower,
								float attackLevel)
	{
		super(worldIn, thrower, attackLevel, 90.0f);
		setInitialPosition();
	}

	/**
	 * デフォルトでの初期表示位置
	 */
	private void setInitialPosition()
	{
        setSize(0.5f, 0.5f);

		final float dist = 2.0f;

		double r = (rand.nextDouble() - 0.5) * 2.0;

		double yaw =  Math.toRadians(-thrower_.rotationYaw + 90.0);

		double x = dist * r * Math.sin(yaw);
		double y = dist * (1.0 - Math.abs(r));
		double z = dist * r * Math.cos(yaw);

		setLocationAndAngles(thrower_.posX + x,
							 thrower_.posY + y,
							 thrower_.posZ + z,
							 thrower_.rotationYaw,
							 thrower_.rotationPitch);

		iniYaw_ = thrower_.rotationYaw;
		iniPitch_ = thrower_.rotationPitch;

		setDriveVector(true);
	}
  
    /**
     * エンティティの初期化処理
     */
	@Override
	protected void entityInit()
	{
		super.entityInit();

		EntityDataManager manager = getDataManager();
        manager.register(TARGET_ENTITY_ID, 0);
		manager.register(INTERVAL, 7);
		manager.register(INI_YAW, 0.0f);
		manager.register(INI_PITCH, -720.0f);
	}

	/**
	 * 標的のエンティティID
	 */
    public final int getTargetEntityId()
	{
        return this.getDataManager().get(TARGET_ENTITY_ID);
    }

	/**
	 * 標的のエンティティID
	 */
    public final void setTargetEntityId(int entityid)
	{
        this.getDataManager().set(TARGET_ENTITY_ID,entityid);
    }
	
	/**
	 * インターバル
	 */
	public final int getInterval()
	{
		return getDataManager().get(INTERVAL);
	}
  
	/**
	 * インターバル
	 */
	public final void setInterval(int value)
	{
		getDataManager().set(INTERVAL, value);
	}

	/**
	 * 初期ヨー
	 */
	public final float getIniYaw()
	{
		return getDataManager().get(INI_YAW);
	}
  
	/**
	 * 初期ヨー
	 */
	public final void setIniYaw(float value)
	{
		getDataManager().set(INI_YAW, value);
	}
  
	/**
	 * 初期ピッチ
	 */
	public final float getIniPitch()
	{
		return getDataManager().get(INI_PITCH);
	}
  
	/**
	 * 初期ピッチ
	 */
	public final void setIniPitch(float value)
	{
		getDataManager().set(INI_PITCH, value);
	}

	/**
	 * 何かに刺さっているか.
	 * @return true=刺さっている
	 */
	protected final boolean isStuck()
	{
		return stuckEntity_ != null;
	}

	/**
	 * 標的を取得
	 *
	 * @return 標的。設定されていなければ null
	 */
	protected final Entity getTargetEntity()
	{
		int targetid = getTargetEntityId();
		if (targetid == 0)
			return null;
				
		return this.world.getEntityByID(targetid);
	}

    private void setDriveVector(boolean init)
	{
		setDriveVector(iniYaw_, iniPitch_, SPEED, init);
	}
	
	
	/**
	 * 更新処理
	 */
    @Override
	public void onUpdate()
	{
		if (isStuck()) {
			updateRidden();
			return;
		}

		// ===== 当たり判定 =====
		AxisAlignedBB bb = new AxisAlignedBB(this.posX - AMBIT,
											 this.posY - AMBIT,
											 this.posZ - AMBIT,
											 this.posX + AMBIT,
											 this.posY + AMBIT,
											 this.posZ + AMBIT);
				
		if (intercept(bb, true))	// 射撃物の迎撃
			return;


		Entity target = getNearestHitEntity(bb);
		if (target != null) {
			attackToEntity(target);

		} else if (!world.getCollisionBoxes(this, getEntityBoundingBox()).isEmpty()) {
			// 障害物に当たった？
			setDead();
			return;
		}

		updatePosition();

		if (this.ticksExisted >= getLifeTime())
			setDead();
	}

	/**
	 * 標的に刺さった後の更新処理
	 */
    @Override
	public void updateRidden()
	{
		// ※ updateRiddenでやる意味はあるのか？
		
		final Entity entity = stuckEntity_;
		if (entity == null)
			return;
    
		if (entity.isDead) {
			setDead();
			return;
		}

		updatePositionBaseStuckEntity();		

		if (this.ticksExisted >= getLifeTime()) {
			StylishRankManager.setNextAttackType(thrower_, StylishRankManager.AttackTypes.BreakPhantomSword);
			onImpact(entity, attackLevel_*0.5f);
			setDead();
		}
	}

	/**
	 * 刺さっている相手の位置と向きに合わせて移動する。
	 */
	private void updatePositionBaseStuckEntity()
	{
		final Entity entity = stuckEntity_;

		double r = Math.toRadians(entity.rotationYaw);
		double x = entity.posX + hitX_*Math.cos(r) - hitZ_*Math.sin(r);
		double y = entity.posY + hitY_;
		double z = entity.posZ + hitX_*Math.sin(r) + hitZ_*Math.cos(r);
		// entity.pos* と hit* が Vec3dだったら、こんな↓感じ
		// entityPos.add(HitPos.rotateYaw(Math.toRadians(-entity.rotationYaw)))

		float pitch = entity.rotationPitch + hitPitch_;
		float yaw = entity.rotationYaw + hitYaw_;
		
		setLocationAndAngles(x, y, z, pitch % 360.0f, yaw % 360.0f);
	}

	/**
	 * 移動.
	 */
	private void updatePosition()
	{
		this.lastTickPosX = this.posX;
		this.lastTickPosY = this.posY;
		this.lastTickPosZ = this.posZ;
      
		if (getInterval() < this.ticksExisted) {
			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
		} else {
			doTargeting();
		}
		setPosition(this.posX, this.posY, this.posZ);
	}

	/**
	 * 標的に向きを向ける
	 */
	private void doTargeting()
	{
		Entity target = getTargetEntity();
		if (target == null)
			return;

		if (Float.isNaN(iniPitch_)) {
			if (getIniPitch() < -700.0f) {
				iniYaw_ = thrower_.rotationYaw;
				iniPitch_ = thrower_.rotationPitch;
			} else {
				iniYaw_ = getIniYaw();
				iniPitch_ = getIniPitch();
			}
		}
		faceEntity(this, target, ticksExisted, ticksExisted);
		setDriveVector(false);
	}

    private void faceEntity(Entity viewer, Entity target, float yawStep, float pitchStep)
    {
        double x = target.posX - viewer.posX;
        double z = target.posZ - viewer.posZ;
        double y = - (viewer.posY + viewer.getEyeHeight());

        if (target instanceof EntityLivingBase) {
            EntityLivingBase e = (EntityLivingBase)target;
            y += e.posY + e.getEyeHeight();
        } else {
            AxisAlignedBB bb = target.getEntityBoundingBox();
            y += (bb.minY + bb.maxY) / 2.0;
        }

        double d3 = Math.sqrt(x * x + z * z);
        float yaw = (float)Math.toDegrees(Math.atan2(z, x)) - 90.0f;
		float pitch = -(float)Math.toDegrees(Math.atan2(y, d3));

        iniPitch_ = updateRotation(iniPitch_, pitch, pitchStep);
        iniYaw_   = updateRotation(iniYaw_, yaw, yawStep);
    }
	
    private float updateRotation(float par1, float par2, float par3)
    {
		return par2;
//        return par1 + MathHelper.clamp(MathHelper.wrapDegrees(par2 - par1), -par3, par3);
    }

	/**
	 * 攻撃.
	 *
	 * @param target 標的
	 */
	protected void attackToEntity(Entity target)
	{
		StylishRankManager.setNextAttackType(thrower_, StylishRankManager.AttackTypes.PhantomSword);
		onImpact(target, attackLevel_);

		stickEntity(target);
	}

	/**
	 * 最も近くに居る敵を探す。
	 *
	 * @param bb 当たり判定
	 * @return 近くの敵
	 */
	private Entity getNearestHitEntity(AxisAlignedBB bb)
	{
		List<Entity> list = world.getEntitiesInAABBexcluding(thrower_, bb, EntitySelectorAttackable.getInstance());

		list.removeAll(alreadyHitEntity_);

		Entity target = getTargetEntity();
		if (target != null &&
			target.isEntityAlive() &&
			target.getEntityBoundingBox().intersectsWith(bb)) {

			list.add(target);
		}

		alreadyHitEntity_.addAll(list);
      
		double d0 = 10.0;
		Entity hitEntity = null;
		for (Entity entity : list) {
			if (!entity.canBeCollidedWith())
				continue;

			double d1 = entity.getDistanceToEntity(this);
			if (d1 < d0 /* || d0 == 0.0*/) {
				hitEntity = entity;
				d0 = d1;
			}
		}
		return hitEntity;
	}

	/**
	 * 攻撃が当たった処理.
	 *
	 * @param target 当たった対象
	 * @param damage 与えるダメージ
	 * @return true=刀を持って生体を攻撃した場合
	 */
	@Override
	protected boolean onImpact(Entity target, float damage)
	{
		if (super.onImpact(target, Math.max(1.0f, damage))) {
			target.motionX = 0.0;
			target.motionY = 0.0;
			target.motionZ = 0.0;
			target.addVelocity(0.0, 0.1, 0.0);
			return true;
		}
		return false;
	}

	/**
	 * 標的に刺さった.
	 * @param target 標的
	 */
    protected void stickEntity(Entity target)
	{
        if (target == null)
			return;

		hitYaw_		= this.rotationYaw - target.rotationYaw;
		hitPitch_	= this.rotationPitch - target.rotationPitch;
		hitX_		= this.posX - target.posX;
		hitY_		= this.posY - target.posY;
		hitZ_		= this.posZ - target.posZ;

		stuckEntity_ = target;

		this.ticksExisted = Math.max(0, getLifeTime() - 20);
	}
}

