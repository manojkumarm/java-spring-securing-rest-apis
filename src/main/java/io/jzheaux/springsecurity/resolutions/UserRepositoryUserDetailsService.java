package io.jzheaux.springsecurity.resolutions;

import java.util.Collection;
import java.util.HashSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserRepositoryUserDetailsService implements UserDetailsService {

  private final UserRepository users;

  public UserRepositoryUserDetailsService(
      UserRepository users) {
    this.users = users;
  }

  private BridgeUser map(User user) {
    Collection<GrantedAuthority> authorities = new HashSet<>();
    for (UserAuthority userAuthority : user.getUserAuthorities()) {
      String authority = userAuthority.getAuthority();
      if ("ROLE_ADMIN".equals(authority)) {
        authorities.add(new SimpleGrantedAuthority("resolution:read"));
        authorities.add(new SimpleGrantedAuthority("resolution:write"));
      }
      authorities.add(new SimpleGrantedAuthority(authority));
    }
    return new BridgeUser(user, authorities);
  }

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    return this.users.findByUsername(s).map(this::map)
        .orElseThrow(() -> new UsernameNotFoundException("invalid user"));
  }

  private static class BridgeUser extends User implements UserDetails {

    private final Collection<GrantedAuthority> authorities;

    public BridgeUser(User user,
        Collection<GrantedAuthority> authorities) {
      super(user);
      this.authorities = authorities;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
      return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
      return this.enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
      return this.enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
      return this.enabled;
    }
  }

}
