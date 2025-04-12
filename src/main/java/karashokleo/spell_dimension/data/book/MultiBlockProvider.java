package karashokleo.spell_dimension.data.book;

import com.klikli_dev.modonomicon.api.datagen.MultiblockProvider;
import karashokleo.enchantment_infusion.init.EIBlocks;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.adventurez.init.BlockInit;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataOutput;

public class MultiBlockProvider extends MultiblockProvider
{
    public MultiBlockProvider(DataOutput packOutput)
    {
        super(packOutput, BookGenUtil.NAMESPACE);
    }

    @Override
    public void buildMultiblocks()
    {
        add(
                modLoc("ender_eye_altar"),
                new DenseMultiblockBuilder()
                        .layer(
                                "     ",
                                " L L ",
                                "  D  ",
                                " L L ",
                                "     "
                        )
                        .layer(
                                "L   L",
                                " CCC ",
                                " CCC ",
                                " CCC ",
                                "L   L"
                        )
                        .layer(
                                "OOOOO",
                                "OOOOO",
                                "OO0OO",
                                "OOOOO",
                                "OOOOO"
                        )
                        .block('0', () -> Blocks.OBSIDIAN)
                        .block('O', () -> Blocks.OBSIDIAN)
                        .block('C', () -> Blocks.CRYING_OBSIDIAN)
                        .block('L', () -> Blocks.END_ROD)
                        .block('D', () -> Blocks.DRAGON_EGG)
        );

        add(
                modLoc("blackstone_altar"),
                new DenseMultiblockBuilder()
                        .layer(
                                "     A     ",
                                " BBBBBBBBB ",
                                " BBBBBBBBB ",
                                " BBBBBBBBB ",
                                " BBBBBBBBB ",
                                "ABBBB0BBBBA",
                                " BBBBBBBBB ",
                                " BBBBBBBBB ",
                                " BBBBBBBBB ",
                                " BBBBBBBBB ",
                                "     A     "
                        )
                        .block('0', () -> Blocks.POLISHED_BLACKSTONE)
                        .block('B', () -> Blocks.POLISHED_BLACKSTONE)
                        .block('A', () -> BlockInit.CHISELED_POLISHED_BLACKSTONE_HOLDER)
        );

        add(
                modLoc("enchantment_infusion"),
                new DenseMultiblockBuilder()
                        .layer(
                                "   P   ",
                                " P   P ",
                                "       ",
                                "P  0  P",
                                "       ",
                                " P   P ",
                                "   P   "
                        )
                        .block('0', () -> EIBlocks.INFUSION_TABLE)
                        .block('P', () -> EIBlocks.INFUSION_PEDESTAL)
        );
    }
}
