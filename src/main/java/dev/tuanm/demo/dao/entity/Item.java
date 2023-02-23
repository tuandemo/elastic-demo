package dev.tuanm.demo.dao.entity;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bicycleId;
    private String description;
    private double price;
    private LocalDateTime listedAt;
    private boolean deleted;
}
