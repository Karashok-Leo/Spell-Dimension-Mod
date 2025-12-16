package karashokleo.spell_dimension.content.object;

import karashokleo.spell_dimension.util.TagUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface Summoner
{
    static Summoner fromEntityType(EntityType<?> entityType)
    {
        return new Impl(new Codecs.TagEntryId(Registries.ENTITY_TYPE.getId(entityType), false));
    }

    static Summoner fromId(Identifier id)
    {
        return new Impl(new Codecs.TagEntryId(id, false));
    }

    static Summoner fromTag(TagKey<EntityType<?>> tag)
    {
        return new Impl(new Codecs.TagEntryId(tag.id(), true));
    }

    @Nullable
    static Summoner fromNbt(NbtElement nbt)
    {
        return Codecs.TAG_ENTRY_ID
            .decode(NbtOps.INSTANCE, nbt)
            .result().map(pair -> new Impl(pair.getFirst())).orElse(null);
    }

    Optional<EntityType<?>> getEntityType(ServerWorld world);

    @Nullable
    NbtElement toNbt();

    record Impl(Codecs.TagEntryId entry) implements Summoner
    {
        @Override
        public Optional<EntityType<?>> getEntityType(ServerWorld world)
        {
            return this.entry.tag() ?
                this.getEntityTypeByTag(world.getRandom()) :
                Optional.of(this.getEntityTypeById());
        }

        @Nullable
        @Override
        public NbtElement toNbt()
        {
            return Codecs.TAG_ENTRY_ID
                .encodeStart(NbtOps.INSTANCE, this.entry)
                .result()
                .orElse(null);
        }

        private Optional<EntityType<?>> getEntityTypeByTag(Random random)
        {
            return Registries.ENTITY_TYPE
                .getEntryList(TagUtil.entityTypeTag(this.entry.id()))
                .flatMap(list ->
                {
                    var randomOne = list.getRandom(random);
                    while (randomOne.isPresent() &&
                        randomOne.get().value() == EntityType.ENDER_DRAGON)
                    {
                        randomOne = list.getRandom(random);
                    }
                    return randomOne;
                })
                .map(RegistryEntry::value);
        }

        private EntityType<?> getEntityTypeById()
        {
            return Registries.ENTITY_TYPE.get(this.entry.id());
        }
    }
}
