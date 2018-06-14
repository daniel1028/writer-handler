package org.gooru.nucleus.insights.handlers.processors.repositories.cassandra.transactions;

import org.gooru.nucleus.insights.handlers.processors.command.executor.DBExecutor;
import org.gooru.nucleus.insights.handlers.processors.command.executor.MessageResponse;

public class CassandraTransactionExecutor {

    public static MessageResponse executeTransaction(DBExecutor handler) {
        // First validations without any DB
        handler.checkSanity();
        // Now we need to run with transaction, if we are going to continue
        return execute(handler);
    }
    
    
    private static MessageResponse execute(DBExecutor handler) {
    	handler.validateRequest();
        return handler.executeRequest();
    }
}
