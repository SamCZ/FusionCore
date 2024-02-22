package cz.sam.fusioncore.client.particle.rendertype;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.registry.client.LodestoneShaderRegistry;

public class TriangleParticleRenderType implements ParticleRenderType  {

    @Override
    public void begin(BufferBuilder builder, TextureManager manager) {
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.enableCull();
        RenderSystem.setShader(LodestoneShaderRegistry.LODESTONE_TEXTURE.getInstance());
        builder.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP);
    }

    @Override
    public void end(Tesselator pTesselator) {
        pTesselator.end();
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableCull();
        RenderSystem.enableDepthTest();
    }

}
