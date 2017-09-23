package slashblade.addonpack.named;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slashblade.addonpack.AddonPack;

/**
 * 無神「刃無し」
 */
public class Kirisaya
{
	/** 刀の登録名 */
	static public final String NAME = "flammpfeil.slashblade.named.kirisaya";

	/** レシピ/実績登録名 */
	static private final String KEY = "kirisaya";

	/**
	 * 刀の登録.
	 */
	public static void registBlade()
	{
		ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
		NBTTagCompound tag = new NBTTagCompound();
		blade.setTagCompound(tag);

		ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
		ItemSlashBladeNamed.CustomMaxDamage.set(tag, 11);
		ItemSlashBlade.setBaseAttackModifier(tag, 3.0f);

		ItemSlashBlade.TextureName.set(tag, "named/kirisaya/kirisaya");
		ItemSlashBlade.ModelName.set(tag, "named/kirisaya/kirisaya");
		ItemSlashBlade.SpecialAttackType.set(tag, 6);
		ItemSlashBlade.StandbyRenderType.set(tag, 1);
		ItemSlashBladeNamed.IsDefaultBewitched.set(tag, true);

		blade.addEnchantment(Enchantments.POWER, 8);
		blade.addEnchantment(Enchantments.UNBREAKING, 10);
		blade.addEnchantment(Enchantments.INFINITY, 4);

        SpecialEffects.addEffect(blade, AddonPack.BurstDrive);
		
		SlashBlade.registerCustomItemStack(NAME, blade);
		ItemSlashBladeNamed.NamedBlades.add(NAME);
	}
  

	/**
	 * レシピの登録.
	 */
	public static void registRecipe()
	{
		ItemStack target = SlashBlade.getCustomBlade(NAME);

        ItemStack sphere = SlashBlade.findItemStack(SlashBlade.modid, SlashBlade.SphereBladeSoulStr, 1);
		ItemSlashBlade.SpecialAttackType.set(sphere.getTagCompound(), 0);

		ItemStack required = new ItemStack(SlashBlade.wrapBlade);
		NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(required);
		ItemSlashBlade.RepairCount.set(tag, 1);
		ItemSlashBlade.KillCount.set(tag, 1000);
		ItemSlashBlade.ProudSoul.set(tag, 20000);
		required.addEnchantment(Enchantments.SHARPNESS, 3);
		required.addEnchantment(Enchantments.POWER, 3);

		SlashBlade.addRecipe(KEY,
							 new RecipeKirisaya(
								 target,
								 required,
								 sphere,
								 "DGD",
								 "ZBZ",
								 "GDG",
								 'G', new ItemStack(Items.GOLDEN_APPLE, 1, 1),
								 'D', Items.RECORD_11,
								 'B', required,
								 'Z', sphere)
			);
	}
  
	/**
	 * 実績登録.
	 */
	public static void registAchievement()
	{
	}
}
