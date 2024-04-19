package com.example.emmisiontracker.domain.user;

import com.example.emmisiontracker.constants.UserRole;
import com.example.emmisiontracker.domain.travel.Travel;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Builder
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean active = true;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Travel> travels;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.toString()));
    }

    @Override
    public String getUsername() { return email; }

    @Override
    public String getPassword() { return password; }

    @Override
    public boolean isAccountNonExpired() { return active; }
    @Override
    public boolean isAccountNonLocked() { return active; }
    @Override
    public boolean isCredentialsNonExpired() { return active; }
    @Override
    public boolean isEnabled() { return active; }
}
