package slashblade.addonpack;

import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.event.LoadEvent;
import mods.flammpfeil.slashblade.util.SlashBladeAchievementCreateEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.oredict.RecipeSorter;
import slashblade.addonpack.named.*;
import slashblade.addonpack.specialattack.*;
import slashblade.addonpack.ability.*;
import slashblade.addonpack.entity.*;
import net.minecraftforge.common.MinecraftForge;

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
	public static final String version = "mc1.11.2-r0";

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

		RecipeSorter.register("flammpfeil.slashblade:nihil:extra",
							  RecipeExtraNihilBlade.class,
							  RecipeSorter.Category.SHAPED,
							  "after:forge:shaped");

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
		
	}

	/**
	 * 初期化処理その３.
	 *
	 * 実績登録
	 */
	@SubscribeEvent
	public void onSlashBladeAchievementCreate(SlashBladeAchievementCreateEvent event)
	{
		BladeMaster.registAchievement();

		Yukari.registAchievement();
		KnifeOfBE.registAchievement();

		Laemmle.registAchievement();

		DarkRaven.registAchievement();
		SnowCrow.registAchievement();

		Nihil.registAchievement();

		Toyako.registAchievement();
		FluorescentBar.registAchievement();

		Wanderer.registAchievement();

		FrostWolf.registAchievement();
		
		Kamuy.registAchievement();
	}

	/**
	 * 追加エンティティの登録
	 */
	private void registEntity()
	{
/*		
		EntityRegistry.registerModEntity(
			new ResourceLocation(SlashBlade.modid, "PhantomSwordEx"),
			EntityPhantomSwordEx.class,
			"PhantomSwordEx",
			2,
			this,
			250,
			1,
			true);
*/

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
}
