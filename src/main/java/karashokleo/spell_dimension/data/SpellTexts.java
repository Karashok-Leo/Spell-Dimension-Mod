package karashokleo.spell_dimension.data;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.buff.BlazingMark;
import karashokleo.spell_dimension.content.buff.Electrocution;
import karashokleo.spell_dimension.content.buff.Nucleus;
import karashokleo.spell_dimension.content.effect.*;
import karashokleo.spell_dimension.content.entity.BallLightningEntity;
import karashokleo.spell_dimension.content.entity.BlackHoleEntity;
import karashokleo.spell_dimension.content.entity.ChainLightningEntity;
import karashokleo.spell_dimension.content.spell.ChainLightningSpell;
import karashokleo.spell_dimension.content.spell.ConvergeSpell;
import karashokleo.spell_dimension.content.spell.RandomEffectSpell;
import karashokleo.spell_dimension.content.spell.SoulSacrificeSpell;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.Locale;

public enum SpellTexts
{
    LIGHT(
        "Spell Light",
        "魔力之光",
        "Launch a spell, if it touches a block, it will light up the surrounding area.",
        "发射一道咒语, 若其触碰到方块，则会点亮周围一片区域."
    ),
    LOCATE(
        "Spell Locate",
        "魔力定位",
        "Launch a spell, if it touches a Lodestone, it will consume the item in your off-hand as a key item to find the corresponding structure or biome. If found, there is a 30%% chance to break the Lodestone and summon a portal to that structure or biome. You can look up all recipes by querying the Lodestone's uses in EMI/REI. Alternatively, search for the structure/biome name in EMI/REI to find it.",
        "发射一道咒语，若其触碰到磁石，则会以消耗你副手的物品作为索引物品，寻找相应的结构或群系。若找到，则有30%%的概率击碎磁石，召唤出一个通往对应结构或群系的传送门。你可以在EMI/REI中对磁石查询用途以查看所有配方。或者在EMI/REI中搜索结构/群系名称进行查找。"
    ),
    SUMMON(
        "Spell Summon",
        "魔力召唤",
        "Launch a spell, if it touches a Spawner, it will consume the item in your off-hand as a key item to replace the mob in the Spawner. You can look up all recipes by querying the Spawner's uses in EMI/REI.",
        "发射一道咒语，若其触碰到刷怪笼，则会以消耗你副手的物品作为索引物品，替换刷怪笼中的生物。你可以在EMI/REI中对刷怪笼查询用途以查看所有配方。"
    ),
    PLACE(
        "Remote Manipulation",
        "远端操控",
        "Launch a spell, if it touches a block, it will use the item in your off-hand on the block.",
        "发射一道咒语，若其触碰到方块，则会对其使用你副手的物品。"
    ),
    BREAK(
        "Remote Destruction",
        "远端瓦解",
        "Launch a spell, if it touches a block, it will break it and drop the item it was supposed to drop.",
        "发射一道咒语，若其触碰到方块，则会将其破坏并掉落其本应掉落的物品。"
    ),
    MOON_SWIM(
        "Moon Swim",
        "月泳",
        "Apply Air Hop and Slow Falling effects to oneself for {effect_duration} seconds.",
        "施法者获得空中跳跃效果和缓降效果，持续{effect_duration}秒。"
    ),
    INCARCERATE(
        "Incarcerate",
        "禁锢",
        "Incarcerate the target for {effect_duration} seconds.",
        "禁锢目标，持续{effect_duration}秒。"
    ),
    FORCE_LANDING(
        "Force Landing",
        "迫降",
        "Make all targets within a certain range get the Force Landing {effect_amplifier} effect for {effect_duration} seconds.",
        "使一定范围内所有目标获得迫降{effect_amplifier}效果，持续{effect_duration}秒。"
    ),
    CONVERGE(
        "Converge",
        "汇聚",
        "Launch a spell to explode at the landing point and make enemies converged. Deal %.1fx spell power damage.".formatted(ConvergeSpell.DAMAGE_FACTOR),
        "发射一道咒语，在落点处爆炸并汇聚敌人。造成%.1f倍法术强度的伤害。".formatted(ConvergeSpell.DAMAGE_FACTOR)
    ),
    BLACK_HOLE(
        "Black Hole",
        "黑洞",
        "Create a Black Hole that continuously attracts surrounding creatures and deals damage, disappearing after %d seconds. The higher your spell power, the larger the radius of the black hole. Each attraction deals %.1fx spell power damage.".formatted(BlackHoleEntity.LIFESPAN / 20, BlackHoleEntity.DAMAGE_FACTOR),
        "制造一个黑洞，持续吸引周围的生物并造成伤害，%d秒后消失。法术强度越高，黑洞半径越大。每次吸引造成%.1f倍法术强度的伤害。".formatted(BlackHoleEntity.LIFESPAN / 20, BlackHoleEntity.DAMAGE_FACTOR)
    ),
    PHASE(
        "Phase",
        "相位",
        "Apply Phase {effect_amplifier} effect to oneself for {effect_duration} seconds. Phase: Free to fly through walls.",
        "施法者获得相位{effect_amplifier}效果，持续{effect_duration}秒。相位：自由穿墙飞行。"
    ),
    SHIFT(
        "Shift",
        "移形换影",
        "Launch a spell, if it touches a mob, exchange your position with that mob.",
        "发射一道咒语，若其触碰到生物，则交换你与该生物的位置。"
    ),
    ARCANE_BARRIER(
        "Arcane Barrier",
        "奥术屏障",
        "Generate an arcane barrier around oneself.",
        "在自身周围生成一个奥术屏障。"
    ),
    BLAST(
        "Blast",
        "热爆",
        "Causing a powerful flame explosion, repelling surrounding creatures and causing {damage} fire spell damage.",
        "造成一次威力巨大的烈焰爆炸，击退周围生物并造成{damage}点伤害。"
    ),
    IGNITE(
        "Ignite",
        "引火",
        "Apply Ignite effect to oneself. Ignite: " + BlazingMark.getDesc(true),
        "施法者获得引火效果。引火：" + BlazingMark.getDesc(false)
    ),
    FIRE_OF_RETRIBUTION(
        "Fire of Retribution",
        "业火",
        "Burn a random trait of the target every second. The higher the levelFactor of the trait consumed, the higher the damage caused. No effect on bosses.",
        "每秒灼烧目标的随机一个词条，该词条的等级消耗越高，造成的伤害越高。对Boss无效。"
    ),
    FROST_AURA(
        "Frost Aura",
        "霜环",
        "Apply Frost Aura {effect_amplifier} effect to oneself for {effect_duration} seconds. Frost Aura: %s; Frosted: %s".formatted(FrostAuraEffect.getDesc(true), FrostedEffect.getDesc(true)),
        "施法者获得霜环{effect_amplifier}效果，持续{effect_duration}秒。霜环：%s；霜冻：%s".formatted(FrostAuraEffect.getDesc(false), FrostedEffect.getDesc(false))
    ),
    FROST_BLINK(
        "Frost Blink",
        "冰瞬",
        "Teleport forward and detonate some icicles at your original position.",
        "传送至前方，在原地引爆冰锥。"
    ),
    FROZEN(
        "Frozen",
        "冰封",
        "Freeze all targets within a certain range for {effect_duration} seconds.",
        "冻结一定范围内所有目标，持续{effect_duration}秒。"
    ),
    ICY_NUCLEUS(
        "Icy Nucleus",
        "冰核",
        "Freeze the target's heart into a ice nucleus that explodes in %s seconds, deals %.1fx spell power damage and shoots icicles into the surrounding area. ".formatted(Nucleus.TOTAL_DURATION / 20F, Nucleus.DAMAGE_FACTOR),
        "将敌人的心脏化作一个冰核，%s秒后爆炸，造成%.1f倍法术强度的伤害并向周围射出冰刺。".formatted(Nucleus.TOTAL_DURATION / 20F, Nucleus.DAMAGE_FACTOR)
    ),
    ICICLE(
        "Icicle",
        "冰刺",
        "Shooting an icicle, which deals {damage} frost spell damage to the hit enemy and can cause a chain reaction.",
        "发射一道冰刺，对命中的敌人造成{damage}点伤害，冰刺可以引发链式反应。"
    ),
    DIVINE_CURSE_BLAST(
        "Divine Curse Blast",
        "神咒轰击",
        "Detonate an area around the target, dealing {damage} damage and applying a Curse V effect.",
        "引爆目标周围一片区域，造成{damage}点伤害并施加诅咒V效果。"
    ),
    SPELL_POWER(
        "Empowering Presence",
        "魔力增强",
        "Apply Empowering Presence {effect_amplifier} effect to target or oneself for {effect_duration} seconds. Empowering Presence: Increases spell power by 10%% per level.",
        "目标或施法者获得魔力增强{effect_amplifier}效果，持续{effect_duration}秒。魔力增强：每级增加10%%法术强度。"
    ),
    SPELL_POWER_ADVANCED(
        "Advanced Empowering Presence",
        "高级魔力增强",
        "Apply Empowering Presence {effect_amplifier} effect to target or oneself for {effect_duration} seconds. Empowering Presence: Increases spell power by 10%% per level.",
        "目标或施法者获得魔力增强{effect_amplifier}效果，持续{effect_duration}秒。魔力增强：每级增加10%%法术强度。"
    ),
    REGEN(
        "Regen",
        "再生",
        "Apply Regenerate {effect_amplifier} effect to target or oneself for {effect_duration} seconds.",
        "目标或施法者获得生命再生{effect_amplifier}效果，持续{effect_duration}秒。"
    ),
    REGEN_ADVANCED(
        "Advanced Regen",
        "高级再生",
        "Apply Regenerate {effect_amplifier} effect to target or oneself for {effect_duration} seconds.",
        "目标或施法者获得生命再生{effect_amplifier}效果，持续{effect_duration}秒。"
    ),
    RESIST(
        "Resist",
        "抗性",
        "Apply Resistance {effect_amplifier} effect to target or oneself for {effect_duration} seconds.",
        "目标或施法者获得抗性提升{effect_amplifier}效果，持续{effect_duration}秒。"
    ),
    RESIST_ADVANCED(
        "Advanced Resist",
        "高级抗性",
        "Apply Resistance {effect_amplifier} effect to target or oneself for {effect_duration} seconds.",
        "目标或施法者获得抗性提升{effect_amplifier}效果，持续{effect_duration}秒。"
    ),
    HASTE(
        "Spell Haste",
        "施法急速",
        "Apply Spell Haste {effect_amplifier} effect to target or oneself for {effect_duration} seconds.",
        "目标或施法者获得施法加速{effect_amplifier}效果，持续{effect_duration}秒。"
    ),
    HASTE_ADVANCED(
        "Advanced Spell Haste",
        "高级施法急速",
        "Apply Spell Haste {effect_amplifier} effect to target or oneself for {effect_duration} seconds.",
        "目标或施法者获得施法加速{effect_amplifier}效果，持续{effect_duration}秒。"
    ),
    SPEED(
        "Speed",
        "迅捷",
        "Apply Speed {effect_amplifier} effect to target or oneself for {effect_duration} seconds.",
        "目标或施法者获得速度{effect_amplifier}效果，持续{effect_duration}秒。"
    ),
    SPEED_ADVANCED(
        "Advanced Speed",
        "高级迅捷",
        "Apply Speed {effect_amplifier} effect to target or oneself for {effect_duration} seconds.",
        "目标或施法者获得速度{effect_amplifier}效果，持续{effect_duration}秒。"
    ),
    CRITICAL_HIT(
        "Spell Volatility",
        "法术波动",
        "Target or caster gains a 50%% spell critical chance for {effect_duration} seconds.",
        "目标或施法者获得50%%法术暴击几率，持续{effect_duration}秒。"
    ),
    CLEANSE(
        "Cleanse",
        "净化",
        "Apply Cleanse effect to target or oneself for {effect_duration} seconds.",
        "目标或施法者获得净化效果，持续{effect_duration}秒"
    ),
    DIVINE_AURA(
        "Divine Aura",
        "神圣光环",
        "Apply Divine Aura {effect_amplifier} effect to oneself for {effect_duration} seconds. Divine Aura: " + DivineAuraEffect.getDesc(true),
        "施法者获得神圣光环{effect_amplifier}效果，持续{effect_duration}秒。神圣光环：" + DivineAuraEffect.getDesc(false)
    ),
    EXORCISM(
        "Exorcism",
        "驱邪",
        "Reduce the target's level to half of the original, and reacquire the traits, while the caster takes damage equal to the target's original levelFactor. No effect on bosses.",
        "使目标等级降至原来的一半，并且重新获取词条，同时施法者受到相当于目标原有等级的伤害。对Boss无效。"
    ),
    BLESSING(
        "Blessing",
        "祝福",
        "Apply a random beneficial effect (up to level %d) to the target or oneself for %d seconds.".formatted(
            RandomEffectSpell.MAX_AMPLIFIER,
            RandomEffectSpell.DURATION
        ),
        "目标或施法者获得一个随机正面效果（最高%d级），持续%d秒。".formatted(
            RandomEffectSpell.MAX_AMPLIFIER,
            RandomEffectSpell.DURATION
        )
    ),
    MISFORTUNE(
        "Misfortune",
        "厄运",
        "Apply a random harmful effect (up to level %d) to the target for %d seconds.".formatted(
            RandomEffectSpell.MAX_AMPLIFIER,
            RandomEffectSpell.DURATION
        ),
        "目标获得一个随机负面效果（最高%d级），持续%d秒。".formatted(
            RandomEffectSpell.MAX_AMPLIFIER,
            RandomEffectSpell.DURATION
        )
    ),
    HEAVENLY_JUSTICE(
        "Heavenly Justice",
        "天降正义",
        "Execute Justice Verdict on the target, dealing {damage} damage and additional damage based on the sum of the caster's positive effect and the target's negative effect type number.",
        "对目标执行正义裁决，造成{damage}伤害和基于施法者的正面效果和目标的负面效果种类数量之和倍率的额外伤害。"
    ),
    CHAIN_LIGHTNING(
        "Chain Lightning",
        "连锁闪电",
        "Discharges a chain of lightning with a default damage multiplier of %d%%%%, a duration of %d seconds, a number of %d per chain, and a chain range of %.1f blocks.".formatted(
            Math.round(ChainLightningEntity.DAMAGE_FACTOR * 100),
            ChainLightningEntity.LIFESPAN / 20,
            ChainLightningEntity.CHAIN_STEP,
            ChainLightningEntity.RANGE
        ),
        "释放连锁闪电，默认伤害倍率%d%%%%，存续时长%d秒，每次连锁数量为%d，连锁范围%.1f格。".formatted(
            Math.round(ChainLightningEntity.DAMAGE_FACTOR * 100),
            ChainLightningEntity.LIFESPAN / 20,
            ChainLightningEntity.CHAIN_STEP,
            ChainLightningEntity.RANGE
        )
    ),
    STEADY_CURRENT(
        "Steady Current",
        "稳流",
        "[Passive] Chain Lightning duration ×%d".formatted(ChainLightningSpell.STEADY_CURRENT_LIFESPAN_MULTIPLIER),
        "[被动] 连锁闪电存续时间 ×%d".formatted(ChainLightningSpell.STEADY_CURRENT_LIFESPAN_MULTIPLIER)
    ),
    FISSION(
        "Fission",
        "裂变",
        "[Passive] Chain Lightning number per chain +%d".formatted(ChainLightningSpell.FISSION_CHAIN_STEP_BONUS),
        "[被动] 连锁闪电每次连锁数量 +%d".formatted(ChainLightningSpell.FISSION_CHAIN_STEP_BONUS)
    ),
    RESONANCE(
        "Resonance",
        "谐振",
        "[Passive] Chain Lightning range per chain +%d".formatted(ChainLightningSpell.RESONANCE_RANGE_BONUS),
        "[被动] 连锁闪电每次连锁范围 +%d".formatted(ChainLightningSpell.RESONANCE_RANGE_BONUS)
    ),
    BREAKDOWN(
        "Breakdown",
        "击穿",
        "[Passive] Chain Lightning can penetrate blocks",
        "[被动] 连锁闪电可以穿透方块"
    ),
    SURGE(
        "Surge",
        "涌动",
        "[Passive] Chain Lightning/Ball Lightning damage multiplier ×%d".formatted(ChainLightningSpell.SURGE_POWER_MULTIPLIER),
        "[被动] 连锁闪电/球状闪电伤害倍率 ×%d".formatted(ChainLightningSpell.SURGE_POWER_MULTIPLIER)
    ),
    ARCLIGHT(
        "Arclight",
        "弧光",
        "[Passive] Chain Lightning/Ball Lightning damage multiplier ×%d".formatted(ChainLightningSpell.ARCLIGHT_POWER_MULTIPLIER),
        "[被动] 连锁闪电/球状闪电伤害倍率 ×%d".formatted(ChainLightningSpell.ARCLIGHT_POWER_MULTIPLIER)
    ),
    CONSTANT_CURRENT(
        "Constant Current",
        "恒流",
        "[Passive] Chain Lightning duration ×%d".formatted(ChainLightningSpell.CONSTANT_CURRENT_LIFESPAN_MULTIPLIER),
        "[被动] 连锁闪电存续时间 ×%d".formatted(ChainLightningSpell.CONSTANT_CURRENT_LIFESPAN_MULTIPLIER)
    ),
    BALL_LIGHTNING(
        "Ball Lightning",
        "球状闪电",
        "Fire a ball lightning that lasts for %d seconds, touching enemies for damage (with a default damage multiplier of %d%%%%) and bouncing off of touching blocks.".formatted(
            BallLightningEntity.LIFESPAN / 20,
            Math.round(BallLightningEntity.DAMAGE_FACTOR * 100)
        ),
        "发射一个球状闪电，触碰敌人造成伤害，伤害倍率%d%%%%，触碰方块反弹，存续时间%d秒。".formatted(
            Math.round(BallLightningEntity.DAMAGE_FACTOR * 100), BallLightningEntity.LIFESPAN / 20
        )
    ),
    CLOSED_LOOP(
        "Closed Loop",
        "闭环",
        "[Passive] When ball lightning bounces, its lifespan increases by %d seconds".formatted(BallLightningEntity.LIFESPAN_INCREMENT / 20),
        "[被动] 球状闪电反弹时，存续时间增加%d秒".formatted(BallLightningEntity.LIFESPAN_INCREMENT / 20)
    ),
    ELECTROCUTION(
        "Electrocution",
        "电刑",
        "[Passive] The %drd lightning spell damage dealt to the same enemy within %.1f seconds is increased by an additional %d%%%%".formatted(
            Electrocution.MAX_STACKS,
            Electrocution.MAX_DURATION / 20F,
            (int) ((Electrocution.DAMAGE_FACTOR - 1) * 100)
        ),
        "[被动] 在%.1f秒内对同一个敌人造成的第%d次雷电法术伤害额外增加%d%%%%".formatted(
            Electrocution.MAX_DURATION / 20F,
            Electrocution.MAX_STACKS,
            (int) ((Electrocution.DAMAGE_FACTOR - 1) * 100)
        )
    ),
    STORMFLASH(
        "Stormflash",
        "风驰电掣",
        "[Passive] When Chain Lightning strikes an empty space, dash forward. The intensity of the dash depends on the damage multiplier.",
        "[被动] 空放连锁闪电时，向前冲刺。冲刺力度取决于连锁闪电的伤害倍率。"
    ),
    THUNDERBOLT(
        "Thunderbolt",
        "霹雳",
        "Summon thunderbolts on surrounding enemies, dealing {damage} damage and applying a stun for {effect_duration} seconds.",
        "对周围敌人降下落雷，造成{damage}点伤害并施加眩晕，持续{effect_duration}秒。"
    ),
    QUANTUM_FIELD(
        "Quantum Field",
        "量子场",
        "Apply Quantum Field effect to oneself for {effect_duration} seconds. Quantum Field: %s".formatted(QuantumFieldEffect.getDesc(true)),
        "施法者获得量子场效果，持续{effect_duration}秒。量子场：%s".formatted(QuantumFieldEffect.getDesc(false))
    ),
    RAILGUN(
        "Railgun",
        "超电磁炮",
        "When you hit a lightning rune in the air, fire the Railgun. Deals damage and destroys all blocks touched.",
        "击中空中的雷电符文时，发射超电磁炮。造成伤害并摧毁一切方块。"
    ),
    ELECTRIC_BONDAGE(
        "Electric Bondage",
        "电缚",
        "[Passive] Apply a stun when dealing lightning spell damage, increasing the duration by 0.1 seconds per 100 spell power.",
        "[被动] 造成雷电法术伤害时施加眩晕，每100点法术强度增加0.1秒持续时间。"
    ),
    SOUL_SLASH(
        "Soul Slash",
        "灵斩",
        "Fire a soul slash that pierces through enemies and deals {damage} area damage, plus additional damage based on the average level of all your soul minions present.",
        "发射一道灵斩，穿透敌人并造成{damage}点范围伤害，同时根据你在场的所有灵仆的平均等级造成额外伤害。"
    ),
    REQUIEM(
        "Requiem",
        "安魂曲",
        "[Passive] When a soul minion deals damage, it adds an instance of soul spell damage from its owner.",
        "[被动] 灵仆造成伤害时会附加一次来自于其主人的灵魂法术伤害。"
    ),
    POSSESS(
        "Possess",
        "附体",
        "When cast on your soul minion, possess it. When cast on your body while possessing a soul minion, release the possession.",
        "对灵仆施放时，附体选定的灵仆。附体灵仆对本体施放时，解除附体状态。"
    ),
    RECALL(
        "Recall",
        "归魂",
        "[Can only be cast while possessing a soul minion] Teleport the possessed soul minion near your body and release the possession.",
        "[仅限附体灵仆时施放] 附体灵仆传送至本体附近，并解除附体状态。"
    ),
    SOUL_COMMAND(
        "Soul Command",
        "魂令",
        "Switch target soul minion behavior mode: Follow / Force Follow / Standby",
        "切换目标灵仆的行为模式：跟随/强制跟随/待命"
    ),
    SOUL_SWAP(
        "Soul Swap",
        "元魂易位",
        "[Can only be cast while possessing a soul minion] Exchange the position of your body and the possessed soul minion.",
        "[仅限附体灵仆时施放] 交换本体与附体灵仆的位置。"
    ),
    SOUL_STEP(
        "Soul Step",
        "灵跃",
        "[Can only be cast while possessing a soul minion] Teleport your body to the location of the possessed soul minion, and the possessed soul minion surges forward for a distance.",
        "[仅限附体灵仆时施放] 本体传送至附体灵仆位置，附体灵仆向前突进一段距离。"
    ),
    SOUL_MARK(
        "Soul Mark",
        "魂印",
        "Mark the enemy, causing all soul minions to teleport near the target and attack it. The marked target takes additional soul spell damage for {effect_duration} seconds.",
        "标记敌人，使所有灵仆传送到目标附近发起攻击。被标记目标受到额外灵魂伤害，持续{effect_duration}秒。"
    ),
    SOUL_DUET(
        "Soul Duet",
        "灵魂二重奏",
        "[Passive] Doubles the soul spell damage dealt by Requiem.",
        "[被动] 安魂曲造成的灵魂法术伤害翻倍。"
    ),
    PHANTOM_SYNDICATE(
        "Phantom Syndicate",
        "魅影辛迪加",
        "[Passive] Damage or healing effects received by soul minions are shared among all soul minions.",
        "[被动] 灵仆受到的伤害或治愈系效果将被分摊到所有灵仆身上。"
    ),
    SOUL_BEAM(
        "Soul Beam",
        "灵能射线",
        "Channels a beam of light, healing soul minions by {heal}, and dealing {damage} spell damage to enemies every second.",
        "引导一束射线，治疗灵仆{heal}点生命值，并对敌人造成每秒{damage}点法术伤害。"
    ),
    SOUL_ECHO(
        "Soul Echo",
        "灵魂回响",
        "You and all your soul minions, fire Soul Echoes toward the target, dealing soul magic damage.",
        "本体连同所有灵仆，向目标方向发射灵魂回响，造成灵魂法术伤害。"
    ),
    SOUL_BURST(
        "Soul Burst",
        "魂爆",
        "When cast on your soul minion, sacrifice and detonate it, dealing ranged soul damage based on the soul minion's health.",
        "对灵仆施放时，献祭并引爆选定的灵仆，根据灵仆生命值造成范围灵魂伤害。"
    ),
    SOUL_SACRIFICE(
        "Soul Sacrifice",
        "灵献",
        "When cast on your soul minion with max health exceeding %d, sacrifice %d%%%% of its max health to gain a set of loot.".formatted(SoulSacrificeSpell.THRESHOLD, Math.round(SoulSacrificeSpell.HEALTH_RATIO * 100)),
        "对最大生命值高于%d灵仆施放时，献祭选定灵仆的%d%%%%最大生命值，获得一份战利品。".formatted(SoulSacrificeSpell.THRESHOLD, Math.round(SoulSacrificeSpell.HEALTH_RATIO * 100))
    ),
    ETHEREAL_EVASION(
        "Ethereal Evasion",
        "分神",
        "Apply Ethereal Evasion effect to oneself for {effect_duration} seconds. Ethereal Evasion: %s".formatted(EtherealEvasionEffect.getDesc(true)),
        "施法者获得分神{effect_amplifier}效果，持续{effect_duration}秒。分神：%s".formatted(EtherealEvasionEffect.getDesc(false))
    ),
    LIGHTMOON(
        "Light Moon",
        "浅月",
        "Leaves a cloud of spells on the target, dealing {damage} damage.",
        "在目标处留下法术云，造成{damage}点伤害。"
    ),
    GREATMOON(
        "Great Moon",
        "漫月",
        "Leaves a cloud of spells on the target, dealing {damage} damage.",
        "在目标处留下法术云，造成{damage}点伤害。"
    ),
    FORLORN(
        "Scythe Dance",
        "镰舞",
        "Continuously swing the scythe in your hand, dealing {damage} damage.",
        "持续挥舞手中巨镰，造成{damage}点伤害。"
    ),
    KAYN(
        "Scythe Dance",
        "镰舞",
        "Continuously swing the scythe in your hand, dealing {damage} damage.",
        "持续挥舞手中巨镰，造成{damage}点伤害。"
    ),
    RHAAST(
        "Scythe Dance",
        "镰舞",
        "Continuously swing the scythe in your hand, dealing {damage} damage.",
        "持续挥舞手中巨镰，造成{damage}点伤害。"
    ),
    BLOODTHIRSTER(
        "Blood Thirster",
        "渴血",
        "Build up your strength and swing your sword forward to deal {damage} melee damage and heal {heal} health.",
        "蓄力后向前挥剑造成{damage}点近战伤害并回复{heal}点生命值。"
    ),
    FEATHERLIGHT(
        "Featherlight",
        "羽轻",
        "Gain a short-lived Speed and Slow-Falling effect",
        "获得短暂的迅捷和缓降效果。"
    ),
    CRUCIBLE(
        "Empyrean",
        "擎天",
        "Two-Week Turn of the Greatsword, dealing {damage} fire spell damage  and slowing and catching the target on fire.",
        "横转巨剑两周，造成{damage}点火焰法术伤害并使目标着火和缓速。"
    ),
    LICHBANE(
        "Lich Bane",
        "妖斩",
        "Splash multiple times, inflicting {damage} damage, and additional damage when the target's maximum blood levelFactor is below 40.",
        "连续劈出多道剑斩，造成{damage}点伤害，当目标最大血量低于40时造成额外伤害。"
    ),
    DAWNBREAKER(
        "Dawnbreaker",
        "破晓",
        "Meteor Dawn, Strikes the target, dealing {damage} damage and stunning the target.",
        "流星破晓，冲击目标，造成{damage}点伤害并使目标晕眩。"
    ),
    ;

