package karashokleo.spell_dimension.init;

import karashokleo.l2hostility.content.item.traits.TraitSymbol;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.data.config.TraitConfig;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.leobrary.datagen.builder.ItemBuilder;
import karashokleo.leobrary.datagen.builder.NamedEntryBuilder;
import karashokleo.leobrary.datagen.builder.provider.DefaultLanguageGeneratorProvider;
import karashokleo.leobrary.datagen.builder.provider.ModelGeneratorProvider;
import karashokleo.leobrary.datagen.builder.provider.TagGeneratorProvider;
import karashokleo.leobrary.datagen.generator.TagGenerator;
import karashokleo.leobrary.datagen.util.StringUtil;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.trait.AirborneTrait;
import karashokleo.spell_dimension.content.trait.ShiftTrait;
import karashokleo.spell_dimension.data.generic.SDTraitConfigProvider;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.function.BiFunction;
import java.util.function.Consumer;

public class AllTraits
{
    public static ShiftTrait SHIFT;
    public static AirborneTrait AIRBORNE;

    public static void register()
    {
        SHIFT = Entry.of(
                        "shift",
                        new ShiftTrait(),
                        80, 80, 3, 175)
                .addEN()
                .addENDesc("Every %s seconds, swap its position with the target the next time it deals damage or takes damage")
                .addZH("换影")
                .addZHDesc("每隔%s秒，下一次造成伤害或受到伤害时交换自身与目标的位置")
                .configure(config -> config.addBlacklist(LHTags.SEMIBOSS))
                .register();
        AIRBORNE = Entry.of(
                        "airborne",
                        new AirborneTrait(),
                        120, 100, 4, 50)
                .addEN()
                .addENDesc("Every %s seconds, the next attack will knock the target into the air")
                .addZH("击飞")
                .addZHDesc("每隔%s秒，下一次攻击将击飞目标")
                .configure(config -> config.addWhitelist(EntityType.WARDEN).addWhitelist(LHTags.MELEE_WEAPON_TARGET))
                .register();
    }

    static class Entry<T extends MobTrait>
            extends NamedEntryBuilder<T>
            implements DefaultLanguageGeneratorProvider, TagGeneratorProvider<MobTrait>, ModelGeneratorProvider
    {
        public static <T extends MobTrait> Entry<T> of(String name, T trait, int cost, int weight, int maxRank, int minLevel)
        {
            return new Entry<>(name, trait, new TraitConfig.Config(SpellDimension.modLoc(name), cost, weight, maxRank, minLevel));
        }

        TraitConfig.Config config;
        String translationKey;

        private Entry(String name, T trait, TraitConfig.Config config)
        {
            super(name, trait);
            this.config = config;
        }

        public <I extends Item> BiFunction<String, I, ItemBuilder<I>> getItemBuilder()
        {
            return AllItems.Entry::of;
        }

        public T register()
        {
            Identifier id = getId();
            this.getItemBuilder()
                    .apply(name, new TraitSymbol(new FabricItemSettings()))
                    .addModel()
                    .addTag(LHTags.TRAIT_ITEM)
                    .register();
            SDTraitConfigProvider.add(content, config);
            return Registry.register(LHTraits.TRAIT, id, content);
        }

        public Entry<T> addEN()
        {
            return addEN(StringUtil.defaultName(name));
        }

        public Entry<T> addEN(String en)
        {
            this.getEnglishGenerator().addText(getTranslationKey(), en);
            return this;
        }

        public Entry<T> addENDesc(String en)
        {
            this.getEnglishGenerator().addText(getTranslationKey() + ".desc", en);
            return this;
        }

        public Entry<T> addZH(String zh)
        {
            this.getChineseGenerator().addText(getTranslationKey(), zh);
            return this;
        }

        public Entry<T> addZHDesc(String zh)
        {
            this.getChineseGenerator().addText(getTranslationKey() + ".desc", zh);
            return this;
        }

        public Entry<T> addTag(TagKey<MobTrait> key)
        {
            this.getTagGenerator(LHTraits.TRAIT_KEY).getOrCreateContainer(key).add(getId());
            return this;
        }

        @SafeVarargs
        public final Entry<T> addTag(TagKey<MobTrait>... keys)
        {
            TagGenerator<MobTrait> tagGenerator = getTagGenerator(LHTraits.TRAIT_KEY);
            for (TagKey<MobTrait> key : keys)
                tagGenerator.getOrCreateContainer(key).add(getId());
            return this;
        }

        public String getTranslationKey()
        {
            if (translationKey == null)
                translationKey = getId().toTranslationKey(LHTraits.TRAIT_KEY.getValue().getPath());
            return translationKey;
        }

        public Entry<T> configure(Consumer<TraitConfig.Config> consumer)
        {
            consumer.accept(config);
            return this;
        }

        @Override
        public String getNameSpace()
        {
            return SpellDimension.MOD_ID;
        }
    }
}
