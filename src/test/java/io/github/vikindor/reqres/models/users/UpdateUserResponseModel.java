package io.github.vikindor.reqres.models.users;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class UpdateUserResponseModel {
    private String name;
    private String job;
    private String updatedAt;
}
