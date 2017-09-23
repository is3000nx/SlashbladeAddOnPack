package slashblade.addonpack.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorDestructable;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IThrowableEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slashblade.addonpack.util.Math2;

/**
 * SAで発射されるエンティティの共通部分
 */
abstract class EntityBase extends Entity implements IThrowableEntity
{
	/**
	 * 撃った人
	 */
    protected EntityLivingBase thrower_ = null;

	/**
	 * 持ってた刀
	 */
    protected ItemStack blade_ = ItemStack.EMPTY;

    /**
	 * 既に当たり判定を済ませたエンティティ.
     * 多段Hit防止用
     */
    protected List<Entity> alreadyHitEntity_ = new ArrayList<Entity>();

	/**
	 * 攻撃レベル
	 */
    protected float attackLevel_ = 0.0f;


	/** パラメータ：寿命 */
    private static final DataParameter<Integer> LIFETIME = EntityDataManager.<Integer>createKey(EntityBase.class, DataSerializers.VARINT);
	
	/** パラメータ：ロール */
    private static final DataParameter<Float> ROLL = EntityDataManager.<Float>createKey(EntityBase.class, DataSerializers.FLOAT);

	/** パラメータ：色 */
    private static final DataParameter<Integer> COLOR = EntityDataManager.<Integer>createKey(EntityBase.class, DataSerializers.VARINT);
	
    /**
     * コンストラクタ
	 *
     * @param worldIn ワールド
     */
    public EntityBase(World worldIn)
    {
        super(worldIn);

		// ※
		// 自分では使わないけど、
		// このコンストラクタを用意しておかないと、
		// どこかの初期化処理でエラーになる。
    }

	/**
	 * コンストラクタ
	 *
	 * @param worldIn ワールド
	 * @param thrower 撃った人
	 * @param attackLevel 攻撃レベル
	 */
    public EntityBase(World worldIn,
					  EntityLivingBase thrower,
					  float attackLevel)
	{
        super(worldIn);

        this.ticksExisted = 0;

        this.thrower_ = thrower;
        this.attackLevel_ = attackLevel;

        this.blade_ = thrower.getHeldItem(EnumHand.MAIN_HAND);
        if (!blade_.isEmpty() && !(blade_.getItem() instanceof ItemSlashBlade))
            blade_ = ItemStack.EMPTY;

        // 撃った人と、撃った人が（に）乗ってるEntityは当たり判定から除外
        alreadyHitEntity_.add(thrower);
        alreadyHitEntity_.add(thrower.getRidingEntity());
        alreadyHitEntity_.addAll(thrower.getPassengers());
    }

    /**
     * エンティティの初期化処理.
	 *
	 * DataManager で管理する変数の登録処理
     */
    @Override
    protected void entityInit()
	{
		EntityDataManager manager = getDataManager();
        manager.register(ROLL, 0.0f);
        manager.register(LIFETIME, 20);
		manager.register(COLOR, 0x3333ff);
    }

	/**
	 * エンティティの初期位置を設定する.
	 *
	 * エンティティのインスタンスを作成後は
	 * 必ずコレで初期化すること。
	 *
	 * @param x 位置(X座標)
	 * @param y 位置(Y座標)
	 * @param z 位置(Z座標)
	 * @param yaw 向き(ヨー)(単位：度)
	 * @param pitch 向き(ピッチ)(単位：度)
	 * @param roll 傾き(ロール)(単位：度)
	 * @param speed 移動速度(移動方向はエンティティの向きと同じ)
	 */
	public void setInitialPosition(double x, double y, double z,
								   float yaw, float pitch, float roll,
								   float speed)
	{
        this.prevPosX = this.lastTickPosX = x;
        this.prevPosY = this.lastTickPosY = y;
        this.prevPosZ = this.lastTickPosZ = z;

        this.prevRotationYaw   = this.rotationYaw   = MathHelper.wrapDegrees(-yaw);
        this.prevRotationPitch = this.rotationPitch = MathHelper.wrapDegrees(-pitch);
		setRoll(roll);

		setMotionToForward(speed);

		setPosition(x, y, z);
	}


    /**
     * x,y,z軸それぞれの移動量を設定する。
	 *
	 * 移動する方向は、エンティティの向いている方向。
	 * そのため、事前に rotationYaw, rotationPitch は
	 * 設定しておくこと。
	 *
	 * @param speed 移動スピード
     */
    protected void setMotionToForward(float speed)
    {
        this.motionX = Math2.sin(rotationYaw)*Math2.cos(rotationPitch)*speed;
        this.motionY = Math2.sin(rotationPitch)*speed;
        this.motionZ = Math2.cos(rotationYaw)*Math2.cos(rotationPitch)*speed;
    }

	/**
	 * 他エンティティに攻撃(?)が通った後の処理.
	 *
	 * @param target 標的
	 * @param damage ダメージ
	 * @return true=刀を持って生体を攻撃した場合
	 */
	protected boolean onImpact(Entity target, float damage)
	{
		return onImpact(target, damage, "directMagic");
	}

