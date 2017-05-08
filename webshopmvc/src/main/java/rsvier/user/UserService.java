package rsvier.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

	private List<User> users;
	@Autowired
	private UserRepository userRepository;

	public List<User> getAllUsers() {
		users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}


	public List<User> getUsersByType(UserType filterType) {
		
		if(users==null){
			getAllUsers();
		}
		List<User> filteredUsers = new ArrayList<>();
		filteredUsers.clear();
		for (User user : users) {
			if (filterType == UserType.ALL || user.getUserType() == filterType) {
				filteredUsers.add(user);
			}
		}
		return filteredUsers;
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
	
	public void clearLocalList() {
		users=null;
	}

}
