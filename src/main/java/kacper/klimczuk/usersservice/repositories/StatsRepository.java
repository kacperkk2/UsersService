package kacper.klimczuk.usersservice.repositories;

import kacper.klimczuk.usersservice.models.Stats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsRepository extends JpaRepository<Stats, String> {
}
