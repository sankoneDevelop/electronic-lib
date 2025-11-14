package ru.alexdev.project.electronic_lib.details;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.alexdev.project.electronic_lib.models.AuthUser;
import ru.alexdev.project.electronic_lib.models.Librarian;

import java.util.Collection;

public class AuthUserDetails implements UserDetails {

    private final AuthUser authUser;

    public AuthUserDetails(AuthUser authUser) {
        this.authUser = authUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.authUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.authUser.getUsername();
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

    public AuthUser getAuthUser() {
        return this.authUser;
    }

    public String getFullName() {
        Librarian librarian = this.authUser.getLibrarian();
        return librarian != null ? (librarian.getName() + " " + librarian.getSurname()) : null;
    }

    public Librarian getLibrarian() {
        return authUser.getLibrarian();
    }
}
