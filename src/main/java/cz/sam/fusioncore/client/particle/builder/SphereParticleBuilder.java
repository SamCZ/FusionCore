package cz.sam.fusioncore.client.particle.builder;

import cz.sam.fusioncore.client.particle.options.SphereParticleOptions;
import cz.sam.fusioncore.client.particle.rendertype.TriangleParticleRenderType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.systems.particle.builder.AbstractWorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;

import java.util.function.Supplier;

public class SphereParticleBuilder extends AbstractWorldParticleBuilder<SphereParticleBuilder, SphereParticleOptions> {

    final SphereParticleOptions options;

    public static SphereParticleBuilder create(Supplier<? extends ParticleType<SphereParticleOptions>> type) {
        return new SphereParticleBuilder(type.get());
    }

    protected SphereParticleBuilder(ParticleType<SphereParticleOptions> type) {
        super(type);
        this.options = new SphereParticleOptions(type);
        setRenderType(new TriangleParticleRenderType());
    }

    @Override
    public SphereParticleOptions getParticleOptions() {
        return options;
    }

    public ResourceLocation getTexture() {
        return getParticleOptions().texture;
    }

    public SphereParticleBuilder setTexture(ResourceLocation texture) {
        getParticleOptions().texture = texture;
        return wrapper();
    }

    public GenericParticleData getRadiusData() {
        return getParticleOptions().radiusData;
    }

    public SphereParticleBuilder setRadiusData(GenericParticleData radiusData) {
        getParticleOptions().radiusData = radiusData;
        return wrapper();
    }

    public int getSegmentCount() {
        return getParticleOptions().segmentCount;
    }

    public SphereParticleBuilder setSegmentCount(int segmentCount) {
        getParticleOptions().segmentCount = segmentCount;
        return wrapper();
    }

    public boolean getAutoScaleUVs() {
        return getParticleOptions().autoScaleUVs;
    }

    public SphereParticleBuilder setAutoScaleUVs(boolean autoScaleUVs) {
        getParticleOptions().autoScaleUVs = autoScaleUVs;
        return wrapper();
    }

    public GenericParticleData getManualUVScaleData() {
        return getParticleOptions().manualUVScaleData;
    }

    public SphereParticleBuilder setManualUVScaleData(GenericParticleData manualUVScaleData) {
        getParticleOptions().manualUVScaleData = manualUVScaleData;
        return setAutoScaleUVs(false);
    }

}
