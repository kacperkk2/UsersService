package kacper.klimczuk.usersservice.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Stats {
    @Id
    @Column(name="LOGIN")
    private String login;
    @Column(name="REQUEST_COUNT")
    private Integer requestCount;

    public Stats() {
    }

    public Stats(Integer requestCount) {
        this.requestCount = requestCount;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(Integer requestCount) {
        this.requestCount = requestCount;
    }
}
