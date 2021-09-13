package kacper.klimczuk.usersservice.services;

import kacper.klimczuk.usersservice.clients.GithubApiClient;
import kacper.klimczuk.usersservice.clients.UserBasicData;
import kacper.klimczuk.usersservice.exceptions.UserNotFoundException;
import kacper.klimczuk.usersservice.models.dto.UserDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {
    private static final String LOGIN = "testLogin";

    @SpyBean
    private UserService userService;

    @MockBean
    private GithubApiClient githubApiClient;

    private static UserBasicData USER_BASIC_DATA;

    @BeforeAll
    private static void init() {
        USER_BASIC_DATA = new UserBasicData();
        USER_BASIC_DATA.setId(1);
        USER_BASIC_DATA.setLogin(LOGIN);
        USER_BASIC_DATA.setPublicRepos(1);
    }

    @Test
    public void shouldReturnValidUserDTOForValidLogin() {
        when(githubApiClient.getUserBasicData(LOGIN)).thenReturn(USER_BASIC_DATA);
        when(githubApiClient.getUserFollowersNumber(LOGIN)).thenReturn(10);
        UserDTO userDTO = userService.getUserInfo(LOGIN);
        assertNotNull(userDTO.getId());
        assertNotNull(userDTO.getLogin());
        assertEquals(1, userDTO.getCalculations());
    }

    @Test
    public void shouldThrowExceptionForInvalidLogin() {
        when(githubApiClient.getUserBasicData(LOGIN)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        assertThrows(UserNotFoundException.class,
                () -> userService.getUserInfo(LOGIN)
        );
    }
}