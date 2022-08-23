package com.cryptonita.app.data.providers.impl;

import com.cryptonita.app.data.daos.ICoinDAO;
import com.cryptonita.app.data.daos.IStackingDAO;
import com.cryptonita.app.data.daos.IUserDao;
import com.cryptonita.app.data.entities.CoinModel;
import com.cryptonita.app.data.entities.StackingModel;
import com.cryptonita.app.data.entities.UserModel;
import com.cryptonita.app.data.entities.WalletModel;
import com.cryptonita.app.data.providers.IStackingProvider;
import com.cryptonita.app.data.providers.mappers.IMapper;
import com.cryptonita.app.dto.data.response.StackingDTO;
import com.cryptonita.app.exceptions.data.CoinNotFoundException;
import com.cryptonita.app.exceptions.data.StackingNotFoundException;
import com.cryptonita.app.exceptions.data.UserNotFoundException;
import com.cryptonita.app.exceptions.data.WalletNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class StackingProviderImpl implements IStackingProvider {

    private static final String USER_ALREADY_EXISTS = "The user already exists!";
    private static final String COIN_ALREADY_EXISTS = "The coin %s already exists!";
    private static final String WALLET_ALREADY_EXISTS = "The wallet already exists!";
    private static final String STACKING_ALREADY_EXISTS = "The stacking with %S already exists!";

    private final IMapper<StackingModel, StackingDTO> stackingDTOIMapper;
    private final IStackingDAO stackingDAO;
    private final IUserDao userDao;
    private final ICoinDAO coinDAO;

    /**
     * Este metodo crea un stake
     *
     * @param userName nombre de usuario
     * @param coinName nombre de la moneda de la que se hará el stake
     * @param quantity cantidad de dicha moneda
     * @return el stake creado
     */
    @Transactional
    @Override
    public synchronized StackingDTO stake(String userName, String coinName, double quantity, int daysToExpire) {
        UserModel userModel = userDao.findByUsername(userName)
                .orElseThrow(() -> new UserNotFoundException(USER_ALREADY_EXISTS));

        CoinModel coinModel = coinDAO.findByName(coinName)
                .orElseThrow(() -> new CoinNotFoundException(COIN_ALREADY_EXISTS));

        WalletModel wallet = userModel.getWallets().stream()
                .filter(walletModel1 -> walletModel1.getCoin().equals(coinModel))
                .findFirst()
                .orElse(null);

        if (wallet == null)
            throw new WalletNotFoundException(WALLET_ALREADY_EXISTS);

        if (wallet.getQuantity() < quantity)
            throw new WalletNotFoundException("Lo siento, no tienes saldo suficiente");


        StackingModel stackingModel = StackingModel.builder()
                .user(userModel)
                .coin(coinModel)
                .createdAt(LocalDateTime.now())
                .quantity(quantity)
                .daysToExpire(daysToExpire)
                .build();


        stackingDAO.save(stackingModel);

        return stackingDTOIMapper.mapToDto(stackingModel);
    }

    /**
     * Este metodo elimina un Stake de un usuario
     *
     * @param userName nombre del usuario
     * @param id       del stake que se va a eliminar
     * @return retorna el stake eliminado
     */
    @Override
    public synchronized StackingDTO unStake(long id, String userName) {

        UserModel userModel = userDao.findByUsername(userName)
                .orElseThrow(() -> new UserNotFoundException(USER_ALREADY_EXISTS));

        StackingModel stackingModel = stackingDAO.findById(id)
                .orElseThrow(() -> new StackingNotFoundException(String.format(STACKING_ALREADY_EXISTS, id)));

        StackingModel stackingModelUser = stackingDAO.findByUserUsername(userName)
                .orElseThrow(() -> new StackingNotFoundException("Ese usuario no tiene un stake con esa moneda"));

        stackingDAO.delete(stackingModel);

        return stackingDTOIMapper.mapToDto(stackingModel);
    }

    /**
     * Este metodo devuelve todos los Stakes de un usuario
     *
     * @param username nombre del usuario
     * @return una lista de stakes de ese usuario
     */
    @Override
    public synchronized List<StackingDTO> getAllUserStakes(String username) {
        UserModel userModel = userDao.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(USER_ALREADY_EXISTS));


        List<StackingModel> stackingModelList = stackingDAO.findAllByUserId(userModel.getId());
        List<StackingDTO> stackingDTOList = new ArrayList<>();

        for (StackingModel stackingModel : stackingModelList) {
            StackingDTO dto = stackingDTOIMapper.mapToDto(stackingModel);
            stackingDTOList.add(dto);
        }
        return stackingDTOList;
    }

    /**
     * Este metodo te da un stake de un usuario
     *
     * @param id       del stake
     * @param username nombre del usuario
     * @return el stake buscado con el id
     */
    @Override
    public synchronized StackingDTO getUserStake(long id, String username) {
        UserModel userModel = userDao.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(USER_ALREADY_EXISTS));


        StackingModel stackingModel = stackingDAO.findById(id)
                .orElseThrow(() -> new StackingNotFoundException(STACKING_ALREADY_EXISTS));

        return stackingDTOIMapper.mapToDto(stackingModel);
    }

    /**
     * Este metodo te da una lista con todos los stakes
     *
     * @return todos los stakes
     */

    @Override
    public synchronized List<StackingDTO> findAll() {
        List<StackingModel> stackingModelList = stackingDAO.findAll();
        List<StackingDTO> stackingDTOList = new ArrayList<>();

        for (StackingModel stackingModel : stackingModelList) {
            StackingDTO dto = stackingDTOIMapper.mapToDto(stackingModel);
            stackingDTOList.add(dto);
        }
        return stackingDTOList;
    }
}
