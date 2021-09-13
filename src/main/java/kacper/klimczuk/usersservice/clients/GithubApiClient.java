package kacper.klimczuk.usersservice.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Component
public class GithubApiClient {

    @Value("${github.api.base.url}")
    private String baseUrl;
    private String basicDataEndpoint;
    private String followersEndpoint;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    private void init() {
        basicDataEndpoint = baseUrl + "%s";
        followersEndpoint = baseUrl + "%s/followers";
    }

    public UserBasicData getUserBasicData(String login) {
        String uri = String.format(basicDataEndpoint, login);
        RequestEntity<Void> request = RequestEntity.get(uri)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        return restTemplate.exchange(request, UserBasicData.class).getBody();
    }

    public int getUserFollowersNumber(String login) {
        String uri = String.format(followersEndpoint, login);
        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(uri, Object[].class);
        Object[] followers = responseEntity.getBody();
        return followers == null ? 0 : followers.length;
    }
}
