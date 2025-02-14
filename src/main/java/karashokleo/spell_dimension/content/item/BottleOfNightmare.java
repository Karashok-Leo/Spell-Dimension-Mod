package karashokleo.spell_dimension.content.item;

import karashokleo.l2hostility.content.item.consumable.DrinkableBottleItem;
import karashokleo.spell_dimension.content.component.GameStageComponent;
import karashokleo.spell_dimension.content.network.S2CTitle;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllPackets;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;

public class BottleOfNightmare extends DrinkableBottleItem
{
    public BottleOfNightmare(Settings settings)
    {
        super(settings);
    }

    @Override
    protected void doServerLogic(ServerPlayerEntity player)
    {
        if (player.getAbilities().creativeMode &&
            player.isSneaking())
        {
            GameStageComponent.resetDifficulty(player);
            sendTitle(player);
        } else if (GameStageComponent.isNormalMode(player))
        {
            GameStageComponent.addDifficulty(player);
            sendTitle(player);
        }
    }

    private static void sendTitle(ServerPlayerEntity player)
    {
        int difficulty = GameStageComponent.getDifficulty(player);
        MutableText text = SDTexts.TOOLTIP$DIFFICULTY_TIER$ENTER.get(
                SDTexts.getDifficultyTierText(difficulty)
        );
        S2CTitle title = new S2CTitle(text);
        AllPackets.toClientPlayer(player, title);
    }
}
