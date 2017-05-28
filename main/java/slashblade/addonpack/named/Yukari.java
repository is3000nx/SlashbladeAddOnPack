package slashblade.addonpack.named;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.Tukumo;
import mods.flammpfeil.slashblade.stats.AchievementList;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.Achievement;
import net.minecraftforge.oredict.OreDictionary;

/**
 * 結月刀「付喪-改」
 */
public class Yukari
{
	/** 刀の登録名 */
	static public final String NAME = "flammpfeil.slashblade.named.yukari";

	/** レシピ/実績登録名 */
	static private final String KEY = "yukari";

	/**
	 * 刀の登録.
	 */
	public static void registBlade()
	{
		ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
		NBTTagCompound tag = new NBTTagCompound();
		blade.setTagCompound(tag);

		ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
		ItemSlashBladeNamed.CustomMaxDamage.set(tag, 50);
		ItemSlashBlade.setBaseAttackModifier(tag, 4 + Item.ToolMaterial.DIAMOND.getDamageVsEntity());

		ItemSlashBlade.TextureName.set(tag, "named/yukari/texture");
		ItemSlashBlade.ModelName.set(tag, "named/yukari/model");
		ItemSlashBlade.SpecialAttackType.set(tag, 8);
		ItemSlashBlade.SummonedSwordColor.set(tag, 0xa248a3);
		ItemSlashBladeNamed.IsDefaultBewitched.set(tag, true);
		ItemSlashBlade.IsDestructable.set(tag, false);
		ItemSlashBlade.IsNoScabbard.set(tag, false);
		ItemSlashBlade.IsBroken.set(tag, false);
		ItemSlashBlade.IsSealed.set(tag, false);
		ItemSlashBlade.StandbyRenderType.set(tag, 2);

		blade.addEnchantment(Enchantments.POWER, 1);

		SlashBlade.registerCustomItemStack(NAME, blade);
		ItemSlashBladeNamed.NamedBlades.add(NAME);
	}

	/**
	 * レシピの登録.
	 */
	public static void registRecipe()
	{
		ItemStack target = SlashBlade.getCustomBlade(NAME);

		ItemStack ingot = SlashBlade.findItemStack(SlashBlade.modid,SlashBlade.IngotBladeSoulStr,1);
        ItemStack sphere = SlashBlade.findItemStack(SlashBlade.modid, SlashBlade.SphereBladeSoulStr, 1);

		ItemStack required = SlashBlade.getCustomBlade(Tukumo.YuzukiTukumo);
		NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(required);
		ItemSlashBlade.KillCount.set(tag, 1000);
		required.setItemDamage(OreDictionary.WILDCARD_VALUE);
		required.addEnchantment(Enchantments.FIRE_ASPECT, 1);

		SlashBlade.addRecipe(KEY,
							 new RecipeAwakeBlade(
								 target,
								 required,
								 "ISI",
								 "SBS",
								 "ISI",
								 'I', ingot,
								 'S', sphere,
								 'B', required)
			);
	}

	/**
	 * 実績登録.
	 */
	public static void registAchievement()
	{
		ItemStack blade = SlashBlade.getCustomBlade(NAME);
		Achievement achievement = AchievementList.registerCraftingAchievement(KEY, blade, null);
		AchievementList.setContent(achievement, KEY);
	}
}
