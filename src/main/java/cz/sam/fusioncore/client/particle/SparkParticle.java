package cz.sam.fusioncore.client.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import cz.sam.fusioncore.client.particle.options.SparkParticleOptions;
import cz.sam.fusioncore.client.particle.options.SphereParticleOptions;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import team.lodestar.lodestone.systems.particle.world.GenericParticle;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.trail.TrailPoint;

import java.util.ArrayList;
import java.util.List;

public class SparkParticle extends GenericParticle<SparkParticleOptions> {

    public static final VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setParticleFormat();
    List<TrailPoint> segments = new ArrayList<>();

    public SparkParticle(ClientLevel world, SparkParticleOptions data, ParticleEngine.MutableSpriteSet spriteSet, double x, double y, double z, double xd, double yd, double zd) {
        super(world, data, spriteSet, x, y, z, xd, yd, zd);

        builder.setVertexSupplier(new VFXBuilders.WorldVFXBuilder.WorldVertexPlacementSupplier() {
            @Override
            public void placeVertex(VertexConsumer consumer, Matrix4f last, float x, float y, float z, float u, float v) {

            }
        });

        segments.add(new TrailPoint(new Vec3(0, 0, 0)));
        segments.add(new TrailPoint(new Vec3(0, 1, 0)));
        segments.add(new TrailPoint(new Vec3(1, 1, 0)));
        segments.add(new TrailPoint(new Vec3(1, 2, 0)));
    }

    @Override
    public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
        if (lifeDelay > 0) {
            return;
        }

        renderActors.forEach(actor -> actor.accept(this));
        consumer = getVertexConsumer(consumer);
        Vec3 vec3 = camera.getPosition();
        float x = (float) (Mth.lerp(partialTicks, this.xo, this.x) - vec3.x());
        float y = (float) (Mth.lerp(partialTicks, this.yo, this.y) - vec3.y());
        float z = (float) (Mth.lerp(partialTicks, this.zo, this.z) - vec3.z());
        final Vec3 pos = new Vec3(x, y, z);

        PoseStack stack = new PoseStack();
        stack.translate(x, y, z);

        builder.setUV(getU0(), getV0(), getU1(), getV1()).setColorRaw(rCol, gCol, bCol).setAlpha(alpha);
        builder.renderTrail(consumer, stack, segments, 0.2f);
    }

}
