


/*
Project Setup (Maven pom.xml):
Add this dependency to your project to handle JSON parsing.
Generated xml
<dependency>
    <groupId>org.json</groupId>
    <artifactId>json</artifactId>
    <version>20231013</version>
</dependency>
*/

  
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;

public class TeraboxApiExample {

    public static void main(String[] args) {
        // The Terabox URL you want to process
        String teraboxShareUrl = "https://1024terabox.com/s/1bNLoEdlmOuyZcofBcnFdow";

        try {
            // 1. URL-encode the Terabox link to make it safe for a query parameter
            String encodedUrl = URLEncoder.encode(teraboxShareUrl, StandardCharsets.UTF_8);

            // 2. Construct the full API endpoint URL
            String apiUrl = "https://terabox-worker.robinkumarshakya103.workers.dev/api?url=" + encodedUrl;

            // 3. Create an HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // 4. Create an HttpRequest
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Accept", "application/json")
                    .build();

            // 5. Send the request and get the response
            System.out.println("Fetching data from API...");
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 6. Parse the JSON response
            if (response.statusCode() == 200) {
                JSONObject jsonResponse = new JSONObject(response.body());

                if (jsonResponse.getBoolean("success")) {
                    JSONArray files = jsonResponse.getJSONArray("files");
                    if (files.length() > 0) {
                        JSONObject file = files.getJSONObject(0); // Get the first file

                        System.out.println("\n--- File Details ---");
                        System.out.println("File Name: " + file.getString("file_name"));
                        System.out.println("Size: " + file.getString("size"));
                        System.out.println("Download Link: " + file.getString("download_url"));
                        System.out.println("Streaming Link: " + file.getString("streaming_url"));
                        System.out.println("--------------------");
                    }
                } else {
                    System.err.println("API Error: " + jsonResponse.getString("error"));
                }
            } else {
                System.err.println("HTTP Error: " + response.statusCode());
                System.err.println("Response Body: " + response.body());
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Request failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
