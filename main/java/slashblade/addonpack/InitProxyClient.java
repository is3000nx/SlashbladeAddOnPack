package slashblade.addonpack;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraft.client.renderer.entity.Render;
import slashblade.addonpack.entity.*;
import slashblade.addonpack.client.renderer.entity.*;

public class InitProxyClient extends InitProxy
{
	@Override
	public void initializeItemRenderer()
	{
        RenderingRegistry.registerEntityRenderingHandler(
			EntityPhantomSwordEx.class,
			new IRenderFactory<EntityPhantomSwordEx>() {
				@Override
				public Render<? super EntityPhantomSwordEx> createRenderFor(RenderManager manager)
				{
					return new RenderPhantomSwordEx(manager);
				}
			});


        RenderingRegistry.registerEntityRenderingHandler(
			EntityDriveEx.class,
			new IRenderFactory<EntityDriveEx>() {
				@Override
				public Render<? super EntityDriveEx> createRenderFor(RenderManager manager)
				{
					return new RenderDriveEx(manager);
				}
			});
	}
}
