package com.cryptonita.app.core.schedulers;

import com.cryptonita.app.data.providers.IRestartProvider;
import com.cryptonita.app.data.providers.IUserProvider;
import com.cryptonita.app.dto.data.response.RestarResponseDTO;
import com.cryptonita.app.dto.data.response.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class ScheduledTasks {

    private final IUserProvider userProvider;
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final IRestartProvider restartProvider;

    @Scheduled(fixedRate = 72000000)
    public void reportCurrentTime() {

        LocalDateTime localDateTime = LocalDateTime.now();
        String month = String.valueOf(localDateTime.getMonth());
        String year = String.valueOf(localDateTime.getYear());

        RestarResponseDTO restarResponseDTO = restartProvider.findRestart(month, year);

        if (restarResponseDTO != null) return;

        RestarResponseDTO newRestart = RestarResponseDTO.builder()
                .month(month)
                .year(year)
                .build();

        restartProvider.newRestart(newRestart);
        restartRequest(userProvider.getAll());

    }

    private void restartRequest(List<UserResponseDTO> users) {
        for (UserResponseDTO user : users) {
            userProvider.restartUserNumRequest(user.getUsername());
        }
    }
}
