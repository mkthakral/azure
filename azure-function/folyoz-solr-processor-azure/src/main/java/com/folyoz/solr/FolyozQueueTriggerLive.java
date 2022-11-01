package com.folyoz.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;

import com.folyoz.solr.processor.SolrProcessorV2;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.QueueTrigger;

/**
 * Azure Functions with Azure Storage Queue trigger.
 */
public class FolyozQueueTriggerLive {
    /**
     * This function will be invoked when a new message is received at the specified path. The message contents are provided as input to this function.
     */
	SolrProcessorV2 solrProcessor;
	
    @FunctionName("FolyozQueueTriggerLive")
    public void run(
        @QueueTrigger(name = "message", queueName = "folyoz-products-live", connection = "queue0storage0account_STORAGE") String message,
        final ExecutionContext context
    ) throws SolrServerException, IOException {
    	solrProcessor = new SolrProcessorV2();
        context.getLogger().info("[START]: " + message);
        solrProcessor.updateRequest(message, context.getLogger(),System.getenv("folyoz_solr_username"),System.getenv("folyoz_solr_password"), System.getenv("folyoz_solr_endpoint_live"));    
    }
}
