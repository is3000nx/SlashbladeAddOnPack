package slashblade.addonpack.named;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slashblade.addonpack.AddonPack;
import slashblade.addonpack.util.Config;

/**
 * 「似蛭」系の五振り
 */
public class Nihil
{
	/** 刀の登録名： 妖刀「似蛭」 */
	public static final String NAME_NIHIL = "flammpfeil.slashblade.named.nihil";
	/** 刀の登録名： 血刀「似蛭」 */
	public static final String NAME_EX = "flammpfeil.slashblade.named.nihilex";
	/** 刀の登録名： 獄刀「似蛭」 */
	public static final String NAME_UL = "flammpfeil.slashblade.named.nihilul";
	/** 刀の登録名： 煉獄刀「死念」 */
	public static final String NAME_BX = "flammpfeil.slashblade.named.nihilbx";
	/** 刀の登録名： 妖刀「紅桜」 */
	public static final String NAME_CC = "flammpfeil.slashblade.named.crimsoncherry";

	/** レシピ/実績登録名： 妖刀「似蛭」 */
	public static final String KEY_NIHIL = "nihil";
	/** レシピ/実績登録名： 血刀「似蛭」 */
	public static final String KEY_EX = "nihilex";
	/** レシピ/実績登録名： 獄刀「似蛭」 */
	public static final String KEY_UL = "nihilul";
	/** レシピ/実績登録名： 煉獄刀「死念」 */
	public static final String KEY_BX = "nihilbx";
	/** レシピ/実績登録名： 妖刀「紅桜」 */
	public static final String KEY_CC = "crimsoncherry";
	
	/**
	 * 刀の登録名：閻魔刀.
	 *
	 * mods.flammpfeil.slashblade.named.Yamato.nameTrue と同じもの
	 */
	static final String NAME_YAMATO = "flammpfeil.slashblade.named.yamato";

	/**
	 * 刀の登録.
	 */
	public static void registBlade()
	{
		// ===== 妖刀「似蛭」 =====
		{
			String NAME = NAME_NIHIL;

			ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
			NBTTagCompound tag = new NBTTagCompound();
			blade.setTagCompound(tag);
    
			ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
			ItemSlashBladeNamed.CustomMaxDamage.set(tag, 45);
			ItemSlashBlade.setBaseAttackModifier(tag, 8.0f);
			ItemSlashBlade.TextureName.set(tag, "named/nihil/nihil");
			ItemSlashBlade.ModelName.set(tag, "named/nihil/nihil");
			ItemSlashBlade.SpecialAttackType.set(tag, 1);
			ItemSlashBlade.StandbyRenderType.set(tag, 1);
			ItemSlashBlade.SummonedSwordColor.set(tag, 0xcc0000);
			ItemSlashBladeNamed.IsDefaultBewitched.set(tag, true);
    
			blade.addEnchantment(Enchantments.UNBREAKING, 3);
			blade.addEnchantment(Enchantments.SHARPNESS, 2);
			blade.addEnchantment(Enchantments.SMITE, 1);
			blade.addEnchantment(Enchantments.FIRE_ASPECT, 1);
    
			SlashBlade.registerCustomItemStack(NAME, blade);
			ItemSlashBladeNamed.NamedBlades.add(NAME);
		}

		// ===== 血刀「似蛭」 =====
		{
			String NAME = NAME_EX;

			ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
			NBTTagCompound tag = new NBTTagCompound();
			blade.setTagCompound(tag);
    
			ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
			ItemSlashBladeNamed.CustomMaxDamage.set(tag, 60);
			ItemSlashBlade.setBaseAttackModifier(tag, 10.0f);
			ItemSlashBlade.TextureName.set(tag, "named/nihil/nihilex");
			ItemSlashBlade.ModelName.set(tag, "named/nihil/nihilex");
			ItemSlashBlade.SpecialAttackType.set(tag, 2);
			ItemSlashBlade.StandbyRenderType.set(tag, 1);
			ItemSlashBlade.SummonedSwordColor.set(tag, 0xcc0000);
			ItemSlashBladeNamed.IsDefaultBewitched.set(tag, true);
    
			blade.addEnchantment(Enchantments.UNBREAKING, 2);
			blade.addEnchantment(Enchantments.SHARPNESS, 3);
			blade.addEnchantment(Enchantments.SMITE, 2);
			blade.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1);
			blade.addEnchantment(Enchantments.FIRE_ASPECT, 2);
			blade.addEnchantment(Enchantments.LOOTING, 1);
    
			SlashBlade.registerCustomItemStack(NAME, blade);
			ItemSlashBladeNamed.NamedBlades.add(NAME);
		}

