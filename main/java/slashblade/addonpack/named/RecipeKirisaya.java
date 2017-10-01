package slashblade.addonpack.named;

import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;

/**
 * 無神「刃無し」のレシピ
 */
public class RecipeKirisaya extends RecipeAwakeBlade
{
	private final ItemStack sphere;
  
	public RecipeKirisaya(ResourceLocation group,
						  ItemStack result,
						  ItemStack requiredStateBlade,
						  ItemStack reqiredSphere,
						  Object... recipe)
	{
		super(group, result, requiredStateBlade, recipe);
		this.sphere = reqiredSphere;
	}
  
	/**
	 * レシピとの一致判定.
	 *
	 * 通常の判定に加え、魂珠のSAも一致しているか判定する。
	 */
    @Override
	public boolean matches(InventoryCrafting inv, World world)
	{
		if (this.sphere == null)
			return false;
		
		boolean result = super.matches(inv, world);
		if (!result)
			return false;

		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack current = inv.getStackInSlot(i);
			if (!current.isItemEqual(this.sphere))
				continue;

			int requiredsa = ItemSlashBlade.SpecialAttackType.get(this.sphere.getTagCompound());
			int currentsa = ItemSlashBlade.SpecialAttackType.get(current.getTagCompound());
			if (requiredsa != currentsa)
				return false;
		}

		return true;
	}
}
