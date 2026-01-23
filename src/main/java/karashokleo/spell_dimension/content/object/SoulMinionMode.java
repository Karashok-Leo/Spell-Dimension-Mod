package karashokleo.spell_dimension.content.object;

public enum SoulMinionMode
{
    FOLLOW,
    FORCE_FOLLOW,
    STANDBY;

    public SoulMinionMode next()
    {
        return switch (this)
        {
            case FOLLOW -> FORCE_FOLLOW;
            case FORCE_FOLLOW -> STANDBY;
            case STANDBY -> FOLLOW;
        };
    }
}
