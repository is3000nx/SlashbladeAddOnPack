package slashblade.addonpack.named;

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
import static slashblade.addonpack.AddonPack.ID_None;
import static slashblade.addonpack.AddonPack.ID_AquaEdge;
import static slashblade.addonpack.AddonPack.ID_FlareSpiral;
import static slashblade.addonpack.AddonPack.ID_LightningSwords;

/**
 * 神威刀
 */
public class Kamuy
{
	/** 刀の登録名： 神威刀「クトネシリカ」*/
	public static final String NAME_BASE = "flammpfeil.slashblade.named.kamuy.base";
	/** 刀の登録名： 神威刀「ワッカ」*/
	public static final String NAME_WATER = "flammpfeil.slashblade.named.kamuy.water";
	/** 刀の登録名： 神威刀「アペ」 */
	public static final String NAME_FIRE = "flammpfeil.slashblade.named.kamuy.fire";
	/** 刀の登録名： 神威刀「カンナ」*/
	public static final String NAME_LIGHTNING = "flammpfeil.slashblade.named.kamuy.lightning";

	/** レシピ/実績登録名： 神威刀「クトネシリカ」 */
	public static final String KEY_BASE = "kamuy.base";
	/** レシピ/実績登録名： 神威刀「ワッカ」 */
	public static final String KEY_WATER = "kamuy.water";
	/** レシピ/実績登録名： 神威刀「アペ」 */
	public static final String KEY_FIRE = "kamuy.fire";
	/** レシピ/実績登録名： 神威刀「カンナ」 */
	public static final String KEY_LIGHTNING = "kamuy.lightning";

	/**
	 * 刀の登録.
	 */
	public static void registBlade()
	{
		// ===== 神威刀「クトネシリカ」 =====
		{
			String NAME = NAME_BASE;

			ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
			NBTTagCompound tag = new NBTTagCompound();
			blade.setTagCompound(tag);

			ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
			ItemSlashBladeNamed.CustomMaxDamage.set(tag, 60);
			ItemSlashBlade.setBaseAttackModifier(tag, 4 + Item.ToolMaterial.DIAMOND.getDamageVsEntity());

			ItemSlashBlade.TextureName.set(tag, "named/kamuy/kamuy");
			ItemSlashBlade.ModelName.set(tag, "named/kamuy/kamuy");
			ItemSlashBlade.SpecialAttackType.set(tag, ID_None);
			ItemSlashBlade.StandbyRenderType.set(tag, 2);
			ItemSlashBlade.SummonedSwordColor.set(tag, 0x999896);
			ItemSlashBladeNamed.IsDefaultBewitched.set(tag, true);

			blade.addEnchantment(Enchantments.LOOTING, 1);

			SlashBlade.registerCustomItemStack(NAME, blade);
			ItemSlashBladeNamed.NamedBlades.add(NAME);
		}

		// ===== 神威刀「ワッカ」 =====
		{
			String NAME = NAME_WATER;

			ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
			NBTTagCompound tag = new NBTTagCompound();
			blade.setTagCompound(tag);

			ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
			ItemSlashBladeNamed.CustomMaxDamage.set(tag, 60);
			ItemSlashBlade.setBaseAttackModifier(tag, 4 + Item.ToolMaterial.DIAMOND.getDamageVsEntity());

			ItemSlashBlade.TextureName.set(tag, "named/kamuy/water");
			ItemSlashBlade.ModelName.set(tag, "named/kamuy/kamuy");
			ItemSlashBlade.SpecialAttackType.set(tag, ID_AquaEdge);
			ItemSlashBlade.StandbyRenderType.set(tag, 2);
			ItemSlashBlade.SummonedSwordColor.set(tag, 0x0b3e5e);
			ItemSlashBladeNamed.IsDefaultBewitched.set(tag, true);

			blade.addEnchantment(Enchantments.UNBREAKING, 3);
			blade.addEnchantment(Enchantments.LOOTING, 3);
			blade.addEnchantment(Enchantments.KNOCKBACK, 2);
			blade.addEnchantment(Enchantments.RESPIRATION, 1);

			SlashBlade.registerCustomItemStack(NAME, blade);
			ItemSlashBladeNamed.NamedBlades.add(NAME);
		}

		// ===== 神威刀「アペ」 =====
		{
			String NAME = NAME_FIRE;

			ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
			NBTTagCompound tag = new NBTTagCompound();
			blade.setTagCompound(tag);

			ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
			ItemSlashBladeNamed.CustomMaxDamage.set(tag, 60);
			ItemSlashBlade.setBaseAttackModifier(tag, 4 + Item.ToolMaterial.DIAMOND.getDamageVsEntity());

			ItemSlashBlade.TextureName.set(tag, "named/kamuy/fire");
			ItemSlashBlade.ModelName.set(tag, "named/kamuy/kamuy");
			ItemSlashBlade.SpecialAttackType.set(tag, ID_FlareSpiral);
			ItemSlashBlade.StandbyRenderType.set(tag, 2);
			ItemSlashBlade.SummonedSwordColor.set(tag, 0x7a1225);
			ItemSlashBladeNamed.IsDefaultBewitched.set(tag, true);

			blade.addEnchantment(Enchantments.UNBREAKING, 3);
			blade.addEnchantment(Enchantments.FIRE_ASPECT, 2);
			blade.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 5);
			blade.addEnchantment(Enchantments.FIRE_PROTECTION, 1);

			SlashBlade.registerCustomItemStack(NAME, blade);
			ItemSlashBladeNamed.NamedBlades.add(NAME);
		}		

