package karashokleo.spell_dimension;

import com.klikli_dev.modonomicon.api.datagen.LanguageProviderCache;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import karashokleo.leobrary.datagen.generator.*;
import karashokleo.leobrary.datagen.generator.init.GeneratorStorage;
import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.api.quest.QuestRegistry;
import karashokleo.spell_dimension.content.component.BuffComponentImpl;
import karashokleo.spell_dimension.content.component.EnlighteningComponent;
import karashokleo.spell_dimension.content.component.GameStageComponent;
import karashokleo.spell_dimension.content.component.QuestComponent;
import karashokleo.spell_dimension.content.misc.SDDebugCommand;
import karashokleo.spell_dimension.content.object.EnchantedModifier;
import karashokleo.spell_dimension.content.object.EnlighteningModifier;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.data.SpellTexts;
import karashokleo.spell_dimension.data.book.MagicGuidanceProvider;
import karashokleo.spell_dimension.data.book.MultiBlockProvider;
import karashokleo.spell_dimension.data.book.lang.BookChineseProvider;
import karashokleo.spell_dimension.data.book.lang.BookEnglishProvider;
import karashokleo.spell_dimension.data.generic.SDAdvancementProvider;
import karashokleo.spell_dimension.data.generic.SDRecipeProvider;
import karashokleo.spell_dimension.data.generic.SDTraitConfigProvider;
import karashokleo.spell_dimension.data.loot_bag.BagProvider;
import karashokleo.spell_dimension.data.loot_bag.ContentProvider;
import karashokleo.spell_dimension.init.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.data.DataOutput;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpellDimension implements ModInitializer, DataGeneratorEntrypoint, EntityComponentInitializer, GeneratorStorage
{
    public static final String MOD_ID = "spell-dimension";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Identifier modLoc(String id)
    {
        return new Identifier(MOD_ID, id);
    }

    @Override
    public void onInitialize()
    {
        AllItems.register();
        AllBlocks.register();
        AllEntities.register();
        AllWorldGen.register();
        AllQuests.register();
        AllLoots.register();
        AllBuffs.register();
        AllTraits.register();
        AllEnchantments.register();
        AllStatusEffects.register();
        AllRecipes.register();
        AllDamageTypes.register();
        AllSpells.register();
        AllGroups.register();
        AllParticles.register();
        AllMiscInit.register();

        AllPackets.init();
        EnchantedModifier.init();
        EnlighteningModifier.init();
        AllEvents.init();
        SDDebugCommand.init();
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator)
    {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        AllTags.register();
        SDTexts.register();
        SpellTexts.register();

        pack.addProvider(ContentProvider::new);
        pack.addProvider(BagProvider::new);

        LanguageProviderCache enUs = new LanguageProviderCache("en_us");
        LanguageProviderCache zhCn = new LanguageProviderCache("zh_cn");
        pack.addProvider((DataOutput output) -> new MagicGuidanceProvider(output, enUs, zhCn));
        pack.addProvider((FabricDataOutput output) -> new BookEnglishProvider(output, enUs));
        pack.addProvider((FabricDataOutput output) -> new BookChineseProvider(output, zhCn));
        pack.addProvider((FabricDataOutput output) -> new MultiBlockProvider(output));

        pack.addProvider(SDRecipeProvider::new);
        pack.addProvider(SDAdvancementProvider::new);
        pack.addProvider(SDTraitConfigProvider::new);

        GeneratorStorage.generate(this.getModId(), pack);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder)
    {
        DYNAMICS.register(registryBuilder);
    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry)
    {
        registry.registerForPlayers(AllComponents.ENLIGHTENING, player -> new EnlighteningComponent(), RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerForPlayers(AllComponents.GAME_STAGE, player -> new GameStageComponent(), RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerForPlayers(AllComponents.QUEST, player -> new QuestComponent(), RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerFor(LivingEntity.class, AllComponents.BUFF, BuffComponentImpl::new);
    }

    public static final LanguageGenerator EN_TEXTS = new LanguageGenerator("en_us");
    public static final LanguageGenerator ZH_TEXTS = new LanguageGenerator("zh_cn");
    public static final ModelGenerator MODELS = new ModelGenerator();
    public static final BlockLootGenerator BLOCK_LOOTS = new BlockLootGenerator();
    public static final TagGenerator<Item> ITEM_TAGS = new TagGenerator<>(RegistryKeys.ITEM);
    public static final TagGenerator<Block> BLOCK_TAGS = new TagGenerator<>(RegistryKeys.BLOCK);
    public static final TagGenerator<EntityType<?>> ENTITY_TYPE_TAGS = new TagGenerator<>(RegistryKeys.ENTITY_TYPE);
    public static final TagGenerator<Fluid> FLUID_TAGS = new TagGenerator<>(RegistryKeys.FLUID);
    public static final TagGenerator<DamageType> DAMAGE_TYPE_TAGS = new TagGenerator<>(RegistryKeys.DAMAGE_TYPE);
    public static final TagGenerator<Enchantment> ENCHANTMENT_TAGS = new TagGenerator<>(RegistryKeys.ENCHANTMENT);
    public static final TagGenerator<StatusEffect> EFFECT_TAGS = new TagGenerator<>(RegistryKeys.STATUS_EFFECT);
    public static final TagGenerator<Quest> QUEST_TAGS = new TagGenerator<>(QuestRegistry.QUEST_REGISTRY_KEY);
    public static final TagGenerator<Biome> BIOME_TAGS = new TagGenerator<>(RegistryKeys.BIOME);

    public static final DynamicRegistryGenerator<DamageType> DYNAMICS = new DynamicRegistryGenerator<>("Spell Dimension Dynamic Registries", RegistryKeys.DAMAGE_TYPE);

    @Override
    public String getModId()
    {
        return MOD_ID;
    }
}