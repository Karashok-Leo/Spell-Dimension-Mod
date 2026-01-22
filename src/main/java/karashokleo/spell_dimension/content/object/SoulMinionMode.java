package karashokleo.spell_dimension.content.object;

public enum SoulMinionMode
{
    FOLLOW,
    FORCED_FOLLOW,
    STANDBY;

    public SoulMinionMode next()
    {
        return switch (this)
        {
            case FOLLOW -> FORCED_FOLLOW;
            case FORCED_FOLLOW -> STANDBY;
            case STANDBY -> FOLLOW;
        };
    }
}
