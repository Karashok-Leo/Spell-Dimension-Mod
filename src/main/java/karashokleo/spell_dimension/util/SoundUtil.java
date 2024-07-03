package karashokleo.spell_dimension.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.spell_engine.api.spell.ExternalSpellSchools;
import net.spell_engine.api.spell.Sound;
import net.spell_engine.utils.SoundHelper;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class SoundUtil
{
    private static final Sound defaultSound = new Sound("block.anvil.use");
    private static final Map<SpellSchool, Sound> map = Map.of(
            SpellSchools.ARCANE, new Sound("spell_engine:generic_arcane_release"),
            SpellSchools.FIRE, new Sound("spell_engine:generic_fire_release"),
            SpellSchools.FROST, new Sound("spell_engine:generic_frost_release"),
            SpellSchools.HEALING, new Sound("spell_engine:generic_healing_release"),
            SpellSchools.LIGHTNING, new Sound("spell_engine:generic_lightning_release"),
            SpellSchools.SOUL, new Sound("spell_engine:generic_soul_release"),
            ExternalSpellSchools.PHYSICAL_MELEE, new Sound("block.anvil.use"),
            ExternalSpellSchools.PHYSICAL_RANGED, new Sound("block.anvil.use")
    );

    public static Sound getSound(@Nullable SpellSchool school)
    {
        return school == null ? defaultSound : map.get(school);
    }

    public static void playSound(Entity entity, Sound sound)
    {
        World world = entity.getWorld();
        if (world.isClient() && entity instanceof PlayerEntity player)
        {
            try
            {
                SoundEvent soundEvent = Registries.SOUND_EVENT.get(new Identifier(sound.id()));
                world.playSound(player, player.getX(), player.getY(), player.getZ(), soundEvent, SoundCategory.PLAYERS, sound.volume(), sound.randomizedPitch());
            } catch (Exception e)
            {
                System.err.println("Failed to play sound: " + sound.id());
                e.printStackTrace();
            }
        } else SoundHelper.playSound(entity.getWorld(), entity, sound);
    }

    public static void playSound(Entity entity, @Nullable SpellSchool school)
    {
        playSound(entity, getSound(school));
    }
}
