package slashblade.addonpack.named;

import mods.flammpfeil.slashblade.named.Doutanuki;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import slashblade.addonpack.AddonPack;
import slashblade.addonpack.util.Config;
import static slashblade.addonpack.AddonPack.NAME_TIZURU;
import static slashblade.addonpack.AddonPack.ID_RapidPhantomSwords;
import static slashblade.addonpack.AddonPack.ID_GalePhantomSwords;

/**
 * 凍桜刃「雪花蒼月」、明獣刃「陽牙氷狼」
 */
public class FrostWolf
{
	/** 刀の登録名： 凍桜刃「雪花蒼月」 */
	static public final String NAME_A = "flammpfeil.slashblade.named.frostwolfa";
	/** 刀の登録名： 明獣刃「陽牙氷狼」 */
	static public final String NAME_B = "flammpfeil.slashblade.named.frostwolfb";

	/** レシピ/実績登録名： 凍桜刃「雪花蒼月」 */
	static public final String KEY_A = "frostwolfa";
	/** レシピ/実績登録名： 明獣刃「陽牙氷狼」 */
	static public final String KEY_B = "frostwolfb";
	
	/**
	 * 刀の登録.
	 */
	public static void registBlade()
	{
		// ===== 凍桜刃「雪花蒼月」 =====
		{
			String NAME = NAME_A;

			ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
			NBTTagCompound tag = new NBTTagCompound();
			blade.setTagCompound(tag);

			ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
			ItemSlashBladeNamed.CustomMaxDamage.set(tag, 50);
			ItemSlashBlade.setBaseAttackModifier(tag, 4 + Item.ToolMaterial.DIAMOND.getAttackDamage());

			ItemSlashBlade.TextureName.set(tag, "named/frostwolf/frostwolfa");
			ItemSlashBlade.ModelName.set(tag, "named/frostwolf/frostwolfa");
			ItemSlashBlade.SpecialAttackType.set(tag, ID_RapidPhantomSwords);
			ItemSlashBlade.StandbyRenderType.set(tag, 2);
			ItemSlashBlade.SummonedSwordColor.set(tag, 0x3df1f8);

			SlashBlade.registerCustomItemStack(NAME, blade);
			ItemSlashBladeNamed.NamedBlades.add(NAME);
		}

		// ===== 明獣刃「陽牙氷狼」 =====
		{
			String NAME = NAME_B;

			ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
			NBTTagCompound tag = new NBTTagCompound();
			blade.setTagCompound(tag);

			ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
			ItemSlashBladeNamed.CustomMaxDamage.set(tag, 50);
			ItemSlashBlade.setBaseAttackModifier(tag, 4 + Item.ToolMaterial.DIAMOND.getAttackDamage());

			ItemSlashBlade.TextureName.set(tag, "named/frostwolf/frostwolfb");
			ItemSlashBlade.ModelName.set(tag, "named/frostwolf/frostwolfb");
			ItemSlashBlade.SpecialAttackType.set(tag, ID_GalePhantomSwords);
			ItemSlashBlade.StandbyRenderType.set(tag, 2);
			ItemSlashBlade.SummonedSwordColor.set(tag, 0x3df1f8);

			SlashBlade.registerCustomItemStack(NAME, blade);
			ItemSlashBladeNamed.NamedBlades.add(NAME);
		}

	}

	/**
	 * レシピの登録.
	 */
	public static void registRecipe()
	{

        ItemStack sphere = SlashBlade.findItemStack(SlashBlade.modid, SlashBlade.SphereBladeSoulStr, 1);
		
		// ===== 凍桜刃「雪花蒼月」 =====
		{
			String NAME = NAME_A;
			String KEY = KEY_A;
			
			ItemStack target = SlashBlade.getCustomBlade(NAME);

			ItemStack required = SlashBlade.getCustomBlade(Doutanuki.namedou);
			NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(required);
			ItemSlashBlade.RepairCount.set(tag, 10);
			required.addEnchantment(Enchantments.FIRE_PROTECTION, 1);

			if (Config.isRegistRequiredBlade()) {
				String reqiredStr = NAME + ".reqired";
				SlashBlade.registerCustomItemStack(reqiredStr, required);
				ItemSlashBladeNamed.NamedBlades.add(reqiredStr);
			}

			required.setItemDamage(OreDictionary.WILDCARD_VALUE);

			AddonPack.addRecipe(KEY,
								new RecipeAwakeBlade(
									AddonPack.RecipeGroup,
									target,
									required,
									" IL",
									"CS ",
									"BQ ",
									'L', Items.DYE,	// "dyeBlack"
									'S', sphere,
									'B', required,
									'Q', Items.QUARTZ,
									'I', Blocks.ICE,
									'C', Blocks.SNOW)
				);
		}

		// ===== 明獣刃「陽牙氷狼」 =====
		{
			String NAME = NAME_B;
			String KEY = KEY_B;
			
			ItemStack target = SlashBlade.getCustomBlade(NAME);

			ItemStack required = SlashBlade.getCustomBlade(NAME_TIZURU);
			NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(required);
			ItemSlashBlade.RepairCount.set(tag, 25);
			required.addEnchantment(Enchantments.FIRE_PROTECTION, 1);

			if (Config.isRegistRequiredBlade()) {
				String reqiredStr = NAME + ".reqired";
				SlashBlade.registerCustomItemStack(reqiredStr, required);
				ItemSlashBladeNamed.NamedBlades.add(reqiredStr);
			}
			
			required.setItemDamage(OreDictionary.WILDCARD_VALUE);

			AddonPack.addRecipe(KEY,
								new RecipeAwakeBlade(
									AddonPack.RecipeGroup,
									target,
									required,
									" IG",
									"CS ",
									"BQ ",
									'S', sphere,
									'G', Items.GOLD_NUGGET,
									'B', required,
									'Q', Items.QUARTZ,
									'I', Blocks.ICE,
									'C', Blocks.SNOW)
				);
		}
	}
}
