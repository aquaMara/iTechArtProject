package org.aquam.learnrest.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Entity
//@Builder
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    @NotBlank(message = "Can not be empty")  // not null + no whitespaces
    private String username;
    // @Length(min = 6, max = 16, message = "Between 6 and 16 characters")
    private String password;
    @NotBlank(message = "Can not be empty")
    private String name;
    @NotBlank(message = "Can not be empty")
    @Email(message = "Valid only")
    private String email;
    private boolean locked;
    private boolean enabled;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Article> allArticles = new ArrayList<>();

    private void addArticle(Article article) {
        this.allArticles.add(article);
        article.setUser(this);
    }

    public AppUser() {
        this.locked = false;
        this.enabled = true;
    }

    public AppUser(UserRole userRole, String username, String password, String name, String email) {
        this();
        this.userRole = userRole;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    // singletonList
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "userId=" + userId +
                ", userRole=" + userRole +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' + '}';
    }

    public AppUser build() {
        return new AppUser(userRole, username, password, name, email);
    }
}
