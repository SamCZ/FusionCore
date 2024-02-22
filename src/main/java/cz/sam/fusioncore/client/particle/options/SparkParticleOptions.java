package cz.sam.fusioncore.client.particle.options;

import com.mojang.brigadier.StringReader;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.options.AbstractWorldParticleOptions;

public class SparkParticleOptions extends AbstractWorldParticleOptions {

    public static Codec<SparkParticleOptions> boltCodec(ParticleType<?> type) {
        return Codec.unit(() -> new SparkParticleOptions(type));
    }



    public SparkParticleOptions(ParticleType<?> type) {
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

    public static final Deserializer<SparkParticleOptions> DESERIALIZER = new Deserializer<>() {
        @Override
        public SparkParticleOptions fromCommand(ParticleType<SparkParticleOptions> type, StringReader reader) {
            return new SparkParticleOptions(type);
        }

        @Override
        public SparkParticleOptions fromNetwork(ParticleType<SparkParticleOptions> type, FriendlyByteBuf buf) {
            return new SparkParticleOptions(type);
        }
    };

}
