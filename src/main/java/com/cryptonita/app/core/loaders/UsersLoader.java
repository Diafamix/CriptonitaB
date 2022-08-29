package com.cryptonita.app.core.loaders;

import com.cryptonita.app.data.entities.enums.UserRole;
import com.cryptonita.app.data.entities.enums.UserType;
import com.cryptonita.app.data.providers.IUserProvider;
import com.cryptonita.app.dto.data.request.UserRegisterDTO;
import com.cryptonita.app.dto.data.response.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class UsersLoader {

    private final IUserProvider userProvider;

    public Flux<UserResponseDTO> load() {

        List<UserRegisterDTO> users = Arrays.asList(
                new UserRegisterDTO("test@gmail.com", "Front-admin", "1234", UserRole.ADMIN, UserType.PREMIUM_PLUS),
                new UserRegisterDTO("sergio.bernal@optimissa.com", "sergio.bernal", "1234", UserRole.ADMIN, UserType.PREMIUM_PLUS),
                new UserRegisterDTO("antonio.borja@optimissa.com", "antonio.borja", "picha", UserRole.ADMIN, UserType.FREE),
                new UserRegisterDTO("carlos.cueva@optimissa.com", "carlos.cueva", "liandolas", UserRole.USER, UserType.FREE),
                new UserRegisterDTO("mario.pricop@optimissa.com", "mario.pricop", "pedazomoto", UserRole.USER, UserType.FREE),
                new UserRegisterDTO("guillermo.baixeras@optimissa.com", "guillermo.baixeras", "123242142", UserRole.USER, UserType.FREE)
        );

        return Flux.fromIterable(users)
                .map(userProvider::register);
    }

}
