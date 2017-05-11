package rsvier.user;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import rsvier.address.Address;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {

	private List<User> users;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<User> getAllUsers() {
		users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}
	

	public List<User> getUsersByType(UserType filterType) {

		if (users == null) {
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
	

	
	public User registerUser(HttpServletRequest request, UserType userType) {

		User user = new User();

		user.setEmail(request.getParameter("email"));
		user.setPassHash(passwordEncoder.encode(request.getParameter("W8")));

		if (userType == null) {
			user.setUserType(UserType.CUSTOMER);

		} else {
			user.setUserType(userType);
		}

		Address adres = new Address();

		adres.setFirstName(request.getParameter("firstName"));
		adres.setFamilyName(request.getParameter("lastName"));
		adres.setInsertion(request.getParameter("insertion"));
		adres.setCity(request.getParameter("city"));
		adres.setNumber(Integer.parseInt(request.getParameter("number")));
		adres.setNumAddition(request.getParameter("addition"));
		adres.setStreet(request.getParameter("street"));
		adres.setZipCode(request.getParameter("zipCode"));
		user.setBillingAddress(adres);
		user.setEnabled(true);

		return user;

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
		users = null;
	}

public void deleteCascadeUser(Long id){
     userRepository.delete(id);
 }

		users = null;
	}

}

	
