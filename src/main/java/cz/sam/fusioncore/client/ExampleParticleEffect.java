package cz.sam.fusioncore.client;

import cz.sam.fusioncore.FusionCore;
import cz.sam.fusioncore.client.particle.builder.SparkParticleBuilder;
import cz.sam.fusioncore.client.particle.builder.SphereParticleBuilder;
import cz.sam.fusioncore.client.particle.type.SparkParticleType;
import cz.sam.fusioncore.client.particle.type.SphereParticleType;
import net.minecraft.client.*;
import net.minecraft.client.player.*;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;

import java.awt.Color;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ExampleParticleEffect {

    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, FusionCore.MODID);

    public static RegistryObject<SphereParticleType> SPHERE_PARTICLE = PARTICLES.register("sphere", SphereParticleType::new);
    public static RegistryObject<SparkParticleType> SPARK_PARTICLE = PARTICLES.register("spark", SparkParticleType::new);

    public static final ResourceLocation HDR_TEXTURE = new ResourceLocation("fusioncore", "textures/particle/rural_crossroads_8k.hdr");

    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(SPHERE_PARTICLE.get(), SphereParticleType.Factory::new);
        event.registerSpriteSet(SPARK_PARTICLE.get(), SparkParticleType.Factory::new);
    }

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        final LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            //spawnSphere(player.level(), new Vec3(-44, 73, 0));
            spawnSpark(player.level(), new Vec3(-44, 73, 0));
            //spawnExampleParticles(player.level(), player.position());
        }
    }

    public static void spawnSphere(Level level, Vec3 pos) {
        Color startingColor = new Color(100, 0, 100);
        Color endingColor = new Color(0, 100, 200);
        SphereParticleBuilder.create(SPHERE_PARTICLE)
                .setTexture(HDR_TEXTURE)
                .setScaleData(GenericParticleData.create(0.5f, 0).build())
                .setTransparencyData(GenericParticleData.create(0.75f, 0.25f).build())
                .setColorData(ColorParticleData.create(startingColor, endingColor).setCoefficient(1.4f).setEasing(Easing.BOUNCE_IN_OUT).build())
                //.setSpinData(SpinParticleData.create(0.2f, 0.4f).setSpinOffset((level.getGameTime() * 0.2f) % 6.28f).setEasing(Easing.QUARTIC_IN).build())
                .setLifetime(1)
                .addMotion(0, 0, 0)
                .enableNoClip()
                .spawn(level, pos.x, pos.y, pos.z);
    }

    public static void spawnSpark(Level level, Vec3 pos) {
        Color startingColor = new Color(100, 0, 100);
        Color endingColor = new Color(0, 100, 200);
        SparkParticleBuilder.create(SPARK_PARTICLE)
                .setScaleData(GenericParticleData.create(0.5f, 0).build())
                .setTransparencyData(GenericParticleData.create(0.75f, 0.25f).build())
                .setColorData(ColorParticleData.create(startingColor, endingColor).setCoefficient(1.4f).setEasing(Easing.BOUNCE_IN_OUT).build())
                //.setSpinData(SpinParticleData.create(0.2f, 0.4f).setSpinOffset((level.getGameTime() * 0.2f) % 6.28f).setEasing(Easing.QUARTIC_IN).build())
                .setLifetime(1)
                .addMotion(0, 0, 0)
                .enableNoClip()
                .spawn(level, pos.x, pos.y, pos.z);
    }
}