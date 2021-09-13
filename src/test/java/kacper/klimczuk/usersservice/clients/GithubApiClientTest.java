package kacper.klimczuk.usersservice.clients;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class GithubApiClientTest {

    @SpyBean
    private GithubApiClient githubApiClient;

    @Test
    public void shouldReturnNotEmptyUserDataForExistingLogin() {
        UserBasicData userBasicData = githubApiClient.getUserBasicData("octocat");
        assertNotNull(userBasicData.getId());
        assertNotNull(userBasicData.getLogin());
        assertNotNull(userBasicData.getName());
        assertNotNull(userBasicData.getType());
        assertNotNull(userBasicData.getAvatarUrl());
        assertNotNull(userBasicData.getCreatedAt());
        assertNotNull(userBasicData.getPublicRepos());
    }

    @Test
    public void shouldThrowHttpClientErrorExceptionForNotExistingLogin() {
        assertThrows(HttpClientErrorException.class,
                () -> githubApiClient.getUserBasicData("thereIsNoSuchLogin")
        );
    }

    @Test
    public void shouldReturnPositiveFollowersNumberForValidLogin() {
        int followersNum = githubApiClient.getUserFollowersNumber("octocat");
        assertTrue(followersNum > 0);
    }
}