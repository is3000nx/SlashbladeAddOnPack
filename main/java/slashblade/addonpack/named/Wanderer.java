package slashblade.addonpack.named;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.Doutanuki;
import mods.flammpfeil.slashblade.stats.AchievementList;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.Achievement;
import net.minecraftforge.oredict.OreDictionary;
import mods.flammpfeil.slashblade.TagPropertyAccessor;
import static slashblade.addonpack.AddonPack.ID_RapidPhantomSwords;

/**
 * 風来の劒
 */
public class Wanderer
{
	/** 刀の登録名： 風来の劒 */
	static public final String NAME = "flammpfeil.slashblade.named.wanderer";

	/** 刀の登録名： 風来の機劒 */
	static public final String NAME_RF = "flammpfeil.slashblade.named.wanderer.rfblade";
	
	/** レシピ/実績登録名： 風来の劒 */
	static private final String KEY = "wanderer";

	/** レシピ/実績登録名： 風来の機劒 */
	static private final String KEY_RF = "wanderer.rfblade";

	/**
	 * 風来の劒.
	 */
	public static void registBlade()
	{
		ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
		NBTTagCompound tag = new NBTTagCompound();
		blade.setTagCompound(tag);

		ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
		ItemSlashBladeNamed.CustomMaxDamage.set(tag, 60);
		ItemSlashBlade.setBaseAttackModifier(tag, 4 + Item.ToolMaterial.DIAMOND.getDamageVsEntity());

		ItemSlashBlade.TextureName.set(tag, "named/wanderer/wanderer");
		ItemSlashBlade.ModelName.set(tag, "named/wanderer/wanderer");

		ItemSlashBlade.SpecialAttackType.set(tag, ID_RapidPhantomSwords);
		ItemSlashBlade.StandbyRenderType.set(tag, 1);

		SlashBlade.registerCustomItemStack(NAME, blade);
		ItemSlashBladeNamed.NamedBlades.add(NAME);

		// =====

		/*
        blade = new ItemStack(
			rfBlade,	//  GameRegistry.findItem("flammpfeil.slashblade.murasamablade", "RFBlade")
			1,
			0);
		tag = tag.copy();
		x.setTagCompound(tag);
		ItemSlashBladeNamed.CurrentItemName.set(tag, NAME_RF);

		SlashBlade.registerCustomItemStack(NAME_RF, blade);
		ItemSlashBladeNamed.NamedBlades.add(NAME_RF);
		*/

	}

	/**
	 * レシピの登録.
	 */
	public static void registRecipe()
	{
		ItemStack target = SlashBlade.getCustomBlade(NAME);

		ItemStack required = SlashBlade.getCustomBlade(Doutanuki.namedou);
		NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(required);
		ItemSlashBlade.RepairCount.set(tag, 10);
		required.setItemDamage(OreDictionary.WILDCARD_VALUE);

		ItemStack ingot = SlashBlade.findItemStack(SlashBlade.modid,SlashBlade.IngotBladeSoulStr,1);


		SlashBlade.addRecipe(KEY,
							 new RecipeAwakeBlade(
								 target,
								 required,
								 "  I",
								 "QI ",
								 "BC ",
								 'B', required,
								 'Q', Items.QUARTZ,
								 'I', ingot,
								 'C', Items.CLOCK
								 )
			);

		// @todo rfblade
/*		
		ItemStack target = SlashBlade.getCustomBlade(NAME_RF);
		ItemStack required = SlashBlade.getCustomBlade(NAME);
		reqiredBlade.setItemDamage(OreDictionary.WILDCARD_VALUE);

        ItemStack fpnmCore = SlashBlade.findItemStack(SlashBlade.modid, "flammpfeil.slashblade.custommaterial.fpnmcore", 1);
		

		SlashBlade.addRecipe(KEY_RF,
							 new RecipeAwakeBladeRf(
								 target,
								 required,
								 "  I",
								 "QI ",
								 "BC ",
								 'B', reqiredBlade,
								 'Q', Items.QUARTZ,
								 'I', Items.REDSTONE,
								 'C', fpnmCore)
			);

		GameRegistry.addRecipe(new RecipeResetUser(
								   target,
								   target,
								   "S", "B", "I",
								   'I', new ItemStack(Blocks.QUARTZ_BLOCK, 1, 1), // 模様付き
								   'B', target,
								   'S', SlashBlade.proudSoul));
*/
	}
	
	/**
	 * 実績登録.
	 */
	public static void registAchievement()
	{
		{
			ItemStack blade = SlashBlade.getCustomBlade(NAME);
			Achievement achievement = AchievementList.registerCraftingAchievement(KEY, blade, null);
			AchievementList.setContent(achievement, KEY);
		}

/*		
		{
			ItemStack blade = SlashBlade.getCustomBlade(NAME_RF);
			Achievement achievement = AchievementList.registerCraftingAchievement(KEY_RF, blade, null);
			AchievementList.setContent(achievement, KEY_RF);
		}
*/
		
	}


	public static final TagPropertyAccessor.TagPropertyBoolean IsFPNCore = new TagPropertyAccessor.TagPropertyBoolean("IsFPNCore");
	
	public static final TagPropertyAccessor.TagPropertyString Username = new TagPropertyAccessor.TagPropertyString("Username");
	

}


