package slashblade.addonpack.named;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.ShapedOreRecipe;
import slashblade.addonpack.AddonPack;

/**
 * ケイコウトウ
 */
public class FluorescentBar
{
	/** 刀の登録名 */
	static public final String NAME = "flammpfeil.slashblade.named.fluorescentbar";

	/** レシピ/実績登録名 */
	static private final String KEY = "fluorescentbar";

	/**
	 * 刀の登録.
	 */
	public static void registBlade()
	{
		ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
		NBTTagCompound tag = new NBTTagCompound();
		blade.setTagCompound(tag);

		ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
		ItemSlashBladeNamed.CustomMaxDamage.set(tag, (int)Item.ToolMaterial.DIAMOND.getDamageVsEntity());
		ItemSlashBlade.setBaseAttackModifier(tag, 2.0f);

		ItemSlashBlade.TextureName.set(tag, "named/fluorescentbar/fluorescentbar");
		ItemSlashBlade.ModelName.set(tag, "named/fluorescentbar/fluorescentbar");
		ItemSlashBlade.SpecialAttackType.set(tag, 3);
		ItemSlashBlade.StandbyRenderType.set(tag, 2);
		ItemSlashBlade.SummonedSwordColor.set(tag, 0xffffff);

		blade.addEnchantment(Enchantments.UNBREAKING, 3);

		SlashBlade.registerCustomItemStack(NAME, blade);
		ItemSlashBladeNamed.NamedBlades.add(NAME);
	}

	/**
	 * レシピの登録.
	 */
	public static void registRecipe()
	{
		ItemStack target = SlashBlade.getCustomBlade(NAME);

		ItemStack tiny = SlashBlade.findItemStack(SlashBlade.modid,SlashBlade.TinyBladeSoulStr,1);

		AddonPack.addRecipe(KEY,
							new ShapedOreRecipe(
								AddonPack.RecipeGroup,
								target,
								" PS",
								"PGP",
								"SP ",
								'P', Items.PAPER,
								'G', Blocks.GLASS,
								'S', tiny)
			);
	}
}
