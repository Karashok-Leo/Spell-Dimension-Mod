package karashokleo.spell_dimension;

import karashokleo.spell_dimension.content.item.DynamicSpellBookItem;
import karashokleo.spell_dimension.content.item.essence.base.ColorProvider;
import karashokleo.spell_dimension.content.item.essence.logic.EnchantedModifier;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllStatusEffects;
import karashokleo.spell_dimension.render.FrostedEffectRenderer;
import karashokleo.spell_dimension.render.FrostedParticleSpawner;
import karashokleo.spell_dimension.render.NucleusRenderer;
import karashokleo.spell_dimension.render.PhaseParticleSpawner;
import karashokleo.spell_dimension.util.NetworkUtil;
import karashokleo.spell_dimension.util.ParticleUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.item.Item;
import net.minecraft.text.TranslatableTextContent;
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
        itemTooltip();

        for (Item item : AllItems.COLOR_PROVIDERS)
            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> (stack.getItem() instanceof ColorProvider c) ? c.getColor(stack) : 0xffffff, item);

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) ->
        {
            registrationHelper.register(new NucleusRenderer<>(entityRenderer));
        });

        CustomModels.registerModelIds(List.of(
                FROSTED_MODEL,
                ICICLE_MODEL,
                CONVERGE_MODEL
        ));

        CustomParticleStatusEffect.register(AllStatusEffects.PHASE_EFFECT, new PhaseParticleSpawner());
        CustomParticleStatusEffect.register(AllStatusEffects.FROSTED_EFFECT, new FrostedParticleSpawner());
        CustomModelStatusEffect.register(AllStatusEffects.FROSTED_EFFECT, new FrostedEffectRenderer());

        ClientPlayNetworking.registerGlobalReceiver(NetworkUtil.DUST_PACKET, (client, handler, buf, responseSender) ->
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

    }

    public static void itemTooltip()
    {
        Identifier tooltipFinal = SpellDimension.modLoc("tooltip_final");
        ItemTooltipCallback.EVENT.addPhaseOrdering(Event.DEFAULT_PHASE, tooltipFinal);
        ItemTooltipCallback.EVENT.register(EnchantedModifier::levelTooltip);
        ItemTooltipCallback.EVENT.register(tooltipFinal, (stack, context, lines) ->
        {
            if (stack.getItem() instanceof DynamicSpellBookItem)
                lines.removeIf(line ->
                        line.getContent() instanceof TranslatableTextContent content &&
                                content.getKey().equals("spell.tooltip.spell_binding_tip"));
        });
    }
}
