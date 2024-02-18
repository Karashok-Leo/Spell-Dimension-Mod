package net.karashokleo.spelldimension.misc;

import com.google.gson.JsonObject;
import net.karashokleo.spelldimension.component.MageComponent;
import net.karashokleo.spelldimension.data.LangData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.JsonHelper;
import net.spell_power.api.MagicSchool;
import org.jetbrains.annotations.Nullable;

public record Mage(int grade, @Nullable MagicSchool school, @Nullable MageMajor major)
{
    private static final String MAGE_KEY = "Mage";
    private static final String GRADE_KEY = "Grade";
    private static final String SCHOOL_KEY = "School";
    private static final String MAJOR_KEY = "Major";
    public static final int MIN_GRADE = 0;
    public static final int MAX_GRADE = 3;
    public static final Mage EMPTY = new Mage(MIN_GRADE, null, null);

    public boolean checkSchoolAndMajor(@Nullable MagicSchool school, @Nullable MageMajor major)
    {
        return (this.school == school || this.school == null) &&
                (this.major == major || this.major == null);
    }

    public boolean test(Mage mage)
    {
        return this.grade == 0 ||
                (this.grade <= mage.grade &&
                        this.checkSchoolAndMajor(mage.school, mage.major));
    }

    public boolean testPlayer(PlayerEntity player)
    {
        return this.test(MageComponent.get(player));
    }

    public boolean isInvalid()
    {
        return this.grade < 0 || this.grade > MAX_GRADE;
    }

    public static Mage readFromStack(ItemStack stack)
    {
        return stack.hasNbt() ? readFromNbt(stack.getOrCreateNbt().getCompound(MAGE_KEY)) : EMPTY;
    }

    public static void writeToStack(ItemStack stack, int grade, @Nullable MagicSchool school, @Nullable MageMajor major)
    {
        new Mage(grade, school, major).writeToStack(stack);
    }

    public void writeToStack(ItemStack stack)
    {
        NbtCompound compound = stack.getOrCreateNbt();
        NbtCompound nbt = new NbtCompound();
        this.writeToNbt(nbt);
        compound.put(MAGE_KEY, nbt);
    }

    public static Mage readFromNbt(NbtCompound tag)
    {
        int grade = tag.getInt(GRADE_KEY);
        MagicSchool school;
        try
        {
            school = MagicSchool.valueOf(tag.getString(SCHOOL_KEY));
        } catch (Exception e)
        {
            school = null;
        }
        MageMajor major;
        try
        {
            major = MageMajor.valueOf(tag.getString(MAJOR_KEY));
        } catch (Exception e)
        {
            major = null;
        }
        return new Mage(grade, school, major);
    }

    public static void writeToNbt(NbtCompound tag, int grade, @Nullable MagicSchool school, @Nullable MageMajor major)
    {
        tag.putInt(GRADE_KEY, grade);
        if (school != null)
            tag.putString(SCHOOL_KEY, school.name());
        if (major != null)
            tag.putString(MAJOR_KEY, major.name());
    }

    public void writeToNbt(NbtCompound tag)
    {
        writeToNbt(tag, this.grade, this.school, this.major);
    }

    public static Mage readFromPacket(PacketByteBuf buf)
    {
        int grade = buf.readInt();
        MagicSchool school = buf.readBoolean() ? buf.readEnumConstant(MagicSchool.class) : null;
        MageMajor major = buf.readBoolean() ? buf.readEnumConstant(MageMajor.class) : null;
        return new Mage(grade, school, major);
    }

    public static void writeToPacket(PacketByteBuf buf, int grade, @Nullable MagicSchool school, @Nullable MageMajor major)
    {
        buf.writeInt(grade);
        buf.writeBoolean(school != null);
        if (school != null)
            buf.writeEnumConstant(school);
        buf.writeBoolean(major != null);
        if (major != null)
            buf.writeEnumConstant(major);
    }

    public void writeToPacket(PacketByteBuf buf)
    {
        writeToPacket(buf, this.grade, this.school, this.major);
    }

    public static Mage readFromJson(JsonObject json)
    {
        int grade = JsonHelper.getInt(json, GRADE_KEY, 0);
        MagicSchool school;
        try
        {
            school = MagicSchool.valueOf(JsonHelper.getString(json, SCHOOL_KEY));
        } catch (Exception e)
        {
            school = null;
        }
        MageMajor major;
        try
        {
            major = MageMajor.valueOf(JsonHelper.getString(json, MAJOR_KEY));
        } catch (Exception e)
        {
            major = null;
        }
        return new Mage(grade, school, major);
    }

    public static void writeToJson(JsonObject json, int grade, @Nullable MagicSchool school, @Nullable MageMajor major)
    {
        json.addProperty(GRADE_KEY, grade);
        if (school != null)
            json.addProperty(SCHOOL_KEY, school.name());
        if (major != null)
            json.addProperty(MAJOR_KEY, major.name());
    }

    public void writeToJson(JsonObject json)
    {
        writeToJson(json, this.grade, this.school, this.major);
    }

    public MutableText getGradeText()
    {
        return Text.translatable(LangData.ENUM_GRADE + this.grade);
    }

    public MutableText getSchoolText()
    {
        if (school == null) return Text.empty();
        return Text.translatable(LangData.ENUM_SCHOOL + this.school.name());
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
            name.setStyle(name.getStyle().withColor(school.color()));
        return name;
    }
}
