package karashokleo.spell_dimension;

import com.klikli_dev.modonomicon.api.datagen.LanguageProviderCache;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import karashokleo.leobrary.datagen.generator.LanguageGenerator;
import karashokleo.leobrary.datagen.generator.ModelGenerator;
import karashokleo.leobrary.datagen.generator.TagGenerator;
import karashokleo.leobrary.datagen.generator.init.GeneratorStorage;
import karashokleo.spell_dimension.content.component.BuffComponentImpl;
import karashokleo.spell_dimension.content.component.EndStageComponent;
import karashokleo.spell_dimension.content.component.EnlighteningComponent;
import karashokleo.spell_dimension.content.component.QuestComponent;
import karashokleo.spell_dimension.content.item.logic.EnchantedModifier;
import karashokleo.spell_dimension.content.item.logic.EnlighteningModifier;
import karashokleo.spell_dimension.content.misc.DebugStaffCommand;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.data.book.MagicGuidanceProvider;
import karashokleo.spell_dimension.data.book.lang.BookChineseProvider;
import karashokleo.spell_dimension.data.book.lang.BookEnglishProvider;
import karashokleo.spell_dimension.data.generic.SDRecipeProvider;
import karashokleo.spell_dimension.data.loot_bag.BagProvider;
import karashokleo.spell_dimension.data.loot_bag.ContentProvider;
import karashokleo.spell_dimension.init.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataOutput;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class SpellDimension implements ModInitializer, DataGeneratorEntrypoint, EntityComponentInitializer, GeneratorStorage
{
    public static final String MOD_ID = "spell-dimension";

    public static Identifier modLoc(String id)
    {
        return new Identifier(MOD_ID, id);
    }

    @Override
    public void onInitialize()
    {
        AllItems.register();
        AllTags.register();
        AllGroups.register();
        AllLoots.register();
        AllBuffs.register();
        AllQuests.register();
        AllEnchantments.register();
        AllStatusEffects.register();
        AllRecipeSerializers.register();
        AllSpells.register();

        EnchantedModifier.init();
        EnlighteningModifier.init();
        AllEvents.init();
        DebugStaffCommand.init();
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator)
    {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        SDTexts.register();

        pack.addProvider(ContentProvider::new);
        pack.addProvider(BagProvider::new);

        LanguageProviderCache enUs = new LanguageProviderCache("en_us");
        LanguageProviderCache zhCn = new LanguageProviderCache("zh_cn");
        pack.addProvider((DataOutput output) -> new MagicGuidanceProvider(output, enUs, zhCn));
        pack.addProvider((FabricDataOutput output) -> new BookEnglishProvider(output, enUs));
        pack.addProvider((FabricDataOutput output) -> new BookChineseProvider(output, zhCn));

        pack.addProvider(SDRecipeProvider::new);

        GeneratorStorage.generate(this.getModId(), pack);
    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry)
    {
        registry.registerForPlayers(AllComponents.ENLIGHTENING, player -> new EnlighteningComponent(), RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerForPlayers(AllComponents.ENTER_END, player -> new EndStageComponent(), RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerForPlayers(AllComponents.QUEST, player -> new QuestComponent(), RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerFor(LivingEntity.class, AllComponents.BUFF, BuffComponentImpl::new);
    }

    public static final LanguageGenerator EN_TEXTS = new LanguageGenerator("en_us");
    public static final LanguageGenerator ZH_TEXTS = new LanguageGenerator("zh_cn");
    public static final ModelGenerator MODELS = new ModelGenerator();
    public static final TagGenerator<Item> ITEM_TAGS = new TagGenerator<>(RegistryKeys.ITEM);

    @Override
    public String getModId()
    {
        return MOD_ID;
    }
}