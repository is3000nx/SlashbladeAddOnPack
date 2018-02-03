package slashblade.addonpack.named;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import slashblade.addonpack.AddonPack;

/**
 * 型月刀「空の境界」
 */
public class KnifeOfBE
{
	/** 刀の登録名 */
	static public final String NAME = "flammpfeil.slashblade.named.tboen";
  
	/** レシピ/実績登録名 */
	static private final String KEY = "tboen";
  
	/**
	 * 刀の登録.
	 */
	public static void registBlade()
	{
		ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
		NBTTagCompound tag = new NBTTagCompound();
		blade.setTagCompound(tag);
    
		ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
		ItemSlashBladeNamed.CustomMaxDamage.set(tag, 70);
		ItemSlashBlade.setBaseAttackModifier(tag, 4 + Item.ToolMaterial.DIAMOND.getAttackDamage());

		ItemSlashBlade.TextureName.set(tag, "named/tboen/texture");
		ItemSlashBlade.ModelName.set(tag, "named/tboen/model");
		ItemSlashBlade.SpecialAttackType.set(tag, 4);
		ItemSlashBlade.SummonedSwordColor.set(tag, 0xff8888);
		ItemSlashBlade.IsDestructable.set(tag, false);
		ItemSlashBlade.IsNoScabbard.set(tag, false);
		ItemSlashBlade.IsBroken.set(tag, false);
		ItemSlashBlade.IsSealed.set(tag, false);
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

		ItemStack soul = SlashBlade.findItemStack(SlashBlade.modid, SlashBlade.ProudSoulStr, 1);
		
		ItemStack required = SlashBlade.getCustomBlade("slashbladeWhite");
		NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(required);

		required.setItemDamage(OreDictionary.WILDCARD_VALUE);
    
		AddonPack.addRecipe(KEY,
							new RecipeAwakeBlade(
								AddonPack.RecipeGroup,
								target,
								required,
								"SSS",
								"SBS",
								"SSS",
								'S', soul,
								'B', required)
			);
	}
}
