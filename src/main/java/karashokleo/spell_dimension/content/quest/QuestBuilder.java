package karashokleo.spell_dimension.content.quest;

import karashokleo.leobrary.datagen.builder.NamedEntryBuilder;
import karashokleo.leobrary.datagen.builder.provider.DefaultLanguageGeneratorProvider;
import karashokleo.leobrary.datagen.builder.provider.TagGeneratorProvider;
import karashokleo.leobrary.datagen.generator.TagGenerator;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.api.quest.QuestRegistry;
import karashokleo.spell_dimension.api.quest.QuestUsage;
import karashokleo.spell_dimension.config.QuestToEntryConfig;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Pair;

import java.util.HashSet;
import java.util.Set;

public class QuestBuilder<Q extends Quest> extends NamedEntryBuilder<Q> implements DefaultLanguageGeneratorProvider, TagGeneratorProvider
{
    private static final Set<Pair<Quest, Quest>> RELATIONS = new HashSet<>();

    public QuestBuilder(String name, Q content)
    {
        super(name, content);
    }

    public static <Q extends Quest> QuestBuilder<Q> of(String name, Q quest)
    {
        return new QuestBuilder<>(name, quest);
    }

    public static void buildRelations()
    {
        for (Pair<Quest, Quest> relation : RELATIONS)
        {
            QuestUsage.configure(relation.getLeft(), relation.getRight());
        }
        RELATIONS.clear();
    }

    public Q register()
    {
        QuestRegistry.register(getId(), content);
        return content;
    }

    public QuestBuilder<Q> addTag(TagKey<Quest> key)
    {
        this.getTagGenerator(QuestRegistry.QUEST_REGISTRY_KEY).getOrCreateContainer(key).add(getId());
        return this;
    }

    @SafeVarargs
    public final QuestBuilder<Q> addTag(TagKey<Quest>... keys)
    {
        TagGenerator<Quest> tagGenerator = getTagGenerator(QuestRegistry.QUEST_REGISTRY_KEY);
        for (TagKey<Quest> key : keys)
        {
            tagGenerator.getOrCreateContainer(key).add(getId());
        }
        return this;
    }

    public QuestBuilder<Q> addDependencies(Quest... dependencies)
    {
        for (Quest quest : dependencies)
        {
            RELATIONS.add(new Pair<>(quest, content));
        }
        return this;
    }

    public QuestBuilder<Q> toEntry(String path)
    {
        QuestToEntryConfig.register(content, path);
        return this;
    }

    public QuestBuilder<Q> addEnTitle(String text)
    {
        getEnglishGenerator().addText(getTitleKey(), text);
        return this;
    }

    public QuestBuilder<Q> addZhTitle(String text)
    {
        getChineseGenerator().addText(getTitleKey(), text);
        return this;
    }

    public QuestBuilder<Q> addEnDesc(String text)
    {
        getEnglishGenerator().addText(getDescriptionKey(), text);
        return this;
    }

    public QuestBuilder<Q> addZhDesc(String text)
    {
        getChineseGenerator().addText(getDescriptionKey(), text);
        return this;
    }

    public QuestBuilder<Q> addEnFeedback(String text)
    {
        getEnglishGenerator().addText(getFeedbackKey(), text);
        return this;
    }

    public QuestBuilder<Q> addZhFeedback(String text)
    {
        getChineseGenerator().addText(getFeedbackKey(), text);
        return this;
    }

    public String getTitleKey()
    {
        return super.getTranslationKey("quest") + ".title";
    }

    public String getDescriptionKey()
    {
        return super.getTranslationKey("quest") + ".description";
    }

    public String getFeedbackKey()
    {
        return super.getTranslationKey("quest") + ".feedback";
    }

    @Override
    public String getNameSpace()
    {
        return SpellDimension.MOD_ID;
    }
}
