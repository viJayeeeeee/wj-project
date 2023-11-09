package com.evan.wj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity //实体类
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "用户名不能为空")
    private String username;
    private String password;
    private String salt;

    private String name;
    private String phone;
    @Email(message = "请输入正确的邮箱地址")
    private String email;
    private boolean enabled;

    @Transient
    List<AdminRole> roles;

    public User(int id, String username, String name, String phone, String email, boolean enabled) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.enabled = enabled;
    }
}
