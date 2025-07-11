package project.kristiyan.WebServer.models;

import java.util.ArrayList;
import java.util.List;

public class PaginationModel<T> {
    private List<T> items;
    private int totalPages;
    private int currentPage;

    public PaginationModel() {
        items = new ArrayList<>();
        totalPages = 0;
        currentPage = 1;
    }

    public PaginationModel(List<T> items, int totalPages, int currentPage) {
        this.items = items;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    // Getters
    public List<T> getItems() { return items; }
    public int getTotalPages() { return totalPages; }
    public int getCurrentPage() { return currentPage; }

    // Setters
    public void setItems(List<T> items) {
        this.items = items;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
