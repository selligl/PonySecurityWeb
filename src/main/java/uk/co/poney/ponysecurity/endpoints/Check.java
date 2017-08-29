package uk.co.poney.ponysecurity.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import uk.co.poney.ponysecurity.exceptions.*;
import uk.co.poney.ponysecurity.models.RequestData;
import uk.co.poney.ponysecurity.models.RequestsMap;
import uk.co.poney.ponysecurity.modules.jwt.Jwt;
import uk.co.poney.ponysecurity.modules.notification.NotificationResponseInterface;
import uk.co.poney.ponysecurity.modules.notification.NotificationServiceInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class Check {

    private static final Logger logger = LoggerFactory.getLogger("PonySecurity.Check");

    @Autowired
    private Jwt jwtTool;

    @Autowired
    private NotificationServiceInterface notificationService;

    @Value("${google.deviceid}")
    private String deviceId;

    @Value("${application.baseurl}")
    private String applicationBaseUrl;

    @Value("${application.2ndFactorTimeout}")
    private String timeoutString;
    @Autowired
    private RequestsMap requestsMap;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/check", method = GET)
    public DeferredResult checkEndpointHandler(@CookieValue(value = "authTicket", defaultValue = "") String jwt,
                                               @RequestHeader(value = "X-PonySecurity-ExpectedAudience", defaultValue = "") String expectedAudience,
                                               HttpServletRequest request, HttpServletResponse response) {

        final Long timeout = Long.parseLong(timeoutString + "000");
        RequestData requestData = new RequestData(expectedAudience, jwt, request, response);

        DeferredResult<ResponseEntity<String>> deferredResult = new DeferredResult<>(timeout, new SecondFactorResponseTimeoutException(requestData));
        requestData.setDeferredResult(deferredResult);

        logger.info(requestData.getUuid().toString() + " - New authentication request for site : " + expectedAudience);

        try {
            if (jwt.isEmpty()) {
                logger.info(requestData.getUuid().toString() + " - No jwt, new auth request");
                this.send2ndFactorRequest(requestData);
            } else {
                // Validate JWT, throws exceptions depending on the error
                jwtTool.validateJWT(requestData);
                logger.info(requestData.getUuid().toString() + " - Jwt OK - Connected");
                deferredResult.setResult(new ResponseEntity<>("Jwt Ok - Connected", HttpStatus.OK));
            }
        } catch (JwtExpiredException | JwtInvalidAudienceException | JwtMalformedClaimException | JwtInvalidException e) {
            logger.info(requestData.getUuid().toString() + " - Jwt Exception : " + e.getClass().getSimpleName() + " | Message : " + e.getMessage());
            try {
                this.send2ndFactorRequest(requestData);
            } catch (NotificationSendException | InternalNotificationSendException e1) {
                deferredResult.setErrorResult(e1);
            }
        } catch (NotificationSendException | InternalNotificationSendException e1) {
            deferredResult.setErrorResult(e1);
        }

        return deferredResult;
    }

    private void send2ndFactorRequest(RequestData requestData) throws NotificationSendException, InternalNotificationSendException {

        notificationService.setPayload(
                deviceId,
                "Authentication Request",
                "Authentication request from " + requestData.getExpectedAudience(),
                applicationBaseUrl.concat("/validate/").concat(requestData.getUuid().toString()),
                requestData.getExpectedAudience(),
                ""
        );

        logger.info(requestData.getUuid().toString() + " - New request : " + requestData.getUuid().toString());


        if (deviceId.length() > 12) {
            logger.debug(requestData.getUuid().toString() + " - Payload to send : " + notificationService.getPayload());
            NotificationResponseInterface response = notificationService.send();
            if (!response.isSuccess()) {
                throw new NotificationSendException(requestData, response);
            }
        } else {
            logger.warn(requestData.getUuid().toString() + " - Payload not send (deviceId null or incorrect) : " + notificationService.getPayload());
        }

        requestsMap.getHMap().put(requestData.getUuid().toString(), requestData);
        requestData.getDeferredResult().onCompletion(() -> {
            logger.debug(requestData.getUuid().toString() + " - Deleting hmap");
            requestsMap.getHMap().remove(requestData.getUuid().toString());
        });
    }

    @ExceptionHandler
    @ResponseBody
    public String handleException(HttpServletRequest req, HttpServletResponse res, AbstractException exception) {
        logger.error(exception.getRequestData().getUuid().toString() + " - Error handler started for " + exception.getClass() + " | Message : " + exception.getMessage());
        res.setStatus(exception.getHttpErrorCode());
        return "Handled exception: " + exception.getClass();
    }

}