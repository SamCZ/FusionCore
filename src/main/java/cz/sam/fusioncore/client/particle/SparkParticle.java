package cz.sam.fusioncore.client.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import cz.sam.fusioncore.client.particle.options.SparkParticleOptions;
import cz.sam.fusioncore.client.particle.options.SphereParticleOptions;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.helpers.RenderHelper;
import team.lodestar.lodestone.systems.particle.world.GenericParticle;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.trail.TrailPoint;
import team.lodestar.lodestone.systems.rendering.trail.TrailPointBuilder;

import java.util.ArrayList;
import java.util.List;

public class SparkParticle extends GenericParticle<SparkParticleOptions> {
    public static VFXBuilders.WorldVFXBuilder BUILDER = VFXBuilders.createWorld().setParticleFormat();

    TrailPointBuilder trailPointBuilder = TrailPointBuilder.create(3);

    public SparkParticle(ClientLevel world, SparkParticleOptions data, ParticleEngine.MutableSpriteSet spriteSet, double x, double y, double z, double xd, double yd, double zd) {
        super(world, data, spriteSet, x, y, z, xd, yd, zd);

        //builder.setVertexSupplier(vertexBuilderSupplier);



        //segments.add(new TrailPoint(new Vec3(1, 2, 0)));
    }

    private void generate()
    {
        trailPointBuilder = TrailPointBuilder.create(3);
        RandomSource randomSource = RandomSource.create();
        trailPointBuilder.addTrailPoint(new Vec3(0, 0, 0));
        Vec3 lastPoint = new Vec3(0, 0, 0);
        Vec3 normal = new Vec3(0, 1, 0);
        float maxLen = 1.0f;
        for (int i = 0; i < 10; i++) {
            if (maxLen <= 0) break;

            float len = RandomHelper.randomBetween(randomSource, 0, maxLen);

            normal = normal.add(new Vec3(RandomHelper.randomBetween(randomSource, -0.5f, 0.5f), 0, RandomHelper.randomBetween(randomSource, -0.5f, 0.5f)));
            normal.normalize();

            Vec3 point = lastPoint.add(normal.multiply(len, len, len));
            trailPointBuilder.addTrailPoint(point);
            lastPoint = point;

            maxLen -= RandomHelper.randomBetween(randomSource, 0, 0.025f);
        }
    }

    @Override
    public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
        if (lifeDelay > 0) {
            return;
        }

        generate();

        renderActors.forEach(actor -> actor.accept(this));

        consumer = getVertexConsumer(consumer);
        Vec3 cameraPosition = camera.getPosition();
        float x = (float) (Mth.lerp(partialTicks, this.xo, this.x) - cameraPosition.x());
        float y = (float) (Mth.lerp(partialTicks, this.yo, this.y) - cameraPosition.y());
        float z = (float) (Mth.lerp(partialTicks, this.zo, this.z) - cameraPosition.z());
        final Vec3 pos = new Vec3(x, y, z);

        PoseStack stack = new PoseStack();
        stack.translate(x, y, z);

        BUILDER.setUV(getU0(), getV0(), getU1(), getV1()).setColorRaw(rCol, gCol, bCol).setAlpha(alpha);

        List<TrailPoint> points = trailPointBuilder.getTrailPoints();
        for (int i = 0; i < points.size() - 1; i++) {
            Vec3 startPos = points.get(i).getPosition().add(pos);
            Vec3 endPos = points.get(i + 1).getPosition().add(pos);

            Vec3 lineNormal = endPos.subtract(startPos).normalize();
            Vec3 lineToCameraNormal = new Vec3(0, 0, 0).subtract(startPos).normalize();

            float width = 0.1f;
            float halfWidth = width / 2.0f;

            Vec3 offset = lineNormal.cross(lineToCameraNormal).multiply(halfWidth, halfWidth, halfWidth);

            consumer.vertex(startPos.x + offset.x, startPos.y + offset.y, startPos.z + offset.z).uv(getU0(), getV0()).color(rCol, gCol, bCol, alpha).uv2(RenderHelper.FULL_BRIGHT).endVertex();
            consumer.vertex(startPos.x - offset.x, startPos.y - offset.y, startPos.z - offset.z).uv(getU0(), getV1()).color(rCol, gCol, bCol, alpha).uv2(RenderHelper.FULL_BRIGHT).endVertex();

            consumer.vertex(endPos.x - offset.x, endPos.y - offset.y, endPos.z - offset.z).uv(getU1(), getV1()).color(rCol, gCol, bCol, alpha).uv2(RenderHelper.FULL_BRIGHT).endVertex();
            consumer.vertex(endPos.x + offset.x, endPos.y + offset.y, endPos.z + offset.z).uv(getU1(), getV0()).color(rCol, gCol, bCol, alpha).uv2(RenderHelper.FULL_BRIGHT).endVertex();
        }
    }

}
