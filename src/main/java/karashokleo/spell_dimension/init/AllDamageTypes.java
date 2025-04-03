package karashokleo.spell_dimension.init;

import karashokleo.leobrary.datagen.generator.DamageTypeGenerator;
import karashokleo.spell_dimension.SpellDimension;
import net.minecraft.entity.damage.DamageScaling;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.DamageTypeTags;

public class AllDamageTypes
{
    public static RegistryKey<DamageType> OBLIVION_BREASTPLATE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, SpellDimension.modLoc("oblivion_breastplate"));

    public static void register()
    {
        DamageTypeGenerator generator = new DamageTypeGenerator(SpellDimension.MOD_ID);

        generator.register(
                OBLIVION_BREASTPLATE,
                new DamageType("oblivion_breastplate", DamageScaling.NEVER, 0F),
                "%s died of Oblivion Breastplate",
                "%s died of Oblivion Breastplate",
                "%s死于湮灭护心镜",
                "%s死于湮灭护心镜",
                DamageTypeTags.BYPASSES_ARMOR,
                DamageTypeTags.BYPASSES_ENCHANTMENTS,
                DamageTypeTags.BYPASSES_RESISTANCE,
                DamageTypeTags.BYPASSES_INVULNERABILITY,
                DamageTypeTags.BYPASSES_EFFECTS,
                DamageTypeTags.NO_IMPACT
        );

        generator.generateDeathMsg(
                "real_damage",
                "%s died of real damage",
                "%s died of real damage by %s",
                "%s死于真实伤害",
                "%s死于%s的真实伤害"
        );
    }
}
