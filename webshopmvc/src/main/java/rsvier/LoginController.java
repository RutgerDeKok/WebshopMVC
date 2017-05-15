package rsvier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rsvier.cart.Cart;
import rsvier.cart.CartService;
import rsvier.cartsuborder.CartSubOrder;
import rsvier.user.User;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import rsvier.user.CurrentUser;

@Controller
public class LoginController {

 
    @Autowired
    private CartService cartService;
    

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String inlog() {
        return "login";
    }
    
    

    @RequestMapping("/success")
    public String success(HttpSession session) {

        // current user set uit de security!!
        
        CurrentUser currentUser = (CurrentUser)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        session.setAttribute("currentUser", currentUser.getUser());
        
        checkForSessionCart(session, currentUser.getUser());
        //System.out.println(currentUser.getUser().toString());

        return "success";
    }
    
    
    private void checkForSessionCart(HttpSession session, User user) {
        // checken of er all een cart in de sessie is, 
        Cart sessionCart = (Cart) session.getAttribute("cart");
        if (sessionCart != null) {

            //in dat geval moeten de suborders 
            //	worden toegevoegd aan de cart uit de DB.
            Cart dbCart = cartService.getCartByUser(user);
            System.out.println("DB cart id is: " + dbCart.getId());
            for (CartSubOrder sub : sessionCart.getSubOrders()) {
                dbCart.getSubOrders().add(sub);
            }

            // dbCart in sessie zetten en opslaan in DB
            session.setAttribute("cart", dbCart);
            cartService.updateCart(dbCart);
        }

    }
    
    
    
    // old methods used before Spring security implementation
    
//    @Autowired
//    private UserService userService;
//    
//    
//  @RequestMapping(value = "/login", method = RequestMethod.POST)
//  public String loginCheck(HttpServletRequest request, HttpServletResponse response) {
//
//      System.out.println("DIT DRAAIT NIET MEER DOOR SERCURITY!, als je dit ziet , dan WEL!");
//      String uname = request.getParameter("username");
//      /*String pass = request.getParameter("psw");
//
//		char[] passChars = pass.toCharArray();*/
//
//      User user = null;
//
//      // get user by email
//      try {
//
//          Validator validator = Validator.getInstance();
//          if (validator.validateEmail(uname)) {
//              user = userService.findUserByEmail(uname);
//              System.out.println("User: " + user.toString());
//          } else {
//              System.out.println("Not a valid user");
//          }
//
//          if (true) { // ori: PassHasher.check(passChars, user.getPassHash())
//              System.out.println("login succesful!");
//              System.out.println("User: " + user.toString());
//
//              // current user set
//              request.getSession().setAttribute("currentUser", user);
//
//              // checken of er all een cart in de sessie is, in dat geval moeten de suborders 
//              //	worden toegevoegd aan de cart uit de DB.
//              checkForSessionCart(request.getSession(), user);
//
////          } else { // foute inlog
////              System.out.println("login incorrect!");
//          }
//
//      } catch (Exception e) {
//          System.out.println(e.getMessage());
//
//      }
//      //gaat naar een succes pagina.
//      return "success";
//
//  }



//  @RequestMapping("/logout")
//  public void Logout(HttpServletRequest request, HttpServletResponse response) {
//
//      request.getSession().removeAttribute("currentUser");
//      System.out.println("logging out");
//
//      try {
//          response.sendRedirect("/");
//      } catch (IOException e) {
//          System.out.println(e.getMessage());
//      }
//  }
    

}