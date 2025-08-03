package project.kristiyan.WebServer.models;

import java.util.ArrayList;
import java.util.List;

public class PaginationModel<T> {
    private List<T> items;
    private int currentPage;

    public PaginationModel() {
        items = new ArrayList<>();
        currentPage = 1;
    }

    public PaginationModel(List<T> items, int currentPage) {
        this.items = items;
        this.currentPage = currentPage;
    }

    // Getters
    public List<T> getItems() { return items; }
    public int getCurrentPage() { return currentPage; }

    // Setters
    public void setItems(List<T> items) {
        this.items = items;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