		// ===== 神威刀「カンナ」 =====
		{
			String NAME = NAME_LIGHTNING;

			ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
			NBTTagCompound tag = new NBTTagCompound();
			blade.setTagCompound(tag);

			ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
			ItemSlashBladeNamed.CustomMaxDamage.set(tag, 60);
			ItemSlashBlade.setBaseAttackModifier(tag, 4 + Item.ToolMaterial.DIAMOND.getDamageVsEntity());

			ItemSlashBlade.TextureName.set(tag, "named/kamuy/lightning");
			ItemSlashBlade.ModelName.set(tag, "named/kamuy/kamuy");
			ItemSlashBlade.SpecialAttackType.set(tag, ID_LightningSwords);
			ItemSlashBlade.StandbyRenderType.set(tag, 2);
			ItemSlashBlade.SummonedSwordColor.set(tag, 0xd59246);
			ItemSlashBladeNamed.IsDefaultBewitched.set(tag, true);

			blade.addEnchantment(Enchantments.UNBREAKING, 3);
			blade.addEnchantment(Enchantments.SMITE, 5);
			blade.addEnchantment(Enchantments.FEATHER_FALLING, 4);

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

		// ===== 神威刀「クトネシリカ」 =====
		{
			String NAME = NAME_BASE;
			String KEY = KEY_BASE;

			ItemStack target = SlashBlade.getCustomBlade(NAME);

			ItemStack required = new ItemStack(SlashBlade.weapon);
			NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(required);
			ItemSlashBlade.RepairCount.set(tag, 5);
			ItemSlashBlade.KillCount.set(tag, 1000);
			ItemSlashBlade.ProudSoul.set(tag, 1000);
			required.addEnchantment(Enchantments.LOOTING, 1);

			SlashBlade.registerCustomItemStack(NAME+".reqired", required.copy());
			ItemSlashBladeNamed.NamedBlades.add(NAME+".reqired");

			required.setItemDamage(OreDictionary.WILDCARD_VALUE);
			
			SlashBlade.addRecipe(
				KEY,
				new RecipeAwakeBlade(
					target,
					required,
					"SQS",
					"IKI",
					"SBS",
					'S', sphere,
					'K', required,
					'Q', Items.QUARTZ,
					'I', Blocks.IRON_BLOCK,
					'B', Items.BOOK)
				);
		}

		// 残り3振りで共通の素材刀
		ItemStack required = SlashBlade.getCustomBlade(NAME_BASE);
		NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(required);
		ItemSlashBlade.RepairCount.set(tag, 20);
		ItemSlashBlade.KillCount.set(tag, 2000);
		ItemSlashBlade.ProudSoul.set(tag, 5000);

		SlashBlade.registerCustomItemStack(NAME_BASE+".base.reqired", required.copy());
		ItemSlashBladeNamed.NamedBlades.add(NAME_BASE+".base.reqired");

		required.setItemDamage(OreDictionary.WILDCARD_VALUE);
		
		// ===== 神威刀「ワッカ」 =====
		{
			String NAME = NAME_WATER;
			String KEY = KEY_WATER;

			ItemStack target = SlashBlade.getCustomBlade(NAME);
			
			SlashBlade.addRecipe(
				KEY,
				new RecipeAwakeBlade(
					target,
					required,
					"S8S",
					"4K6",
					"S2S",
					'S', sphere,
					'K', required,
					'8', Blocks.LAPIS_BLOCK,
					'4', Blocks.ICE,
					'6', Blocks.SNOW,
					'2', Items.WATER_BUCKET)
				);
		}

		// ===== 神威刀「アペ」 =====
		{
			String NAME = NAME_FIRE;
			String KEY = KEY_FIRE;

			ItemStack target = SlashBlade.getCustomBlade(NAME);
			
			SlashBlade.addRecipe(
				KEY,
				new RecipeAwakeBlade(
					target,
					required,
					"S8S",
					"4K6",
					"S2S",
					'S', sphere,
					'K', required,
					'8', Blocks.REDSTONE_BLOCK,
					'4', Items.FIRE_CHARGE,
					'6', Items.BLAZE_ROD,
					'2', Items.LAVA_BUCKET)
				);
		}

		// ===== 神威刀「カンナ」 =====
		{
			String NAME = NAME_LIGHTNING;
			String KEY = KEY_LIGHTNING;

			ItemStack target = SlashBlade.getCustomBlade(NAME);
			
			SlashBlade.addRecipe(
				KEY,
				new RecipeAwakeBlade(
					target,
					required,
					"S8S",
					"4K6",
					"S2S",
					'S', sphere,
					'K', required,
					'8', Blocks.IRON_BLOCK,
					'4', Blocks.GOLD_BLOCK,
					'6', Blocks.DIAMOND_BLOCK,
					'2', Blocks.EMERALD_BLOCK)
				);
		}
		
	}

	/**
	 * 実績登録.
	 */
	public static void registAchievement()
	{
	}
}
