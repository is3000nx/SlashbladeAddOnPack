package slashblade.addonpack.named;

import net.minecraftforge.oredict.ShapedOreRecipe;
import mods.flammpfeil.slashblade.SlashBlade;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

/**
 * 和風MOD連携の刀用レシピ
 */ 
public class RecipeWa extends ShapedOreRecipe
{
	/** 刀の登録名 */
	private final String name_;

	/** 刀のテクスチャ名 */
	private final String texture_;
	
    public RecipeWa(String name, String texture, Object... recipe)
	{
        super(
			SlashBlade.findItemStack(SlashBlade.modid, name, 1),
			recipe);

		this.name_ = name;
		this.texture_ = texture;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World world)
	{
        boolean result = super.matches(inv, world);

		if (!result)
			return false;

		// 中央は 空の鞘
		ItemStack sc = inv.getStackInRowAndColumn(1, 1);
		if (sc.isEmpty() ||
			sc.getItem() != SlashBlade.wrapBlade ||
			SlashBlade.wrapBlade.hasWrapedItem(sc)) {

			return false;
		}

		return true;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		// 素材の鞘
		ItemStack scabbard = inv.getStackInRowAndColumn(1, 1);
		if (scabbard.isEmpty())
			return ItemStack.EMPTY;

		scabbard = scabbard.copy();

		Wa.makeBlade(scabbard, name_, texture_);

		return scabbard;
	}
	
	
}
