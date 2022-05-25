package com.infobip.sample.ffosspring_sample;

import com.infobip.ApiClient;
import com.infobip.ApiException;
import com.infobip.Configuration;
import com.infobip.api.SendSmsApi;
import com.infobip.model.SmsAdvancedTextualRequest;
import com.infobip.model.SmsDestination;
import com.infobip.model.SmsResponse;
import com.infobip.model.SmsTextualMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController  // ready for use by Spring MVC to handle web requests
public class FfosSpringSampleController {

    private static final String BASE_URL = "https://api.infobip.com";
    private static final String API_KEY = "API KEY";

    @Value("${phone.numbers}")
    private String numbers;

    @GetMapping("/")
    public String index() {
        return "Pozdrav FFOS-u iz Spring Boot aplikacije! <br/>Brojevi na koje će se slati SMS: " + numbers;
    }

    @PostMapping(path = "/sms")
    public ResponseEntity<Integer> sendSms(@RequestBody String text) {
        String[] phoneNumbers = useAsArray(numbers);
        if (phoneNumbers == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ApiClient apiClient = new ApiClient();
        apiClient.setApiKeyPrefix("App");
        apiClient.setApiKey(API_KEY);
        apiClient.setBasePath(BASE_URL);
        Configuration.setDefaultApiClient(apiClient);

        SendSmsApi sendSmsApi = new SendSmsApi();
        SmsTextualMessage smsMessage = new SmsTextualMessage().from("InfoSMS").text(text);

        for (String number : phoneNumbers) {
            smsMessage.addDestinationsItem(new SmsDestination().to(number));
        }

        SmsAdvancedTextualRequest smsMessageRequest = new SmsAdvancedTextualRequest().messages(Collections.singletonList(smsMessage));
        System.out.println("SMS message request: " + smsMessage);

        try {
            SmsResponse smsResponse = sendSmsApi.sendSmsMessage(smsMessageRequest);
            System.out.println("SMS message response: " + smsResponse);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (ApiException apiException) {
            // HANDLE THE EXCEPTION
            System.out.println("SMS api exception: " + apiException);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private String[] useAsArray(String numbers) {
        if (numbers != null && numbers.trim().isEmpty()) {
            return numbers.split(",");
        }
        return null;
    }

}
