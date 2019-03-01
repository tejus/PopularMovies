package com.tejus.popularmovies.model;

import java.util.List;

public class Videos {

    private List<VideoResults> results;

    //Getter method
    public List<VideoResults> getResults() {
        return results;
    }

    //Setter method
    public void setResults(List<VideoResults> results) {
        this.results = results;
    }

    public class VideoResults {

        private String id;
        private String key;
        private String name;
        private String site;
        private String type;

        //Getter methods
        public String getId() {
            return id;
        }

        public String getKey() {
            return key;
        }

        public String getName() {
            return name;
        }

        public String getSite() {
            return site;
        }

        public String getType() {
            return type;
        }

        //Setter methods
        public void setId(String id) {
            this.id = id;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
