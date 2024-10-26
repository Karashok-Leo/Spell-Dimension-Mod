package karashokleo.spell_dimension.content.spell;

import com.mojang.datafixers.util.Pair;
import karashokleo.spell_dimension.config.recipe.LocateSpellConfig;
import karashokleo.spell_dimension.content.entity.LocatePortalEntity;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.init.AllTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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

public class LocateSpell
{
    public static final double BREAK_CHANCE = 0.3;

    public static void handle(SpellProjectile projectile, Identifier spellId, BlockHitResult hitResult)
    {
        if (!spellId.equals(AllSpells.LOCATE)) return;
        if (!(projectile.getWorld() instanceof ServerWorld world)) return;
        if (!(projectile.getOwner() instanceof LivingEntity living)) return;
        BlockPos blockPos = hitResult.getBlockPos();
        if (!isLocateTargetBlock(world, blockPos)) return;

        ItemStack offHandStack = living.getOffHandStack();
        RegistryKey<Structure> structureRegistryKey = LocateSpellConfig.getStructure(offHandStack.getItem());
        TagKey<Biome> biomeTagKey = LocateSpellConfig.getBiome(offHandStack.getItem());

        boolean consume = false;

        if (structureRegistryKey != null)
        {
            Optional<BlockPos> optional = locateStructure(world, blockPos, structureRegistryKey);
            if (optional.isPresent())
            {
                spawnLocatePortal(world, optional.get(), blockPos);
                consume = true;
            } else if (living instanceof PlayerEntity player)
            {
                player.sendMessage(Text.translatable("commands.locate.structure.not_found", structureRegistryKey.getValue()), true);
            }
        } else if (biomeTagKey != null)
        {
            Optional<BlockPos> optional = locateBiome(world, blockPos, biomeTagKey);
            if (optional.isPresent())
            {
                spawnLocatePortal(world, optional.get(), blockPos);
                consume = true;
            } else if (living instanceof PlayerEntity player)
            {
                player.sendMessage(Text.translatable("commands.locate.biome.not_found", biomeTagKey.id()), true);
            }
        } else if (living instanceof PlayerEntity player)
            player.sendMessage(SDTexts.TEXT$INVALID_KEY_ITEM.get(), true);

        if (consume)
        {
            if (!(living instanceof PlayerEntity player &&
                  player.getAbilities().creativeMode))
                offHandStack.decrement(1);
            if (living.getRandom().nextFloat() < BREAK_CHANCE)
                world.breakBlock(blockPos, false);
        }
    }

    private static Optional<BlockPos> locateBiome(ServerWorld world, BlockPos pos, TagKey<Biome> tagKey)
    {
        Pair<BlockPos, RegistryEntry<Biome>> pair = world.locateBiome(e -> e.isIn(tagKey), pos, 6400, 32, 64);
        return Optional.ofNullable(pair).map(Pair::getFirst);
    }

    private static Optional<BlockPos> locateStructure(ServerWorld world, BlockPos pos, RegistryKey<Structure> registryKey)
    {
        RegistryEntryList<Structure> registryEntryList = world
                .getRegistryManager()
                .get(RegistryKeys.STRUCTURE)
                .getEntry(registryKey)
                .map(RegistryEntryList::of)
                .orElseThrow();
        Pair<BlockPos, RegistryEntry<Structure>> pair = world
                .getChunkManager()
                .getChunkGenerator()
                .locateStructure(world, registryEntryList, pos, 100, false);
        return Optional.ofNullable(pair).map(Pair::getFirst);
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
