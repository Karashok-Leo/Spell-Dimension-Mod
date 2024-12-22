package karashokleo.spell_dimension.data.book.entry.boss;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.util.Identifier;

import java.util.List;

public class GraveyardLichEntry extends BaseEntryProvider
{
    public GraveyardLichEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Corrupted Lich";
    }

    @Override
    protected String nameZH()
    {
        return "巫妖";
    }

    @Override
    protected String descEN()
    {
        return "";
    }

    @Override
    protected String descZH()
    {
        return "";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("boss");
        this.lang().add(context.pageTitle(), nameEN());
        this.lang().add(context.pageText(),
                """
                        \\
                        Level: 200+
                        \\
                        \\
                        Corrupted Lich can be summoned in the **Lich Prison**(graveyard:lich_prison).
                        \\
                        This structure can be located in the Overworld using the Locate Spell.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        等级：200+
                        \\
                        \\
                        巫妖可在**巫妖之狱**(graveyard:lich_prison)召唤。
                        \\
                        可通过定位法术在主世界中定位到此结构。
                        """
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getIngredient(new Identifier("graveyard:white_bone_staff")))
                .build();

        context.page("prev");
        this.lang().add(context.pageText(),
                """
                        \\
                        At night, place the top, middle, and bottom three **Ominous Bone Staff Fragments** in the center of the shattered deep stone in front of the altar from left to right. Then pour a bottle of blood onto the altar block in the center of the altar to summon the Lich.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        在夜晚依次将上、中、下共3块**不祥的骨杖残片**依次从左至右放置于祭坛前的碎裂深石中心，然后将一瓶血倒在祭坛中央的祭坛方块上，即可召唤巫妖。
                        """
        );
        BookTextPageModel prev = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        context.page("next");
        this.lang().add(context.pageText(),
                """
                        \\
                        Ominous Bone Staff Fragment: Can be found in the Ruins(graveyard:ruins).
                        \\
                        This structure can be located in the Overworld using the Locate Spell.
                        \\
                        \\
                        Blood Bottle: When main hand holding a bone dagger and the offhand holding an empty glass bottle, killing villagers can fill the empty glass bottle and turn it into a blood bottle.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        不祥的骨杖残片: 可以在遗迹(graveyard:ruins)中发现。
                        \\
                        可通过定位法术在主世界中定位到此结构。
                        \\
                        \\
                        血瓶：主手持骨头匕首，副手持空玻璃瓶时杀死村民可以填充空玻璃瓶，将玻璃瓶变为血瓶。
                        """
        );

        BookTextPageModel next = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        BookEntityPageModel entity = BookEntityPageModel
                .builder()
                .withEntityId("graveyard:lich")
                .build();

        return List.of(boss, prev, next, entity);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(BookGenUtil.getItem(new Identifier("graveyard:white_bone_staff")));
    }

    @Override
    protected String entryId()
    {
        return "graveyard_lich";
    }
}
