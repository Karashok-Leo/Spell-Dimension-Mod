package karashokleo.spell_dimension.client.render;

import karashokleo.spell_dimension.SpellDimension;
import mod.azure.azurelibarmor.model.GeoModel;
import net.minecraft.util.Identifier;
import net.wizards.item.WizardArmor;

public class CustomRobeModel extends GeoModel<WizardArmor>
{
    @Override
    public Identifier getModelResource(WizardArmor wizardArmor)
    {
        return SpellDimension.modLoc("geo/robes.geo.json");
    }

    @Override
    public Identifier getTextureResource(WizardArmor wizardArmor)
    {
        String texture = wizardArmor.customMaterial.name();
        return SpellDimension.modLoc("textures/armor/" + texture + ".png");
    }

    @Override
    public Identifier getAnimationResource(WizardArmor wizardArmor)
    {
        return null;
    }
}
