package bitmovers.elementaldimensions.util;

import javax.annotation.Nonnull;
import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;

/**
 * Created by Elec332 on 6-8-2016.
 */
@SuppressWarnings("all")
public class SafelyCachedObject<T> {

    public SafelyCachedObject(Callable<T> getter){
        this.callable = getter;
        reference = new WeakReference<T>(null);
    }

    private final Callable<T> callable;
    private WeakReference<T> reference;

    @Nonnull
    public T get(){
        T t = reference.get();
        if (t == null){
            try {
                t = callable.call();
            } catch (Exception e){
                throw new IllegalStateException(e);
            }
            if (t == null){
                throw new IllegalArgumentException();
            }
        }
        return t;
    }

}
