import java.util.ArrayList;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 *
 * @author Asus
 */

 
public class TestApiObject {

    private ArrayList<String> idObjects = new ArrayList<String>();

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://api.restful-api.dev/objects";
    }

    @Test(priority=0)
    public void GetObjects(){
        RequestSpecification httpRequest = RestAssured.given();

        Response response = httpRequest.request(Method.GET,"");
       
        //System.out.println("Response nya : " + response.asPrettyString());
        System.out.println("status code : "+ response.statusCode());

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority=0)
    public void GetObjectWithParam(){
        GetObjectByParams(10);
        GetObjectByParams(5);
        GetObjectByParams(7);
    }

    public void GetObjectByParams(int _id){
        RequestSpecification httpRequest = RestAssured.given().param(("id"), _id);

        Response response = httpRequest.request(Method.GET,"");
       
        System.out.println("Response nya : " + response.asPrettyString());
        System.out.println("status code : "+ response.statusCode());

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority=0)
    public void PostObjects(){
        PostObjectByParams("Apple MacBook Pro 16", 2019, 1849.99, "Intel Core i9", "1 TB");
        PostObjectByParams("Asus TUF", 2020, 1500, "Intel Core i7", "1 TB");
        PostObjectByParams("Lenovo Legion", 2023, 1650.55, "Intel Core i9", "2 TB");
    }

    public void PostObjectByParams(String _name, int _year, double _price, String _model, String _diskSize){
         RequestSpecification httpRequest = RestAssured.given();
         httpRequest.contentType("application/json; utf-8");
        // Body request
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", _name);

        JSONObject dataObject = new JSONObject();
        dataObject.put("year", _year);
        dataObject.put("price", _price);
        dataObject.put("CPU model", _model);
        dataObject.put("Hard disk size", _diskSize);
        
        jsonObject.put("data", dataObject);
        
        httpRequest.body(jsonObject.toString());
        Response response = httpRequest.post();
        System.out.println("Response: " + response.asPrettyString());

        idObjects.add(response.jsonPath().getString("id"));
        Assert.assertNotNull(response.jsonPath().getString("createdAt"));
        Assert.assertEquals(response.getStatusCode(), 200);
        
    }

    @Test(priority=1)
    public void DeleteObjectById(){
        //RequestSpecification httpRequest = RestAssured.given().param(("id"), _id);
        RequestSpecification httpRequest = RestAssured.given();

        for (String idObj : idObjects) {
            System.out.println("idobj nya : " + idObj);
            Response response = httpRequest.request(Method.DELETE,idObj);

            System.out.println("Response nya : " + response.asPrettyString());
            System.out.println("status code : "+ response.statusCode());

            Assert.assertEquals(response.getStatusCode(), 200);
        }   
   }

}
