package com.folyoz.solr.test;

import java.io.IOException;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

public class TestSolrConnection1 {
	public static void main(String[] z) throws SolrServerException, IOException {
		// SolrClient solrClient;

		/*
		 * CredentialsProvider provider = new BasicCredentialsProvider();
		 * UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
		 * "myusername","mypassword"); provider.setCredentials(AuthScope.ANY,
		 * credentials); HttpClient client =
		 * HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
		 * 
		 * HttpSolrClient solrClient = new HttpSolrClient(
		 * "http://folyozsolr.southcentralus.cloudapp.azure.com:8983/solr/folyoz-products-live",
		 * client, null, false); // new HttpSolrClient.Builder(
		 * "http://folyozsolr.southcentralus.cloudapp.azure.com:8983/solr/folyoz-products-live"
		 * ).build().
		 */

		SolrClient solrClient;
		solrClient = new HttpSolrClient.Builder("http://folyozsolr.southcentralus.cloudapp.azure.com:8983/solr/").withHttpClient(getHttpClient()).build();
		//client = new SolrClient.Builder().withSolrUrl("http://folyozsolr.southcentralus.cloudapp.azure.com:8983/solr/").withHttpClient(getHttpClient()).build();
//		client.connect();
		solrClient.deleteById("132");	
	
		

	}
	
	private static HttpClient getHttpClient() {
	    CredentialsProvider provider = new BasicCredentialsProvider();
	    UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("solr", "vK7NFhh7nnYtSwshVH5L");
	    provider.setCredentials(AuthScope.ANY, credentials);
	    return HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
	  }
}
	