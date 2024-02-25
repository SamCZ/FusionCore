package cz.sam.fusioncore.client.particle.builder;

import cz.sam.fusioncore.client.particle.options.SparkParticleOptions;
import cz.sam.fusioncore.client.particle.options.SphereParticleOptions;
import net.minecraft.core.particles.ParticleType;
import team.lodestar.lodestone.systems.particle.builder.AbstractWorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

import java.util.function.Supplier;

public class SparkParticleBuilder extends AbstractWorldParticleBuilder<SparkParticleBuilder, SparkParticleOptions> {

    final SparkParticleOptions options;

    public static SparkParticleBuilder create(Supplier<? extends ParticleType<SparkParticleOptions>> type) {
        return new SparkParticleBuilder(type.get());
    }

    protected SparkParticleBuilder(ParticleType<SparkParticleOptions> type) {
        super(type);
        this.options = new SparkParticleOptions(type);
        this.setRenderType(LodestoneWorldParticleRenderType.ADDITIVE);
    }

    @Override
    public SparkParticleOptions getParticleOptions() {
        return options;
    }

}
