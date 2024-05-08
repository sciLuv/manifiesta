package fr.sciluv.application.manifiesta.manifiestaBack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class SpotifyApiCallManager {
    private ConcurrentHashMap<Integer, RegularSpotifyApiCallForSessionUpdate> sessionApiCalls = new ConcurrentHashMap<>();
    private final ApplicationContext applicationContext;

    @Autowired
    public SpotifyApiCallManager(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public RegularSpotifyApiCallForSessionUpdate getOrCreateApiCall(Integer sessionId) {
        return sessionApiCalls.computeIfAbsent(sessionId, id -> applicationContext.getBean(RegularSpotifyApiCallForSessionUpdate.class));
    }

    public void endApiCall(Integer sessionId) {
        RegularSpotifyApiCallForSessionUpdate apiCall = sessionApiCalls.remove(sessionId);
        if (apiCall != null) {
            apiCall.stopExecution();
        }
    }
}

