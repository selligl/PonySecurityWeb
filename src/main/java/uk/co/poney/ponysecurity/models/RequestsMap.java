package uk.co.poney.ponysecurity.models;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class RequestsMap {
    private final HashMap<String, RequestData> hMap = new HashMap<>();

    public HashMap<String, RequestData> getHMap() {
        return this.hMap;
    }
}
