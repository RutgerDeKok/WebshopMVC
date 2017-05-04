package rsvier.user;

import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Created by J on 5/3/2017.
 */
public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 1L;

    private User user;

    public CurrentUser(User user) {
        super(user.getEmail(),
                user.getPassHash(),
                AuthorityUtils.createAuthorityList("USER"));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return user.getId();
    }

    public UserType getRole() {
        return user.getUserType();
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "user=" + user +
                "} " + super.toString();
    }

}
