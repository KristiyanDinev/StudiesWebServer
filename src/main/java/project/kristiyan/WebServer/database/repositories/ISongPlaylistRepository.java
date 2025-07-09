package project.kristiyan.WebServer.database.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;
import project.kristiyan.WebServer.database.entities.SongPlaylistEntityW;

@Repository
@Transactional
public interface ISongPlaylistRepository extends JpaRepositoryImplementation<SongPlaylistEntityW, Integer> {
}
