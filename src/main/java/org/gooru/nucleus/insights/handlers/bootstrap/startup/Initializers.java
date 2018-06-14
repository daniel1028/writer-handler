package org.gooru.nucleus.insights.handlers.bootstrap.startup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.gooru.nucleus.insights.handlers.infra.V1CassandraDataSourceRegistry;
import org.gooru.nucleus.insights.handlers.infra.V2CassandraDataSourceRegistry;

public class Initializers implements Iterable<Initializer> {
    private final Iterator<Initializer> internalIterator;

    public Initializers() {
        final List<Initializer> initializers = new ArrayList<>();
        initializers.add(V1CassandraDataSourceRegistry.getInstance());
        initializers.add(V2CassandraDataSourceRegistry.getInstance());
        internalIterator = initializers.iterator();
    }

    @Override
    public Iterator<Initializer> iterator() {
        return new Iterator<Initializer>() {

            @Override
            public boolean hasNext() {
                return internalIterator.hasNext();
            }

            @Override
            public Initializer next() {
                return internalIterator.next();
            }

        };
    }

}
