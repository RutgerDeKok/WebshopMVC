package rsvier.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

	private List<User> users;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;

	public List<User> getAllUsers() {
		users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}

	public User getUser(Long id) {
		return userRepository.findOne(id);
	}

	public void addUser(User user) {
		userRepository.save(user);
	}

	public void updateUser(User user) {
		userRepository.save(user);
	}

	public void deleteUser(Long id) {
		userRepository.delete(id);
	}

	public User findUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findUserByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("E-mail ongeldig.");
		} else {
			List<String> userRoles = userRoleRepository.findRoleByEmail(email);
		}
	}
}
