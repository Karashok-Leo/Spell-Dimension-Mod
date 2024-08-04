package karashokleo.spell_dimension.data.loot_bag;

import karashokleo.loot_bag.api.common.bag.Bag;
import karashokleo.loot_bag.api.common.bag.BagEntry;
import net.minecraft.util.Identifier;

import java.util.Objects;

@Deprecated
public class BagModel
{
    public static BagModel create()
    {
        return new BagModel();
    }

    private Identifier id;
    private Bag bag;
    private BagEntry entry;
    private String nameEn;
    private String nameZh;

    private BagModel()
    {
    }

    public Identifier getId()
    {
        return id;
    }

    public Bag getBag()
    {
        return bag;
    }

    public String getNameKey()
    {
        return entry.nameKey();
    }

    public String getNameEn()
    {
        return nameEn;
    }

    public String getNameZh()
    {
        return nameZh;
    }

    public BagModel setId(Identifier id)
    {
        this.id = id;
        return this;
    }

    public BagModel setBag(Bag bag)
    {
        this.bag = bag;
        return this;
    }

    public BagModel setNameEn(String nameEn)
    {
        this.nameEn = nameEn;
        return this;
    }

    public BagModel setNameZh(String nameZh)
    {
        this.nameZh = nameZh;
        return this;
    }

    public BagModel buildEntry()
    {
        Objects.requireNonNull(this.id);
        Objects.requireNonNull(this.bag);
        this.entry = new BagEntry(this.id, this.bag);
        return this;
    }
}
