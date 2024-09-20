package karashokleo.spell_dimension;

import dev.xkmc.l2tabs.tabs.core.TabToken;
import dev.xkmc.l2tabs.tabs.inventory.InvTabData;
import dev.xkmc.l2tabs.tabs.inventory.TabRegistry;
import karashokleo.enchantment_infusion.api.render.InfusionTableTileRenderer;
import karashokleo.leobrary.gui.api.GuiOverlayRegistry;
import karashokleo.leobrary.gui.api.TextureOverlayRegistry;
import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.api.quest.QuestUsage;
import karashokleo.spell_dimension.content.item.DynamicSpellBookItem;
import karashokleo.spell_dimension.content.item.QuestScrollItem;
import karashokleo.spell_dimension.content.item.essence.base.ColorProvider;
import karashokleo.spell_dimension.content.item.logic.EnchantedModifier;
import karashokleo.spell_dimension.content.misc.INoClip;
import karashokleo.spell_dimension.content.network.S2CSpellDash;
import karashokleo.spell_dimension.content.network.S2CTitle;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllBlocks;
import karashokleo.spell_dimension.init.AllEntities;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllStatusEffects;
import karashokleo.spell_dimension.mixin.RollManagerInvoker;
import karashokleo.spell_dimension.render.*;
import karashokleo.spell_dimension.screen.QuestOverlay;
import karashokleo.spell_dimension.screen.SpellPowerTab;
import net.combatroll.internals.RollingEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.item.Item;
import net.minecraft.text.TranslatableTextContent;
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

        EntityRendererRegistry.register(AllEntities.LOCATE_PORTAL, LocatePortalRenderer::new);

        GuiOverlayRegistry.registerLayer(6, new QuestOverlay());

        TextureOverlayRegistry.register(PHASE_LAYER, 0.5F, (client, player, context, tickDelta) -> INoClip.noClip(player));

        TAB_SPELL_POWER = TabRegistry.GROUP.registerTab(3600, SpellPowerTab::new,
                () -> Armors.wizardRobeSet.head, SDTexts.TEXT$SPELL_POWER_INFO.get());

        for (Item item : AllItems.COLOR_PROVIDERS)
            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> (stack.getItem() instanceof ColorProvider c) ? c.getColor(stack) : 0xffffff, item);

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) ->
        {
            registrationHelper.register(new NucleusRenderer<>(entityRenderer));
        });

        CustomModels.registerModelIds(List.of(
                FROSTED_MODEL,
                ICICLE_MODEL,
                CONVERGE_MODEL,
                GENERIC_MODEL
        ));

        CustomParticleStatusEffect.register(AllStatusEffects.PHASE, new PhaseParticleSpawner());
        CustomParticleStatusEffect.register(AllStatusEffects.FROSTED, new FrostedParticleSpawner());
        CustomModelStatusEffect.register(AllStatusEffects.FROSTED, new FrostedEffectRenderer());

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
    }

    public static void itemTooltip()
    {
        Identifier tooltipFinal = SpellDimension.modLoc("tooltip_final");
        ItemTooltipCallback.EVENT.addPhaseOrdering(Event.DEFAULT_PHASE, tooltipFinal);
        ItemTooltipCallback.EVENT.register(EnchantedModifier::levelTooltip);
        ItemTooltipCallback.EVENT.register((stack, context, lines) ->
        {
            if (context.isAdvanced() && stack.getItem() instanceof QuestScrollItem item)
            {
                ClientPlayerEntity player = MinecraftClient.getInstance().player;
                Optional<Quest> optional = item.getQuest(stack);
                if (player != null &&
                    optional.isPresent() &&
                    QuestUsage.isQuestCompleted(player, optional.get()))
                    lines.add(SDTexts.TEXT$QUEST_COMPLETED.get());
            }
        });
        ItemTooltipCallback.EVENT.register(tooltipFinal, (stack, context, lines) ->
        {
            if (stack.getItem() instanceof DynamicSpellBookItem)
                lines.removeIf(line ->
                        line.getContent() instanceof TranslatableTextContent content &&
                        content.getKey().equals("spell.tooltip.spell_binding_tip"));
        });
    }
}
