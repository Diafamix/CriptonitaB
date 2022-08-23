package com.cryptonita.app.security.utils;

import com.cryptonita.app.data.entities.enums.UserRole;
import com.cryptonita.app.dto.data.response.UserResponseDTO;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.index.qual.NonNegative;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class RatePerMinuteMapService {

    private final LoadingCache<String, Integer> map = Caffeine.newBuilder()
            .expireAfter(new Expiry<String, Integer>() {
                @Override
                public long expireAfterCreate(String s, Integer integer, long l) {
                    return TimeUnit.MINUTES.toNanos(1);
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

    public void consume(UserResponseDTO userDTO, int tokens) {
        map.asMap().merge(userDTO.mail, tokens, Integer::sum);
    }

    public boolean isBlocked(UserResponseDTO userDTO) {
        if (userDTO.role == UserRole.ADMIN) return false;

        Integer value = map.get(userDTO.mail);
        return value != null && value >= userDTO.type.getRateLimitPerMinute();
    }

    public Integer getTokenConsumed(UserResponseDTO userDTO) {
        return map.get(userDTO.mail);
    }

}
