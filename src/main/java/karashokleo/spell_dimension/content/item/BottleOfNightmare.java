package karashokleo.spell_dimension.content.item;

import karashokleo.l2hostility.content.item.consumable.DrinkableBottleItem;
import karashokleo.spell_dimension.content.component.GameStageComponent;
import net.minecraft.server.network.ServerPlayerEntity;

public class BottleOfNightmare extends DrinkableBottleItem
{
    public BottleOfNightmare(Settings settings)
    {
        super(settings);
    }

    @Override
    protected void doServerLogic(ServerPlayerEntity player)
    {
        if (GameStageComponent.isNormalMode(player))
            GameStageComponent.addDifficulty(player);
    }
}
