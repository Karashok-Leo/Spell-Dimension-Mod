package karashokleo.spell_dimension.mixin.modded;

import com.google.common.collect.Sets;
import com.spellbladenext.Spellblades;
import com.spellbladenext.entity.CycloneEntity;
import karashokleo.spell_dimension.init.AllSpells;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.SpellRegistry;
import net.spell_engine.internals.casting.SpellCast;
import net.spell_engine.internals.casting.SpellCasterEntity;
import net.spell_engine.utils.TargetHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;
import java.util.Set;

@Mixin(CycloneEntity.class)
public abstract class CycloneEntityMixin extends Entity implements Ownable
{
    @Unique
    private static final Set<Identifier> FOLLOW_SPELLS = Sets.newHashSet(
            AllSpells.INFERNO,
            AllSpells.MAELSTROM,
            AllSpells.TEMPEST,
            new Identifier("spellbladenext:whirlwind"),
            new Identifier("spellbladenext:reckoning")
    );

    @Shadow(remap = false)
    public abstract int getColor();

    @Shadow
    public Entity target;

    @Shadow(remap = false)
    public SpellHelper.ImpactContext context;

    @Shadow
    public abstract @Nullable Entity getOwner();

    private CycloneEntityMixin(EntityType<?> type, World world)
    {
        super(type, world);
    }

    /**
     * @author Karashok_Leo
     * @reason CycloneEntityMixin
     */
    @Overwrite
    public void tick()
    {
        super.tick();

        boolean isClient = this.getWorld().isClient();

        if (!isClient)
        {
            if (this.getOwner() == null || this.age > 160)
            {
                this.discard();
                return;
            }
        }

        // update position
        this.setPosition(this.getPos().add(this.getVelocity()));

        if (!(this.getOwner() instanceof LivingEntity livingOwner))
        {
            return;
        }

        if (this.getColor() == 5)
        {
            // follow target
            if (this.target != null)
            {
                if (isClient)
                {
                    this.lastRenderY = this.getY();
                }

                this.setVelocity(
                        this.getVelocity()
                                .multiply(0.95)
                                .add(
                                        this.target.getPos()
                                                .subtract(this.getPos())
                                                .normalize()
                                                .multiply(0.05)
                                )
                );
            }
        } else
        {
            // follow owner
            this.setPos(livingOwner.getX(), livingOwner.getY(), livingOwner.getZ());

            // discard if owner is not casting follow spells
            if (!isClient &&
                livingOwner instanceof SpellCasterEntity caster)
            {
                SpellCast.Process process = caster.getSpellCastProcess();

                if (process == null ||
                    !FOLLOW_SPELLS.contains(process.id()))
                {
                    this.discard();
                }
            }
        }

        // spell impacts
        if (!isClient &&
            this.age % 10 == 0 &&
            this.context != null)
        {
            List<LivingEntity> targets = this.getWorld()
                    .getEntitiesByClass(
                            LivingEntity.class,
                            this.getBoundingBox(),
                            e -> this.target == e ||
                                 TargetHelper.actionAllowed(TargetHelper.TargetingMode.AREA, TargetHelper.Intent.HARMFUL, livingOwner, e)

                    );
            Identifier spellId = new Identifier(Spellblades.MOD_ID, "bladestorm");
            SpellInfo spell = new SpellInfo(SpellRegistry.getSpell(spellId), spellId);
            for (var livingTarget : targets)
            {
                SpellHelper.performImpacts(livingTarget.getWorld(), livingOwner, livingTarget, this, spell, this.context, false);
            }
        }
    }
}
