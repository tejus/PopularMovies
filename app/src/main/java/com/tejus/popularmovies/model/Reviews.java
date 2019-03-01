package com.tejus.popularmovies.model;

import java.util.List;

public class Reviews {

    private List<ReviewResults> results;

    //Getter method
    public List<ReviewResults> getResults() {
        return results;
    }

    //Setter method
    public void setResults(List<ReviewResults> results) {
        this.results = results;
    }

    public class ReviewResults {

        private String author;
        private String content;
        private String id;

        //Getter methods
        public String getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }

        public String getId() {
            return id;
        }

        //Setter methods
        public void setAuthor(String author) {
            this.author = author;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
