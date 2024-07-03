package karashokleo.spell_dimension.content.misc;

import com.google.gson.JsonObject;
import karashokleo.spell_dimension.data.LangData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.JsonHelper;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

public record Mage(int grade, @Nullable SpellSchool school, @Nullable MageMajor major)
{
    private static final String MAGE_KEY = "Mage";
    private static final String GRADE_KEY = "Grade";
    private static final String SCHOOL_KEY = "School";
    private static final String MAJOR_KEY = "Major";
    public static final int MIN_GRADE = 0;
    public static final int MAX_GRADE = 3;
    public static final Mage EMPTY = new Mage(MIN_GRADE, null, null);

    public boolean isInvalid()
    {
        return this.grade < MIN_GRADE || this.grade > MAX_GRADE;
    }

    public static Mage readFromStack(ItemStack stack)
    {
        return stack.hasNbt() ? readFromNbt(stack.getOrCreateNbt().getCompound(MAGE_KEY)) : EMPTY;
    }

    public ItemStack writeToStack(ItemStack stack)
    {
        stack.getOrCreateNbt().put(MAGE_KEY, this.writeToNbt(new NbtCompound()));
        return stack;
    }

    public static Mage readFromNbt(NbtCompound tag)
    {
        try
        {
            int grade = tag.getInt(GRADE_KEY);
            SpellSchool school = SpellSchools.getSchool(tag.getString(SCHOOL_KEY));
            MageMajor major = MageMajor.valueOf(tag.getString(MAJOR_KEY));
            return new Mage(grade, school, major);
        } catch (Exception e)
        {
            return EMPTY;
        }
    }

    public NbtCompound writeToNbt(NbtCompound tag)
    {
        tag.putInt(GRADE_KEY, this.grade);
        if (this.school != null)
            tag.putString(SCHOOL_KEY, this.school.id.toString());
        if (this.major != null)
            tag.putString(MAJOR_KEY, this.major.name());
        return tag;
    }

    public static Mage readFromPacket(PacketByteBuf buf)
    {
        int grade = buf.readInt();
        SpellSchool school = buf.readBoolean() ? SpellSchools.getSchool(buf.readString()) : null;
        MageMajor major = buf.readBoolean() ? buf.readEnumConstant(MageMajor.class) : null;
        return new Mage(grade, school, major);
    }

    public PacketByteBuf writeToPacket(PacketByteBuf buf)
    {
        buf.writeInt(this.grade);
        buf.writeBoolean(this.school != null);
        if (this.school != null)
            buf.writeString(this.school.id.toString());
        buf.writeBoolean(this.major != null);
        if (this.major != null)
            buf.writeEnumConstant(this.major);
        return buf;
    }

    public static Mage readFromJson(JsonObject json)
    {
        try
        {
            int grade = JsonHelper.getInt(json, GRADE_KEY, 0);
            SpellSchool school = SpellSchools.getSchool(JsonHelper.getString(json, SCHOOL_KEY));
            MageMajor major = MageMajor.valueOf(JsonHelper.getString(json, MAJOR_KEY));
            return new Mage(grade, school, major);
        } catch (Exception e)
        {
            return EMPTY;
        }
    }

    public JsonObject writeToJson(JsonObject json)
    {
        json.addProperty(GRADE_KEY, this.grade);
        if (this.school != null)
            json.addProperty(SCHOOL_KEY, this.school.id.toString());
        if (this.major != null)
            json.addProperty(MAJOR_KEY, this.major.name());
        return json;
    }

    public MutableText getGradeText()
    {
        return Text.translatable(LangData.ENUM_GRADE + this.grade);
    }

    public MutableText getSchoolText()
    {
        if (school == null) return Text.empty();
        return Text.translatable(LangData.ENUM_SCHOOL + this.school.id.toString());
    }

    public MutableText getMajorText()
    {
        if (major == null) return Text.empty();
        return Text.translatable(LangData.ENUM_MAJOR + this.major.name());
    }

    public Text getMageTitle(MutableText append)
    {
        MutableText name = Text.empty();
        name.append(this.getGradeText())
                .append(Text.of(" "));
        if (this.school() != null)
            name.append(this.getSchoolText())
                    .append(Text.of(" "));
        name.append(append);
        if (this.major() != null)
            name.append(Text.of(" • "))
                    .append(this.getMajorText())
                    .append(Text.of(" "))
                    .append(Text.translatable(LangData.MASTERY));
        if (school == null)
            name.formatted(Formatting.GRAY);
        else
            name.setStyle(name.getStyle().withColor(school.color));
        return name;
    }
}
