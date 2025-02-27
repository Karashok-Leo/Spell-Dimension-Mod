package karashokleo.spell_dimension.content.component;

import dev.onyxstudios.cca.api.v3.component.Component;
import karashokleo.spell_dimension.content.object.EnlighteningModifier;
import karashokleo.spell_dimension.init.AllComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EnlighteningComponent implements Component
{
    private final List<EnlighteningModifier> modifiers = new ArrayList<>();

    public static EnlighteningComponent get(PlayerEntity player)
    {
        return AllComponents.ENLIGHTENING.get(player);
    }

    @Override
    public void readFromNbt(NbtCompound tag)
    {
        this.modifiers.clear();
        if (tag.contains(EnlighteningModifier.NBT_KEY, NbtElement.LIST_TYPE))
        {
            NbtList list = tag.getList(EnlighteningModifier.NBT_KEY, NbtElement.COMPOUND_TYPE);
            for (int i = 0; i < list.size(); i++)
            {
                EnlighteningModifier modifier = EnlighteningModifier.fromNbt(list.getCompound(i));
                if (modifier != null) this.modifiers.add(modifier);
            }
        }
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag)
    {
        NbtList list = new NbtList();
        for (EnlighteningModifier modifier : this.modifiers)
            list.add(modifier.toNbt());
        tag.put(EnlighteningModifier.NBT_KEY, list);
    }

    public List<EnlighteningModifier> getModifiers()
    {
        return modifiers;
    }
}
