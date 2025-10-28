package io.github.vikindor.reqres.models.users;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class UpdateUserRequestModel {
    private String name;
    private String job;
}
