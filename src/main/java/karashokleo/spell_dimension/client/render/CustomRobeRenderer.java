package karashokleo.spell_dimension.client.render;

import mod.azure.azurelibarmor.renderer.GeoArmorRenderer;
import net.wizards.item.WizardArmor;

public class CustomRobeRenderer extends GeoArmorRenderer<WizardArmor>
{
    public CustomRobeRenderer()
    {
        super(new CustomRobeModel());
    }
}