    private final String nameEN;
    private final String nameZH;
    private final String descEN;
    private final String descZH;

    SpellTexts(String nameEN, String nameZH, String descEN, String descZH)
    {
        this.nameEN = nameEN;
        this.nameZH = nameZH;
        this.descEN = descEN;
        this.descZH = descZH;
    }

    public String getName()
    {
        return name().toLowerCase(Locale.ROOT);
    }

    public String getNameKey()
    {
        return "spell.spell-dimension.%s.name".formatted(getName());
    }

    public String getDescKey()
    {
        return "spell.spell-dimension.%s.description".formatted(getName());
    }

    public MutableText getNameText(Object... args)
    {
        return Text.translatable(getNameKey(), args);
    }

    public MutableText getDescText(Object... args)
    {
        return Text.translatable(getDescKey(), args);
    }

    public static void register()
    {
        for (SpellTexts value : SpellTexts.values())
        {
            String nameKey = value.getNameKey();
            String descKey = value.getDescKey();
            SpellDimension.EN_TEXTS.addText(nameKey, value.nameEN);
            SpellDimension.ZH_TEXTS.addText(nameKey, value.nameZH);
            SpellDimension.EN_TEXTS.addText(descKey, value.descEN);
            SpellDimension.ZH_TEXTS.addText(descKey, value.descZH);
        }
    }
}
