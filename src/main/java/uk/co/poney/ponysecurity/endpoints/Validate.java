package uk.co.poney.ponysecurity.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import uk.co.poney.ponysecurity.exceptions.AbstractException;
import uk.co.poney.ponysecurity.exceptions.JwtCreationException;
import uk.co.poney.ponysecurity.models.RequestData;
import uk.co.poney.ponysecurity.models.RequestsMap;
import uk.co.poney.ponysecurity.modules.jwt.Jwt;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class Validate {

    private static final Logger logger = LoggerFactory.getLogger("PonySecurity.Validate");
    @Autowired
    private Jwt jwtTool;
    @Autowired
    private RequestsMap requestsMap;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/validate/{id}", method = GET)
    public String validateEndpointHandler(@RequestHeader(value = "X-PonySecurity-ExpectedAudience", defaultValue = "") String expectedAudience,
                                          @PathVariable("id") String id, HttpServletResponse response) throws JwtCreationException {

        RequestData requestData = requestsMap.getHMap().get(id);
        String jwt = null;
        try {
            jwt = jwtTool.createJWT(requestData);
        } catch (JwtCreationException exception) {
            throw exception;
        }


        if (requestData == null) {
            // Authenticate Id not found
            logger.info("Second factor id invalid received : " + id);
            response.setStatus(404);
            return "";
        } else {
            logger.warn(requestData.getUuid().toString() + " - Second factor received for  " + requestData.getExpectedAudience());
            DeferredResult<ResponseEntity<String>> deferredResult = requestData.getDeferredResult();

            Cookie cookie = new Cookie("authTicket", jwt);
            requestData.getResponse().addCookie(cookie);
            ResponseEntity<String> responseEntity = new ResponseEntity<>("Second factor received - Auth ok", HttpStatus.OK);

            deferredResult.setResult(responseEntity);

            return "Second factor OK";
        }
    }

    @ExceptionHandler
    @ResponseBody
    public String handleException(HttpServletRequest req, HttpServletResponse res, AbstractException ex) {
        logger.error(ex.getRequestData().getUuid().toString() + " - Error handler started for " + ex.getClass() + " | Message : " + ex.getMessage());
        res.setStatus(ex.getHttpErrorCode());
        return "Handled exception: " + ex.getClass();
    }
}