	/**
	 * 他エンティティに攻撃(?)が通った後の処理.
	 *
	 * @param target 標的
	 * @param damage ダメージ
	 * @param source 攻撃方法
	 * @return true=刀を持って生体を攻撃した場合
	 */
	protected boolean onImpact(Entity target, float damage, String source)
	{
		DamageSource ds = new EntityDamageSource(source, thrower_)
			.setDamageBypassesArmor()
			.setMagicDamage();
		
		return onImpact(target, damage, ds);
	}

	/**
	 * 他エンティティに攻撃(?)が通った後の処理.
	 *
	 * @param target 標的
	 * @param damage ダメージ
	 * @param ds 攻撃方法
	 * @return true=刀を持って生体を攻撃した場合
	 */
	protected boolean onImpact(Entity target, float damage, DamageSource ds)
	{
		target.hurtResistantTime = 0;
		target.attackEntityFrom(ds, damage);
		
		if (blade_.isEmpty() || !(target instanceof EntityLivingBase))
			return false;

		blade_.getItem().hitEntity(blade_,
								   (EntityLivingBase)target,
								   thrower_);
		return true;
	}
	
	/**
	 * 飛翔物の迎撃.
	 *
	 * @param area 当たり判定のある領域
	 * @param fragile true=迎撃したら自分も壊れる場合
	 * @return true=迎撃して壊れた
	 */
	protected boolean intercept(AxisAlignedBB area, boolean fragile)
	{
		// 迎撃対象物
		List<Entity> list = world.getEntitiesInAABBexcluding(thrower_, area, EntitySelectorDestructable.getInstance());

		list.removeAll(alreadyHitEntity_);
		alreadyHitEntity_.addAll(list);
        
		for (Entity target : list) {
			if (!isDestruction(target))
				continue;

			target.motionX = 0.0;
			target.motionY = 0.0;
			target.motionZ = 0.0;
			target.setDead();

			spawnExplodeParticle(target);

			StylishRankManager.setNextAttackType(thrower_, StylishRankManager.AttackTypes.DestructObject);
			StylishRankManager.doAttack(thrower_);

			if (fragile) {
				setDead();
				return true;
			}
		}
		return false;
	}

	/**
	 * 迎撃可能か判定する.
	 * 自分自身が撃ったもの以外を可とする。
	 *
	 * @param target 対象物
	 * @return true=破壊可
	 */
	private boolean isDestruction(Entity target)
	{
		if (target instanceof EntityArrow)
			return !isSameThrower(((EntityArrow)target).shootingEntity);
		if (target instanceof IThrowableEntity)
			return !isSameThrower(((IThrowableEntity)target).getThrower());
		if (target instanceof EntityThrowable)
			return !isSameThrower(((EntityThrowable)target).getThrower());

		if (target instanceof EntityFireball) {
			if (isSameThrower(((EntityFireball)target).shootingEntity)) {
				return false;
			} else {
				return !target.attackEntityFrom(
					DamageSource.causeMobDamage(thrower_), attackLevel_);
			}
		}
		
		// 多分、ここまで来ることは無い。
		return true;
	}

	/**
	 * 自分自身が撃ったものかどうか
	 *
	 * @param targetThrower 対象物を撃った人
	 * @return true=自分自身が撃った
	 */
	private boolean isSameThrower(Entity targetThrower)
	{
		return targetThrower != null &&
			targetThrower.getEntityId() == thrower_.getEntityId();
	}

	/**
	 * 指定物の周囲に破壊のパーティクルを発生させる.
	 *
	 * @param entity 対象物
	 */
	private void spawnExplodeParticle(Entity entity)
	{
		Random rand = this.rand;

		for (int var1 = 0; var1 < 10; var1++) {

			double xSpeed = rand.nextGaussian() * 0.02;
			double ySpeed = rand.nextGaussian() * 0.02;
			double zSpeed = rand.nextGaussian() * 0.02;

			double rx = rand.nextDouble();
			double ry = rand.nextDouble();
			double rz = rand.nextDouble();
			
			world.spawnParticle(
				EnumParticleTypes.EXPLOSION_NORMAL,
				entity.posX + (rx*2 - 1)*entity.width  - xSpeed * 10.0,
				entity.posY + (ry      )*entity.height - ySpeed * 10.0,
				entity.posZ + (rz*2 - 1)*entity.width  - zSpeed * 10.0,
				xSpeed, ySpeed, zSpeed);
		}
	}

	/**
	 * Endermanを消極的にする.
	 * @param entity 対象
	 */
	protected static void coolDownEnderman(EntityEnderman entity)
	{
		entity.setAttackTarget(null);

		for (EntityAITasks.EntityAITaskEntry task : entity.targetTasks.taskEntries) {
			if (task.priority == 1) {
				// プレイヤーのロックオンするためのAI.
				// ※ MCのバージョンが変わったら、ココは要確認
				task.action.resetTask();
			}
		}
	}

