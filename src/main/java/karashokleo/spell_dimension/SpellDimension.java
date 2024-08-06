package karashokleo.spell_dimension;

import com.klikli_dev.modonomicon.api.datagen.LanguageProviderCache;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import karashokleo.spell_dimension.content.component.BlazingMarkComponent;
import karashokleo.spell_dimension.content.component.EnlighteningComponent;
import karashokleo.spell_dimension.content.component.NucleusComponent;
import karashokleo.spell_dimension.content.event.PlayerHealthEvent;
import karashokleo.spell_dimension.content.item.logic.EnchantedModifier;
import karashokleo.spell_dimension.content.item.logic.EnlighteningModifier;
import karashokleo.spell_dimension.content.misc.DebugStaffCommand;
import karashokleo.spell_dimension.data.book.MagicGuidanceProvider;
import karashokleo.spell_dimension.data.book.lang.BookChineseProvider;
import karashokleo.spell_dimension.data.book.lang.BookEnglishProvider;
import karashokleo.spell_dimension.data.generic.*;
import karashokleo.spell_dimension.data.loot_bag.BagProvider;
import karashokleo.spell_dimension.data.loot_bag.ContentProvider;
import karashokleo.spell_dimension.init.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataOutput;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class SpellDimension implements ModInitializer, DataGeneratorEntrypoint, EntityComponentInitializer
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
        AllGroups.register();
        AllLoots.register();
        AllSkills.register();
        AllEnchantments.register();
        AllStatusEffects.register();
        AllRecipeSerializers.register();
        AllSpells.register();
        EnchantedModifier.init();
        EnlighteningModifier.init();
        PlayerHealthEvent.init();
        DebugStaffCommand.init();
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator)
    {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ContentProvider::new);
        pack.addProvider(BagProvider::new);

        LanguageProviderCache enUs = new LanguageProviderCache("en_us");
        LanguageProviderCache zhCn = new LanguageProviderCache("zh_cn");
        pack.addProvider((DataOutput output) -> new MagicGuidanceProvider(output, enUs, zhCn));
        pack.addProvider((FabricDataOutput output) -> new BookEnglishProvider(output, enUs));
        pack.addProvider((FabricDataOutput output) -> new BookChineseProvider(output, zhCn));

        pack.addProvider(SDEnglishProvider::new);
        pack.addProvider(SDChineseProvider::new);
        pack.addProvider(SDModelProvider::new);
        pack.addProvider(SDRecipeProvider::new);
        pack.addProvider(SDItemTagProvider::new);
    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry)
    {
        registry.registerForPlayers(AllComponents.ENLIGHTENING, player -> new EnlighteningComponent(), RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerFor(LivingEntity.class, AllComponents.BLAZING_MARK, BlazingMarkComponent::new);
        registry.registerFor(LivingEntity.class, AllComponents.NUCLEUS, NucleusComponent::new);
    }
}