package karashokleo.spell_dimension.content.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.spell_power.api.SpellSchool;

import java.util.Set;
import java.util.UUID;

public class SpellBladeEnchantment extends Enchantment
{
    public static final UUID MAINHAND_MODIFIER_ID = UUID.fromString("9ed774ce-6bd0-4216-9ca5-1ec2adf3b4ed");
    public static final UUID OFFHAND_MODIFIER_ID = UUID.fromString("ffad9f1c-e94d-492c-b874-bb5a81f8a248");

    private final Set<SpellSchool> schools;

    public SpellBladeEnchantment(SpellSchool... schools)
    {
        super(Rarity.UNCOMMON, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
        this.schools = Set.of(schools);
    }

    public Set<SpellSchool> getSchools()
    {
        return schools;
    }

    @Override
    public int getMinLevel()
    {
        return 1;
    }

    @Override
    public int getMaxLevel()
    {
        return 1;
    }
}
