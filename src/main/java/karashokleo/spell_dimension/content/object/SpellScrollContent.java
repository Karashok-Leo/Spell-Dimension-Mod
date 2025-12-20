package karashokleo.spell_dimension.content.object;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import karashokleo.loot_bag.api.common.content.ContentType;
import karashokleo.loot_bag.api.common.content.StacksContent;
import karashokleo.loot_bag.api.common.icon.Icon;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.util.RandomUtil;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.SpellContainer;
import net.spell_engine.internals.SpellContainerHelper;
import net.spell_engine.internals.SpellRegistry;
import net.spell_power.api.SpellSchool;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SpellScrollContent extends StacksContent
{
    public static final Codec<SpellScrollContent> CODEC = RecordCodecBuilder.create((ins) -> ins.group(contentFields(ins).t1()).apply(ins, SpellScrollContent::new));
    public static final ContentType<SpellScrollContent> TYPE = new ContentType<>(CODEC);

    public SpellScrollContent(Icon icon)
    {
        super(icon);
    }

    @Override
    protected ContentType<?> getType()
    {
        return TYPE;
    }

    @Override
    protected List<ItemStack> getLootStacks(ServerPlayerEntity serverPlayerEntity)
    {
        Set<Identifier> spells = AllSpells.getSpells(type -> type == ScrollType.CRAFTING || type == ScrollType.EVENT_AWARD);

        // remove by school
        Set<SpellSchool> schools = new HashSet<>();
        schools.addAll(SchoolUtil.getLivingSchools(serverPlayerEntity));
        schools.addAll(SchoolUtil.getLivingSecondarySchools(serverPlayerEntity));
        spells.removeIf(id ->
        {
            Spell spell = SpellRegistry.getSpell(id);
            return spell != null && !schools.contains(spell.school);
        });

        // remove duplicated spells
        SpellContainer spellContainer = SpellContainerHelper.getEquipped(serverPlayerEntity.getMainHandStack(), serverPlayerEntity);
        if (spellContainer != null)
        {
            for (String spellId : spellContainer.spell_ids)
            {
                spells.remove(new Identifier(spellId));
            }
        }
        if (spells.isEmpty())
        {
            return Collections.emptyList();
        }
        Identifier ans = RandomUtil.randomFromSet(serverPlayerEntity.getRandom(), spells);
        return Collections.singletonList(AllItems.SPELL_SCROLL.getStack(ans));
    }
}
