package karashokleo.spell_dimension.content.item.armor;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;

import java.util.Objects;

public record ArmorSet(
    ArmorItem helmet,
    ArmorItem chestplate,
    ArmorItem leggings,
    ArmorItem boots
)
{
    @Override
    public ArmorItem helmet()
    {
        return Objects.requireNonNull(helmet);
    }

    @Override
    public ArmorItem chestplate()
    {
        return Objects.requireNonNull(chestplate);
    }

    @Override
    public ArmorItem leggings()
    {
        return Objects.requireNonNull(leggings);
    }

    @Override
    public ArmorItem boots()
    {
        return Objects.requireNonNull(boots);
    }

    public ArmorItem get(ArmorItem.Type type)
    {
        return switch (type)
        {
            case HELMET -> helmet();
            case CHESTPLATE -> chestplate();
            case LEGGINGS -> leggings();
            case BOOTS -> boots();
        };
    }

    public ArmorItem get(EquipmentSlot slot)
    {
        return switch (slot)
        {
            case HEAD -> helmet();
            case CHEST -> chestplate();
            case LEGS -> leggings();
            case FEET -> boots();
            default -> throw new IllegalArgumentException("Invalid equipment slot: " + slot);
        };
    }
}
