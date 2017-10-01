package slashblade.addonpack.named;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.ShapedOreRecipe;
import slashblade.addonpack.AddonPack;

/**
 * 利刀「蒼梅」
 */
public class Blue
{
	/** 刀の登録名 */
	static public final String NAME = "flammpfeil.slashblade.named.cs2.blue";

	/** レシピ/実績登録名 */
	static private final String KEY = "cs2.blue";

	/**
	 * 刀の登録.
	 */
	public static void registBlade()
	{
		ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
		NBTTagCompound tag = new NBTTagCompound();
		blade.setTagCompound(tag);

		ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
		ItemSlashBladeNamed.CustomMaxDamage.set(tag, 65);
		ItemSlashBlade.setBaseAttackModifier(tag, 6);

		ItemSlashBlade.TextureName.set(tag, "named/cs2/template");
		ItemSlashBlade.ModelName.set(tag, "named/muramasa/muramasa");
		ItemSlashBlade.SpecialAttackType.set(tag, 1);
		ItemSlashBlade.StandbyRenderType.set(tag, 2);

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

		AddonPack.addRecipe(KEY,
							new ShapedOreRecipe(
								AddonPack.RecipeGroup,
								target,
								"LCS",
								"CS ",
								"BI ",
								'L', new ItemStack(Items.DYE, 1, 4),	//Lapis Lazuli
								'C', Blocks.COAL_BLOCK,
								'S', sphere,
								'B', Items.STICK,
								'I', Items.STRING)
			);
	}
}
