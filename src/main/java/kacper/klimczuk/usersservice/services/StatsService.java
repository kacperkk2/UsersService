package kacper.klimczuk.usersservice.services;

import kacper.klimczuk.usersservice.models.Stats;
import kacper.klimczuk.usersservice.repositories.StatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;

@Service
public class StatsService {

    @Autowired
    private StatsRepository statsRepository;

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void upsertStatistics(String login) {
        statsRepository.findById(login)
                .map(stats -> {
                    stats.setRequestCount(stats.getRequestCount() + 1);
                    return statsRepository.save(stats);
                }).orElseGet(() -> {
                    Stats stats = new Stats();
                    stats.setLogin(login);
                    stats.setRequestCount(0);
                    return statsRepository.save(stats);
                });
    }
}