		// ===== 獄刀「似蛭」 =====
		{
			String NAME = NAME_UL;

			ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
			NBTTagCompound tag = new NBTTagCompound();
			blade.setTagCompound(tag);
    
			ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
			ItemSlashBladeNamed.CustomMaxDamage.set(tag, 70);
			ItemSlashBlade.setBaseAttackModifier(tag, 12.0f);
			ItemSlashBlade.TextureName.set(tag, "named/nihil/nihilul");
			ItemSlashBlade.ModelName.set(tag, "named/nihil/nihilex");
			ItemSlashBlade.SpecialAttackType.set(tag, 2);
			ItemSlashBlade.StandbyRenderType.set(tag, 1);
			ItemSlashBlade.SummonedSwordColor.set(tag, 0xcc0000);
			ItemSlashBladeNamed.IsDefaultBewitched.set(tag, true);
    
			blade.addEnchantment(Enchantments.UNBREAKING, 3);
			blade.addEnchantment(Enchantments.SHARPNESS, 5);
			blade.addEnchantment(Enchantments.SMITE, 3);
			blade.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 2);
			blade.addEnchantment(Enchantments.FIRE_ASPECT, 2);
			blade.addEnchantment(Enchantments.LOOTING, 3);
    
			SlashBlade.registerCustomItemStack(NAME, blade);
			ItemSlashBladeNamed.NamedBlades.add(NAME);
		}


		// ===== 妖刀「紅桜」 =====
		{
			String NAME = NAME_CC;

			ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
			NBTTagCompound tag = new NBTTagCompound();
			blade.setTagCompound(tag);
    
			ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
			ItemSlashBladeNamed.CustomMaxDamage.set(tag, 65);
			ItemSlashBlade.setBaseAttackModifier(tag, 11.0f);
			ItemSlashBlade.TextureName.set(tag, "named/nihil/crimsoncherry");
			ItemSlashBlade.ModelName.set(tag, "named/nihil/cc");
			ItemSlashBlade.SpecialAttackType.set(tag, 7);
			ItemSlashBlade.StandbyRenderType.set(tag, 1);
			ItemSlashBlade.SummonedSwordColor.set(tag, 0xcc0000);
			ItemSlashBladeNamed.IsDefaultBewitched.set(tag, true);
    
			blade.addEnchantment(Enchantments.SHARPNESS, 5);
			blade.addEnchantment(Enchantments.SMITE, 3);
			blade.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 3);
			blade.addEnchantment(Enchantments.FIRE_ASPECT, 2);
    
			SlashBlade.registerCustomItemStack(NAME, blade);
			ItemSlashBladeNamed.NamedBlades.add(NAME);
		}

		// ===== 煉獄刀「死念」 =====
		{
			String NAME = NAME_BX;

			ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
			NBTTagCompound tag = new NBTTagCompound();
			blade.setTagCompound(tag);
    
			ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
			ItemSlashBladeNamed.CustomMaxDamage.set(tag, 240);
			ItemSlashBlade.setBaseAttackModifier(tag, 13.0f);
			ItemSlashBlade.TextureName.set(tag, "named/nihil/nihil_bx");
			ItemSlashBlade.ModelName.set(tag, "named/nihil/nihil_bx");
			ItemSlashBlade.SpecialAttackType.set(tag, 7);
			ItemSlashBlade.StandbyRenderType.set(tag, 1);
			ItemSlashBlade.SummonedSwordColor.set(tag, 0xcc0000);
			ItemSlashBladeNamed.IsDefaultBewitched.set(tag, true);
    
			blade.addEnchantment(Enchantments.SHARPNESS, 5);
			blade.addEnchantment(Enchantments.SMITE, 5);
			blade.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 5);
			blade.addEnchantment(Enchantments.FIRE_ASPECT, 2);
			blade.addEnchantment(Enchantments.UNBREAKING, 3);
    
			SlashBlade.registerCustomItemStack(NAME, blade);
			ItemSlashBladeNamed.NamedBlades.add(NAME);
		}

	}

	/**
	 * レシピの登録.
	 */
	public static void registRecipe()
	{
        ItemStack ingot = SlashBlade.findItemStack(SlashBlade.modid, SlashBlade.IngotBladeSoulStr, 1);
        ItemStack sphere = SlashBlade.findItemStack(SlashBlade.modid, SlashBlade.SphereBladeSoulStr, 1);

		// ===== 妖刀「似蛭」 =====
		{
			String NAME = NAME_NIHIL;
			String KEY = KEY_NIHIL;

			ItemStack target = SlashBlade.getCustomBlade(NAME);

			ItemStack required = new ItemStack(SlashBlade.weapon);
    
			AddonPack.addRecipe(
				KEY,
				new RecipeAwakeBlade(
					AddonPack.RecipeGroup,
					target,
					required,
					"SIS",
					"IBI",
					"SIS", 
					'S', sphere, 
					'I', ingot, 
					'B', required)
				);
		}

		// ===== 血刀「似蛭」 =====
		{
			String NAME = NAME_EX;
			String KEY = KEY_EX;

			ItemStack target = SlashBlade.getCustomBlade(NAME);

			ItemStack required = SlashBlade.getCustomBlade(NAME_NIHIL);

			setRequiredCount(required, 1000, 1000, 1);

			if (Config.isRegistRequiredBlade()) {
				String reqiredStr = NAME + ".reqired";
				SlashBlade.registerCustomItemStack(reqiredStr, required);
				ItemSlashBladeNamed.NamedBlades.add(reqiredStr);
			}
			
			AddonPack.addRecipe(
				KEY,
				new RecipeAwakeBlade(
					AddonPack.RecipeGroup,
					target,
					required,
					"SNS",
					"IBI",
					"SDS", 
					'S', sphere, 
					'I', ingot, 
					'B', required, 
					'N', Items.NETHER_STAR, 
					'D', Blocks.DIAMOND_BLOCK)
				);
		}


		// ===== 獄刀「似蛭」 =====
		{
			String NAME = NAME_UL;
			String KEY = KEY_UL;
		
			ItemStack target = SlashBlade.getCustomBlade(NAME);

			ItemStack required = SlashBlade.getCustomBlade(NAME_EX);
			ItemStack yamato = SlashBlade.getCustomBlade(NAME_YAMATO);
			ItemStack slashblade = new ItemStack(SlashBlade.weapon);

			setRequiredCount(required, 3000, 6500, 3);

			if (Config.isRegistRequiredBlade()) {
				String reqiredStr = NAME + ".reqired";
				SlashBlade.registerCustomItemStack(reqiredStr, required);
				ItemSlashBladeNamed.NamedBlades.add(reqiredStr);
			}

			AddonPack.addRecipe(
				KEY,
				new RecipeExtraNihilBlade(
					AddonPack.RecipeGroup,
					target,
					required, 1, 1,
					yamato, 1, 2, true,
					"SNS",
					"DBD",
					"SYS",
					'S', slashblade,
					'Y', yamato,
					'B', required,
					'N', Items.NETHER_STAR, 
					'D', Blocks.DIAMOND_BLOCK)
				);
		}

		// ===== 妖刀「紅桜」 =====
		{
			String NAME = NAME_CC;
			String KEY = KEY_CC;

			ItemStack target = SlashBlade.getCustomBlade(NAME);

			ItemStack requiredMain = SlashBlade.getCustomBlade(NAME_EX);
			ItemStack requiredSub = SlashBlade.getCustomBlade(NAME_NIHIL);

			setRequiredCount(requiredMain, 3000, 6500, 3);

			if (Config.isRegistRequiredBlade()) {
				String reqiredStr = NAME + ".reqired";
				SlashBlade.registerCustomItemStack(reqiredStr, requiredMain);
				ItemSlashBladeNamed.NamedBlades.add(reqiredStr);
			}

			AddonPack.addRecipe(
				KEY,
				new RecipeExtraNihilBlade(
					AddonPack.RecipeGroup,
					target,
					requiredMain, 1, 1,
					requiredSub, 1, 0, false,
					"DSD",
					"DMD",
					"DDD",
					'S', requiredSub,
					'M', requiredMain,
					'D', Blocks.DIAMOND_BLOCK)
				);

		}

		// ===== 煉獄刀「死念」 =====
		{
			String NAME = NAME_BX;
			String KEY = KEY_BX;

			ItemStack target = SlashBlade.getCustomBlade(NAME);

			ItemStack requiredMain = SlashBlade.getCustomBlade(NAME_UL);
			ItemStack requiredSub = SlashBlade.getCustomBlade(NAME_CC);
			ItemStack sb = new ItemStack(SlashBlade.weapon);

			setRequiredCount(requiredMain, 0, 0, 0);
			
			AddonPack.addRecipe(
				KEY,
				new RecipeExtraNihilBlade(
					AddonPack.RecipeGroup,
					target,
					requiredMain, 0, 1,
					requiredSub, 2, 1, false,
					"DDD",
					"ACB",
					"DDD",
					'A', requiredMain,
					'B', requiredSub,
					'C', sb,
					'D', Blocks.DIAMOND_BLOCK)
				);
		}
	}

	/**
	 * クラフトに必要な各カウント数を設定する.
	 *
	 * @param blade 素材となる対象の刀
	 * @param kill クラフトに必要なKillCount
	 * @param ps クラフトに必要なProudSoul
	 * @param repair クラフトに必要なRefine
	 */
	static private void setRequiredCount(ItemStack blade, int kill, int ps, int repair)
	{
		NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(blade);
		ItemSlashBlade.RepairCount.set(tag, repair);
		ItemSlashBlade.KillCount.set(tag, kill);
		ItemSlashBlade.ProudSoul.set(tag, ps);
	}
}
