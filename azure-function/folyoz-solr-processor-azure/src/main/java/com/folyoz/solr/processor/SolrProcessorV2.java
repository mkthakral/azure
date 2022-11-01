package com.folyoz.solr.processor;

import java.io.IOException;
import java.util.logging.Logger;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.io.SolrClientCache;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.json.JSONObject;

public class SolrProcessorV2 {

	SolrClient solrClient;
	SolrClient solrClientModified;

	private static Logger logger;
	private String solrUsername;
	private String solrPassword;

	public void updateRequest(String message, Logger contextLogger, String solrUsername, String solrPassword,
			String solrURL) throws SolrServerException, IOException {
		logger = contextLogger;
		logger.info("[PROCESSING] " + message);
		
		logger.info("Solr URL Row: " + solrURL);
		this.solrClient = new HttpSolrClient.Builder(solrURL).build();
		
		solrURL = solrURL.replace("http://", "http://" + solrUsername + ":" + solrPassword + "@");
		this.solrClientModified = new HttpSolrClient.Builder(solrURL).build();
		
		logger.info("Solr URL Modified: " + solrURL);
		logger.info("Solr Credentials: " + solrUsername + " : " + solrPassword);

		
		this.solrUsername = solrUsername;

		JSONObject jsonObject = new JSONObject(message);
		String action = (String) jsonObject.get("action");
		JSONObject jsonProduct = (JSONObject) jsonObject.get("product");

		if (action.equals("ADD")) {
			actiondAdd(jsonProduct);
		} else if (action.equals("DELETE")) {
			actionDelete(jsonProduct);
		} else if (action.equals("DELETE_ALL")) {
			actionDeleteAll();
		} else if (action.equals("UPDATE")) {
			actionDeleteAndAdd(jsonProduct);
		}

		logger.info("[PROCESSED] " + message);

	}

	private void actionDelete(JSONObject product) throws SolrServerException, IOException {
		logger.info("Solr: DELETE TRY: Product Id: " + product.get("productId"));
		
		QueryRequest req = new QueryRequest(new SolrQuery("productId:" + product.get("productId")));
		req.setBasicAuthCredentials(this.solrUsername, this.solrPassword);
		QueryResponse rsp = req.process(solrClientModified);
		
		SolrDocumentList docList = rsp.getResults();
		System.out.println("Docs Found: " + docList.getNumFound());
		
		
		//if (solrClient.getById((String) product.get("productId")) != null) {
		if (docList.getNumFound() >= 1) {
			logger.info("Solr: document found, will delete now: Product Id: " + product.get("productId"));

			// Solr Request
			UpdateRequest updateRequest = new UpdateRequest();
			updateRequest.deleteById((String) product.get("productId"));
			updateRequest.setBasicAuthCredentials(this.solrUsername, this.solrPassword);
			updateRequest.process(solrClient);
			solrClient.commit();

			logger.info("Solr: DELETED: Product Id: " + product.get("productId"));
		} else {
			logger.info("Solr: DELETE: could not find product: Product Id: " + product.get("productId"));
		}

	}

	private void actionDeleteAll() throws SolrServerException, IOException {
		logger.info("Solr: DELETING ALL PRODUCTS");

		// Solr Request
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.deleteByQuery("*");
		updateRequest.setBasicAuthCredentials(this.solrUsername, this.solrPassword);
		updateRequest.process(solrClient);
		solrClient.commit();

		logger.info("Solr: DELETED ALL PRODUCTS");
	}

	private void actiondAdd(JSONObject product) throws SolrServerException, IOException {
		logger.info("Solr: Adding: Product Id: " + product.get("productId"));
		SolrInputDocument doc = new SolrInputDocument();

		String artistCity = (product.get("artistCity") == JSONObject.NULL
				|| ((String) product.get("artistCity")).isEmpty()) ? "Not Available"
						: (String) product.get("artistCity");
		String artistState = (product.get("artistState") == JSONObject.NULL
				|| ((String) product.get("artistState")).isEmpty()) ? "Not Available"
						: (String) product.get("artistState");
		String artistCountry = (product.get("artistCountry") == JSONObject.NULL
				|| ((String) product.get("artistCountry")).isEmpty()) ? "Not Available"
						: (String) product.get("artistCountry");
		String artistGender = (product.get("artistGender") == JSONObject.NULL
				|| ((String) product.get("artistGender")).isEmpty()) ? "Not Available"
						: (String) product.get("artistGender");
		String artistEthnicity = (product.get("artistEthnicity") == JSONObject.NULL
				|| ((String) product.get("artistEthnicity")).isEmpty()) ? "Not Available"
						: (String) product.get("artistEthnicity");

		doc.addField("productId", product.get("productId"));
		doc.addField("name", product.get("name"));
		doc.addField("image", product.get("image"));
		doc.addField("imageModal", product.get("imageModal"));
		doc.addField("imageModalHeight", product.get("imageModalHeight"));
		doc.addField("imageModalWidth", product.get("imageModalWidth"));
		doc.addField("categoryId", product.get("categoryId"));
		doc.addField("categoryName", product.get("categoryName"));
		doc.addField("attributeStyle", product.get("style"));
		doc.addField("attributeCategory", product.get("categories"));
		doc.addField("artistID", product.get("artistId"));
		doc.addField("artistName", product.get("artistName"));
		doc.addField("artistCity", artistCity);
		doc.addField("artistState", artistState);
		doc.addField("artistCountry", artistCountry);
		doc.addField("artistGender", artistGender);
		doc.addField("artistEthnicity", artistEthnicity);
		doc.addField("productAvailabilty", product.get("availability"));
		doc.addField("productClient", product.get("client"));
		doc.addField("artistImage", product.get("artistImage"));
		doc.addField("artistEducation", product.get("artistEducation"));
		doc.addField("artistPortfolioLink", product.get("artistPortfolioLink"));
		doc.addField("keywords", product.get("productKeywords"));

		// Solr Request
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.add(doc);
		updateRequest.setBasicAuthCredentials(this.solrUsername, this.solrPassword);
		updateRequest.process(solrClient);
		solrClient.commit();

		logger.info("Solr: Added: Product Id: " + product.get("productId"));
	}

	private void actionDeleteAndAdd(JSONObject product) throws SolrServerException, IOException {
		logger.info("Solr: Updating: Product Id: " + product.get("productId"));
		actionDelete(product);
		actiondAdd(product);
		logger.info("Solr: Updated: Product Id: " + product.get("productId"));
	}

}
