package slashblade.addonpack.named;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.Tukumo;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import static slashblade.addonpack.AddonPack.NAME_TIZURU;
import static slashblade.addonpack.AddonPack.ID_RapidPhantomSwords;
import static slashblade.addonpack.AddonPack.ID_SpiralEdge;
import static slashblade.addonpack.AddonPack.ID_GalePhantomSwords;

/**
 * 
 */
public class BladeMaster
{
	/** 刀の登録名： 聖幸刀「緑乃霧」*/
	public static final String NAME_GREEN = "flammpfeil.slashblade.named.blademaster.greenmist";
	/** 刀の登録名： 侍月刀「炎水薄斬」 */
	public static final String NAME_AQUA = "flammpfeil.slashblade.named.blademaster.aquablaze";
	/** 刀の登録名： 月光桜「吹雪一閃」 */
	public static final String NAME_MOON = "flammpfeil.slashblade.named.blademaster.moonlightcherry";

	/** レシピ/実績登録名： 聖幸刀「緑乃霧」*/
	public static final String KEY_GREEN = "blademaster.greenmist";
	/** レシピ/実績登録名： 侍月刀「炎水薄斬」 */
	public static final String KEY_AQUA = "blademaster.aquablaze";
	/** レシピ/実績登録名： 月光桜「吹雪一閃」 */
	public static final String KEY_MOON = "blademaster.moonlightcherry";