	// =====================================================
	
	/**
	 * ロール
	 */
    public final float getRoll()
	{
        return this.getDataManager().get(ROLL);
    }

	/**
	 * ロール
	 */
    public final void setRoll(float roll)
	{
        this.getDataManager().set(ROLL,roll);
    }

	/**
	 * 寿命
	 */
    public final int getLifeTime()
	{
        return this.getDataManager().get(LIFETIME);
    }

	/**
	 * 寿命
	 */
    public final void setLifeTime(int lifetime)
	{
        this.getDataManager().set(LIFETIME,lifetime);
    }

	/**
	 * 色
	 */
	public final int getColor()
	{
		return getDataManager().get(COLOR);
	}
  
	/**
	 * 色
	 */
	public final void setColor(int value)
	{
		getDataManager().set(COLOR, value);
	}
	
	/**
	 * 乱数
	 */
    public Random getRand()
    {
        return this.rand;
    }
	

	// =====================================================

    /**
     * 現在位置 起点の (x,y,z)の位置が、水などの中かどうか
     * @param x オフセット
	 * @param y オフセット
	 * @param z オフセット
	 * @return true=流体内部
     */
    @Override
    public boolean isOffsetPositionInLiquid(double x, double y, double z)
    {
		// 水などの影響は受けない
        return false;
    }

    /**
     * 指定の場所へ移動させる（可能なら）
	 *
	 * @param type 移動手段
     * @param x 移動先座標
	 * @param y 移動先座標
	 * @param y 移動先座標
     */
    @Override
    public void move(MoverType type, double x, double y, double z)
	{
		// 自分で動く以外は動かない
	}

    /**
     * 火によるダメージ処理
	 * @param amount ダメージ量
     */
    @Override
    protected void dealFireDamage(int amount)
	{
		// 火などの影響は受けない
	}

    /**
     * 水で流される処理
	 * @return true=水中
     */
    @Override
    public boolean handleWaterMovement()
    {
		// 水などの影響は受けない
        return false;
    }

    /**
     * 指定ブロックの内側にいるかどうか(重なっているか？ 窒息？)
	 * @param materialIn ブロックの素材(種類)
	 * @return true=内側
     */
    @Override
    public boolean isInsideOfMaterial(Material materialIn)
    {
		// 溶岩の影響は受けない
        return false;
    }

    /**
     * 溶岩の中かどうか（溶岩中として処理するかどうか）
	 * @return true=溶岩中
     */
    @Override
    public boolean isInLava()
	{
		// 溶岩の影響は受けない
        return false;
    }

    /**
     * エンティティの明るさ
     */
    @SideOnly(Side.CLIENT)
    @Override
    public int getBrightnessForRender()
    {
		// EntityXPOrb.getBrightnessForRender と同じ処理

        float f1 = 0.5f;

        int i = super.getBrightnessForRender();
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int)(f1 * 15.0f * 16.0f);

        if (j > 240)
            j = 240;

        return j | k << 16;
    }

    /**
     * エンティティの明るさ
     */
    @Override
    public float getBrightness()
    {
		// EntityPortalFX と同じらしいが？
		
        float f1 = super.getBrightness();

        float f2 = 0.9f;
        f2 = f2 * f2 * f2 * f2;
        return f1 * (1.0f - f2) + f2;

		// return MathHelper.clampedLerp(f1, 1.0, Math.pow(0.9, 4));
    }

    /**
     * NBTからの読み込み
     */
    @Override
    protected void readEntityFromNBT(NBTTagCompound compound)
	{
	}

    /**
     * NBTへの書き出し
     */
    @Override
    protected void writeEntityToNBT(NBTTagCompound compound)
	{
	}

    /**
     * ポータルに入ってテレポートするかどうかの処理、かな？
     */
    @Override
    public void setPortal(BlockPos pos)
	{
    }

    /**
     * エンティティが燃えているかどうか
     */
    @Override
    public boolean isBurning()
    {
		// 燃えない
        return false;
    }

    /**
     * 蜘蛛の巣に引っかかった時の処理
     */
    @Override
    public void setInWeb()
	{
		// 蜘蛛の巣の影響は受けない
	}

	/**
	 * 描画タイミングの制御、かな？
	 */
    @Override
    public boolean shouldRenderInPass(int pass)
    {
        return pass == 1;
    }

	// -----------------------------------------------------
	
	/**
	 * 撃った人
	 */
    @Override
    public Entity getThrower()
	{
        return this.thrower_;
    }

	/**
	 * 撃った人
	 */
    @Override
    public void setThrower(Entity entity)
	{
        this.thrower_ = (EntityLivingBase)entity;

		// EntityLivingBase 以外、指定しないように
		// 利用側で気をつける。
		// 間違えた場合に例外が投げられて気付けるように、
		// 条件判定はしない。
    }

	// =====================================================
}
