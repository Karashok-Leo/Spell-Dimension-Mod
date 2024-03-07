package net.karashokleo.spelldimension.misc;

import com.mojang.brigadier.Command;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.karashokleo.spelldimension.item.AllItems;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.registry.Registries;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.SpellContainer;
import net.spell_engine.internals.SpellRegistry;

public class DebugStaffCommand
{
    public static void init()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
        {
            dispatcher.register(
                    CommandManager
                            .literal("sd_debug_staff")
                            .requires(source -> source.hasPermissionLevel(2))
                            .then(
                                    CommandManager
                                            .literal("add")
                                            .then(
                                                    CommandManager
                                                            .argument("spellId", IdentifierArgumentType.identifier())
                                                            .executes(context ->
                                                            {
                                                                Identifier spellId = IdentifierArgumentType.getIdentifier(context, "spellId");
                                                                Spell spell = SpellRegistry.getSpell(spellId);
                                                                if (spell == null) return 0;
                                                                else return executeAdd(spellId);
                                                            })
                                            )
                            )
                            .then(
                                    CommandManager
                                            .literal("clear")
                                            .executes(context -> executeClear())
                            )
            );
        });
    }

    private static int executeAdd(Identifier spellId)
    {
        SpellContainer container = SpellRegistry.containerForItem(Registries.ITEM.getId(AllItems.DEBUG_STAFF));
        if (container == null ||
                !container.isValid() ||
                container.spell_ids.contains(spellId.toString()))
            return 0;
        container.spell_ids.add(spellId.toString());
        return Command.SINGLE_SUCCESS;
    }

    private static int executeClear()
    {
        SpellContainer container = SpellRegistry.containerForItem(Registries.ITEM.getId(AllItems.DEBUG_STAFF));
        if (container == null || !container.isValid()) return 0;
        container.spell_ids.clear();
        return Command.SINGLE_SUCCESS;
    }
}
