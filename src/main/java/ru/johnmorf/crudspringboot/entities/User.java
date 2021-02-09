package ru.johnmorf.crudspringboot.entities;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    private String lastName;

    private byte age;

    private transient String[] rolesStrings;

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void refreshRoles() {
        roles = new ArrayList<>();
        for (int i = 0; i < rolesStrings.length; i++) {
            roles.add(new Role(rolesStrings[i]));
            System.out.println(rolesStrings[i]);
        }
    }



    public String listRoles() {
        StringBuffer stringBuffer = new StringBuffer();
        for (GrantedAuthority r : getAuthorities()) {
            stringBuffer.append(r.toString()).append(' ');
        }
        return stringBuffer.toString();
    }
}
