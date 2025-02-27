package karashokleo.spell_dimension.data.loot_bag;

public class TextConstants
{
    public static final String[] RARITIES = {"common", "uncommon", "rare", "epic", "legendary"};
    public static final String[] RARITIES_EN = {"Common", "Uncommon", "Rare", "Epic", "Legendary"};
    public static final String[] RARITIES_ZH = {"普通", "不凡", "罕见", "史诗", "传奇"};
    public static final String[] ITEMS_EN = {"Enchanted Book", "Gear", "Material"};
    public static final String[] ITEMS_ZH = {"附魔书", "装备", "材料"};
    public static final String[][] COUNT = {
            // Book, Gear, Material
            {"1~2", "1~2", "9~11"},
            {"1~2", "1~2", "7~9"},
            {"1~3", "1~2", "5~7"},
            {"2~3", "1~2", "3~5"},
            {"2~4", "1~2", "1~3"},
    };
    public static final int[] BOSS_LEVELS = {80, 160, 320, 560, 880};
}
