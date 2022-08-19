package com.cryptonita.app.data.daos;

import com.cryptonita.app.data.entities.BannedUsersModel;
import com.cryptonita.app.data.entities.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBannedUserDao extends JpaRepository<BannedUsersModel, UserModel> {

    Optional<BannedUsersModel> findByUserId(long id);

    Optional<BannedUsersModel> findByUserMail(String mail);

    Optional<BannedUsersModel> findByUser_Username(String username);

}
