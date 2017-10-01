package slashblade.addonpack.named;

import java.util.Random;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.entity.IMerchant;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import static net.minecraft.entity.passive.EntityVillager.EmeraldForItems;
import static net.minecraft.entity.passive.EntityVillager.ListEnchantedItemForEmeralds;
import static net.minecraft.entity.passive.EntityVillager.ListItemForEmeralds;
import static net.minecraft.entity.passive.EntityVillager.PriceInfo;
import static net.minecraft.entity.passive.EntityVillager.ITradeList;
import static net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import static net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

/**
 * 洞爺湖
 */
public class Toyako
{
	/** 刀の登録名 */
	public static final String NAME = "flammpfeil.slashblade.named.toyako";
	
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
		ItemSlashBlade.setBaseAttackModifier(tag, 4 + Item.ToolMaterial.STONE.getDamageVsEntity());
		ItemSlashBlade.TextureName.set(tag, "named/toyako/toyako");
		ItemSlashBlade.ModelName.set(tag, "named/toyako/toyako");
		ItemSlashBlade.SpecialAttackType.set(tag, 4);
		ItemSlashBlade.StandbyRenderType.set(tag, 1);

		SlashBlade.registerCustomItemStack(NAME, blade);
		ItemSlashBladeNamed.NamedBlades.add(NAME);
	}

	/**
	 * レシピの登録.
	 *
	 * 村人との取引内容に追加
	 */
	public static void registRecipe()
	{
		VillagerProfession prof = VillagerRegistry.getById(3);
		//                                         ↑@Deprecated

		VillagerCareer career = new VillagerCareer(prof, "weapon2");
		career
			.addTrade(1, new EmeraldForItems(Items.COAL, new PriceInfo(16, 24)))
			.addTrade(1, new ListItemForEmeralds(Items.IRON_AXE, new PriceInfo(6, 8)))
			.addTrade(1, new SimpleTrade(SlashBlade.getCustomBlade(NAME), new PriceInfo(3, 7)))
			.addTrade(2, new EmeraldForItems(Items.IRON_INGOT, new PriceInfo(7, 9)))
			.addTrade(2, new ListEnchantedItemForEmeralds(Items.IRON_SWORD, new PriceInfo(9, 10)))
			.addTrade(3, new EmeraldForItems(Items.DIAMOND, new PriceInfo(3, 4)))
			.addTrade(3, new ListEnchantedItemForEmeralds(Items.DIAMOND_SWORD, new PriceInfo(12, 15)))
			.addTrade(3, new ListEnchantedItemForEmeralds(Items.DIAMOND_AXE, new PriceInfo(9, 12)))
			;

		// ↑
		// 武器屋と同じ取引内容を指定する。
		// net.minecraft.entity.passive.EntityVillagerの
		// DEFAULT_TRADE_LIST_MAP を参考。
	}

	/**
	 * 単純な取引
	 */
    private static class SimpleTrade implements ITradeList
	{
		/**
		 * 取引で得られるアイテム
		 */
		private ItemStack item;

		/**
		 * 取引に必要なエメラルドの数
		 */
		private PriceInfo price;

		/**
		 * コンストラクタ
		 *
		 * @param item 取引で得られるアイテム
		 * @param price 取引に必要なエメラルドの数
		 */
		public SimpleTrade(ItemStack item, PriceInfo price)
		{
			this.item = item;
			this.price = price;
		}

		/**
		 * 取引内容の設定
		 */ 
		public void addMerchantRecipe(IMerchant merchant,
									  MerchantRecipeList recipeList,
									  Random random)
		{
			int i = price != null ? price.getPrice(random) : 1;
			recipeList.add(new MerchantRecipe(
							   new ItemStack(Items.EMERALD, i, 0),
							   item.copy()));
        }

		// ※
		// 標準で用意されている ITradeList 実装クラスは
		// アイテムを一旦 Item 型にしてしているため、
		// 取引アイテムとして刀を指定すると全部「無銘」になってしまう。
		// そのため、ItemStackのままで指定できるクラスを用意した。
	}
}
