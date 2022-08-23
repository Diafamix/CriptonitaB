package com.cryptonita.app.data.daos;

import com.cryptonita.app.data.entities.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserDao extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByMail(String mail);

    Optional<UserModel> findByUsername(String username);

    void deleteByUsername(String username);

    void deleteByMail(String mail);

}
