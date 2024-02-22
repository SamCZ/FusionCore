package cz.sam.fusioncore.client.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import cz.sam.fusioncore.client.particle.options.SphereParticleOptions;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import team.lodestar.lodestone.helpers.RenderHelper;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.world.GenericParticle;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

public class SphereParticle extends GenericParticle<SphereParticleOptions> {

    public static final VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();

    private final ResourceLocation texture;
    private final GenericParticleData radiusData;
    private final int segmentCount;
    private final boolean autoScaleUVs;
    private final GenericParticleData manualUVScaleData;

    public SphereParticle(ClientLevel world, SphereParticleOptions data, ParticleEngine.MutableSpriteSet spriteSet, double x, double y, double z, double xd, double yd, double zd) {
        super(world, data, spriteSet, x, y, z, xd, yd, zd);
        this.texture = data.texture;
        this.radiusData = data.radiusData;
        this.segmentCount = data.segmentCount;
        this.autoScaleUVs = data.autoScaleUVs;
        this.manualUVScaleData = data.manualUVScaleData;
    }

    @Override
    public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
        if (lifeDelay > 0) {
            return;
        }

        RenderSystem.setShaderTexture(0, texture);

        renderActors.forEach(actor -> actor.accept(this));
        consumer = getVertexConsumer(consumer);
        Vec3 vec3 = camera.getPosition();
        float x = (float) (Mth.lerp(partialTicks, this.xo, this.x) - vec3.x());
        float y = (float) (Mth.lerp(partialTicks, this.yo, this.y) - vec3.y());
        float z = (float) (Mth.lerp(partialTicks, this.zo, this.z) - vec3.z());
        final Vec3 pos = new Vec3(x, y, z);

        float radius = radiusData.getValue(age, lifetime);
        //builder.setUV(getU0(), getV0(), getU1(), getV1()).setColorRaw(rCol, gCol, bCol).setAlpha(alpha);
        //builder.renderBeam(consumer, null, movingFrom, movingTo, quadSize, Vec3.ZERO);

        PoseStack poseStack = new PoseStack();
        poseStack.setIdentity();
        poseStack.translate(pos.x, pos.y, pos.z);

        renderSphere(consumer, poseStack, radius, segmentCount, segmentCount);
    }

    protected float r = 1, g = 1, b = 1, a = 1;
    protected int light = RenderHelper.FULL_BRIGHT;
    protected float u0 = 0, v0 = 0, u1 = 1, v1 = 1;

    public VFXBuilders.WorldVFXBuilder renderSphere(VertexConsumer vertexConsumer, PoseStack stack, float radius, int longs, int lats) {
        Matrix4f last = stack.last().pose();
        float startU = 0;
        float startV = 0;
        float endU = Mth.PI * 2;
        float endV = Mth.PI;
        float stepU = (endU - startU) / longs;
        float stepV = (endV - startV) / lats;
        for (int i = 0; i < longs; ++i) {
            // U-points
            for (int j = 0; j < lats; ++j) {
                // V-points
                float u = i * stepU + startU;
                float v = j * stepV + startV;
                float un = (i + 1 == longs) ? endU : (i + 1) * stepU + startU;
                float vn = (j + 1 == lats) ? endV : (j + 1) * stepV + startV;
                Vector3f p0 = RenderHelper.parametricSphere(u, v, radius);
                Vector3f p1 = RenderHelper.parametricSphere(u, vn, radius);
                Vector3f p2 = RenderHelper.parametricSphere(un, v, radius);
                Vector3f p3 = RenderHelper.parametricSphere(un, vn, radius);

                float scale = this.autoScaleUVs ? radius : manualUVScaleData.getValue(age, lifetime);

                float textureU = u / endU * scale;
                float textureV = v / endV * scale;
                float textureUN = un / endU * scale;
                float textureVN = vn / endV * scale;
                RenderHelper.vertexPosColorUVLight(vertexConsumer, last, p0.x(), p0.y(), p0.z(), r, g, b, a, textureU, textureV, light);
                RenderHelper.vertexPosColorUVLight(vertexConsumer, last, p2.x(), p2.y(), p2.z(), r, g, b, a, textureUN, textureV, light);
                RenderHelper.vertexPosColorUVLight(vertexConsumer, last, p1.x(), p1.y(), p1.z(), r, g, b, a, textureU, textureVN, light);

                RenderHelper.vertexPosColorUVLight(vertexConsumer, last, p3.x(), p3.y(), p3.z(), r, g, b, a, textureUN, textureVN, light);
                RenderHelper.vertexPosColorUVLight(vertexConsumer, last, p1.x(), p1.y(), p1.z(), r, g, b, a, textureU, textureVN, light);
                RenderHelper.vertexPosColorUVLight(vertexConsumer, last, p2.x(), p2.y(), p2.z(), r, g, b, a, textureUN, textureV, light);
            }
        }
        return builder;
    }

}
