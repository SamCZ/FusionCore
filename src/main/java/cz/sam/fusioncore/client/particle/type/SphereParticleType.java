package cz.sam.fusioncore.client.particle.type;

import com.mojang.serialization.Codec;
import cz.sam.fusioncore.client.particle.SphereParticle;
import cz.sam.fusioncore.client.particle.options.SphereParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleType;
import org.jetbrains.annotations.Nullable;

public class SphereParticleType extends ParticleType<SphereParticleOptions> {
    public SphereParticleType() {
        super(false, SphereParticleOptions.DESERIALIZER);
    }

    @Override
    public Codec<SphereParticleOptions> codec() {
        return SphereParticleOptions.boltCodec(this);
    }

    public static class Factory implements ParticleProvider<SphereParticleOptions> {
        private final SpriteSet sprite;

        public Factory(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(SphereParticleOptions data, ClientLevel world, double x, double y, double z, double mx, double my, double mz) {
            return new SphereParticle(world, data, null, x, y, z, mx, my, mz);
            //(ParticleEngine.MutableSpriteSet) sprite
        }
    }
}
