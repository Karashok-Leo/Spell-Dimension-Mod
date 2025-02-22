package karashokleo.spell_dimension.content.misc;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import karashokleo.spell_dimension.mixin.modded.SpellRegistryAccessor;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.spell_engine.SpellEngineMod;
import net.spell_engine.internals.SpellRegistry;
import net.spell_engine.network.Packets;
import net.spell_engine.utils.WeaponCompatibility;

import java.util.Collection;

public class SDDebugCommand
{
    public static void init()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
        {
            dispatcher.register(
                    CommandManager
                            .literal("spell_dimension")
                            .then(sendDeath())
                            .then(fixFakeDeath())
                            .then(reloadSpells())
            );

        });
    }

    private static LiteralArgumentBuilder<ServerCommandSource> sendDeath()
    {
        return CommandManager
                .literal("send_stats")
                .executes(
                        context ->
                                sendStats(
                                        context.getSource(),
                                        ImmutableList.of(context.getSource().getEntityOrThrow())
                                )
                )
                .then(
                        CommandManager
                                .argument(
                                        "targets",
                                        EntityArgumentType.entities()
                                )
                                .executes(
                                        context -> sendStats(
                                                context.getSource(),
                                                EntityArgumentType.getEntities(context, "targets")
                                        )
                                )
                );
    }

    private static int sendStats(ServerCommandSource source, Collection<? extends Entity> targets)
    {
        for (Entity target : targets)
        {
            if (target instanceof LivingEntity living)
            {
                source.sendFeedback(() -> Text.literal("Health: " + living.getHealth()), false);
                source.sendFeedback(() -> Text.literal("Already died? " + living.isDead()), false);
            }
        }
        return Command.SINGLE_SUCCESS;
    }

    private static LiteralArgumentBuilder<ServerCommandSource> fixFakeDeath()
    {
        return CommandManager
                .literal("fix_fake_death")
                .executes(context ->
                {
                    ServerPlayerEntity player = context.getSource().getPlayer();
                    if (player != null)
                    {
                        player.setHealth(0);
                        player.onDeath(player.getDamageSources().genericKill());
                    }
                    return Command.SINGLE_SUCCESS;
                });
    }

    private static LiteralArgumentBuilder<ServerCommandSource> reloadSpells()
    {
        return CommandManager
                .literal("reload_spells")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(context -> executeReload(context.getSource().getServer()));
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
