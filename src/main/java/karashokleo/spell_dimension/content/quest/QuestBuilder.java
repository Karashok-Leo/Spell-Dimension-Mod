package karashokleo.spell_dimension.content.quest;

import karashokleo.leobrary.datagen.builder.NamedEntryBuilder;
import karashokleo.leobrary.datagen.builder.provider.DefaultLanguageGeneratorProvider;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.api.quest.QuestRegistry;
import karashokleo.spell_dimension.api.quest.QuestUsage;
import karashokleo.spell_dimension.config.QuestToEntryConfig;
import net.minecraft.util.Pair;

import java.util.HashSet;
import java.util.Set;

public class QuestBuilder<Q extends Quest> extends NamedEntryBuilder<Q> implements DefaultLanguageGeneratorProvider
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
            QuestUsage.configure(relation.getLeft(), relation.getRight());
        RELATIONS.clear();
    }

    public Q register()
    {
        QuestRegistry.register(getId(), content);
        return content;
    }

    public QuestBuilder<Q> addDependencies(Quest... dependencies)
    {
        for (Quest quest : dependencies)
            RELATIONS.add(new Pair<>(quest, content));
        return this;
    }

    public QuestBuilder<Q> toEntry(String path)
    {
        QuestToEntryConfig.register(content, path);
        return this;
    }

    public QuestBuilder<Q> addEnTitle(String desc)
    {
        getEnglishGenerator().addText(getTitleKey(), desc);
        return this;
    }

    public QuestBuilder<Q> addZhTitle(String desc)
    {
        getChineseGenerator().addText(getTitleKey(), desc);
        return this;
    }

    public QuestBuilder<Q> addEnDesc(String desc)
    {
        getEnglishGenerator().addText(getDescKey(), desc);
        return this;
    }

    public QuestBuilder<Q> addZhDesc(String desc)
    {
        getChineseGenerator().addText(getDescKey(), desc);
        return this;
    }

    public String getTitleKey()
    {
        return super.getTranslationKey("quest") + ".title";
    }

    public String getDescKey()
    {
        return super.getTranslationKey("quest") + ".description";
    }

    @Override
    public String getNameSpace()
    {
        return SpellDimension.MOD_ID;
    }
}
