package slashblade.addonpack.util;

/**
 * 数値計算関係
 */
public class Math2
{
	/**
	 * sin値テーブル.
	 *
	 * 360°を分解能65536で事前計算してある。
	 */
    public static final float[] SIN_TABLE = new float[65536];
	// MathHelper.SIN_TABLE が private なので、
	// 同じものをコッチも用意する。
	// 256Kbyteほど浪費するが、
	// Reflectionでprivateのものは あまり参照したくない.

	static
	{
        for (int i = 0; i < 65536; i++) {
            SIN_TABLE[i] = (float)Math.sin(i * Math.PI * 2.0 / 65536.0);
        }
	}

	
	/**
	 * sin
	 *
	 * @param deg 角度(単位：度)
	 * @return sin値
	 */
	public static float sin(float deg)
	{
		// ラジアンで指定する場合は、MathHelper.sin()を使う
		
		return SIN_TABLE[(int)(65536.0/360.0*deg) & 0xffff];
	}

	/**
	 * cos
	 *
	 * @param deg 角度(単位：度)
	 * @return cos値
	 */
	public static float cos(float deg)
	{
		return SIN_TABLE[((int)(65536.0/360.0*deg) + 65536/4) & 0xffff];
	}
	


	/**
	 * 度 → ラジアン 変換
	 * @param angdeg 度で表した角度
	 * @return ラジアンで表した角度
	 */
	public static float toRadians(float angdeg)
	{
		// Math.toRadians() のfloat版
		return angdeg * (float)Math.PI / 180.0f;
	}

	
};
