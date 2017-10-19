package slashblade.addonpack;

import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.event.LoadEvent;
import mods.flammpfeil.slashblade.specialeffect.ISpecialEffect;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import slashblade.addonpack.ability.*;
import slashblade.addonpack.entity.*;
import slashblade.addonpack.named.*;
import slashblade.addonpack.specialattack.*;
import slashblade.addonpack.specialeffect.*;

/**
 * 追加刀剣 詰め合わせ
 */
@Mod(name = AddonPack.modname,
	 modid = AddonPack.modid,
	 version = AddonPack.version,
	 dependencies="required-after:" + SlashBlade.modid)
public class AddonPack
{
	public static final String modname = "Slashblade-AddonPack";
	public static final String modid = "slashblade.addonpack";
	public static final String version = "mc1.12.2-r5";

	public static final ResourceLocation RecipeGroup = new ResourceLocation(SlashBlade.modid,"addon_pack");

	/**
	 * 刀の登録名：「千鶴」村正
	 *
	 * mods.flammpfeil.slashblade.named.Tizuru.name と同じもの
	 */
	public static final String NAME_TIZURU = "flammpfeil.slashblade.named.muramasa";

	/** SAのID： 急襲幻影剣 */
	public static final int ID_RapidPhantomSwords = 30;
	/** SAのID： 旋刃 */
	public static final int ID_SpiralEdge = 31;
	/** SAのID： 烈風幻影剣 */
	public static final int ID_GalePhantomSwords = 32;
	/** SAのID： カムイノミ */
	public static final int ID_None = 35;
	/** SAのID： ルヤンペチュイェ */
	public static final int ID_AquaEdge = 36;
	/** SAのID： キムンリムセ */
	public static final int ID_FlareSpiral = 37;
	/** SAのID： ニシコトロアイ */
	public static final int ID_LightningSwords = 38;

	public static ISpecialEffect BurstDrive = SpecialEffects.register(new BurstDrive());
	
	/**
	 * 最初の初期化処理.
	 *
	 * イベントへの登録
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent evt)
	{
		registSpecialAttack();
		registSpecialEffect();
		
		InitProxy.proxy.initializeItemRenderer();
		
		SlashBlade.InitEventBus.register(this);
	}

	/**
	 * 初期化処理その１.
	 *
	 * 刀の登録
	 */
	@SubscribeEvent
	public void init(LoadEvent.InitEvent event)
	{
		registEntity();
		registAbility();
		// -----
		
		BladeMaster.registBlade();
		
		Yukari.registBlade();
		KnifeOfBE.registBlade();

		Laemmle.registBlade();

		DarkRaven.registBlade();
		SnowCrow.registBlade();

		Nihil.registBlade();

		Toyako.registBlade();
		FluorescentBar.registBlade();

		Wanderer.registBlade();

		FrostWolf.registBlade();

		Kamuy.registBlade();

		Kirisaya.registBlade();

		Blue.registBlade();

		Wa.registBlade();
	}

	/**
	 * 初期化処理その２.
	 *
	 * レシピ等の刀の入手手段の登録
	 */
	@SubscribeEvent
	public void postinit(LoadEvent.PostInitEvent event)
	{
		BladeMaster.registRecipe();

		Yukari.registRecipe();
		KnifeOfBE.registRecipe();

		Laemmle.registRecipe();

		DarkRaven.registRecipe();
		SnowCrow.registRecipe();
		
		Nihil.registRecipe();
		
		Toyako.registRecipe();
		FluorescentBar.registRecipe();

		Wanderer.registRecipe();

		FrostWolf.registRecipe();

		Kamuy.registRecipe();

		Kirisaya.registRecipe();

		Blue.registRecipe();

		Wa.registRecipe();
	}

	/**
	 * 追加エンティティの登録
	 */
	private void registEntity()
	{
		EntityRegistry.registerModEntity(
			new ResourceLocation(SlashBlade.modid, "PhantomSwordEx"),
			EntityPhantomSwordEx.class,
			"PhantomSwordEx",
			1,
			this,
			250,
			1,
			true);

		EntityRegistry.registerModEntity(
			new ResourceLocation(SlashBlade.modid, "LightningSword"),
			EntityLightningSword.class,
			"LightningSword",
			2,
			this,
			250,
			1,
			true);

		EntityRegistry.registerModEntity(
			new ResourceLocation(SlashBlade.modid, "NoFireLigntningBolt"),
			EntityNoFireLightningBolt.class,
			"NoFireLigntningBolt",
			3,
			this,
			250,
			1,
			true);
		EntityRegistry.registerModEntity(
			new ResourceLocation(SlashBlade.modid, "EntityDriveEx"),
			EntityDriveEx.class,
			"EntityDriveEx",
			4,
			this,
			250,
			1,
			true);

		EntityRegistry.registerModEntity(
			new ResourceLocation(SlashBlade.modid, "EntityAquaEdge"),
			EntityAquaEdge.class,
			"EntityAquaEdge",
			5,
			this,
			250,
			1,
			true);

		EntityRegistry.registerModEntity(
			new ResourceLocation(SlashBlade.modid, "EntityFlareEdge"),
			EntityFlareEdge.class,
			"EntityFlareEdge",
			6,
			this,
			250,
			1,
			true);
	}

	/**
	 * 追加SAの登録
	 */
	private void registSpecialAttack()
	{
		ItemSlashBlade.specialAttacks.put(ID_RapidPhantomSwords, new RapidPhantomSwords());
		ItemSlashBlade.specialAttacks.put(ID_SpiralEdge, new SpiralEdge());
		ItemSlashBlade.specialAttacks.put(ID_GalePhantomSwords, new GalePhantomSwords());

		ItemSlashBlade.specialAttacks.put(ID_None, new None());
		ItemSlashBlade.specialAttacks.put(ID_AquaEdge, new AquaEdge());
		ItemSlashBlade.specialAttacks.put(ID_FlareSpiral, new FlareSpiral());
		ItemSlashBlade.specialAttacks.put(ID_LightningSwords, new LightningSwords());
	}

	/**
	 * 追加エフェクトの登録
	 */
	private void registSpecialEffect()
	{
	}

	/**
	 * 追加アビリティの登録
	 */
	private void registAbility()
	{
        MinecraftForge.EVENT_BUS.register(new EnderTeleportCanceller());
	}

	/**
	 * レシピの登録（補助）.
	 *
	 * @param key SlashBlade管理のレシピ登録キー
	 * @param name Minecraft管理のレシピ登録名
	 * @param recipe レシピ
	 */
	public static void addRecipe(String key, String name, IRecipe recipe)
	{
		recipe.setRegistryName(new ResourceLocation(SlashBlade.modid, name));
		SlashBlade.addRecipe(key, recipe);
	}

	/**
	 * レシピの登録（補助）.
	 *
	 * @param key レシピ登録キー
	 * @param recipe レシピ
	 */
	public static void addRecipe(String key, IRecipe recipe)
	{
		addRecipe(key, key, recipe);
	}
	
}
