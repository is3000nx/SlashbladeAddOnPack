package slashblade.addonpack;

import mods.flammpfeil.slashblade.network.MessageRankpointSynchronize;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class InitProxy
{
	@SidedProxy(clientSide="slashblade.addonpack.InitProxyClient",
				serverSide="slashblade.addonpack.InitProxy")
	public static InitProxy proxy;
  
	public void initializeItemRenderer()
	{
	}
}

