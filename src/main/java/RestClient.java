import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by deniz on 26/04/17.
 */
public class RestClient
{
    private HttpClient client;

    public RestClient()
    {
        client = HttpClientBuilder.create().build();
    }

    // dictionary methods

    public String getNLength(int n)
    {
        HttpGet getMethod = new HttpGet(Config.SERVER_URL + Config.GET_N_LENGTH_STRING + n);
        return makeGetRequest(getMethod);
    }

    // document_analyzer methods
    public String analyzeSearchResults(String query, int length, int count)
    {
        String JsonString = "{\"query\":\" " + query + "\", \"length\": " + length + ", \"count\": " + count + "}";
        System.out.println(JsonString);
        HttpPost postMethod = new HttpPost(Config.SERVER_URL + Config.ANALYZE_SEARCH_RESULT_STRING);

        return makePostRequest(postMethod, JsonString);
    }

    // datamuse methods
    public String dataMuseAnswerList(String ml, int wordLength)
    {
        String JsonString = "{\"ml\":\"" + ml + "\", \"word_length\": " + wordLength + "}";
        HttpPost postMethod = new HttpPost(Config.SERVER_URL + Config.SEARCH_DATAMUSE_ANSWER_LIST_STRING);

        return makePostRequest(postMethod, JsonString);
    }

    public String wikiSearch(String ml, int wordLength)
    {
        String JsonString = "{\"ml\":\"" + ml + "\", \"word_length\": " + wordLength + "}";
        HttpPost postMethod = new HttpPost(Config.SERVER_URL + Config.WIKI_SEARCH_STRING);

        return makePostRequest(postMethod, JsonString);
    }

    // syn_ant methods
    public String findAllSynonyms(String str, int wordLength)
    {
        String JsonString = "{\"str\":\"" + str + "\", \"word_length\": " + wordLength + "}";
        HttpPost postMethod = new HttpPost(Config.SERVER_URL + Config.FIND_ALL_SYNONYMS_STRING);

        return makePostRequest(postMethod, JsonString);
    }

    public String findAllAntonyms(String str, int wordLength)
    {
        String JsonString = "{\"str\":\"" + str + "\", \"word_length\": " + wordLength + "}";
        HttpPost postMethod = new HttpPost(Config.SERVER_URL + Config.FIND_ALL_ANTONYMS_STRING);

        return makePostRequest(postMethod, JsonString);
    }

    // Requests
    private String makePostRequest(HttpPost postMethod, String JsonString)
    {
        StringEntity requestEntity = new StringEntity(JsonString, ContentType.APPLICATION_JSON);
        postMethod.addHeader("User-Agent", Config.USER_AGENT);
        postMethod.setEntity(requestEntity);
        StringBuffer result = new StringBuffer();

        try {
            HttpResponse rawResponse = client.execute(postMethod);
            System.out.println("Response Code : " + rawResponse.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(new InputStreamReader(rawResponse.getEntity().getContent()));

            String line = "";
            while ((line = rd.readLine()) != null)
            {
                result.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    private String makeGetRequest(HttpGet getMethod)
    {
        getMethod.addHeader("User-Agent", Config.USER_AGENT);
        StringBuffer result = new StringBuffer();

        try {
            HttpResponse rawResponse = client.execute(getMethod);
            System.out.println("Response Code : " + rawResponse.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(new InputStreamReader(rawResponse.getEntity().getContent()));

            String line = "";
            while ((line = rd.readLine()) != null)
            {
                result.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}
