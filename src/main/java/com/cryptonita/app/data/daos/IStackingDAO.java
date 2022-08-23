package com.cryptonita.app.data.daos;

import com.cryptonita.app.data.entities.StackingModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IStackingDAO extends JpaRepository<StackingModel, Long> {

    Optional<StackingModel> findByUserId(long id);

    List<StackingModel> findAllByUserId(long id);

    Optional<StackingModel> findByUserUsername(String userName);

    Optional<StackingModel> findByCoinName(String coinName);


}
