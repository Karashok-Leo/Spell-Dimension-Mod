package karashokleo.spell_dimension.content.spell;

import com.mojang.datafixers.util.Pair;
import karashokleo.spell_dimension.config.recipe.LocateSpellConfig;
import karashokleo.spell_dimension.content.entity.LocatePortalEntity;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.init.AllTags;
import karashokleo.spell_dimension.util.FutureTask;
import karashokleo.spell_dimension.util.TeleportUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.Structure;
import net.spell_engine.entity.SpellProjectile;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class LocateSpell
{
    public static final double BREAK_CHANCE = 0.3;

    public static void handle(SpellProjectile projectile, Identifier spellId, BlockHitResult hitResult)
    {
        if (!spellId.equals(AllSpells.LOCATE)) return;
        if (!(projectile.getWorld() instanceof ServerWorld world)) return;
        if (!(projectile.getOwner() instanceof PlayerEntity player)) return;
        BlockPos blockPos = hitResult.getBlockPos();
        if (!isLocateTargetBlock(world, blockPos)) return;

        ItemStack offHandStack = player.getOffHandStack();
        Item item = offHandStack.getItem();
        RegistryKey<Structure> structureRegistryKey = LocateSpellConfig.STRUCTURE_CONFIG.get(item, world.getRegistryKey());
        TagKey<Biome> biomeTagKey = LocateSpellConfig.BIOME_CONFIG.get(item, world.getRegistryKey());

        AtomicBoolean consume = new AtomicBoolean(false);

        if (structureRegistryKey != null)
        {
            Text spotName = LocateSpellConfig.getSpotName(structureRegistryKey);
            player.sendMessage(SDTexts.TEXT$LOCATING.get(spotName), false);

            FutureTask.submit(
                    locateStructure(world, blockPos, structureRegistryKey),
                    optional ->
                    {
                        if (optional.isPresent())
                        {
                            spawnLocatePortal(world, optional.get(), blockPos);
                            consume.set(true);
                        } else
                            player.sendMessage(Text.translatable("commands.locate.structure.not_found", spotName), false);
                    }
            );
        } else if (biomeTagKey != null)
        {
            Text spotName = LocateSpellConfig.getSpotName(biomeTagKey);
            player.sendMessage(SDTexts.TEXT$LOCATING.get(spotName), false);

            FutureTask.submit(
                    locateBiome(world, blockPos, biomeTagKey),
                    optional ->
                    {
                        if (optional.isPresent())
                        {
                            spawnLocatePortal(world, optional.get(), blockPos);
                            consume.set(true);
                        } else
                            player.sendMessage(Text.translatable("commands.locate.biome.not_found", spotName), false);
                    }
            );
        } else player.sendMessage(SDTexts.TEXT$INVALID_KEY_ITEM.get(), true);

        if (consume.get() && !player.getAbilities().creativeMode)
        {
            offHandStack.decrement(1);
            if (player.getRandom().nextFloat() < BREAK_CHANCE)
                world.breakBlock(blockPos, false);
        }
    }

    private static CompletableFuture<Optional<BlockPos>> locateBiome(ServerWorld world, BlockPos pos, TagKey<Biome> tagKey)
    {
        Pair<BlockPos, RegistryEntry<Biome>> pair = world.locateBiome(e -> e.isIn(tagKey), pos, 6400, 32, 64);
        if (pair == null)
            return CompletableFuture.completedFuture(Optional.empty());
        return TeleportUtil.getTeleportPosFuture(world, pair.getFirst());
    }

    private static CompletableFuture<Optional<BlockPos>> locateStructure(ServerWorld world, BlockPos pos, RegistryKey<Structure> registryKey)
    {
        Optional<RegistryEntryList<Structure>> optional = world
                .getRegistryManager()
                .get(RegistryKeys.STRUCTURE)
                .getEntry(registryKey)
                .map(RegistryEntryList::of);
        if (optional.isEmpty())
            return CompletableFuture.completedFuture(Optional.empty());
        Pair<BlockPos, RegistryEntry<Structure>> pair = world
                .getChunkManager()
                .getChunkGenerator()
                .locateStructure(world, optional.get(), pos, 100, false);
        if (pair == null)
            return CompletableFuture.completedFuture(Optional.empty());
        return TeleportUtil.getTeleportPosFuture(world, pair.getFirst());
    }

    private static void spawnLocatePortal(ServerWorld world, BlockPos destination, BlockPos pos)
    {
        world.spawnParticles(ParticleTypes.PORTAL, pos.getX() + 0.5, pos.getY() - 0.5 + 3, pos.getZ() + 0.5, 1000, 0, 0, 0, 2);
        LocatePortalEntity locatePortalEntity = new LocatePortalEntity(world, destination);
        locatePortalEntity.setPosition(pos.getX() + 0.5, pos.getY() + 3, pos.getZ() + 0.5);
        world.spawnEntity(locatePortalEntity);
    }

    private static boolean isLocateTargetBlock(World world, BlockPos pos)
    {
        return world.getBlockState(pos).isIn(AllTags.LOCATE_TARGET);
    }
}
