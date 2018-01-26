package com.brusi.ggj2018.game.objects;

import com.badlogic.gdx.Game;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;

/**
 * Created by Asaf on 26/01/2018.
 */
public class DeactivateClassRemover  {

    private static boolean cond(GameObject o) {
        return o.active;
    }


    public static<T extends GameObject> void filter(Collection<T> c) {
        for (Iterator<T> it = c.iterator(); it.hasNext(); )
            if (!cond(it.next()))
                it.remove();
    }

}
