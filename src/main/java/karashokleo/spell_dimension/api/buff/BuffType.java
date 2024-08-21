package karashokleo.spell_dimension.api.buff;

import com.mojang.serialization.Codec;

public record BuffType<B extends Buff>(Codec<B> codec, boolean sync)
{
}
