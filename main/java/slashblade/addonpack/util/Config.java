package slashblade.addonpack.util;

import mods.flammpfeil.slashblade.SlashBlade;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

/**
 * 設定ファイル
 */
public class Config
{
	/**
	 * クラフトの前提刀剣をCreativeタブに登録するかどうか
	 *
	 * @return true=登録する / false=登録しない(デフォルト設定)
	 */
	public static boolean isRegistRequiredBlade()
	{
		Configuration config = SlashBlade.mainConfiguration;
		Property prop = config.get("addon", "RequiredBladeCreativeTab", false);
		return prop.getBoolean();
	}

	/**
	 * Mod「TwilightForest」未導入時のヤミガラスのドロップ率.
	 *
	 * @return ドロップ率
	 */
	public static float getDarkRavenDropRate()
	{
		Configuration config = SlashBlade.mainConfiguration;
		Property prop = config.get("addon", "DarkRavenDropRate", 0.15);
		return (float)prop.getDouble();
	}

	/**
	 * Mod「TwilightForest」導入時のヤミガラスのドロップ率.
	 *
	 * @return ドロップ率
	 */
	public static float getDarkRavenDropRateWithMod()
	{
		Configuration config = SlashBlade.mainConfiguration;
		Property prop = config.get("general", "DarkRavenDropRate", 0.05);
		return (float)prop.getDouble();
	}
}
