package project.kristiyan.WebServer.services;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.stereotype.Service;
import project.kristiyan.WebServer.WebServerApplication;
import project.kristiyan.WebServer.models.SermonModel;
import project.kristiyan.WebServer.models.SongModel;
import project.kristiyan.WebServer.models.StudyModel;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private JdbcIndexedSessionRepository v;

    private final String studySessionResultsKey = "search_study_result";
    private final String songSessionResultsKey = "search_song_result";
    private final String sermonSessionResultsKey = "search_sermon_result";

    private final String studySessionSearchQueryKey = "search_study_query";
    private final String songSessionSearchQueryKey = "search_song_query";
    private final String sermonSessionSearchQueryKey = "search_sermon_query";

    public void setStudySessionResults(List<StudyModel> studyModels, HttpSession session) {
        session.setAttribute(studySessionResultsKey, studyModels);
        // INSERT INTO SPRING_SESSION_ATTRIBUTES (SESSION_PRIMARY_ID, ATTRIBUTE_NAME, ATTRIBUTE_BYTES) VALUES (?, ?, ?)
    }

    public void setStudySearchQuery(String alike_study, List<String> series, HttpSession session) {
        session.setAttribute(studySessionSearchQueryKey, List.of(alike_study, series));
    }

    public void setSongSessionResults(List<SongModel> songModels, HttpSession session) {
        session.setAttribute(songSessionResultsKey, songModels);
    }

    public void setSermonSessionResults(List<SermonModel> sermonModels, HttpSession session) {
        session.setAttribute(sermonSessionResultsKey, sermonModels);
    }


    public void deleteStudySearchQuery(HttpSession session) {
        if (getStudySearchQuery(session) != null) {
            session.removeAttribute(studySessionSearchQueryKey);
        }
    }

    public void deleteSongSearchQuery(HttpSession session) {
        if (getSongSearchQuery(session) != null) {
            session.removeAttribute(songSessionSearchQueryKey);
        }
    }

    public void deleteSermonSearchQuery(HttpSession session) {
        if (getSermonSearchQuery(session) != null) {
            session.removeAttribute(sermonSessionSearchQueryKey);
        }
    }


    public List<StudyModel> getStudySessionResults(HttpSession session) {
        List<StudyModel> models = (List<StudyModel>) session.getAttribute(studySessionResultsKey);
        session.removeAttribute(studySessionResultsKey);
        return models;
    }

    public List<SongModel> getSongSessionResults(HttpSession session) {
        List<SongModel> models = (List<SongModel>) session.getAttribute(songSessionResultsKey);
        session.removeAttribute(songSessionResultsKey);
        return models;
    }

    public List<SermonModel> getSermonSessionResults(HttpSession session) {
        List<SermonModel> models = (List<SermonModel>) session.getAttribute(sermonSessionResultsKey);
        session.removeAttribute(sermonSessionResultsKey);
        return models;
    }


    public List<Object> getStudySearchQuery(HttpSession session) {
        return (List<Object>) session.getAttribute(studySessionSearchQueryKey);
    }

    public List<Object> getSongSearchQuery(HttpSession session) {
        return (List<Object>) session.getAttribute(songSessionSearchQueryKey);
    }

    public List<Object> getSermonSearchQuery(HttpSession session) {
        return (List<Object>) session.getAttribute(sermonSessionSearchQueryKey);
    }
}
