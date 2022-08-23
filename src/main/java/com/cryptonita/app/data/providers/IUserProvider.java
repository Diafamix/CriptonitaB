package com.cryptonita.app.data.providers;

import com.cryptonita.app.data.entities.enums.UserType;
import com.cryptonita.app.dto.data.request.UserRegisterDTO;
import com.cryptonita.app.dto.data.response.BannedUserResponseDTO;
import com.cryptonita.app.dto.data.response.FavoritesResponseDto;
import com.cryptonita.app.dto.data.response.UserResponseDTO;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface IUserProvider {


    List<UserResponseDTO> getAll();

    /**
     * Registers a user
     *
     * @param dto the dto carrying all the information
     * @return a response dto with the information of the transaction
     */
    UserResponseDTO register(UserRegisterDTO dto);

    UserResponseDTO getById(long id);

     UserResponseDTO getByName(String name);

    UserResponseDTO getByEmail(String mail);

    UserResponseDTO changeUserType(String mail, UserType userType);

    UserResponseDTO changeUserNumRequests(String name,int tokens);

    UserResponseDTO changeUserPassword(String email);

    UserResponseDTO restartUserNumRequest(String name);

    /**
     * Convenient method to check if a raw password matches the user hashed stored password
     *
     * @param mail the user mail to check the password
     * @param password the raw password to check
     * @return true if matches, false if not
     */
    boolean matchesPassword(String mail, String password);

    /**
     * Convenient method to check if a raw password matches the user hashed stored password
     *
     * @param username the username to check the hashed password
     * @param password the raw password to check
     * @return true if matches, false if not
     */
    boolean matchesPasswordByUsername(String username, String password);

    /**
     * Convenient method to check if a user with the given mail is already registered
     *
     * @param mail the mail to check
     * @return true if a user exists, false if not
     */
    boolean exists(String mail);

    /**
     * Convenient method to check if a user with the given username is already registered
     *
     * @param username the mail to check
     * @return true if a user exists, false if not
     */
    boolean existsByUsername(String username);

    /**
     * Convenient to ban a user
     *
     * @param mail the mail of the user to be banned
     * @return the dto with all the information of the user banned
     */
    BannedUserResponseDTO banUser(String mail);

    /**
     * Convenient method to unban a user
     *
     * @param mail the mail of the user to unban
     * @return the dto with all the information of the user unbanned
     */
    BannedUserResponseDTO unBanUser(String mail);

    /**
     * Convenient method to ban a user by its username
     *
     * @param username the username
     * @return TODO
     */
    BannedUserResponseDTO banUserByUsername(String username);

    /**
     * Convenient method to unban a user by username
     *
     * @param username the username of the user to ban
     * @return TODO
     */
    BannedUserResponseDTO unbanUserByUsername(String username);

    /**
     * Method to return all the banned users
     *
     * @return a list of all the banned users TODO
     */
    List<BannedUserResponseDTO> getBannedUsers();

    /**
     * Convenient method to know if a user is banned
     *
     * @param mail the username of the user to check
     * @return true if it is banned, false if it does not exist or false
     */
    boolean isBanned(String mail);

    /**
     * Convenient method to know if a user is banned
     *
     * @param username the username of the user to check
     * @return true if it is banned, false if it does not exist or false
     */
    boolean isBannedByUsername(String username);

    /**
     * Convenient method to get a bannedUser
     *
     * @param mail the mail of the banned user to get
     * @return dto with the information of the user
     */
    BannedUserResponseDTO get(String mail);

    /**
     * Convenient method to get a bannedUser
     *
     * @param username the username of the banned user to get
     * @return dto with the information of the user
     */
    BannedUserResponseDTO getByUsername(String username);

    /**
     * Method to add a favourite coin to a user
     *
     * @param name the username of the user to add the coin to
     * @param coin the name of the coin to add
     * @return the dto with all the information of the transaction
     */
    FavoritesResponseDto addFavourite(String name, String coin);

    /**
     * Method to remove a favourite coin to a user
     *
     * @param name the username of the user to remove the coin to
     * @param coin the name of the coin to remove
     * @return the dto with all the information of the transaction
     */
    FavoritesResponseDto removeFavorite(String name, String coin);

}
