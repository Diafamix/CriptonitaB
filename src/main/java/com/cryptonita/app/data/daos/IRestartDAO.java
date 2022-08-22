package com.cryptonita.app.data.daos;

import com.cryptonita.app.data.entities.RestartModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRestartDAO extends JpaRepository<RestartModel, Long> {

    Optional<RestartModel> findByMonthAndAndYear(String month, String year);

}
