package io.chatastic.chatastic.Events;

import com.squareup.otto.Bus;

/**
 * Created by r on 9/9/2014.
 */
public final class BusProvider {

    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }
}
