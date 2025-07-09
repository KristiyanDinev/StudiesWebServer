package project.kristiyan.WebServer.database.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;
import project.kristiyan.WebServer.database.entities.SongCategoryEntityW;

@Repository
@Transactional
public interface ISongCategoryRepository extends JpaRepositoryImplementation<SongCategoryEntityW, Integer> {
}
