package karashokleo.spell_dimension.content.item;

import karashokleo.spell_dimension.content.misc.Mage;

public interface IMageGetter<T>
{
    Mage getMage(T t);
}
