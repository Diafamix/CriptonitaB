package com.cryptonita.app.data.daos;

import com.cryptonita.app.data.entities.HistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * History repository
 * */

public interface IHistoryDao extends JpaRepository<HistoryModel,Long> {

    Optional<HistoryModel> findById(long id);

    List<HistoryModel> findAll();

    List<HistoryModel> findAllByUser_Id(long id);

    List<HistoryModel> findAllByUser_Username(String name);

    void deleteById(long id);

    List<HistoryModel> findAllByUser_UsernameAndDateAfterAndDateBefore(String username, LocalDate start,LocalDate end);



}
