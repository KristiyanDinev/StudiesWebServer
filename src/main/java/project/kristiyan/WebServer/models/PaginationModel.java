package project.kristiyan.WebServer.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PaginationModel<T> implements Serializable {
    public List<T> items;
    public int currentPage;

    public PaginationModel() {
        items = new ArrayList<>();
        currentPage = 1;
    }

    public PaginationModel(List<T> items, int currentPage) {
        this.items = items;
        this.currentPage = currentPage;
    }
}
