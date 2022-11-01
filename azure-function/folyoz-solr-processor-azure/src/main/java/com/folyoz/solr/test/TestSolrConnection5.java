package com.folyoz.solr.test;

import java.io.IOException;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.params.ModifiableSolrParams;

public class TestSolrConnection5 {
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

		// HttpSolrClient solrClient;
		/*
		 * solrClient = new HttpSolrClient.Builder(
		 * "http://solr:vK7NFhh7nnYtSwshVH5L@folyozsolr.southcentralus.cloudapp.azure.com:8983/solr/folyoz-products-live"
		 * ).build();
		 * 
		 * solrClient.deleteById("132");
		 * 
		 * System.out.println("Done");
		 */

		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set(HttpClientUtil.PROP_BASIC_AUTH_USER, "solr");
		params.set(HttpClientUtil.PROP_BASIC_AUTH_PASS, "vK7NFhh7nnYtSwshVH5L");

		params.set(HttpClientUtil.PROP_FOLLOW_REDIRECTS, false);
		  params.set(HttpClientUtil.PROP_ALLOW_COMPRESSION, true);
		  
		HttpClient httpClient = HttpClientUtil.createClient(params);

		SolrClient solrClient;
		solrClient = new HttpSolrClient.Builder("http://folyozsolr.southcentralus.cloudapp.azure.com:8983/solr/")
				.withHttpClient(httpClient).build();
		solrClient.deleteById("132");

		System.out.println("Done");

	}

	private static HttpClient getHttpClient() {
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("solr", "vK7NFhh7nnYtSwshVH5L");
		provider.setCredentials(AuthScope.ANY, credentials);
		return HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
	}
}
