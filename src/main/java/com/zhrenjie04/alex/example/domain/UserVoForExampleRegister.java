package com.zhrenjie04.alex.example.domain;

import com.zhrenjie04.alex.core.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode(of = {"userId"}, callSuper = false)
@ToString
public class UserVoForExampleRegister extends User {
    @NotEmpty(message = "电子邮箱不能为空")
    @Email(message = "电子邮箱格式不正确")
    String email;
}
