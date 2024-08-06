package karashokleo.spell_dimension.content.misc;

import com.mojang.brigadier.Command;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.mixin.SpellRegistryAccessor;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.spell_engine.SpellEngineMod;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.SpellContainer;
import net.spell_engine.internals.SpellRegistry;
import net.spell_engine.network.Packets;
import net.spell_engine.utils.WeaponCompatibility;

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

            dispatcher.register(
                    CommandManager
                            .literal("sd_reload_spell")
                            .requires(source -> source.hasPermissionLevel(2))
                            .executes(context -> executeReload(context.getSource().getServer()))
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

    private static int executeReload(MinecraftServer server)
    {
        SpellRegistry.loadSpells(server.getResourceManager());
        SpellRegistry.loadPools(server.getResourceManager());
        SpellRegistry.loadContainers(server.getResourceManager());
        WeaponCompatibility.initialize();
        SpellRegistryAccessor.invokeEncodedContent();

        PacketByteBuf configSerialized = Packets.ConfigSync.write(SpellEngineMod.config);
        for (ServerPlayerEntity player : PlayerLookup.all(server))
        {
            PacketSender sender = ServerPlayNetworking.getSender(player);
            sender.sendPacket(Packets.SpellRegistrySync.ID, SpellRegistry.encoded);
            sender.sendPacket(Packets.ConfigSync.ID, configSerialized);
        }
        return Command.SINGLE_SUCCESS;
    }
}
