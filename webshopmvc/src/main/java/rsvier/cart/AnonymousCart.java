package rsvier.cart;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import rsvier.cartsuborder.CartSubOrder;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by J on 4/28/2017.
 */
@Entity
@Table(name="anonymous_carts")
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class AnonymousCart implements Serializable, CartInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String sessionId;
    @OneToMany(cascade=CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "anon_cart_id")
    private List<CartSubOrder> subOrders = new ArrayList<>();
    private BigDecimal totalPrice;

    public AnonymousCart() {

    }

    public AnonymousCart(String sessionId) {
        this.sessionId = sessionId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<CartSubOrder> getSubOrders() {
        return subOrders;
    }

    public void setSubOrders(List<CartSubOrder> subOrders) {
        this.subOrders = subOrders;
    }

    @Override
    public void addSubOrder(CartSubOrder cso) {
        subOrders.add(cso);
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void calculateTotalPrice() {
        for (CartSubOrder cso : subOrders) {
            BigDecimal subTotal = cso.getSubTotal();
            totalPrice = totalPrice.add(subTotal);
        }
    }

}
