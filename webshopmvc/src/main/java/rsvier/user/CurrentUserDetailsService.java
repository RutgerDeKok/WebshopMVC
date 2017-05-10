package rsvier.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by J on 5/3/2017.
 */

@Service
public class CurrentUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public CurrentUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Transactional(readOnly=true)
    @Override
    public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        User user = userService.findUserByEmail(username);
        System.out.println("User: " +user+
                "\nType: " + user.getUserType().toString());
        return new CurrentUser(user);
    }
}
