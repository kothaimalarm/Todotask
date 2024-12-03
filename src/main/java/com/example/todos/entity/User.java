package com.example.todos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="Users")
public class User implements UserDetails{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name",nullable = false)
    private String name;

    @NotEmpty(message = "UserName cannot be null")
    @Column(name="username",nullable = false,unique = true)
    private String username;

    @NotEmpty(message = "Password can not be null")
    @Column(name="password",nullable = false)
    private String password;

    @NotEmpty(message = "Email can not be null")
    @Column(name="email",nullable=false,unique = true)
    private String email;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "id"))
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Todo> todos;


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of();
//    }
//
//    @Override
//    public String getUsername() {
//        return "";
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;  // Return true to indicate the account is not expired
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;  // Return true to indicate the account is not locked
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;  // Return true to indicate the credentials are not expired
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;  // Return true to indicate the user is enabled
//    }

}
