package karashokleo.spell_dimension.content.quest;

import karashokleo.spell_dimension.content.quest.special.HealthQuest;
import karashokleo.spell_dimension.init.AllTags;
import net.minecraft.item.Items;

public class HealthQuests
{
    public static HealthQuest HEALTH_0;
    public static HealthQuest HEALTH_1;
    public static HealthQuest HEALTH_2;
    public static HealthQuest HEALTH_3;

    public static void register()
    {
        HEALTH_0 = QuestBuilder.of(
                        "health_0",
                        new HealthQuest(
                                20,
                                Items.ENCHANTED_GOLDEN_APPLE::getDefaultStack
                        )
                )
                .addEnDesc("Kill monsters to increase your %s to %s")
                .addZhDesc("击杀怪物以提升你的%s至%s")
                .toEntry("power/health")
                .addTag(AllTags.BRANCH)
                .addDependencies(BaseQuests.FIRST_DAY)
                .register();
        HEALTH_1 = QuestBuilder.of(
                        "health_1",
                        new HealthQuest(
                                66,
                                Items.ENCHANTED_GOLDEN_APPLE::getDefaultStack
                        )
                )
                .addEnDesc("Increase your %s to %s")
                .addZhDesc("提升你的%s至%s")
                .toEntry("power/health")
                .addTag(AllTags.BRANCH)
                .addDependencies(HEALTH_0)
                .register();
        HEALTH_2 = QuestBuilder.of(
                        "health_2",
                        new HealthQuest(
                                333,
                                Items.ENCHANTED_GOLDEN_APPLE::getDefaultStack
                        )
                )
                .addEnDesc("Increase your %s to %s")
                .addZhDesc("提升你的%s至%s")
                .toEntry("power/health")
                .addTag(AllTags.BRANCH)
                .addDependencies(HEALTH_1)
                .register();
        HEALTH_3 = QuestBuilder.of(
                        "health_3",
                        new HealthQuest(
                                999,
                                Items.ENCHANTED_GOLDEN_APPLE::getDefaultStack
                        )
                )
                .addEnTitle("Magical Feeding")
                .addZhTitle("魔力反哺")
                .addEnDesc("Increase your %s to %s")
                .addZhDesc("提升你的%s至%s")
                .toEntry("power/health")
                .addTag(AllTags.BRANCH, AllTags.CHALLENGE)
                .addDependencies(HEALTH_2)
                .register();
    }
}
