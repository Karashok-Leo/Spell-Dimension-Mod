package karashokleo.spell_dimension.content.item;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.item.essence.base.ColorProvider;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.util.SpellContainerUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.spell_engine.api.item.trinket.SpellBookTrinketItem;
import net.spell_engine.api.spell.SpellContainer;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellContainerHelper;
import net.spell_engine.spellbinding.SpellBinding;
import net.spell_engine.spellbinding.SpellBindingScreenHandler;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class DynamicSpellBookItem extends SpellBookTrinketItem implements ColorProvider
{
    public static final Identifier DYNAMIC_POOL = SpellDimension.modLoc("dynamic");
    public static final Map<SpellSchool, Identifier> POOLS = Map.of(
            SpellSchools.ARCANE, SpellDimension.modLoc("arcane"),
            SpellSchools.FIRE, SpellDimension.modLoc("fire"),
            SpellSchools.FROST, SpellDimension.modLoc("frost"),
            SpellSchools.HEALING, SpellDimension.modLoc("healing"),
            SpellSchools.LIGHTNING, DYNAMIC_POOL,
            SpellSchools.SOUL, DYNAMIC_POOL
    );

    private final SpellSchool school;
    private final int grade;

    public DynamicSpellBookItem(SpellSchool school, int grade)
    {
        super(POOLS.get(school), new FabricItemSettings().maxCount(1));
        this.school = school;
        this.grade = grade;
    }

    public int getMaxSpellCount()
    {
        return this.grade + 4;
    }

    public SpellContainer getSpellContainer()
    {
        return new SpellContainer(SpellContainer.ContentType.MAGIC, false, this.getPoolId().toString(), this.getMaxSpellCount(), List.of());
    }

    @Override
    public Text getName()
    {
        return this.getName(this.getDefaultStack());
    }

    @Override
    public Text getName(ItemStack stack)
    {
        return SDTexts.getGradeText(this.grade)
                .append(SDTexts.getSchoolText(this.school))
                .append(SDTexts.TEXT_SPELL_BOOK.get())
                .setStyle(Style.EMPTY.withColor(this.getColor(stack)));
    }

    @Override
    public int getColor(ItemStack stack)
    {
        return this.school.color;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
    }

    public void tryAddScroll(ItemStack stack, ItemStack scroll, PlayerEntity player)
    {
        if (scroll.getItem() instanceof SpellScrollItem scrollItem)
        {
            SpellInfo spellInfo = scrollItem.getSpellInfo(scroll);
            if (spellInfo == null)
            {
                player.sendMessage(SDTexts.TOOLTIP_INVALID.get());
                return;
            }
            if (this.school != spellInfo.spell().school)
            {
                player.sendMessage(SDTexts.TEXT_INCOMPATIBLE_SCHOOL.get());
                return;
            }
            SpellBinding.State.ApplyState state = SpellBinding.State.of(spellInfo.id(), stack, 0, 0, 0).state;
            switch (state)
            {
                case INVALID -> player.sendMessage(SDTexts.TOOLTIP_INVALID.get());
                case ALREADY_APPLIED ->
                        player.sendMessage(Text.translatable("gui.spell_engine.spell_binding.already_bound"));
                case NO_MORE_SLOT ->
                        player.sendMessage(Text.translatable("gui.spell_engine.spell_binding.no_more_slots"));
                case APPLICABLE ->
                {
                    SpellContainer container = SpellContainerHelper.containerFromItemStack(stack);
                    if (container == null || !container.isValid()) return;
                    SpellContainer modifiedContainer = SpellContainerUtil.addSpell(container, spellInfo.id());
                    SpellContainerHelper.addContainerToItemStack(modifiedContainer, stack);
                    scroll.decrement(1);
                    this.playSound(player);
                }
            }
        }
    }

    public Optional<ItemStack> removeSpell(ItemStack stack, PlayerEntity player)
    {
        SpellContainer container = SpellContainerHelper.containerFromItemStack(stack);
        if (container == null || !container.isValid())
        {
            player.sendMessage(SDTexts.TOOLTIP_INVALID.get());
            return Optional.empty();
        }
        int size = container.spell_ids.size();
        if (size < 1)
        {
            player.sendMessage(SDTexts.TEXT_BLANK_BOOK.get());
            return Optional.empty();
        }
        String spellId = container.spell_ids.get(size - 1);
        ItemStack scroll = AllItems.SPELL_SCROLL.getStack(new Identifier(spellId));
        SpellContainer modifiedContainer = SpellContainerUtil.removeLastSpell(container);
        SpellContainerHelper.addContainerToItemStack(modifiedContainer, stack);
        return Optional.of(scroll);
    }

    private void playSound(Entity entity)
    {
        entity.playSound(SpellBindingScreenHandler.soundEvent, 0.8F, 0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
    }

    @Override
    public boolean onStackClicked(ItemStack holding, Slot slot, ClickType clickType, PlayerEntity player)
    {
        if (clickType != ClickType.RIGHT) return false;
        ItemStack clicking = slot.getStack();
        if (clicking.isEmpty())
        {
            this.removeSpell(holding, player).ifPresent(slot::insertStack);
            this.playSound(player);
        } else tryAddScroll(holding, clicking, player);
        return true;
    }

    @Override
    public boolean onClicked(ItemStack clicking, ItemStack holding, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference)
    {
        if (clickType == ClickType.RIGHT && slot.canTakePartial(player))
        {
            if (holding.isEmpty())
                removeSpell(clicking, player).ifPresent((itemStack) ->
                {
                    this.playSound(player);
                    cursorStackReference.set(itemStack);
                });
            else tryAddScroll(clicking, holding, player);
            return true;
        } else return false;
    }

    @Override
    public void onItemEntityDestroyed(ItemEntity entity)
    {
        ItemUsage.spawnItemContents(entity, getAllScrolls(entity.getStack()));
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid)
    {
        Multimap<EntityAttribute, EntityAttributeModifier> modifiers = super.getModifiers(stack, slot, entity, uuid);
        modifiers.put(this.school.attribute, new EntityAttributeModifier(uuid, "SpellBookModifier", (this.grade + 1) * 0.2, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        return modifiers;
    }

    public Stream<ItemStack> getAllScrolls(ItemStack stack)
    {
        SpellContainer container = SpellContainerHelper.containerFromItemStack(stack);
        if (container == null || !container.isValid()) return Stream.empty();
        return container.spell_ids.stream().map(s -> AllItems.SPELL_SCROLL.getStack(new Identifier(s)));
    }
}
