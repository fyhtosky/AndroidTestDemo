package com.example.network_sdk.movie.bean;

public class Movie {
    private Rate rating;
    private String title;
    private String collect_count;
    private String original_title;
    private String subtype;
    private String year;
    private MovieImage images;


    public Rate getRating() {
        return rating;
    }

    public void setRating(Rate rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(String collect_count) {
        this.collect_count = collect_count;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public MovieImage getImages() {
        return images;
    }

    public void setImages(MovieImage images) {
        this.images = images;
    }

    public static class Rate{
        private int max;
        private float average;
        private String stars;
        private int min;

        @Override
        public String toString() {
            return "Rate{" +
                    "max=" + max +
                    ", average=" + average +
                    ", stars='" + stars + '\'' +
                    ", min=" + min +
                    '}';
        }
    }

    public static class MovieImage{
        private String small;
        private String large;
        private String medium;


        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        @Override
        public String toString() {
            return "MovieImage{" +
                    "small='" + small + '\'' +
                    ", large='" + large + '\'' +
                    ", medium='" + medium + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Movie{" +
                "rating=" + rating +
                ", title='" + title + '\'' +
                ", collect_count='" + collect_count + '\'' +
                ", original_title='" + original_title + '\'' +
                ", subtype='" + subtype + '\'' +
                ", year='" + year + '\'' +
                ", images=" + images +
                '}';
    }
}
