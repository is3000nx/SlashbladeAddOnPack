package slashblade.addonpack.named;

import net.minecraft.item.ItemStack;
import mods.flammpfeil.slashblade.SlashBlade;
import net.minecraft.nbt.NBTTagCompound;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import net.minecraft.init.Items;
import slashblade.addonpack.AddonPack;

/**
 * 和風MOD連携の「刀」「太刀」
 */
public class Wa
{
	/** 刀の登録名：刀 */
	static public final String NAME_A = "flammpfeil.slashblade.named.Wa.katana";
	/** テクスチャ名：刀 */
	static public final String TEXTURE_A = "named/wa/waA";
	/** レシピ/実績登録名：刀 */
	static private final String KEY_A = "Wa.katana";

	/** 刀の登録名：太刀 */
	static public final String NAME_B = "flammpfeil.slashblade.named.Wa.tachi";
	/** テクスチャ名：太刀 */
	static public final String TEXTURE_B = "named/wa/waB";
	/** レシピ/実績登録名：太刀 */
	static private final String KEY_B = "Wa.tachi";

	/**
	 * 刀の登録.
	 */
	public static void registBlade()
	{
		registBlade(NAME_A, TEXTURE_A);
		registBlade(NAME_B, TEXTURE_B);
	}

	/**
	 * 刀の登録.
	 * @param name 登録名
	 * @param texture テクスチャ名
	 */
	private static void registBlade(String name, String texture)
	{
        ItemStack blade = SlashBlade.findItemStack(SlashBlade.modid,
												   "slashbladeWrapper",
												   1);
		makeBlade(blade, name, texture);

        SlashBlade.registerCustomItemStack(name, blade);
        ItemSlashBladeNamed.NamedBlades.add(name);
	}

	/**
	 * レシピの登録.
	 */
	public static void registRecipe()
	{
		ItemStack ingot = SlashBlade.findItemStack(SlashBlade.modid, SlashBlade.IngotBladeSoulStr, 1);
		
			
		AddonPack.addRecipe(
			KEY_A,
			new RecipeWa(
				AddonPack.RecipeGroup,
				NAME_A,
				TEXTURE_A,
				"I S",
				"IW ",
				"B  ",
				'I', ingot,
				'S', SlashBlade.proudSoul,
				'W', SlashBlade.wrapBlade,
				'B', Items.STICK)
			);

		AddonPack.addRecipe(
			KEY_B,
			new RecipeWa(
				AddonPack.RecipeGroup,
				NAME_B,
				TEXTURE_B,
				"I S",
				"IW ",
				"BII",
				'I', ingot,
				'S', SlashBlade.proudSoul,
				'W', SlashBlade.wrapBlade,
				'B', Items.STICK)
			);
	}

	/**
	 * 鞘の設定を変更して刀を作成する
	 *
	 * @param scabbard 鞘
	 * @param name 刀の登録名
	 * @param texture テクスチャ
	 */
	static void makeBlade(ItemStack scabbard,
						  String name,
						  String texture)
	{
		// 鞘に刀を収める
		ItemStack sword = SlashBlade.findItemStack("minecraft", "wooden_sword", 1);
		SlashBlade.wrapBlade.removeWrapItem(scabbard);
		SlashBlade.wrapBlade.setWrapItem(scabbard, sword);

		// 鞘の設定を変える
		NBTTagCompound tag = scabbard.getTagCompound();
		ItemSlashBladeNamed.CurrentItemName.set(tag, name);
		ItemSlashBladeNamed.BaseAttackModifier.set(tag, 3.0f);
		ItemSlashBladeNamed.TextureName.set(tag, texture);
		ItemSlashBladeNamed.ModelName.set(tag, "named/muramasa/muramasa");
		
		scabbard.setStackDisplayName(scabbard.getDisplayName());
		
	}
	
}
