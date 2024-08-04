package karashokleo.spell_dimension.data.loot_bag;

import karashokleo.loot_bag.api.common.content.Content;
import karashokleo.loot_bag.api.common.content.ContentEntry;
import net.minecraft.util.Identifier;

import java.util.Objects;

@Deprecated
public class ContentModel
{
    public static ContentModel create()
    {
        return new ContentModel();
    }

    private Identifier id;
    private Content content;
    private ContentEntry entry;
    private String nameEn;
    private String nameZh;
    private String descEn;
    private String descZh;

    private ContentModel()
    {
    }

    public Identifier getId()
    {
        return id;
    }

    public Content getContent()
    {
        return content;
    }

    public String getNameKey()
    {
        return entry.nameKey();
    }

    public String getDescKey()
    {
        return entry.descKey();
    }

    public String getNameEn()
    {
        return nameEn;
    }

    public String getNameZh()
    {
        return nameZh;
    }

    public String getDescEn()
    {
        return descEn;
    }

    public String getDescZh()
    {
        return descZh;
    }

    public ContentModel setId(Identifier id)
    {
        this.id = id;
        return this;
    }

    public ContentModel setContent(Content content)
    {
        this.content = content;
        return this;
    }

    public ContentModel setNameEn(String nameEn)
    {
        this.nameEn = nameEn;
        return this;
    }

    public ContentModel setNameZh(String nameZh)
    {
        this.nameZh = nameZh;
        return this;
    }

    public ContentModel setDescEn(String descEn)
    {
        this.descEn = descEn;
        return this;
    }

    public ContentModel setDescZh(String descZh)
    {
        this.descZh = descZh;
        return this;
    }

    public ContentModel buildEntry()
    {
        Objects.requireNonNull(this.id);
        Objects.requireNonNull(this.content);
        this.entry = new ContentEntry(this.id, this.content);
        return this;
    }
}
