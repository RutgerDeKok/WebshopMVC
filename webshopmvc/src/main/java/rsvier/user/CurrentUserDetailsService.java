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
    public CurrentUser loadUserByUsername(String uname) throws UsernameNotFoundException {
        System.out.println("Username: " + uname);
        User user = userService.findUserByEmail(uname);
        System.out.println("User: "+ user);
        /*System.out.println("Als het goed zie je dit");
        System.out.println(user.getUserType().toString());
        System.out.println("Hierna gaat het mis");*/
        List<GrantedAuthority> blabla = AuthorityUtils.createAuthorityList("USER", "ADMIN");
        System.out.println(blabla);
        return new CurrentUser(user);
    }
}
