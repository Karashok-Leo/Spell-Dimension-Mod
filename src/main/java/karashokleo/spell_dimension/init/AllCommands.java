package karashokleo.spell_dimension.init;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import karashokleo.spell_dimension.content.component.SpiritTomeComponent;
import karashokleo.spell_dimension.content.object.EnchantedModifier;
import karashokleo.spell_dimension.mixin.modded.SpellRegistryAccessor;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import net.minecraft.util.TypeFilter;
import net.spell_engine.SpellEngineMod;
import net.spell_engine.internals.SpellRegistry;
import net.spell_engine.network.Packets;
import net.spell_engine.utils.WeaponCompatibility;

import java.util.ArrayList;

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
                    .then(clearItemEntities())
                    .then(fixFakeDeath())
                    .then(reloadSpells())
                    .then(convertModifiers())
                    .then(spirit())
            );
        });
    }

    private static LiteralArgumentBuilder<ServerCommandSource> printHandStack()
    {
        return CommandManager
            .literal("hand")
            .executes(AllCommands::executePrintHandStack);
    }

    private static LiteralArgumentBuilder<ServerCommandSource> clearItemEntities()
    {
        return CommandManager
            .literal("clear_item_entities")
            .executes(AllCommands::executeClearItemEntities);
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

    private static LiteralArgumentBuilder<ServerCommandSource> spirit()
    {
        return CommandManager
            .literal("spirit")
            .requires(source -> source.hasPermissionLevel(2))
            .then(
                CommandManager.literal("positive")
                    .then(
                        CommandManager
                            .argument("amount", IntegerArgumentType.integer())
                            .executes(ctx -> executeSpirit(ctx, SpiritTomeComponent.SpiritType.POSITIVE))
                    )
            )
            .then(
                CommandManager.literal("negative")
                    .then(
                        CommandManager
                            .argument("amount", IntegerArgumentType.integer())
                            .executes(ctx -> executeSpirit(ctx, SpiritTomeComponent.SpiritType.NEGATIVE))
                    )
            );
    }

    @SuppressWarnings("all")
    private static int executePrintHandStack(CommandContext<ServerCommandSource> context)
    {
        ServerPlayerEntity player = context.getSource().getPlayer();
        if (player == null)
        {
            return Command.SINGLE_SUCCESS;
        }

        ItemStack stack = player.getMainHandStack();
        if (stack.isEmpty())
        {
            return Command.SINGLE_SUCCESS;
        }
        Item item = stack.getItem();
        var registryEntry = item.getRegistryEntry();

        ArrayList<Text> lines = new ArrayList<>();

        lines.add(Text.literal("Item:").formatted(Formatting.AQUA));
        lines.add(Texts.bracketedCopyable(registryEntry.registryKey().getValue().toString()));

        lines.add(Text.literal("Tags:").formatted(Formatting.AQUA));
        registryEntry.streamTags().map(tag -> Texts.bracketedCopyable("#" + tag.id())).forEach(lines::add);

        var nbt = stack.getNbt();
        if (nbt != null)
        {
            lines.add(Text.literal("Nbt:").formatted(Formatting.AQUA));
            lines.add(Texts.bracketedCopyable(nbt.toString()));
        }

        for (Text line : lines)
        {
            context.getSource().sendMessage(line);
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int executeClearItemEntities(CommandContext<ServerCommandSource> context)
    {
        var list = context.getSource().getWorld().getEntitiesByType(TypeFilter.instanceOf(ItemEntity.class), entity -> true);
        for (ItemEntity itemEntity : list)
        {
            itemEntity.kill();
        }
        return list.size();
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

    private static int executeSpirit(CommandContext<ServerCommandSource> context, SpiritTomeComponent.SpiritType type)
    {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayer();
        if (player == null)
        {
            return Command.SINGLE_SUCCESS;
        }
        int amount = IntegerArgumentType.getInteger(context, "amount");
        SpiritTomeComponent component = SpiritTomeComponent.get(player);
        component.changeSpirit(type, amount);
        source.sendMessage(Text.literal("Positive: " + component.getSpirit(SpiritTomeComponent.SpiritType.POSITIVE)));
        source.sendMessage(Text.literal("Negative: " + component.getSpirit(SpiritTomeComponent.SpiritType.NEGATIVE)));
        source.sendMessage(Text.literal("Total: " + component.getSpirit(SpiritTomeComponent.SpiritType.TOTAL)));
        return amount;
    }
}
