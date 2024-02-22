package cz.sam.fusioncore.client.particle.type;

import com.mojang.serialization.Codec;
import cz.sam.fusioncore.client.particle.SparkParticle;
import cz.sam.fusioncore.client.particle.options.SparkParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleType;
import org.jetbrains.annotations.Nullable;

public class SparkParticleType extends ParticleType<SparkParticleOptions> {

    public SparkParticleType() {
        super(false, SparkParticleOptions.DESERIALIZER);
    }

    @Override
    public Codec<SparkParticleOptions> codec() {
        return SparkParticleOptions.boltCodec(this);
    }

    public static class Factory implements ParticleProvider<SparkParticleOptions> {
        private final SpriteSet sprite;

        public Factory(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(SparkParticleOptions data, ClientLevel world, double x, double y, double z, double mx, double my, double mz) {
            return new SparkParticle(world, data, (ParticleEngine.MutableSpriteSet) sprite, x, y, z, mx, my, mz);
        }
    }

}
