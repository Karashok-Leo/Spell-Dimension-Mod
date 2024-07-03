package karashokleo.spell_dimension.util;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;

import java.util.Random;
import java.util.UUID;

public class UuidUtil
{
    public static final UUID[] forSelf = {
            UUID.fromString("3A2FADFA-13AE-CE9D-3310-69C728E4BCDB"),
            UUID.fromString("EC424B5E-8673-7170-B54B-D42D08C60420"),
            UUID.fromString("23BF01DC-FECE-1B76-8DCE-1E3F42672265")
    };
    public static final UUID[] forEquip = {
            UUID.fromString("BD2A7B93-5129-8D61-8911-F488ADE53603"),
            UUID.fromString("2862132E-8E4B-7AD5-E985-5550C0E4702A"),
            UUID.fromString("2192EF67-5EF7-35D7-14BB-2273A09324F2"),
            UUID.fromString("9EE15485-4CA7-50F7-E7F5-EF578D6BD041"),
            UUID.fromString("E94D79ED-D248-67BF-A82D-B5099D7B3F70"),
            UUID.fromString("393C4D75-DE59-9269-E245-08056A7D837C"),
            UUID.fromString("CFB19DFC-67E3-4307-4A94-6A0EC9170CDD"),
            UUID.fromString("CD01CCAB-847D-0CD5-2F30-C35616CEE0EF"),
            UUID.fromString("519BC167-86B9-D7C8-A8BC-F8EA3A4A286C"),
            UUID.fromString("2EB63A14-B7AB-53CA-1739-610CF763E50B"),
            UUID.fromString("A514E675-F070-9D1F-B2DE-8EF00820E50C"),
            UUID.fromString("8BECD455-D2F6-15CB-18FF-C6C5BB32A108"),
            UUID.fromString("EB37360E-8537-E699-B70D-3C9817528E33"),
            UUID.fromString("5926869A-B3DE-7FCF-A673-FD16614AD0BD"),
            UUID.fromString("92A4AE66-37B7-32D4-649F-040D629CDD22"),
            UUID.fromString("FAC4BCF0-C942-D220-E9D1-37CAE159207A"),
            UUID.fromString("F5AFF634-C80C-8FDE-CA83-B0A5321E2561"),
            UUID.fromString("0078AF86-4FEE-D58A-019C-07A1760320BD"),
    };

    public static UUID generateFromString(String str) {
        int hash = str.hashCode();
        Random r = new Random(hash);
        long l0 = r.nextLong();
        long l1 = r.nextLong();
        return new UUID(l0, l1);
    }

    public static UUID getSelfUuid(EntityAttributeModifier.Operation operation)
    {
        return forSelf[operation.getId()];
    }

    public static UUID getEquipUuid(EquipmentSlot slot, EntityAttributeModifier.Operation operation)
    {
        return forEquip[slot.ordinal() * 3 + operation.getId()];
    }
}
