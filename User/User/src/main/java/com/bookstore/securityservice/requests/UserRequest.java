package com.bookstore.securityservice.requests;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserRequest {
    String username;
    String password;
}
