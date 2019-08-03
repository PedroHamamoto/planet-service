package br.com.hamamoto.planetservice.integration.resource;

import java.util.List;

public class SwapiSearcResult<T> {

    private String next;
    private List<T> results;

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
