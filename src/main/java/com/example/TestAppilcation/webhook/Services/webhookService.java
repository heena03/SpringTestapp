package com.example.TestAppilcation.webhook.Services;

import com.example.TestAppilcation.webhook.webhookReq;
import com.example.TestAppilcation.webhook.webhookRes;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class webhookService {

    private final RestTemplate restTemplate = new RestTemplate();

    public void startProcess() {

        webhookReq request = new webhookReq("John Doe", "REG12347", "john@example.com");


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<webhookReq> entity = new HttpEntity<>(request, headers);

        ResponseEntity<webhookRes> response = restTemplate.postForEntity(
                "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA",
                entity,
                webhookRes.class
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            webhookRes webhookData = response.getBody();
             System.out.println("Webhook URL: " + webhookData.getWebhook());
              System.out.println("Access Token: " + webhookData.getAccessToken());


            String regNo = request.getRegNo();
            String finalQuery = solveSQLProblem(regNo);


            submitFinalQuery(webhookData.getWebhook(), webhookData.getAccessToken(), finalQuery);
        } else {
            System.out.println("Failed to generate webhook.");
        }
    }

    private String solveSQLProblem(String regNo) {
        int lastDigit = Character.getNumericValue(regNo.charAt(regNo.length() - 1));

        if (lastDigit % 2 == 1) {
            //
            System.out.println("Assigned Question 1 (Odd)");
            return "";
        } else {

            System.out.println("Assigned Question 2 (Even)");
            return "";
        }
    }

    private void submitFinalQuery(String webhookUrl, String accessToken, String finalQuery) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken); // Sets Authorization: Bearer <token>

        String payload = "{\"finalQuery\": \"" + finalQuery + "\"}";

        HttpEntity<String> entity = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                webhookUrl,
                HttpMethod.POST,
                entity,
                String.class
        );

        System.out.println("Submit status: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody());
    }
}
