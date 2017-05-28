package slashblade.addonpack.named;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.stats.AchievementList;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.Achievement;
import static slashblade.addonpack.AddonPack.NAME_TIZURU;

/**
 * 錬金術師の太刀：幻魔練金拵
 */
public class Laemmle
{
	/** 刀の登録名 */
	static public final String NAME = "flammpfeil.slashblade.named.laemmle";

	/** レシピ/実績登録名 */
	static public final String KEY = "laemmle";

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

		ItemSlashBlade.TextureName.set(tag, "named/laemmle/lem");
		ItemSlashBlade.ModelName.set(tag, "named/laemmle/blade");
		ItemSlashBlade.StandbyRenderType.set(tag, 2);
		ItemSlashBlade.SummonedSwordColor.set(tag, 0xbbcbcb);
    
		blade.addEnchantment(Enchantments.SHARPNESS, 3);
    
		SlashBlade.registerCustomItemStack(NAME, blade);
		ItemSlashBladeNamed.NamedBlades.add(NAME);
	}
  
	/**
	 * レシピの登録.
	 */
	public static void registRecipe()
	{
		ItemStack target = SlashBlade.getCustomBlade(NAME);

		ItemStack required = SlashBlade.getCustomBlade(NAME_TIZURU);

		ItemStack potion = PotionUtils.addPotionToItemStack(
			new ItemStack(Items.POTIONITEM),
			PotionTypes.STRONG_STRENGTH);
		// ↑ 水入り瓶に魂片で作ったポーション
    
		SlashBlade.addRecipe(KEY,
							 new RecipeAwakeBlade(
								 target,
								 required,
								 "XGO",
								 "GBG",
								 "QGX",
								 'X', potion,
								 'G', Items.GOLD_INGOT,
								 'O', Blocks.OBSIDIAN,
								 'Q', Blocks.QUARTZ_BLOCK,
								 'B', required)
			);
	}

	/**
	 * 実績登録.
	 */
	public static void registAchievement()
	{
		ItemStack blade = SlashBlade.getCustomBlade(NAME);
		Achievement achievement = AchievementList.registerCraftingAchievement(KEY, blade, net.minecraft.stats.AchievementList.POTION);
		AchievementList.setContent(achievement, KEY);
	}
}
