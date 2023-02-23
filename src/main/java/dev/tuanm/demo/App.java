package dev.tuanm.demo;

import org.joda.time.DateTimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class App {
    @PostConstruct
    public void init() {
         DateTimeZone.setDefault(DateTimeZone.getDefault());
         TimeZone.setDefault(TimeZone.getDefault());
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }
}