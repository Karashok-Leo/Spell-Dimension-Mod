package net.karashokleo.spelldimension;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.karashokleo.spelldimension.data.LangData;
import net.karashokleo.spelldimension.item.AllItems;
import net.karashokleo.spelldimension.misc.ExtraModifier;
import net.karashokleo.spelldimension.effect.AllStatusEffects;
import net.karashokleo.spelldimension.misc.Mage;
import net.karashokleo.spelldimension.render.*;
import net.karashokleo.spelldimension.util.ParticleUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.spell_engine.api.effect.CustomModelStatusEffect;
import net.spell_engine.api.effect.CustomParticleStatusEffect;
import net.spell_engine.api.render.CustomModels;

import java.util.List;

public class SpellDimensionClient implements ClientModInitializer
{
    public static final Identifier PHASE_LAYER = SpellDimension.modLoc("textures/spell_effect/phase.png");
    public static final Identifier CONVERGE_MODEL = SpellDimension.modLoc("spell_projectile/converge");
    public static final Identifier FROSTED_MODEL = SpellDimension.modLoc("spell_effect/frosted");
    public static final Identifier ICICLE_MODEL = SpellDimension.modLoc("spell_projectile/icicle");

    @Override
    public void onInitializeClient()
    {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                ParticleUtil.getSchoolColor(stack), AllItems.MAGE_MEDAL, AllItems.ENCHANTED_ESSENCE, AllItems.DISENCHANTED_ESSENCE);

        ItemTooltipCallback.EVENT.register(ExtraModifier::levelTooltip);

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) ->
        {
            registrationHelper.register(new NucleusRenderer<>(entityRenderer));
        });

        CustomModels.registerModelIds(List.of(
                FROSTED_MODEL,
                ICICLE_MODEL,
                CONVERGE_MODEL
        ));

        CustomParticleStatusEffect.register(AllStatusEffects.PHASE_EFFECT, new PhaseParticles());
        CustomParticleStatusEffect.register(AllStatusEffects.FROSTED_EFFECT, new FrostedParticles());
        CustomModelStatusEffect.register(AllStatusEffects.FROSTED_EFFECT, new FrostedEffectRenderer());

        ClientPlayNetworking.registerGlobalReceiver(SpellDimensionNetworking.DUST_PACKET, (client, handler, buf, responseSender) ->
        {
            Vec3d pos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
            int count = buf.readInt();
            int color = buf.readInt();
            float range = buf.readFloat();
            Random random = Random.create();
            client.execute(() ->
            {
                for (int i = 0; i < count; i++)
                    client.particleManager.addParticle(ParticleUtil.getDustParticle(color), pos.addRandom(random, range).x, pos.addRandom(random, range).y, pos.addRandom(random, range).z, 0D, 0D, 0D);
            });
        });
        ClientPlayNetworking.registerGlobalReceiver(SpellDimensionNetworking.UPGRADE_PACKET, (client, handler, buf, responseSender) ->
        {
            Mage mage = Mage.readFromPacket(buf);
            client.execute(() ->
            {
                client.inGameHud.setTitle(Text.translatable(LangData.TITLE_UPGRADE));
                client.inGameHud.setSubtitle(mage.getMageTitle(Text.translatable(LangData.MAGE)));
            });
        });
        ClientPlayNetworking.registerGlobalReceiver(SpellDimensionNetworking.CLEAR_PACKET, (client, handler, buf, responseSender) ->
        {
            client.execute(() ->
            {
                client.inGameHud.setTitle(Text.translatable(LangData.TITLE_CLEAR));
            });
        });
    }
}
