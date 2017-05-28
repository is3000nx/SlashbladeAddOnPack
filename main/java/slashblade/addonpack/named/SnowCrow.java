package slashblade.addonpack.named;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.Doutanuki;
import mods.flammpfeil.slashblade.stats.AchievementList;
import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.Achievement;

/**
 * ユキガラス
 */
public class SnowCrow
{
	/** 刀の登録名 */
	static public final String NAME = "flammpfeil.slashblade.named.snowcrow";

	/** レシピ/実績登録名 */
	static public final String KEY = "snowcrow";

	/**
	 * 刀の登録.
	 */
	public static void registBlade()
	{
		ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
		NBTTagCompound tag = new NBTTagCompound();
		blade.setTagCompound(tag);
    
		ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
		ItemSlashBladeNamed.CustomMaxDamage.set(tag, 60);
		ItemSlashBlade.setBaseAttackModifier(tag, 4 + Item.ToolMaterial.IRON.getDamageVsEntity());
		ItemSlashBlade.TextureName.set(tag, "named/darkraven/snowcrow");
		ItemSlashBlade.ModelName.set(tag, "named/darkraven/snowcrow");
		ItemSlashBlade.StandbyRenderType.set(tag, 2);
		ItemSlashBlade.SummonedSwordColor.set(tag, 0xedebc3);

		SlashBlade.registerCustomItemStack(NAME, blade);
		ItemSlashBladeNamed.NamedBlades.add(NAME);
	}

	/**
	 * レシピの登録.
	 */
	public static void registRecipe()
	{
		ItemStack target = SlashBlade.getCustomBlade(NAME);

		ItemStack required = SlashBlade.getCustomBlade(Doutanuki.namedou);

		SlashBlade.addRecipe(KEY,
							 new RecipeAwakeBlade(
								 target,
								 required,
								 " FQ",
								 "SQ ",
								 "B  ",
								 'Q', Blocks.QUARTZ_BLOCK,
								 'F', Items.FEATHER,
								 'S', Items.SNOWBALL,
								 'B', required)
			);
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
