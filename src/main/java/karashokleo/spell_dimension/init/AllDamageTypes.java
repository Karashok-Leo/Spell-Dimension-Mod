package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.SpellDimension;
import net.minecraft.entity.damage.DamageScaling;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.TagKey;

public class AllDamageTypes
{
    public static RegistryKey<DamageType> OBLIVION_BREASTPLATE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, SpellDimension.modLoc("oblivion_breastplate"));

    public static void register()
    {
        register(
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
    }

    @SafeVarargs
    public static void register(RegistryKey<DamageType> registryKey, DamageType damageType, String deathMsgEn, String deathMsgPlayerEn, String deathMsgZh, String deathMsgPlayerZh, TagKey<DamageType>... tags)
    {
        SpellDimension.DYNAMICS.add(registryKey, damageType);
        String deathMsg = "death.attack." + damageType.msgId();
        String deathMsgPlayer = "death.attack." + damageType.msgId() + ".player";
        SpellDimension.EN_TEXTS.addText(deathMsg, deathMsgEn);
        SpellDimension.EN_TEXTS.addText(deathMsgPlayer, deathMsgPlayerEn);
        SpellDimension.ZH_TEXTS.addText(deathMsg, deathMsgZh);
        SpellDimension.ZH_TEXTS.addText(deathMsgPlayer, deathMsgPlayerZh);
        for (TagKey<DamageType> tag : tags)
            SpellDimension.DAMAGE_TYPE_TAGS.getOrCreateContainer(tag).add(registryKey);
    }
}
