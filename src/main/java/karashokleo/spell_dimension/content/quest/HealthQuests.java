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
                .addEnFeedback("You realize that you can enhance your physique by killing monsters that are stronger than you. You are ecstatic that this can go a long way to make up for your shortcomings.")
                .addZhFeedback("你发现自己可以通过击杀那些比你强大的怪物来增强自己的体质。你欣喜若狂，这可以很大程度上补足自己的短板。")
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
                .addEnFeedback("Reality pours cold water on you. As you adapt to your surroundings, your physique growth gradually tends to stop. Next you may only be able to continue to build up your physique by ingesting special foods.")
                .addZhFeedback("现实给你浇了一盆冷水。随着对周遭环境的适应，你的体质增长逐渐趋于停止。接下来你可能只能通过摄入一些特殊食物来继续增强体质了。")
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
                .addEnFeedback("Without realizing it your life force has become ponderous. You are no longer the weak human being who languishes in reality.")
                .addZhFeedback("不知不觉间你的生命力已变得庞然不息。你已经不再是那个在现实中苟延残喘的弱小人类了。")
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
                .addEnFeedback("You have fully learned how to strengthen yourself with magic, and the only thing that will limit you next will be the magic cap itself.")
                .addZhFeedback("你已经完全学会了如何用魔力强化自己，接下来限制你的只会是魔力的上限本身。")
                .toEntry("power/health")
                .addTag(AllTags.BRANCH, AllTags.CHALLENGE)
                .addDependencies(HEALTH_2)
                .register();
    }
}
