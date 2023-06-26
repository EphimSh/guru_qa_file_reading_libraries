package guru.qa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExampleModel {

    @JsonProperty("title")
    private String myTitle;

    private Person person;

    public String getMyTitle() {
        return myTitle;
    }

    public Person getPerson() {
        return person;
    }

    public static class Person {
        private int id;
        private String[] skills;
        private boolean selenium;
        private boolean selenide;

        public int getId() {
            return id;
        }


        public String[] getSkills() {
            return skills;
        }


        public boolean isSelenium() {
            return selenium;
        }

        public boolean isSelenide() {
            return selenide;
        }

    }
}
