package karashokleo.spell_dimension.client.render;

import karashokleo.spell_dimension.content.entity.FakePlayerEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.*;
import net.minecraft.client.render.entity.model.ArmorEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.UUID;

public class FakePlayerRenderer extends LivingEntityRenderer<FakePlayerEntity, PlayerEntityModel<FakePlayerEntity>>
{
    private static final HashMap<UUID, Identifier> SKIN_CACHE = new HashMap<>();
    private static final Identifier STEVE_TEXTURE = new Identifier("textures/entity/player/wide/steve.png");

    public FakePlayerRenderer(EntityRendererFactory.Context ctx)
    {
        this(ctx, false);
    }

    public FakePlayerRenderer(EntityRendererFactory.Context ctx, boolean slim)
    {
        super(ctx, new PlayerEntityModel<>(ctx.getPart(slim ? EntityModelLayers.PLAYER_SLIM : EntityModelLayers.PLAYER), slim), 0.5F);
        this.addFeature(new ArmorFeatureRenderer<>(
            this,
            new ArmorEntityModel<>(ctx.getPart(slim ?
                EntityModelLayers.PLAYER_SLIM_INNER_ARMOR :
                EntityModelLayers.PLAYER_INNER_ARMOR)),
            new ArmorEntityModel<>(ctx.getPart(slim ?
                EntityModelLayers.PLAYER_SLIM_OUTER_ARMOR :
                EntityModelLayers.PLAYER_OUTER_ARMOR)),
            ctx.getModelManager()
        ));
        this.addFeature(new HeldItemFeatureRenderer<>(this, ctx.getHeldItemRenderer()));
        this.addFeature(new StuckArrowsFeatureRenderer<>(ctx, this));
        this.addFeature(new HeadFeatureRenderer<>(this, ctx.getModelLoader(), ctx.getHeldItemRenderer()));
        this.addFeature(new ElytraFeatureRenderer<>(this, ctx.getModelLoader()));
        this.addFeature(new StuckStingersFeatureRenderer<>(this));
    }

    @Override
    public Identifier getTexture(FakePlayerEntity entity)
    {
        UUID uuid = entity.getPlayerUUID();

        if (uuid == null)
        {
            return STEVE_TEXTURE;
        }

        if (SKIN_CACHE.containsKey(uuid))
        {
            return SKIN_CACHE.get(uuid);
        }

        var networkHandler = MinecraftClient.getInstance().getNetworkHandler();
        if (networkHandler != null)
        {
            var info = networkHandler.getPlayerListEntry(uuid);
            if (info != null)
            {
                Identifier skin = info.getSkinTexture();
                SKIN_CACHE.put(uuid, skin);
                return skin;
            }
        }

        Identifier skin = DefaultSkinHelper.getTexture(uuid);
        SKIN_CACHE.put(uuid, skin);
        return skin;
    }
}
