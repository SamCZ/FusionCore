package cz.sam.fusioncore.client.particle.options;

import com.mojang.brigadier.StringReader;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.options.AbstractWorldParticleOptions;

public class SphereParticleOptions extends AbstractWorldParticleOptions {

    public static Codec<SphereParticleOptions> boltCodec(ParticleType<?> type) {
        return Codec.unit(() -> new SphereParticleOptions(type));
    }

    public ResourceLocation texture = null;
    public GenericParticleData radiusData = DEFAULT_GENERIC;
    public int segmentCount = 10;
    public boolean autoScaleUVs = false;
    public GenericParticleData manualUVScaleData = DEFAULT_GENERIC;

    public SphereParticleOptions(ParticleType<?> type) {
        super(type);
    }

    @Override
    public ParticleType<?> getType() {
        return type;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {
    }

    @Override
    public String writeToString() {
        return "";
    }

    public static final Deserializer<SphereParticleOptions> DESERIALIZER = new Deserializer<>() {
        @Override
        public SphereParticleOptions fromCommand(ParticleType<SphereParticleOptions> type, StringReader reader) {
            return new SphereParticleOptions(type);
        }

        @Override
        public SphereParticleOptions fromNetwork(ParticleType<SphereParticleOptions> type, FriendlyByteBuf buf) {
            return new SphereParticleOptions(type);
        }
    };

}
