package rsvier.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import rsvier.address.Address;
import rsvier.cart.Cart;
import rsvier.cart.CartService;
import rsvier.cartsuborder.CartSubOrder;





@Controller
public class UserController {

    @Autowired
     PasswordEncoder passwordEncoder;
    
    
    @Autowired
    private UserService userService;
    private UserType typeFilter;

    @Autowired
    private CartService cartService;

    @RequestMapping(value = {"/employees/users"}, method = RequestMethod.GET)
    public String showUsersEmployees(Map<String, Object> model, HttpServletRequest request) {

        request.getSession().setAttribute("users", userService.getAllUsers());

        putUsersInSession(request.getSession());
        model.put("types", UserType.values());
        return "emp_users";
    }

    private void putUsersInSession(HttpSession session) {
        System.out.println("running put products in session");

        typeFilter = (UserType) session.getAttribute("typeFilter");

        if (typeFilter == null) {
            typeFilter = UserType.ALL;
        }

        List<User> users = userService.getUsersByType(typeFilter);;
        session.setAttribute("users", users);
        session.setAttribute("typeFilter", typeFilter);
    }

    @RequestMapping(value = "employees/users/filter", method = RequestMethod.POST)
    public @ResponseBody
    void filterUsersEdit(@RequestParam("filter") String radioWaarde, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("radiowaarde is: " + radioWaarde);
        UserType typeFilter = UserType.valueOf(radioWaarde);
        request.getSession().setAttribute("typeFilter", typeFilter);

        try {
            response.sendRedirect("/employees/users");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @RequestMapping(value = "employees/users/delete", method = RequestMethod.POST)
    public String deleteUSers(@RequestParam("delindex") String choice, HttpServletRequest request, HttpServletResponse response) {

        System.out.println("delete user methode");
        @SuppressWarnings("unchecked")
        List<User> users = (ArrayList<User>) request.getSession().getAttribute("users");
        int userIndex = (Integer.parseInt(choice));
        System.out.println("usersIndex is: " + userIndex);

        User user = (User) users.get(userIndex);
        System.out.println("product to be deleted id: " + user.getId() + " email: " + user.getEmail());
        //ECHT DELETEN GEBEURD HIER

        cartService.deleteCart(cartService.getCartByUser(user));
        userService.deleteUser(user.getId());
        users.remove(userIndex);

        //RESET TABLE
        request.getSession().setAttribute("users", users);

        return "emp_users";

    }

    @RequestMapping(value = "employees/users/new", method = RequestMethod.GET)
    public @ResponseBody
    void newUser(HttpSession session, HttpServletResponse response) {

        User user = new User();
        //om null pointer in het pull down menu te voorkomen
        // enum waarde toevoegen
        user.setUserType(UserType.values()[1]);

        session.setAttribute("newUser", user);

        try {
            response.sendRedirect("/user_new");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @RequestMapping(value = "employees/user_edit", method = RequestMethod.POST)
    public  String editUser(@RequestParam("userIndex") String choice ,HttpSession session, HttpServletResponse response, HttpServletRequest request) {

        
        System.out.println("update user methode");
        @SuppressWarnings("unchecked")
        List<User> users = (ArrayList<User>) request.getSession().getAttribute("users");
        int userIndex = (Integer.parseInt(choice));
        System.out.println("usersIndex is: " + userIndex);

        User user = (User) users.get(userIndex);
        System.out.println("product to be updated in employees user edit id: " + user.getId() + " email: " + user.getEmail());
        
        //OPSLAAN IN DE SESSIE VAN DE OUDE GEGEVENS
        //Wachtwoord moet altijd opnieuw!
        request.getSession().setAttribute("tempAdres", users.get(Integer.parseInt(choice)).getBillingAddress());
        request.getSession().setAttribute("userEmail", users.get(Integer.parseInt(choice)).getEmail());
        
        
        
       
        userService.updateUser(user);
        
        //MAAK EEN NIEUWE AAN MET DEZE GEGEVENS!
       //Komt in de HTML.
        
        
        //RESET TABLE
        request.getSession().setAttribute("users", users);
        request.getSession().setAttribute("user", user);
        return "emp_user_edit";
        
    }

    @RequestMapping(value = "/user_new", method = RequestMethod.GET)
    public String UserNEW(HttpSession session, HttpServletResponse response) {

//        Eerste keer een leeg adres
        if (session.getAttribute("tempAdres") == null) {

            Address nieuweAdres = new Address();
            session.setAttribute("tempAdres", nieuweAdres);
        }
        return "user_new";

    }

    @RequestMapping(value = "/MaakAccountR/ok", method = RequestMethod.POST)
    public String voegAccountToeR(HttpServletRequest request, HttpSession session) {

        session.removeAttribute("message");
        session.removeAttribute("nieuweAdress");
        System.out.println("Account gegevens worden verwerkt");

        // Waarom print hij hier Klant en niet CUSTOMER?
        String userType = request.getParameter("usertype");

        //geeft de juiste usertype
        UserType accountType = UserType.valueOf(userType);

        System.out.println(accountType);
        User newUser = userService.registerUser(request, accountType);

        //CONTROLE EMAIL AL BESTAAT!
        boolean emailvalid = true;

        try {

            User dommeuser = userService.findUserByEmail(request.getParameter("email"));
            dommeuser.getEmail();
            System.out.println("Email bestaat al");

            emailvalid = false;

        } catch (Exception e) {
            e.getMessage();
            System.out.println("Deze email is nog niet in de DB- dus validemail = true");
        }

        if (!emailvalid) {
            System.out.println(" email is niet valid");

            session.setAttribute("tempAdres", newUser.getBillingAddress());

            int message = 13;
            session.setAttribute("message", message);
            return "redirect:/employees/users";
        }

        //Deel 2 DB.
        // dan wijzigingen opslaan in DB (cart of user)
        //deze if is niet nodig toch, want er is een nieuwe en niks anders nodig?
        //if(request.getSession().getAttribute("currentUser")!=null){
        System.out.println("Gegevens worden opgeslagen in DB");
        //maakt de user!
        userService.addUser(newUser);
        //Maakt een lege suborder array.
        List<CartSubOrder> subs = new ArrayList<>();
        //Maakt een cart
        Cart newCart = new Cart();
        //linkt subs met cart
        newCart.setSubOrders(subs);
        //linkt cart met de user.
        newCart.setUser(newUser);
        //Geeft cart zelfde id als de nieuwe user (om parrallel te lopen gemak)
        newCart.setId(newUser.getId());
        //sla de cart op
        cartService.createCart(newCart);
        //check
        System.out.println("tot hier gaat het nog goed-Einde DB opslaan.");

        return "redirect:/employees/users";
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public void updateUser(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
    
       User editUser = (User) request.getSession().getAttribute("user");
       Address updateAddress = editUser.getBillingAddress();
        
       //data ophalen.
       
      
      
            updateAddress.setFirstName(request.getParameter("firstName"));
            updateAddress.setFamilyName(request.getParameter("lastName"));
            updateAddress.setInsertion(request.getParameter("insertion"));
            updateAddress.setCity(request.getParameter("city"));
            updateAddress.setNumber(Integer.parseInt(request.getParameter("number")));
            updateAddress.setNumAddition(request.getParameter("addition"));
            updateAddress.setStreet(request.getParameter("street"));
            updateAddress.setZipCode(request.getParameter("zipCode"));
       
             editUser.setEmail(request.getParameter("email"));
             
             System.out.println(passwordEncoder);
             if(request.getParameter("W8")!=null && !request.getParameter("W8").equals("X!@#12z")){
            	 
            	 System.out.println("Updating password");
            	 editUser.setPassHash(passwordEncoder.encode(request.getParameter("W8")));
             }
             
             
                
       
       
       userService.updateUser(editUser);
       
        try {
           response.sendRedirect("/employees/users");
          } catch (IOException e) {
           System.out.println(e.getMessage());
            }
 
    }
    
    
    
    @RequestMapping(value = {"/mijn-gegevens"}, method = RequestMethod.GET)
    public String userDetails(Map<String, Object> model, HttpServletRequest request) {
    	
    	User user = (User) request.getSession().getAttribute("currentUser");
    
    	model.put("address", user.getBillingAddress());
    	model.put("email", user.getEmail());
  
        return "user_details";
    }
    
    @RequestMapping(value = "/mijn-gegevens", method = RequestMethod.POST)
    public void updateCurrentUser(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
    	System.out.println("updating mijn gegevens");
       User editUser = (User) request.getSession().getAttribute("currentUser");
       Address updateAddress = editUser.getBillingAddress();
        
       //data ophalen.
      
      
            updateAddress.setFirstName(request.getParameter("firstName"));
            updateAddress.setFamilyName(request.getParameter("lastName"));
            updateAddress.setInsertion(request.getParameter("insertion"));
            updateAddress.setCity(request.getParameter("city"));
            updateAddress.setNumber(Integer.parseInt(request.getParameter("number")));
            updateAddress.setNumAddition(request.getParameter("addition"));
            updateAddress.setStreet(request.getParameter("street"));
            updateAddress.setZipCode(request.getParameter("zipCode"));
       
             editUser.setEmail(request.getParameter("email"));
            
             if(!request.getParameter("passnew").equals("X!@#12z")){
            	 
            	 System.out.println("Updating password");
            	 editUser.setPassHash(passwordEncoder.encode(request.getParameter("passnew")));
             }
                
       userService.updateUser(editUser);
       
        try {
           response.sendRedirect("/products");
          } catch (IOException e) {
           System.out.println(e.getMessage());
            }
 
    }
    
    
    
}

