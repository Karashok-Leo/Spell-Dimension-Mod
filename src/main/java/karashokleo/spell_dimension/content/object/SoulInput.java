package karashokleo.spell_dimension.content.object;

import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;

import java.util.HashSet;

/// Fixed issues:
/// 仆从死亡后没有取消控制
/// 僵尸被控制后手臂角度停动
/// 部分生物在地面无法移动，只能在空中移动：兔子、鹦鹉、史莱姆，且在空中速度不够快
/// 生物视角无法渲染玩家
/// Known issues:
/// 跳跃无冷却
/// 鹦鹉落地速度不够快
/// 生物视角移动缓慢，在服务端侧更新视角同步到客户端有延迟
/// 部分生物无法获取全速，如被动生物受伤后的恐慌加速、马的冲刺等
/// 生物视角会渲染玩家的手
/// 无法加载区块
@SerialClass
public class SoulInput
{
    @SerialClass.SerialField
    public float cursorDeltaX = 0;
    @SerialClass.SerialField
    public float cursorDeltaY = 0;
    @SerialClass.SerialField
    public float forward = 0;
    @SerialClass.SerialField
    public float sideways = 0;
    @SerialClass.SerialField
    public boolean jumping = false;
    @SerialClass.SerialField
    public boolean sneaking = false;
    @SerialClass.SerialField
    public boolean controlling = false;

    public SoulInput()
    {
    }

    public void copyFrom(SoulInput input)
    {
        this.cursorDeltaX = input.cursorDeltaX;
        this.cursorDeltaY = input.cursorDeltaY;
        this.forward = input.forward;
        this.sideways = input.sideways;
        this.jumping = input.jumping;
        this.sneaking = input.sneaking;
        this.controlling = input.controlling;
    }

    public void clear()
    {
        this.cursorDeltaX = 0f;
        this.cursorDeltaY = 0f;
        this.forward = 0f;
        this.sideways = 0f;
        this.jumping = false;
        this.sneaking = false;
        this.controlling = false;
    }

    public void applyRotation(Entity entity, float multiplier)
    {
        float deltaX = cursorDeltaY * multiplier;
        float deltaY = cursorDeltaX * multiplier;
        entity.setYaw(entity.getYaw() + deltaY);
        // bodyYaw should be updated by mob itself
//        entity.setBodyYaw(entity.getBodyYaw() + deltaY);
        entity.setHeadYaw(entity.getHeadYaw() + deltaY);
        entity.setPitch(MathHelper.clamp(entity.getPitch() + deltaX, -90.0F, 90.0F));
        entity.prevYaw += deltaY;
        entity.prevPitch = MathHelper.clamp(entity.prevPitch + deltaX, -90.0F, 90.0F);
    }

    public void applyMovement(MobEntity mob)
    {
        boolean canFly = canFly(mob);

        float moveSpeed = (float) mob.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        float flySpeed = moveSpeed;
        if (canFly &&
            mob.getAttributes().hasAttribute(EntityAttributes.GENERIC_FLYING_SPEED))
        {
            flySpeed = (float) mob.getAttributeValue(EntityAttributes.GENERIC_FLYING_SPEED);
        }
        float actualSpeed = mob.isOnGround() ? moveSpeed : flySpeed;

        if (GROUND_STATIC.contains(mob.getType()) && mob.isOnGround() && !jumping)
        {
            actualSpeed = 0f;
        }

        mob.setMovementSpeed(actualSpeed);
        mob.setForwardSpeed(forward * actualSpeed);
        mob.setSidewaysSpeed(sideways * actualSpeed);

        if (canFly)
        {
            mob.setNoGravity(!mob.isOnGround());
            if (jumping == sneaking)
            {
                mob.setUpwardSpeed(0);
            } else if (jumping)
            {
                mob.setUpwardSpeed(flySpeed);
            } else
            {
                mob.setUpwardSpeed(-flySpeed);
            }
        } else
        {
            mob.setJumping(jumping);
        }
    }

    private static final HashSet<EntityType<?>> FLYING = new HashSet<>();
    private static final HashSet<EntityType<?>> GROUND_STATIC = new HashSet<>();

    static
    {
        FLYING.add(EntityType.BAT);
        FLYING.add(EntityType.BEE);
        FLYING.add(EntityType.BLAZE);
        FLYING.add(EntityType.PHANTOM);
        FLYING.add(EntityType.GHAST);
        FLYING.add(EntityType.ALLAY);
        FLYING.add(EntityType.VEX);
        FLYING.add(EntityType.WITHER);

        GROUND_STATIC.add(EntityType.PARROT);
        GROUND_STATIC.add(EntityType.SLIME);
        GROUND_STATIC.add(EntityType.RABBIT);
    }

    private static boolean canFly(MobEntity mob)
    {
        return mob instanceof Flutterer ||
            mob instanceof FlyingEntity ||
            FLYING.contains(mob.getType());
    }
}
