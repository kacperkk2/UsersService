package kacper.klimczuk.usersservice.services;

import kacper.klimczuk.usersservice.clients.GithubApiClient;
import kacper.klimczuk.usersservice.clients.UserBasicData;
import kacper.klimczuk.usersservice.exceptions.UserNotFoundException;
import kacper.klimczuk.usersservice.models.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class UserService {

    @Autowired
    private GithubApiClient githubApiClient;

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO getUserInfo(String login) {
        UserBasicData userBasicData;
        try {
            userBasicData = githubApiClient.getUserBasicData(login);
        }
        catch (HttpClientErrorException e) {
            throw new UserNotFoundException(login);
        }
        int followersNumber = githubApiClient.getUserFollowersNumber(login);
        return createFinalUserData(userBasicData, followersNumber);
    }

    private UserDTO createFinalUserData(UserBasicData userBasicData, int followersNumber) {
        UserDTO userDTO = modelMapper.map(userBasicData, UserDTO.class);
        userDTO.setCalculations((int) (6.0 / followersNumber * (2 + userBasicData.getPublicRepos())));
        return userDTO;
    }
}
