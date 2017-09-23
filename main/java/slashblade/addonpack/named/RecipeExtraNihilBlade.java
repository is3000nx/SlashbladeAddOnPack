package slashblade.addonpack.named;

import java.util.Map;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.TagPropertyAccessor;
import mods.flammpfeil.slashblade.TagPropertyAccessor;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;

/**
 * 追加レシピの共通処理.
 *
 * 素材に使っている刀の前提条件とかの判定とか
 */
public class RecipeExtraNihilBlade
	extends ShapedOreRecipe
{
	/** 素材として要求するメインの刀 */
	final protected ItemStack requiredBladeMain;

	/** 素材として要求するサブの刀 */
	final protected ItemStack requiredBladeSub;

	/** クラフト後もサブの刀を残すかどうか */
	final protected boolean remainedBladeSub;
	// ※
	// 獄刀が
	// （エンドラのドロップ品であるため1本しか入手できない）閻魔刀を
	// 素材として要求するための措置。
	// 1.11.2 時点では複数本 入手可能ではあるが
	// 「todo: drop point move enderdragon egg point」と
	// コメントが付いているので、
	// 将来的に1本しか入手出来ないようになると思われ。
	//
	// 残すかどうかを、閻魔刀かどうか直接判定すれば
	// このフラグは不要となり 設定間違いのリスクも減るが
	// 判定処理が増えるので、フラグで管理。

	/** メイン刀のクラフト位置 */
	final protected int posXMain;
	/** メイン刀のクラフト位置 */
	final protected int posYMain;

	/** サブ刀のクラフト位置 */
	final protected int posXSub;
	/** サブ刀のクラフト位置 */
	final protected int posYSub;
	
	/**
	 * コンストラクタ.
	 *
	 * @param result クラフト結果の刀
	 * @param requiredBladeMain 素材として要求する刀
	 * @param posXMain クラフト位置
	 * @param posYMain クラフト位置
	 * @param requiredBladeSub 素材として要求する刀
	 * @param posXSub クラフト位置
	 * @param posYSub クラフト位置
	 * @param remainedBladeSub サブの刀を残すかどうか
	 * @param recipe レシピの情報
	 */
	public RecipeExtraNihilBlade(ItemStack result,
								 ItemStack requiredBladeMain,
								 int posXMain,
								 int posYMain,
								 ItemStack requiredBladeSub,
								 int posXSub,
								 int posYSub,
								 boolean remainedBladeSub,
								 Object... recipe)
	{
		super(AddonPack.RecipeGroup, result, recipe);
		
		this.requiredBladeMain = requiredBladeMain;
		this.posXMain = posXMain;
		this.posYMain = posYMain;
		this.requiredBladeSub = requiredBladeSub;
		this.posXSub = posXSub;
		this.posYSub = posYSub;
		this.remainedBladeSub = remainedBladeSub;
	}

	/**
	 * タグに登録されている値の比較
	 *
	 * @param access 比較に使う値の種類
	 * @param tag1
	 * @param tag2
	 * @return 0:等しい、1以上:tag1が大きい、-1以下:tag2が大きい
	 */
	protected static int tagValueCompare(TagPropertyAccessor access,
									   NBTTagCompound tag1,
									   NBTTagCompound tag2)
	{
		return access.get(tag1).compareTo(access.get(tag2));
	}

	/**
	 * 指定アイテムが有効な刀かどうか
	 *
	 * @param item アイテム
	 * @return true=有効
	 */
	protected static boolean isValidBlade(ItemStack item)
	{
		return !item.isEmpty() &&
			item.getItem() instanceof ItemSlashBlade &&
			item.hasTagCompound();
	}

	/**
	 * レシピとの一致判定
	 */
    @Override
	public boolean matches(InventoryCrafting inv, World world)
	{
		boolean result = super.matches(inv, world);
        if (!result)
			return false;

		if (requiredBladeMain.isEmpty() || requiredBladeSub.isEmpty())
			return false;

		// -----
		ItemStack sub = inv.getStackInRowAndColumn(posXSub, posYSub);
		if (!isValidBlade(sub))
			return false;
		if (!matchesName(sub, requiredBladeSub))
			return false;
		
		// -----
		ItemStack main = inv.getStackInRowAndColumn(posXMain, posYMain);
		if (!isValidBlade(main))
			return false;
		if (!matchesName(main, requiredBladeMain))
			return false;
		if (!matchesCount(main, requiredBladeMain))
			return false;

		return true;
	}

	/**
	 * 刀の名前の一致判定
	 *
	 * @param slot 作業台上の刀
	 * @param required レシピで要求している刀
	 * @return true=一致
	 */
	private static boolean matchesName(ItemStack slot, ItemStack required)
	{
		return slot.getUnlocalizedName().equals(required.getUnlocalizedName());
	}

	/**
	 * 各カウントについての要求を満たしているか判定
	 *
	 * @param slot 作業台上の刀
	 * @param required レシピで要求している刀
	 * @return true=一致
	 */
	private static boolean matchesCount(ItemStack slot, ItemStack required)
	{
		NBTTagCompound tagSlot = ItemSlashBlade.getItemTagCompound(slot);
		NBTTagCompound tagReq  = ItemSlashBlade.getItemTagCompound(required);
		
		return
			tagValueCompare(ItemSlashBlade.ProudSoul, tagSlot, tagReq) >= 0 &&
			tagValueCompare(ItemSlashBlade.KillCount, tagSlot, tagReq) >= 0 &&
			tagValueCompare(ItemSlashBlade.RepairCount, tagSlot, tagReq) >= 0;
	}

	/**
	 * クラフト結果
	 */
	@Override
	public ItemStack getCraftingResult(InventoryCrafting var1)
	{
		ItemStack result = super.getCraftingResult(var1);
    
		ItemStack curIs = var1.getStackInRowAndColumn(1, 1);
		if (!isValidBlade(curIs))
			return result;
		// ※
		// 対象の刀素材を中央に固定している以外
		// RecipeAwakeBlade と全く一緒

		NBTTagCompound oldTag = curIs.getTagCompound();
		oldTag = (NBTTagCompound)oldTag.copy();

		NBTTagCompound newTag = ItemSlashBlade.getItemTagCompound(result);
		if (ItemSlashBladeNamed.CurrentItemName.exists(newTag)) {

			String key = ItemSlashBladeNamed.CurrentItemName.get(newTag);
			ItemStack tmp = SlashBlade.getCustomBlade(key);
			if (!tmp.isEmpty())
				result = tmp;
		}
		
		newTag = ItemSlashBlade.getItemTagCompound(result);
		ItemSlashBlade.KillCount.set(newTag, ItemSlashBlade.KillCount.get(oldTag));
		ItemSlashBlade.ProudSoul.set(newTag, ItemSlashBlade.ProudSoul.get(oldTag));
		ItemSlashBlade.RepairCount.set(newTag, ItemSlashBlade.RepairCount.get(oldTag));

		if (oldTag.hasUniqueId("Owner"))
			newTag.setUniqueId("Owner", oldTag.getUniqueId("Owner"));
		// ※ ↑
		// 似蛭のレシピにはなかったけど、RecipeAwakeBladeにあるコード。
		// 何のためにあるのか分からないけど、一応足した。
		
		if(oldTag.hasKey(ItemSlashBlade.adjustXStr))
			newTag.setFloat(ItemSlashBlade.adjustXStr, oldTag.getFloat(ItemSlashBlade.adjustXStr));

		if(oldTag.hasKey(ItemSlashBlade.adjustYStr))
			newTag.setFloat(ItemSlashBlade.adjustYStr, oldTag.getFloat(ItemSlashBlade.adjustYStr));

		if(oldTag.hasKey(ItemSlashBlade.adjustZStr))
			newTag.setFloat(ItemSlashBlade.adjustZStr, oldTag.getFloat(ItemSlashBlade.adjustZStr));


		{
			Map<Enchantment,Integer> newItemEnchants = EnchantmentHelper.getEnchantments(result);
			Map<Enchantment,Integer> oldItemEnchants = EnchantmentHelper.getEnchantments(curIs);
			for(Enchantment enchantIndex : oldItemEnchants.keySet())
			{
				Enchantment enchantment = enchantIndex;

				int destLevel = newItemEnchants.containsKey(enchantIndex) ? newItemEnchants.get(enchantIndex) : 0;
				int srcLevel = oldItemEnchants.get(enchantIndex);

				srcLevel = Math.max(srcLevel, destLevel);
				srcLevel = Math.min(srcLevel, enchantment.getMaxLevel());


				boolean canApplyFlag = enchantment.canApply(result);
				if(canApplyFlag){
					for(Enchantment curEnchantIndex : newItemEnchants.keySet()){
						if (curEnchantIndex != enchantIndex && !enchantment.canApplyTogether(curEnchantIndex))
						{
							canApplyFlag = false;
							break;
						}
					}
					if (canApplyFlag)
						newItemEnchants.put(enchantIndex, Integer.valueOf(srcLevel));
				}
			}
			EnchantmentHelper.setEnchantments(newItemEnchants, result);
		}

		return result;
	}

	/**
	 * クラフト後に残す素材の設定。
	 */
	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
	{
		if (!remainedBladeSub)
			return super.getRemainingItems(inv);

        NonNullList<ItemStack> ret = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        for (int i = 0; i < ret.size(); i++) {
			ItemStack slot = inv.getStackInSlot(i);

			if (isValidBlade(slot) && matchesName(slot, requiredBladeSub))
				ret.set(i, slot.copy());
			else
				ret.set(i, ForgeHooks.getContainerItem(inv.getStackInSlot(i)));
        }
        return ret;

		// << オリジナルからの改変点 >>
		// 閻魔刀は作業台に残す
	}
	
}