	/**
	 * 刀の登録.
	 */
	public static void registBlade()
	{
		// ===== 聖幸刀「緑乃霧」 =====
		{
			String NAME = NAME_GREEN;

			ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
			NBTTagCompound tag = new NBTTagCompound();
			blade.setTagCompound(tag);

			ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
			ItemSlashBladeNamed.CustomMaxDamage.set(tag, 60);
			ItemSlashBlade.setBaseAttackModifier(tag, 4 + Item.ToolMaterial.DIAMOND.getDamageVsEntity());

			ItemSlashBlade.TextureName.set(tag, "named/blademaster/greenmist");
			ItemSlashBlade.ModelName.set(tag, "named/blademaster/blademaster");
			ItemSlashBlade.SpecialAttackType.set(tag, ID_RapidPhantomSwords);
			ItemSlashBlade.StandbyRenderType.set(tag, 2);
			ItemSlashBladeNamed.IsDefaultBewitched.set(tag, true);
			ItemSlashBlade.SummonedSwordColor.set(tag, 0x71d971);

			blade.addEnchantment(Enchantments.POWER, 3);
			blade.addEnchantment(Enchantments.FORTUNE, 3);

			SlashBlade.registerCustomItemStack(NAME, blade);
			ItemSlashBladeNamed.NamedBlades.add(NAME);
		}

		// ===== 侍月刀「炎水薄斬」 =====
		{
			String NAME = NAME_AQUA;

			ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
			NBTTagCompound tag = new NBTTagCompound();
			blade.setTagCompound(tag);

			ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
			ItemSlashBladeNamed.CustomMaxDamage.set(tag, 60);
			ItemSlashBlade.setBaseAttackModifier(tag, 4 + Item.ToolMaterial.DIAMOND.getDamageVsEntity());

			ItemSlashBlade.TextureName.set(tag, "named/blademaster/aquablaze");
			ItemSlashBlade.ModelName.set(tag, "named/blademaster/blademaster");
			ItemSlashBlade.SpecialAttackType.set(tag, ID_SpiralEdge);
			ItemSlashBlade.StandbyRenderType.set(tag, 2);
			ItemSlashBladeNamed.IsDefaultBewitched.set(tag, true);
			ItemSlashBlade.SummonedSwordColor.set(tag, 0xef7a5a);

			blade.addEnchantment(Enchantments.FIRE_PROTECTION, 1);
			blade.addEnchantment(Enchantments.FIRE_ASPECT, 2);

			SlashBlade.registerCustomItemStack(NAME, blade);
			ItemSlashBladeNamed.NamedBlades.add(NAME);
		}

		// ===== 月光桜「吹雪一閃」 =====
		{
			String NAME = NAME_MOON;

			ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
			NBTTagCompound tag = new NBTTagCompound();
			blade.setTagCompound(tag);

			ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
			ItemSlashBladeNamed.CustomMaxDamage.set(tag, 60);
			ItemSlashBlade.setBaseAttackModifier(tag, 4 + Item.ToolMaterial.DIAMOND.getDamageVsEntity());

			ItemSlashBlade.TextureName.set(tag, "named/blademaster/moonlightcherry");
			ItemSlashBlade.ModelName.set(tag, "named/blademaster/blademaster");
			ItemSlashBlade.SpecialAttackType.set(tag, ID_GalePhantomSwords);
			ItemSlashBlade.StandbyRenderType.set(tag, 2);
			ItemSlashBladeNamed.IsDefaultBewitched.set(tag, true);
			ItemSlashBlade.SummonedSwordColor.set(tag, 0xdda4cb);

			blade.addEnchantment(Enchantments.THORNS, 1);
			blade.addEnchantment(Enchantments.SMITE, 5);

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

		ItemStack sakura = SlashBlade.findItemStack("BambooMod", "sakuraLeaves", 1);
		if (sakura == null || sakura.isEmpty()) {
			sakura = new ItemStack(Items.QUARTZ);
		} else {
			sakura.setItemDamage(OreDictionary.WILDCARD_VALUE);
		}

		// ===== 聖幸刀「緑乃霧」 =====
		{
			String NAME = NAME_GREEN;
			String KEY = KEY_GREEN;

			ItemStack target = SlashBlade.getCustomBlade(NAME);

			ItemStack required = SlashBlade.getCustomBlade(NAME_TIZURU);
			NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(required);
			ItemSlashBlade.ProudSoul.set(tag, 10000);
			ItemSlashBlade.KillCount.set(tag, 1000);
			ItemSlashBlade.RepairCount.set(tag, 25);
			required.addEnchantment(Enchantments.POWER, 1);

			required.setItemDamage(OreDictionary.WILDCARD_VALUE);

			SlashBlade.addRecipe(KEY,
								 new RecipeAwakeBlade(
									 target,
									 required,
									 "SRE",
									 "RE ",
									 "BGC",
									 'B', required,
									 'E', Blocks.EMERALD_BLOCK,
									 'R', Blocks.REDSTONE_BLOCK,
									 'G', Blocks.GOLD_BLOCK,
									 'C', sakura,
									 'S', sphere)
				);
		}

		// ===== 侍月刀「炎水薄斬」 =====
		{
			String NAME = NAME_AQUA;
			String KEY = KEY_AQUA;

			ItemStack target = SlashBlade.getCustomBlade(NAME);

			ItemStack required = SlashBlade.getCustomBlade(NAME_TIZURU);
			NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(required);
			ItemSlashBlade.ProudSoul.set(tag, 10000);
			ItemSlashBlade.KillCount.set(tag, 1000);
			ItemSlashBlade.RepairCount.set(tag, 25);
			required.addEnchantment(Enchantments.FIRE_PROTECTION, 1);

			required.setItemDamage(OreDictionary.WILDCARD_VALUE);
			
			
			SlashBlade.addRecipe(KEY,
								 new RecipeAwakeBlade(
									 target,
									 required,
									 "SRW",
									 "RL ",
									 "BGC",
									 'B', required,
									 'W', Items.WATER_BUCKET,
									 'L', Items.LAVA_BUCKET,
									 'R', Blocks.REDSTONE_BLOCK,
									 'G', Blocks.GOLD_BLOCK,
									 'C', sakura,
									 'S', sphere)
				);
		}

		// ===== 月光桜「吹雪一閃」 =====
		{
			String NAME = NAME_MOON;
			String KEY = KEY_MOON;

			ItemStack target = SlashBlade.getCustomBlade(NAME);

			ItemStack required = SlashBlade.getCustomBlade(NAME_TIZURU);
			NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(required);
			ItemSlashBlade.ProudSoul.set(tag, 10000);
			ItemSlashBlade.KillCount.set(tag, 1000);
			ItemSlashBlade.RepairCount.set(tag, 25);
			required.addEnchantment(Enchantments.THORNS, 1);
			
			required.setItemDamage(OreDictionary.WILDCARD_VALUE);
			

			SlashBlade.addRecipe(KEY,
								 new RecipeAwakeBlade(
									 target,
									 required,
									 "SRW",
									 "RL ",
									 "BGC",
									 'B', required,
									 'W', Blocks.QUARTZ_BLOCK,
									 'L', Blocks.GLOWSTONE,
									 'R', Blocks.REDSTONE_BLOCK,
									 'G', Blocks.GOLD_BLOCK,
									 'C', sakura,
									 'S', sphere)
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
