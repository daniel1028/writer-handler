package org.gooru.nucleus.insights.handlers.bootstrap.shutdown;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.gooru.nucleus.insights.handlers.infra.V1CassandraDataSourceRegistry;
import org.gooru.nucleus.insights.handlers.infra.V2CassandraDataSourceRegistry;

public class Finalizers implements Iterable<Finalizer> {

    private final Iterator<Finalizer> internalIterator;

    public Finalizers() {
        final List<Finalizer> finalizers = new ArrayList<>();
        finalizers.add(V1CassandraDataSourceRegistry.getInstance());
        finalizers.add(V2CassandraDataSourceRegistry.getInstance());
        internalIterator = finalizers.iterator();
    }

    @Override
    public Iterator<Finalizer> iterator() {
        return new Iterator<Finalizer>() {

            @Override
            public boolean hasNext() {
                return internalIterator.hasNext();
            }

            @Override
            public Finalizer next() {
                return internalIterator.next();
            }

        };
    }

}
