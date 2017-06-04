package slashblade.addonpack.named;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.event.DropEventHandler;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.stats.AchievementList;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * ヤミガラス
 */
public class DarkRaven
{
	/** 刀の登録名 */
	static public final String NAME = "flammpfeil.slashblade.named.darkraven";

	/** レシピ/実績登録名 */
	static public final String KEY = "darkraven";
	
	/**
	 * 刀の登録.
	 */
	public static void registBlade()
	{
		ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
		NBTTagCompound tag = new NBTTagCompound();
		blade.setTagCompound(tag);
    
		ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
		ItemSlashBladeNamed.CustomMaxDamage.set(tag, 80);
		ItemSlashBlade.setBaseAttackModifier(tag, 4 + Item.ToolMaterial.DIAMOND.getDamageVsEntity());
		ItemSlashBlade.TextureName.set(tag, "named/darkraven/darkraven");
		ItemSlashBlade.ModelName.set(tag, "named/darkraven/darkraven");
		ItemSlashBlade.SpecialAttackType.set(tag, 2);
		ItemSlashBlade.StandbyRenderType.set(tag, 2);
		ItemSlashBlade.SummonedSwordColor.set(tag, 0xa989d5);
    
		SlashBlade.registerCustomItemStack(NAME, blade);
		ItemSlashBladeNamed.NamedBlades.add(NAME);
	}

	/**
	 * レシピの登録.
	 *
	 * アイテムドロップ設定。
	 * 実績画面での表示用のダミーレシピ登録。
	 */
	public static void registRecipe()
	{
		ItemStack target = SlashBlade.getCustomBlade(NAME);
		
		float rateItemDrop;
		String nameDropEntity;
		if (Loader.isModLoaded("TwilightForest")) {
			rateItemDrop = getItemDropRateFromConfig();
			nameDropEntity = "TwilightForest.Forest Raven"; 
		} else {
			// << オリジナルからの改変点 >>
			// ・ Twilight Forest Mod がなければ、「コウモリ」がドロップ
			// ・ ドロップ率は 5% 固定
			rateItemDrop = 0.05f;
			nameDropEntity = "Bat"; 
		}
    
		DropEventHandler.registerEntityDrop(
			nameDropEntity,
			rateItemDrop,
			target);

		// ------------
		// 実績画面で表示するためのダミーのレシピの登録

		ItemStack egg = new ItemStack(Items.EGG);
    
		NBTTagCompound displayTag = new NBTTagCompound();
		egg.setTagInfo("display", displayTag);
		NBTTagList loreList = new NBTTagList();
		loreList.appendTag(new NBTTagString(nameDropEntity));
		loreList.appendTag(new NBTTagString(String.format("DropRate:%.2f%%", rateItemDrop * 100.0)));
		displayTag.setTag("Lore", loreList);

		SlashBlade.addRecipe(KEY,
							 new ShapedOreRecipe(
								 target,
								 "   ",
								 " E ",
								 "   ",
								 'E', egg),
							 true);
	}

	/**
	 * 設定ファイルからアイテムのドロップ率を取得する。
	 *
	 * @return ドロップ率
	 */
	private static float getItemDropRateFromConfig()
	{
		float fact = 0.05f;
		try {
			SlashBlade.mainConfiguration.load();
			Property prop = SlashBlade.mainConfiguration.get("general", "DarkRavenDropRate", fact);
			return (float)prop.getDouble();
		} finally {
			SlashBlade.mainConfiguration.save();
		}
	}
  

	/**
	 * 実績登録.
	 */
	public static void registAchievement()
	{
		ItemStack blade = SlashBlade.getCustomBlade(NAME);
		Achievement achievement = AchievementList.registerCraftingAchievement(KEY, blade, net.minecraft.stats.AchievementList.KILL_ENEMY);
		AchievementList.setContent(achievement, KEY);

	}
}