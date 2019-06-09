package com.andy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "people", type = "people")
public class People {

    @Id
    @Field(type = FieldType.Long, index = true, store = true)
    private Long id;

    @Field(type = FieldType.Keyword, store = true)
    public String lastName;

    @Field(type = FieldType.Text, store = true, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    public String context;

    public People(String lastName, String context) {
        this.lastName = lastName;
        this.context = context;
    }

}
