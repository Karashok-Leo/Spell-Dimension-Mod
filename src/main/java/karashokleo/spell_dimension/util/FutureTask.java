package karashokleo.spell_dimension.util;

import karashokleo.l2hostility.content.event.GenericEvents;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class FutureTask<T>
{
    public static <T> void submit(CompletableFuture<T> future, Consumer<T> consumer)
    {
        new FutureTask<>(future).submit(consumer);
    }

    private T result;
    private boolean done;
    private final CompletableFuture<T> future;

    public FutureTask(CompletableFuture<T> future)
    {
        this.result = null;
        this.future = future;
        this.done = false;
    }

    public void submit(Consumer<T> consumer)
    {
        this.future.thenAccept(t ->
        {
            this.result = t;
            this.done = true;
        });
        GenericEvents.schedulePersistent(() ->
        {
            if (this.done) consumer.accept(result);
            return this.done;
        });
    }
}
