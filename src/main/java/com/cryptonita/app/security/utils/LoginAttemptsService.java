package com.cryptonita.app.security.utils;

import com.cryptonita.app.data.providers.IUserProvider;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.checkerframework.checker.index.qual.NonNegative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptsService {

    private final int MAX_ATTEMPT = 3;
    @Autowired
    IUserProvider iUserProvider;
    private final LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptsService() {
        attemptsCache = Caffeine.newBuilder()
                .expireAfter(new Expiry<String, Integer>() {
                    @Override
                    public long expireAfterCreate(String s, Integer integer, long l) {
                        return TimeUnit.MINUTES.toNanos(3);
                    }

                    @Override
                    public long expireAfterUpdate(String s, Integer integer, long l, @NonNegative long l1) {
                        return l1;
                    }

                    @Override
                    public long expireAfterRead(String s, Integer integer, long l, @NonNegative long l1) {
                        return l1;
                    }
                })
                .build(s -> 0);
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
