package com.folyoz.solr;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageCredentials;
import com.microsoft.azure.storage.StorageCredentialsAccountAndKey;
import com.microsoft.azure.storage.StorageCredentialsSharedAccessSignature;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.queue.CloudQueue;
import com.microsoft.azure.storage.queue.CloudQueueMessage;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;

import com.microsoft.azure.functions.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Azure Functions with Azure Storage Queue trigger.
 */
public class FolyozQueueTriggerPoisonLive {
    /**
     * This function will be invoked when a new message is received at the specified path. The message contents are provided as input to this function.
     */
	
    @FunctionName("FolyozQueueTriggerPoisonLive")
    public void run(
        @QueueTrigger(name = "message", queueName = "folyoz-products-live-poison", connection = "queue0storage0account_STORAGE") String message,
        final ExecutionContext context
    ) throws SolrServerException, IOException, StorageException, URISyntaxException {

    	context.getLogger().info("[START]: " + message);
        
        //original queue
        String outputQueueURIString = "https://queue0storage0account.queue.core.windows.net/folyoz-products-live"; 
        URI outputQueueURI = new URI(outputQueueURIString);
        StorageCredentials lStorageCredentials = new StorageCredentialsAccountAndKey("queue0storage0account", "GVxogXY86YAZYLhiIF1w0+hThwhYNeQz/Jml1ShseDizXlk84Zc47ZpbHJVxaYy/HkKUuY1qFLPtp6X1+uoqQA==");
        CloudQueue outputQueue = new CloudQueue(outputQueueURI, lStorageCredentials);
        		
        
        //delayMessageVisibility in seconds
        int delayMessageVisibility = Integer.parseInt(System.getenv("folyoz_delay_seconds_from_poiston_to_live"));
        
        //insert message into queue
        outputQueue.addMessage(new CloudQueueMessage(message), -1, delayMessageVisibility, null, new OperationContext());
        
        context.getLogger().info("[END]: " + message);
        
        
    }
}
