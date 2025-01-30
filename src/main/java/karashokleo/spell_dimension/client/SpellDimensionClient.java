package karashokleo.spell_dimension.client;

import dev.xkmc.l2tabs.tabs.core.TabToken;
import dev.xkmc.l2tabs.tabs.inventory.InvTabData;
import dev.xkmc.l2tabs.tabs.inventory.TabRegistry;
import karashokleo.enchantment_infusion.api.render.InfusionTableTileRenderer;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.l2hostility.content.logic.DifficultyLevel;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.leobrary.gui.api.GuiOverlayRegistry;
import karashokleo.leobrary.gui.api.TextureOverlayRegistry;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.api.quest.QuestUsage;
import karashokleo.spell_dimension.client.quest.QuestItemTooltipComponent;
import karashokleo.spell_dimension.client.quest.QuestItemTooltipData;
import karashokleo.spell_dimension.client.render.*;
import karashokleo.spell_dimension.client.screen.ConsciousCoreOverlay;
import karashokleo.spell_dimension.client.screen.GameOverOverlay;
import karashokleo.spell_dimension.client.screen.QuestOverlay;
import karashokleo.spell_dimension.client.screen.SpellPowerTab;
import karashokleo.spell_dimension.content.item.DynamicSpellBookItem;
import karashokleo.spell_dimension.content.item.essence.base.ColorProvider;
import karashokleo.spell_dimension.content.item.logic.EnchantedModifier;
import karashokleo.spell_dimension.content.misc.INoClip;
import karashokleo.spell_dimension.content.network.S2CFloatingItem;
import karashokleo.spell_dimension.content.network.S2CSpellDash;
import karashokleo.spell_dimension.content.network.S2CTitle;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllBlocks;
import karashokleo.spell_dimension.init.AllEntities;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllStatusEffects;
import karashokleo.spell_dimension.mixin.client.RollManagerInvoker;
import net.combatroll.internals.RollingEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.item.Item;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.spell_engine.api.effect.CustomModelStatusEffect;
import net.spell_engine.api.effect.CustomParticleStatusEffect;
import net.spell_engine.api.render.CustomModels;
import net.wizards.item.Armors;

import java.util.List;
import java.util.Optional;

public class SpellDimensionClient implements ClientModInitializer
{
    public static final Identifier PHASE_LAYER = SpellDimension.modLoc("textures/spell_effect/phase.png");
    public static final Identifier CONVERGE_MODEL = SpellDimension.modLoc("spell_projectile/converge");
    public static final Identifier FROSTED_MODEL = SpellDimension.modLoc("spell_effect/frosted");
    public static final Identifier ICICLE_MODEL = SpellDimension.modLoc("spell_projectile/icicle");
    public static final Identifier GENERIC_MODEL = SpellDimension.modLoc("spell_projectile/generic");

    public static TabToken<InvTabData, SpellPowerTab> TAB_SPELL_POWER;

