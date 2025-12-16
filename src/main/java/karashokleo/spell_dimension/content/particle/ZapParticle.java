package karashokleo.spell_dimension.content.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.Arrays;

/**
 * Learned from <a href="https://github.com/iron431/irons-spells-n-spellbooks/blob/1.20.1/src/main/java/io/redspace/ironsspellbooks/particle/ZapParticle.java">...</a>
 */
public class ZapParticle extends SpriteBillboardParticle
{
    private final Random random;
    private final PositionProvider fromPos;
    private final PositionProvider toPos;
    private final long timestamp;
    private final int strands;
    private final float segmentLength;
    private final float maxDisturb;
    private final float branchChance;
    private final float branchLength;

    @SuppressWarnings("unused")
    private ZapParticle(ClientWorld world, double x, double y, double z, double vx, double vy, double vz, ZapParticleOption options)
    {
        super(world, x, y, z, 0, 0, 0);
        this.setBoundingBoxSpacing(1, 1);
        this.setRGBA(1, 1, 1, 1);
        this.scale = 1f;
        this.maxAge = world.getRandom().nextBetween(4, 8);
        this.random = Random.create();
        this.fromPos = PositionProvider.get(options.from(), world);
        this.toPos = PositionProvider.get(options.to(), world);
        this.timestamp = System.currentTimeMillis();
        this.strands = options.strands();
        this.segmentLength = 3f;
        this.maxDisturb = 1.6f;
        this.branchChance = 0.6f;
        this.branchLength = 0.2f;
    }

    @Override
    public void tick()
    {
        this.random.setSeed((age + maxAge) * timestamp);
        if (this.age++ >= this.maxAge)
        {
            this.markDead();
        }
    }

    // x/y/z range: -scale ~ scale
    private Vector3f randomVector3f(float scale)
    {
        return new Vector3f(
            (2f * random.nextFloat() - 1f),
            (2f * random.nextFloat() - 1f),
            (2f * random.nextFloat() - 1f)
        ).mul(scale);
    }

    @SuppressWarnings("SameParameterValue")
    private void setRGBA(float r, float g, float b, float a)
    {
        this.red = r * a;
        this.green = g * a;
        this.blue = b * a;
        this.alpha = 1;
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta)
    {
        if (this.fromPos == null || this.toPos == null)
        {
            return;
        }

        Vec3d from = this.fromPos
            .getRenderPosition(tickDelta)
            .subtract(camera.getPos());
        Vec3d to = this.toPos
            .getRenderPosition(tickDelta)
            .subtract(camera.getPos());
        Vec3d distance = to.subtract(from);

        int segments = random.nextBetween(1, 2) + (int) (distance.length() / segmentLength);
        Vector3f start = from.toVector3f();
        Vector3f step = distance.toVector3f().div(segments);

        // segment points pos relative to camera
        Vector3f[] points = new Vector3f[segments + 1];
        for (int i = 0; i < segments + 1; i++)
        {
            points[i] = new Vector3f(step)
                .mul(i)
                .add(start);
        }

        for (int e = 0; e < strands; e++)
        {
            Vector3f[] copy = Arrays.copyOf(points, points.length);
            for (int i = 1; i < segments; i++)
            {
                // -0.5f ~ 0f ~ 0.5f
                float f = (float) i / segments - 0.5f;
                // 0.5f ~ 1f ~ 0.5f
                f = 1 - MathHelper.abs(f);
                float disturb = f * random.nextFloat() * maxDisturb;
                copy[i].add(randomVector3f(disturb));
            }
            for (int i = 0; i < segments; i++)
            {
                makeBeam(vertexConsumer, tickDelta, copy[i], copy[i + 1], branchChance);
            }
        }
    }

    private void makeBeam(VertexConsumer vertexConsumer, float tickDelta, Vector3f start, Vector3f end, float chanceToBranch)
    {
        Vector3f direction = end.sub(start, new Vector3f()).normalize();
        float yaw = (float) -MathHelper.atan2(direction.x(), direction.z());

        // make small/mid/large tubes
        setRGBA(1, 1, 1, 1);
        makeTube(vertexConsumer, tickDelta, yaw, start, end, 0.03f);

        setRGBA(0.25f, 0.7f, 1, 0.3f);
        makeTube(vertexConsumer, tickDelta, yaw, start, end, 0.06f);

        setRGBA(0.25f, 0.7f, 1, 0.15f);
        makeTube(vertexConsumer, tickDelta, yaw, start, end, 0.13f);

        if (random.nextFloat() < chanceToBranch)
        {
            Vector3f branch = randomVector3f(branchLength).add(start);
            makeBeam(vertexConsumer, tickDelta, start, branch, chanceToBranch * 0.5f);
        }
    }

