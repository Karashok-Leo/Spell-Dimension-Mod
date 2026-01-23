package karashokleo.spell_dimension.content.object;

public class SoulMinionMode
{
    public enum Move
    {
        FOLLOW,
        FORCE_FOLLOW,
        STANDBY;

        public Move next()
        {
            return switch (this)
            {
                case FOLLOW -> FORCE_FOLLOW;
                case FORCE_FOLLOW -> STANDBY;
                case STANDBY -> FOLLOW;
            };
        }
    }

    public enum Attack
    {
        DEFAULT,
        AGGRESSIVE;

        public Attack next()
        {
            return switch (this)
            {
                case DEFAULT -> AGGRESSIVE;
                case AGGRESSIVE -> DEFAULT;
            };
        }
    }
}
