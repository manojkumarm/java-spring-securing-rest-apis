package io.jzheaux.springsecurity.resolutions;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "authorities")
public class UserAuthority {

  @Id
  UUID id;

  @Column
  String authority;

  @JoinColumn(name = "username", referencedColumnName = "username")
  @ManyToOne
  User user;

  public UserAuthority(User user, String authority) {
    this.id = UUID.randomUUID();
    this.authority = authority;
    this.user = user;
  }

  public UserAuthority() {
  }


  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getAuthority() {
    return authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }
}
