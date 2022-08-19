package com.cryptonita.app.security.utils;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptsService {

    private final int MAX_ATTEMPT = 3;
    private LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptsService() {
        super();
        attemptsCache = Caffeine.newBuilder().
                expireAfterWrite(3, TimeUnit.MINUTES)
                .build(key -> 0);
    }
    public void loginSucceeded(String key) {
        attemptsCache.invalidate(key);
    }

    public void loginFailed(String key) throws ExecutionException {
        int attempts = 0;
        attempts = attemptsCache.get(key);
        attempts++;
        attemptsCache.put(key, attempts);
    }

    public boolean isBlocked(String key) throws ExecutionException {

        return attemptsCache.get(key) >= MAX_ATTEMPT;

    }
}
