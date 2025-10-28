package io.github.vikindor.reqres.models.users;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserListItemResponse {
        private int id;
        private String email;

        @com.fasterxml.jackson.annotation.JsonProperty("first_name")
        private String firstName;

        @com.fasterxml.jackson.annotation.JsonProperty("last_name")
        private String lastName;

        private String avatar;
        private String job;
}
