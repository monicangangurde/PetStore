package StepDefinition;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.formatter.model.DataTableRow;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.management.Query;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectReader;

//import static com.jayway.restassured.RestAssured.basic;
//import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PetStore 
{
	private Response response;
	private String url;
	Map<String, String> body;

	@Given("^store inventory is up and running for \"([^\"]*)\"$")
	public void store_inventory_is_up_and_running_for(String url)
	{
		this.url = url;
		response = given().when().get(url);
		Assert.assertEquals("PetStore is up and running", 200, response.getStatusCode());
	}
	
	@When("^user sends a get request to \"([^\"]*)\"$")
	public void user_sends_a_get_request_to(String api_url)
	{
		this.url = this.url + api_url;
	}
	
	@And("^user performs the get request$")
	public void user_performs_the_get_request()
	{
		response = given().headers("api_key", "special-key").when().get(this.url);
	}
	
	@Then("^the response code must be (\\d+)$")
	public void the_response_code_must_be (int responseCode)
	{
		response.then().assertThat().statusCode(responseCode);
	}
	
	@And("^I should see response with pairs as below$")
	public void I_should_see_response_with_pairs_as_below (DataTable dataTable) throws Throwable
	{
	
		Map<String, String> responseFields = new LinkedHashMap<String, String>();
		for(DataTableRow row : dataTable.getGherkinRows())
		{
			String key = row.getCells().get(0);
			String value = row.getCells().get(1);
			responseFields.put(key, value);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> responseMap;
		responseMap = mapper.readValue(response.getBody().asString(), new TypeReference<HashMap<String,String>>(){});
		
		for (String key: responseFields.keySet())
		{
			Assert.assertTrue(responseMap.containsKey(key));
			Assert.assertEquals(responseFields.get(key), (responseMap.get(key).toString()));
		}
	}
	
	@When("^user sends a post request to \"([^\"]*)\" with below details$")
	public void user_sends_a_post_request_to_with_below_details(String api_url, DataTable dataTable) throws Throwable 
	{
		 this.url = this.url + api_url;
		 this.body= new LinkedHashMap<String, String>();
		for (DataTableRow row : dataTable.getGherkinRows())
		{
			String key = row.getCells().get(0);
			String value = row.getCells().get(1);
			this.body.put(key, value);
		}

	}

	@And("^perform the post request$")
	public void perform_the_post_request() 
	{
		response = given().contentType(ContentType.JSON).body(this.body).when().post(this.url);
	}
	
	@Given("^user performs a delete request on \"([^\"]*)\" with order id as \"([^\"]*)\"$")
	public void user_performs_a_delete_request_on_with_order_id_as(String api_url, String orderID) throws Throwable {
		this.url = this.url + api_url + orderID;

	}

	@Given("^user performs the delete request$")
	public void user_performs_the_delete_request() throws Throwable {
		response = given().contentType(ContentType.JSON).when().delete(this.url);

	}
}

