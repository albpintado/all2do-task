package com.all2do.task;

import java.util.List;

public class Response<T> {
    private int currentPage;
    private int totalPages;
    private long totalElements;
    private List<T> elements;

    public Response(int currentPage, int totalPages, long totalTasks, List<T> elements) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalElements = totalTasks;
        this.elements = elements;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }
}
