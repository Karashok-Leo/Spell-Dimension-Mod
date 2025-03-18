package karashokleo.spell_dimension.content.trait;

import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.util.UuidUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.internals.SpellRegistry;
import net.spell_power.api.SpellSchool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class SpellTrait extends MobTrait
{
    protected final Identifier spellId;
    @Nullable
    protected Spell spell;
    protected final float powerFactor;

    public SpellTrait(Identifier spellId, float powerFactor)
    {
        super(Formatting.LIGHT_PURPLE);
        this.spellId = spellId;
        this.powerFactor = powerFactor;
    }

    @Override
    public boolean compatibleWith(MobTrait trait, int lv)
    {
        return !(trait instanceof SpellTrait);
    }

    @Override
    public void postInit(MobDifficulty difficulty, LivingEntity mob, int lv)
    {
        var diff = MobDifficulty.get(mob);
        if (diff.isEmpty()) return;
        int mobLevel = diff.get().getLevel();
        SpellSchool school = this.getSpell().school;
        EntityAttributeInstance attributeInstance = mob.getAttributeInstance(school.attribute);
        if (attributeInstance == null) return;
        String spellIdString = this.getSpellId().toString();
        UUID uuid = UuidUtil.getUUIDFromString(spellIdString);
        attributeInstance.removeModifier(uuid);
        EntityAttributeModifier modifier = new EntityAttributeModifier(uuid, "Spell Trait Bonus - %s".formatted(spellIdString), mobLevel * powerFactor, EntityAttributeModifier.Operation.ADDITION);
        attributeInstance.addPersistentModifier(modifier);
    }

    public Identifier getSpellId()
    {
        return spellId;
    }

    public Spell getSpell()
    {
        if (spell == null)
        {
            spell = SpellRegistry.getSpell(spellId);
        }
        return spell;
    }

    @Override
    public int getColor()
    {
        return this.getSpell().school.color;
    }

    @Override
    public @NotNull String getNameKey()
    {
        if (desc == null)
            desc = spellId.toTranslationKey("spell", "name");
        return desc;
    }

    @Override
    public @NotNull String getDescKey()
    {
        return spellId.toTranslationKey("spell", "description");
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(SDTexts.TEXT$SPELL_TRAIT$POWER.get(Math.round(powerFactor * 100)));
        super.addDetail(list);
    }
}
