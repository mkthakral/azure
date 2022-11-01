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
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;

public class TestSolrConnection6 {
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
		 * "http://solr:GmB6tyxN5zns423W3nNRUtaRc45MzQWnp3fFbuTU@folyozsolr.southcentralus.cloudapp.azure.com:8983/solr/folyoz-products-live"
		 * ).build();
		 * 
		 * solrClient.deleteById("132");
		 * 
		 * System.out.println("Done");
		 */

		 SolrClient solrClient;
		 solrClient = new HttpSolrClient.Builder("http://solr:GmB6tyxN5zns423W3nNRUtaRc45MzQWnp3fFbuTU@folyozsolr.southcentralus.cloudapp.azure.com:8983/solr/folyoz-products-live").build();
		QueryRequest req = new QueryRequest(new SolrQuery("*:*"));
		req.setBasicAuthCredentials("solr","GmB6tyxN5zns423W3nNRUtaRc45MzQWnp3fFbuTU");
		QueryResponse rsp = req.process(solrClient);
		System.out.println(rsp);
		System.out.println("Done 1");
		req = new QueryRequest();
		
		UpdateRequest req2 = new UpdateRequest();
		req2.deleteById("133");
		req2.setBasicAuthCredentials("solr","GmB6tyxN5zns423W3nNRUtaRc45MzQWnp3fFbuTU");
		req2.process(solrClient);
		
		System.out.println("Done 2");
		
		UpdateRequest req3 = new UpdateRequest();
		req3.deleteByQuery("*");
		req3.setBasicAuthCredentials("solr","GmB6tyxN5zns423W3nNRUtaRc45MzQWnp3fFbuTU");
		req3.process(solrClient);
		solrClient.commit();
		
		System.out.println("Done 3");
		
		
		
		
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("productId", "0003");
		doc.addField("name", "DELETE");
		doc.addField("image", "DELETE");
		doc.addField("imageModal", "DELETE");
		doc.addField("imageModalHeight", "DELETE");
		doc.addField("imageModalWidth", "DELETE");
		doc.addField("categoryId", "DELETE");
		doc.addField("categoryName", "DELETE");
		doc.addField("attributeStyle", "DELETE");
		doc.addField("attributeCategory", "DELETE");
		doc.addField("artistID", "DELETE");
		doc.addField("artistName", "DELETE");
		doc.addField("artistCity", "DELETE");
		doc.addField("artistState", "DELETE");
		doc.addField("artistCountry", "DELETE");
		doc.addField("artistGender", "DELETE");
		doc.addField("artistEthnicity", "DELETE");
		doc.addField("productAvailabilty", "DELETE");
		doc.addField("productClient", "DELETE");
		doc.addField("artistImage", "DELETE");
		doc.addField("artistEducation", "DELETE");
		doc.addField("artistPortfolioLink", "DELETE");
		doc.addField("keywords", "DELETE");
		
		UpdateRequest req4 = new UpdateRequest();
		req4.add(doc);
		req4.setBasicAuthCredentials("solr","GmB6tyxN5zns423W3nNRUtaRc45MzQWnp3fFbuTU");
		req4.process(solrClient);
		solrClient.commit();
		
		System.out.println("Done 4");
		
		req = new QueryRequest(new SolrQuery("productId:0003"));
		req.setBasicAuthCredentials("solr","GmB6tyxN5zns423W3nNRUtaRc45MzQWnp3fFbuTU");
		rsp = req.process(solrClient);
		System.out.println(rsp);
		
		SolrDocumentList docList = rsp.getResults();
		System.out.println("Docs Found: " + docList.getNumFound());
		
		System.out.println(rsp.getStatus());
		System.out.println("Done 5");
	}

	private static HttpClient getHttpClient() {
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("solr", "GmB6tyxN5zns423W3nNRUtaRc45MzQWnp3fFbuTU");
		provider.setCredentials(AuthScope.ANY, credentials);
		return HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
	}
}
