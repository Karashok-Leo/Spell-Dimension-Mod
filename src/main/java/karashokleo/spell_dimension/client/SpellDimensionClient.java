package karashokleo.spell_dimension.client;

import dev.xkmc.l2tabs.tabs.core.TabToken;
import dev.xkmc.l2tabs.tabs.inventory.InvTabData;
import dev.xkmc.l2tabs.tabs.inventory.TabRegistry;
import karashokleo.enchantment_infusion.api.render.InfusionTableTileRenderer;
import karashokleo.leobrary.gui.api.GuiOverlayRegistry;
import karashokleo.leobrary.gui.api.TextureOverlayRegistry;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.client.misc.AdditionalTooltip;
import karashokleo.spell_dimension.client.misc.ClientAirHopHandler;
import karashokleo.spell_dimension.client.misc.SoulControlHandler;
import karashokleo.spell_dimension.client.quest.QuestItemTooltipComponent;
import karashokleo.spell_dimension.client.quest.QuestItemTooltipData;
import karashokleo.spell_dimension.client.render.*;
import karashokleo.spell_dimension.client.screen.ConsciousCoreOverlay;
import karashokleo.spell_dimension.client.screen.GameOverOverlay;
import karashokleo.spell_dimension.client.screen.QuestOverlay;
import karashokleo.spell_dimension.client.screen.SpellPowerTab;
import karashokleo.spell_dimension.content.item.essence.base.ColorProvider;
import karashokleo.spell_dimension.content.item.upgrade.IllusionUpgradeTab;
import karashokleo.spell_dimension.content.misc.NoClip;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeGuiManager;
import net.spell_engine.api.effect.CustomModelStatusEffect;
import net.spell_engine.api.effect.CustomParticleStatusEffect;
import net.spell_engine.api.render.CustomModels;
import net.spell_engine.api.render.StunParticleSpawner;
import net.wizards.item.Armors;

import java.util.List;

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
        AdditionalTooltip.register();

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
        EntityRendererRegistry.register(AllEntities.CHAIN_LIGHTNING, EmptyEntityRenderer::new);
        EntityRendererRegistry.register(AllEntities.BALL_LIGHTNING, BallLightningRenderer::new);
        EntityRendererRegistry.register(AllEntities.RAILGUN, RailgunRenderer::new);

        BuiltinItemRendererRegistry.INSTANCE.register(AllItems.MACRO_ELECTRON, new MacroElectronRenderer());

        EntityModelLayerRegistry.registerModelLayer(BallLightningRenderer.MODEL_LAYER_LOCATION, BallLightningRenderer::createBodyLayer);

        TooltipComponentCallback.EVENT.register(data ->
        {
            if (data instanceof QuestItemTooltipData questData)
                return new QuestItemTooltipComponent(questData);
            return null;
        });

        GuiOverlayRegistry.registerLayer(6, new QuestOverlay());
        GuiOverlayRegistry.registerLayer(7, new ConsciousCoreOverlay());
        GuiOverlayRegistry.registerLayer(3, new GameOverOverlay());

        TextureOverlayRegistry.register(PHASE_LAYER, 0.5F, (client, player, context, tickDelta) -> NoClip.noClip(player));

        TAB_SPELL_POWER = TabRegistry.GROUP.registerTab(3600, SpellPowerTab::new,
                () -> Armors.wizardRobeSet.head, SDTexts.TEXT$SPELL_POWER_INFO.get());

        for (Item item : AllItems.COLOR_PROVIDERS)
            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> (stack.getItem() instanceof ColorProvider c) ? c.getColor(stack) : 0xffffff, item);

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) ->
        {
            registrationHelper.register(new NucleusRenderer<>(entityRenderer));
            registrationHelper.register(new QuantumFieldRenderer<>(entityRenderer));
            if (entityRenderer instanceof MobEntityRenderer<?, ?> mobEntityRenderer)
            {
                registrationHelper.register(new MobBeamRenderer<>(mobEntityRenderer));
            }
        });

        CustomModels.registerModelIds(List.of(
                FROSTED_MODEL,
                ICICLE_MODEL,
                CONVERGE_MODEL,
                GENERIC_MODEL
        ));

        CustomParticleStatusEffect.register(AllStatusEffects.PHASE, new PhaseParticleSpawner());
        CustomParticleStatusEffect.register(AllStatusEffects.FROSTED, new FrostedParticleSpawner());
        CustomParticleStatusEffect.register(AllStatusEffects.DIVINE_AURA, new DivineAuraParticleSpawner());
        CustomParticleStatusEffect.register(AllStatusEffects.STUN, new StunParticleSpawner());

        CustomModelStatusEffect.register(AllStatusEffects.FROSTED, new FrostedEffectRenderer());

        AllPackets.initClient();
        ClientAirHopHandler.register();

        UpgradeGuiManager.registerTab(AllItems.REFORGE_TYPE, IllusionUpgradeTab::new);

        AllParticles.registerClient();

        SoulControlHandler.register();
    }
}
