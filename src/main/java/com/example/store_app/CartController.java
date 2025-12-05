package com.example.store_app;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepository orderRepository; // Используем репозиторий напрямую для простоты

    @GetMapping
    public String showCart(HttpSession session, Model model) {
        Cart cart = getCartFromSession(session);
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("totalPrice", cart.getTotalPrice());
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, HttpSession session) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            Cart cart = getCartFromSession(session);
            cart.addProduct(product);
        }
        return "redirect:/computer_components";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long productId, HttpSession session) {
        Cart cart = getCartFromSession(session);
        cart.removeProductById(productId);
        return "redirect:/cart";
    }

    // Страница оформления заказа (форма ввода данных)
    @GetMapping("/checkout")
    public String showCheckoutPage(HttpSession session, Model model) {
        Cart cart = getCartFromSession(session);
        if(cart.getItems().isEmpty()) return "redirect:/cart";

        model.addAttribute("totalPrice", cart.getTotalPrice());
        return "checkout"; // Создадим этот файл ниже
    }

    // Финальное сохранение заказа
    @PostMapping("/checkout/confirm")
    public String confirmOrder(@RequestParam String address,
                               @RequestParam String phone,
                               HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Cart cart = getCartFromSession(session);
        if (cart.getItems().isEmpty()) return "redirect:/cart";

        // 1. Создаем заказ
        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        order.setAddress(address);
        order.setPhone(phone);

        // 2. Сохраняем товары в заказ
        for (Product p : cart.getItems()) {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(p);
            item.setQuantity(1); // Пока считаем поштучно
            item.setPriceAtPurchase(p.getPrice());
            order.getItems().add(item);
        }

        orderRepository.save(order); // Hibernate сам сохранит и order, и items благодаря CascadeType.ALL

        cart.clear();
        return "redirect:/computer_components?orderSuccess=true";
    }

    private Cart getCartFromSession(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }
}