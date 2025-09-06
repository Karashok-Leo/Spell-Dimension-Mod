package karashokleo.spell_dimension.init;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import karashokleo.spell_dimension.content.object.EnchantedModifier;
import karashokleo.spell_dimension.mixin.modded.SpellRegistryAccessor;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
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

public class AllCommands
{
    public static void init()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
        {
            dispatcher.register(
                    CommandManager
                            .literal("spell_dimension")
                            .then(printHandStack())
                            .then(fixFakeDeath())
                            .then(reloadSpells())
                            .then(convertModifiers())
            );
        });
    }

    private static LiteralArgumentBuilder<ServerCommandSource> printHandStack()
    {
        return CommandManager
                .literal("hand")
                .executes(AllCommands::executePrintHandStack);
    }

    private static LiteralArgumentBuilder<ServerCommandSource> fixFakeDeath()
    {
        return CommandManager
                .literal("fix_fake_death")
                .executes(AllCommands::executeFixFakeDeath);
    }

    private static LiteralArgumentBuilder<ServerCommandSource> reloadSpells()
    {
        return CommandManager
                .literal("reload_spells")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(AllCommands::executeReload);
    }

    private static LiteralArgumentBuilder<ServerCommandSource> convertModifiers()
    {
        return CommandManager
                .literal("convert_modifiers")
                .executes(AllCommands::executeConvertModifiers);
    }

    private static int executePrintHandStack(CommandContext<ServerCommandSource> context)
    {
        ServerPlayerEntity player = context.getSource().getPlayer();
        if (player != null)
        {
            var nbt = player.getMainHandStack().getNbt();
            if (nbt != null)
            {
                context.getSource().sendMessage(Text.literal(nbt.toString()));
            }
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int executeFixFakeDeath(CommandContext<ServerCommandSource> context)
    {
        ServerPlayerEntity player = context.getSource().getPlayer();
        if (player != null)
        {
            player.setHealth(0);
            player.onDeath(player.getDamageSources().genericKill());
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int executeReload(CommandContext<ServerCommandSource> context)
    {
        MinecraftServer server = context.getSource().getServer();
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

    private static int executeConvertModifiers(CommandContext<ServerCommandSource> context)
    {
        ServerPlayerEntity player = context.getSource().getPlayer();
        if (player != null)
        {
            ItemStack stack = player.getMainHandStack();
            EnchantedModifier.tryConvert(stack);
        }
        return Command.SINGLE_SUCCESS;
    }
}
