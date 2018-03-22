package es.demo.spocktesting.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String id;

    private String username;

    private String firstName;

    private String lastName;

    private Integer age;
}