    private void makeTube(VertexConsumer vertexConsumer, float tickDelta, float yaw, Vector3f start, Vector3f end, float radius)
    {
        // here we ignore pitch just for simplicity
        float dx = radius * MathHelper.cos(yaw);
        float dz = radius * MathHelper.sin(yaw);

        Vector3f[] left = new Vector3f[]{
            new Vector3f(start).add(-dx, -radius, -dz),
            new Vector3f(start).add(-dx, radius, -dz),
            new Vector3f(end).add(-dx, radius, -dz),
            new Vector3f(end).add(-dx, -radius, -dz),
        };
        Vector3f[] right = new Vector3f[]{
            new Vector3f(end).add(dx, -radius, dz),
            new Vector3f(end).add(dx, radius, dz),
            new Vector3f(start).add(dx, radius, dz),
            new Vector3f(start).add(dx, -radius, dz),
        };
        Vector3f[] top = new Vector3f[]{
            new Vector3f(start).add(dx, -radius, dz),
            new Vector3f(start).add(-dx, -radius, -dz),
            new Vector3f(end).add(-dx, -radius, -dz),
            new Vector3f(end).add(dx, -radius, dz),
        };
        Vector3f[] bottom = new Vector3f[]{
            new Vector3f(end).add(dx, radius, dz),
            new Vector3f(end).add(-dx, radius, -dz),
            new Vector3f(start).add(-dx, radius, -dz),
            new Vector3f(start).add(dx, radius, dz),
        };

        // make 4 surfaces of the tube
        makeQuad(vertexConsumer, tickDelta, left);
        makeQuad(vertexConsumer, tickDelta, right);
        makeQuad(vertexConsumer, tickDelta, top);
        makeQuad(vertexConsumer, tickDelta, bottom);
    }

    private void makeQuad(VertexConsumer vertexConsumer, float tickDelta, Vector3f[] points)
    {
        int brightness = this.getBrightness(tickDelta);
        makeVertex(vertexConsumer, points[0], this.getMinU(), this.getMinV(), brightness);
        makeVertex(vertexConsumer, points[1], this.getMaxU(), this.getMinV(), brightness);
        makeVertex(vertexConsumer, points[2], this.getMaxU(), this.getMaxV(), brightness);
        makeVertex(vertexConsumer, points[3], this.getMinU(), this.getMaxV(), brightness);
    }

    private void makeVertex(VertexConsumer vertexConsumer, Vector3f point, float u, float v, int brightness)
    {
        vertexConsumer.vertex(point.x(), point.y(), point.z())
            .texture(u, v)
            .color(this.red, this.green, this.blue, this.alpha)
            .light(brightness)
            .next();
    }

    @NotNull
    @Override
    public ParticleTextureSheet getType()
    {
        return PARTICLE_EMISSIVE;
    }

    public static final ParticleTextureSheet PARTICLE_EMISSIVE = new ParticleTextureSheet()
    {
        @Override
        public void begin(BufferBuilder builder, TextureManager textureManager)
        {
            RenderSystem.depthMask(true);
            RenderSystem.enableBlend();
            RenderSystem.disableCull();
            RenderSystem.blendFunc(GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE);
            builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
        }

        @Override
        public void draw(Tessellator tessellator)
        {
            tessellator.draw();
        }

        public String toString()
        {
            return "PARTICLE_EMISSIVE";
        }
    };

    @Override
    protected int getBrightness(float tickDelta)
    {
        return LightmapTextureManager.MAX_LIGHT_COORDINATE;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<ZapParticleOption>
    {
        private final SpriteProvider sprite;

        public Factory(SpriteProvider pSprite)
        {
            this.sprite = pSprite;
        }

        public Particle createParticle(@NotNull ZapParticleOption options, @NotNull ClientWorld world, double x, double y, double z, double vx, double vy, double vz)
        {
            var particle = new ZapParticle(world, x, y, z, vx, vy, vz, options);
            particle.setSprite(this.sprite);
            return particle;
        }
    }
}