    @Override
    public void onInitializeClient()
    {
        itemTooltip();

        BlockEntityRendererFactories.register(AllBlocks.SPELL_INFUSION_PEDESTAL_TILE, ctx -> new InfusionTableTileRenderer<>(1.05F, ctx));
        BlockEntityRendererFactories.register(AllBlocks.CONSCIOUSNESS_CORE_TILE, ConsciousnessCoreRenderer::new);

        FluidRenderHandlerRegistry.INSTANCE.register(
                AllBlocks.STILL_CONSCIOUSNESS,
                AllBlocks.FLOWING_CONSCIOUSNESS,
                new ConsciousnessFluidRenderHandler()
        );

        BlockRenderLayerMap.INSTANCE.putBlocks(
                RenderLayer.getTranslucent(),
                AllBlocks.PROTECTIVE_COVER.block(),
                AllBlocks.CONSCIOUSNESS_CORE.block()
        );
        BlockRenderLayerMap.INSTANCE.putFluids(
                RenderLayer.getTranslucent(),
                AllBlocks.STILL_CONSCIOUSNESS,
                AllBlocks.FLOWING_CONSCIOUSNESS
        );

        EntityRendererRegistry.register(AllEntities.LOCATE_PORTAL, LocatePortalRenderer::new);
        EntityRendererRegistry.register(AllEntities.CONSCIOUSNESS_EVENT, EmptyEntityRenderer::new);
        EntityRendererRegistry.register(AllEntities.BLACK_HOLE, BlackHoleRenderer::new);

        TooltipComponentCallback.EVENT.register(data ->
        {
            if (data instanceof QuestItemTooltipData questData)
                return new QuestItemTooltipComponent(questData);
            return null;
        });

        GuiOverlayRegistry.registerLayer(6, new QuestOverlay());
        GuiOverlayRegistry.registerLayer(7, new ConsciousCoreOverlay());
        GuiOverlayRegistry.registerLayer(3, new GameOverOverlay());

        TextureOverlayRegistry.register(PHASE_LAYER, 0.5F, (client, player, context, tickDelta) -> INoClip.noClip(player));

        TAB_SPELL_POWER = TabRegistry.GROUP.registerTab(3600, SpellPowerTab::new,
                () -> Armors.wizardRobeSet.head, SDTexts.TEXT$SPELL_POWER_INFO.get());

        for (Item item : AllItems.COLOR_PROVIDERS)
            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> (stack.getItem() instanceof ColorProvider c) ? c.getColor(stack) : 0xffffff, item);

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> registrationHelper.register(new NucleusRenderer<>(entityRenderer)));

        CustomModels.registerModelIds(List.of(
                FROSTED_MODEL,
                ICICLE_MODEL,
                CONVERGE_MODEL,
                GENERIC_MODEL
        ));

        CustomParticleStatusEffect.register(AllStatusEffects.PHASE, new PhaseParticleSpawner());
        CustomParticleStatusEffect.register(AllStatusEffects.FROSTED, new FrostedParticleSpawner());
        CustomParticleStatusEffect.register(AllStatusEffects.DIVINE_AURA, new DivineAuraParticleSpawner());
        CustomModelStatusEffect.register(AllStatusEffects.FROSTED, new FrostedEffectRenderer());

        init_network();
        ClientAirHopHandler.register();
    }

    private static void init_network()
    {
        ClientPlayNetworking.registerGlobalReceiver(S2CTitle.TYPE, (packet, player, responseSender) ->
        {
            InGameHud inGameHud = MinecraftClient.getInstance().inGameHud;
            inGameHud.setTitle(packet.title());
            if (packet.subTitle() != null) inGameHud.setSubtitle(packet.subTitle());
        });
        ClientPlayNetworking.registerGlobalReceiver(S2CSpellDash.TYPE, (packet, player, responseSender) ->
        {
            if (player instanceof RollingEntity rolling)
                ((RollManagerInvoker) rolling.getRollManager()).invokeRechargeRoll(player);
        });
        ClientPlayNetworking.registerGlobalReceiver(S2CFloatingItem.TYPE, (packet, player, responseSender) ->
                MinecraftClient.getInstance().gameRenderer.showFloatingItem(packet.stack()));
    }

    private static void itemTooltip()
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

        ItemTooltipCallback.EVENT.register((stack, context, lines) ->
        {
            if (!context.isAdvanced()) return;
            if (stack.isOf(AllItems.QUEST_SCROLL))
            {
                var player = MinecraftClient.getInstance().player;
                Optional<Quest> optional = AllItems.QUEST_SCROLL.getQuest(stack);
                if (player != null &&
                    optional.isPresent() &&
                    QuestUsage.isQuestCompleted(player, optional.get()))
                    lines.add(SDTexts.TEXT$QUEST_COMPLETED.get());
            }
        });

        ItemTooltipCallback.EVENT.register((stack, context, lines) ->
        {
            if (stack.isOf(AllItems.FLEX_BREASTPLATE))
            {
                var player = MinecraftClient.getInstance().player;
                if (player == null) return;
                lines.add(SDTexts.TOOLTIP$FLEX_BREASTPLATE$DAMAGE_FACTOR.get(
                        "%.1f%%".formatted((1 - AllItems.FLEX_BREASTPLATE.getDamageFactor(player)) * 100)
                ).formatted(Formatting.RED));
            }
        });

        ItemTooltipCallback.EVENT.register((stack, context, lines) ->
        {
            if (stack.isOf(TrinketItems.CURSE_PRIDE))
            {
                var player = MinecraftClient.getInstance().player;
                if (player == null) return;
                int level = DifficultyLevel.ofAny(player);
                double rate = LHConfig.common().items.curse.prideDamageBonus;
                lines.add(SDTexts.TOOLTIP$CURSE_PRIDE_2.get(
                        "%.1f%%".formatted((level * rate) * 100)
                ).formatted(Formatting.AQUA));
            }
        });
    }
}
