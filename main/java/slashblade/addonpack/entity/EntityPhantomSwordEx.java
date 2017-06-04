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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import slashblade.addonpack.specialattack.PhantomSwordsBase;
import slashblade.addonpack.util.Math2;

/**
 * PhantomSword の拡張版.
 *
 * 固定値だったものをパラメータ化しただけだが、
 * いくつか処理を見直し。
 */
public class EntityPhantomSwordEx extends EntityBase
{
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
	private float hitBaseYaw_;	// 刺さった時の標的の向き

	private PhantomSwordsBase sa_ = null;

	/** 移動スピード */
	public static final float SPEED = 1.75f;

	/** 当たり判定の大きさ */
	private static final double AMBIT = 0.75;
	
	/** パラメータ：標的 */
    private static final DataParameter<Integer> TARGET_ENTITY_ID = EntityDataManager.<Integer>createKey(EntityPhantomSwordEx.class, DataSerializers.VARINT);
	
	/** パラメータ：移動開始までのインターバル */
    private static final DataParameter<Integer> INTERVAL = EntityDataManager.<Integer>createKey(EntityPhantomSwordEx.class, DataSerializers.VARINT);

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
	 * @param sa このエンティティを発生させたSA (指定しない(null)場合もある)
	 */
	public EntityPhantomSwordEx(World worldIn,
								EntityLivingBase thrower,
								float attackLevel,
								PhantomSwordsBase sa)
	{
		super(worldIn, thrower, attackLevel);
		this.sa_ = sa;
        setSize(0.5f, 0.5f);
	}
  
    /**
     * エンティティの初期化処理.
	 *
	 * DataManager で管理する変数の登録処理
     */
	@Override
	protected void entityInit()
	{
		super.entityInit();

		EntityDataManager manager = getDataManager();
        manager.register(TARGET_ENTITY_ID, 0);
		manager.register(INTERVAL, 7);
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
	 * 移動開始までのインターバル
	 */
	public final int getInterval()
	{
		return getDataManager().get(INTERVAL);
	}
  
	/**
	 * 移動開始までのインターバル
	 */
	public final void setInterval(int value)
	{
		getDataManager().set(INTERVAL, value);
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
			// 障害物に当たった
			setDead();
			return;
		}

		move();

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

		if (sa_ != null)
			sa_.onSticking(entity);

		updatePositionBaseStuckEntity();		

		if (this.ticksExisted >= getLifeTime()) {
			// 消滅する際に、もう1回ダメージを与える。
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

		float r = entity.rotationYaw - hitBaseYaw_;
		double x = entity.posX + hitX_*Math2.cos(r) - hitZ_*Math2.sin(r);
		double y = entity.posY + hitY_;
		double z = entity.posZ + hitX_*Math2.sin(r) + hitZ_*Math2.cos(r);

		float pitch = entity.rotationPitch + hitPitch_;
		float yaw = entity.rotationYaw + hitYaw_;
		
		setLocationAndAngles(x, y, z,
							 MathHelper.wrapDegrees(yaw),
							 MathHelper.wrapDegrees(pitch));
	}

	/**
	 * 移動.
	 *
	 * 最初は、標的の方向へ向きを変えるだけ。
	 * 一定時間経過後は、その方向へまっすぐ進む。
	 */
	private void move()
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
	 * 標的の方向へに向きを向ける
	 */
	private void doTargeting()
	{
		Entity target = getTargetEntity();
		if (target == null)
			return;

        final double dx = target.posX - posX;
        final double dz = target.posZ - posZ;
        double dy = - posY;

        if (target instanceof EntityLivingBase) {
            dy += target.posY + target.getEyeHeight()/2;
        } else {
            AxisAlignedBB bb = target.getEntityBoundingBox();
            dy += (bb.minY + bb.maxY) / 2.0;
        }

        double d = Math.sqrt(dx*dx + dz*dz);
        float yaw   = (float)Math.toDegrees(Math.atan2(dx, dz));
		float pitch = (float)Math.toDegrees(Math.atan2(dy, d));

		prevRotationYaw   = rotationYaw;
		prevRotationPitch = rotationPitch;

		rotationYaw   += MathHelper.clamp(yaw   - rotationYaw, -4.5f, 4.5f);
		rotationPitch += MathHelper.clamp(pitch - rotationPitch, -4.5f, 4.5f);
		// オリジナルでは
		// 「初期方向から ticksExisted度以内 の方向転換」 となっていたが
		// 「前回方向から 4.5度以内」と変えた。
		// （方向転換の速度を調整しやすい）

		setMotionToForward(SPEED);
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
	 * 当たり判定内にいる攻撃可能エンティティを取得する.
	 *
	 * 複数居る場合は、一番距離が近いもの
	 *
	 * @param bb 当たり判定
	 * @return 標的
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
			if (sa_ != null) {
				sa_.onImpact(target);
			}
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
		hitBaseYaw_	= target.rotationYaw;

		stuckEntity_ = target;

		this.ticksExisted = Math.max(0, getLifeTime() - 20);
	}
}

