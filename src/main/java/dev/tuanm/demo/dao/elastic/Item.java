package dev.tuanm.demo.dao.elastic;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalDateTime;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

@Getter
@Setter
@Document(indexName = "bbb")
public class Item {
    @Id
    private Long id;
    @Field(type = FieldType.Text)
    private String bicycleName;
    @Field(type = FieldType.Text)
    private String bicycleYear;
    @Field(type = FieldType.Text)
    private String brandName;
    @Field(type = FieldType.Text)
    private String modelName;
    @Field(type = FieldType.Text)
    private String description;
    @Field(type = FieldType.Double)
    private double price;
    @Field(type = FieldType.Date)
    private Long listedAt;
}
