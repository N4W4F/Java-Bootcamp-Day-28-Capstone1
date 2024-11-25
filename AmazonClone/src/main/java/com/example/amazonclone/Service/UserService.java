package com.example.amazonclone.Service;

import com.example.amazonclone.Model.Merchant;
import com.example.amazonclone.Model.MerchantStock;
import com.example.amazonclone.Model.Product;
import com.example.amazonclone.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {

    ArrayList<User> users = new ArrayList<>();

    private final MerchantService merchantService;
    private final MerchantStockService merchantStockService;
    private final ProductService productService;

    public ArrayList<User> getUsers() {
        if (users.isEmpty())
            return null;

        return users;
    }

    public boolean addUser(User user) {
        for (User u : users) {
            if (u.getId().equals(user.getId())) {
                return false;
            }
        }
        users.add(user);
        return true;
    }

    public String updateUser(String id, User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.set(i, user);
                return "ok";
            }
        }
        return "invalid id";
    }

    public boolean deleteUser(String id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    public String buyProduct(String user_id, String merchant_id, String product_id) {
        if (merchantService.getMerchants() == null)
            return "null m";
        if (productService.getProducts() == null)
            return "null p";
        if (merchantStockService.getMerchantStocks() == null)
            return "null ms";

        User user = findUserById(user_id);
        if (user == null) return "invalid user";

        Merchant merchant = findMerchantById(merchant_id);
        if (merchant == null) return "invalid merchant";

        Product product = findProductById(product_id);
        if (product == null) return "invalid product";

        for (MerchantStock ms : merchantStockService.getMerchantStocks()) {
            if (ms.getProductId().equals(product_id) && ms.getMerchantId().equals(merchant_id)) {
                if (user.getBalance() >= product.getPrice()) {
                    if (ms.getStock() > 0) {
                        ms.setStock(ms.getStock() - 1);
                        user.setBalance(user.getBalance() - product.getPrice());
                        return "ok";
                    } else return "out of stock";
                } else return "no balance";
            }
        }
        return "out of stock";
    }

    // 1- Extra Endpoint 'transferBalance' is used to transfer money from user to another
    public String transferBalance(String sender_id, String receiver_id, double amount) {
        User sender = findUserById(sender_id);
        if (sender == null) return "invalid sender";

        User receiver = findUserById(receiver_id);
        if (receiver == null) return "invalid receiver";

        if (sender.getBalance() >= amount) {
            sender.setBalance(sender.getBalance() - amount);
            receiver.setBalance(receiver.getBalance() + amount);
            return "ok";
        }
        return "no balance";
    }

    // 2- Extra Endpoint 'resetPassword' is used to reset a user's password
    public String resetPassword(String user_id, String password) {
        User user = findUserById(user_id);
        if (user == null) return "invalid user";

        if (isValid(password)) {
            user.setPassword(password);
            return "ok";
        }
        return "invalid password";
    }

    // 3- Extra Endpoint 'refundPurchase' is used to return the money back to user after a valid refund
    public String refundPurchase(String user_id, String merchant_id, String product_id) {
        if (merchantService.getMerchants() == null)
            return "null m";
        if (productService.getProducts() == null)
            return "null p";
        if (merchantStockService.getMerchantStocks() == null)
            return "null ms";

        User user = findUserById(user_id);
        if (user == null) return "invalid user";

        Merchant merchant = findMerchantById(merchant_id);
        if (merchant == null) return "invalid merchant";

        Product product = findProductById(product_id);
        if (product == null) return "invalid product";

        user.setBalance(user.getBalance() + product.getPrice());
        return "ok";
    }

    // 4- Extra Endpoint 'addToWishlist' allows the user to add a product by its ID to their wishlist
    public String addToWishlist(String user_id, String product_id) {
        User user = findUserById(user_id);
        if (user == null) return "invalid user";

        if (productService.getProducts() == null)
            return "null p";

        for (String s : user.getWishlist()) {
            if (s.equals(product_id)) {
                return "already wished";
            }
        }

        Product product = findProductById(product_id);
        if (product == null) return "invalid product";

        user.getWishlist().add(product_id);
        return "ok";
    }

    // 5- Extra Endpoint 'removeFromWishlist' allows the user to remove a product by its ID from their wishlist
    public String removeFromWishlist(String user_id, String product_id) {
        User user = findUserById(user_id);
        if (user == null) return "invalid user";

        if (productService.getProducts() == null)
            return "null p";

        for (String s : user.getWishlist()) {
            if (s.equals(product_id)) {
                user.getWishlist().remove(product_id);
                return "ok";
            }
        }
        return "invalid product";
    }

    // 6- Extra Endpoint 'addToCart' allows the user to add a product to their cart
    public String addToCart(String user_id, String product_id) {
        User user = findUserById(user_id);
        if (user == null)
            return "invalid user";

        Product product = findProductById(product_id);
        if (product == null)
            return "invalid product";

        user.getCart().add(product);
        return "ok";
    }

    // 7- Extra Endpoint 'removeFromCart' allows the user to remove a product from their cart
    public String removeFromCart(String user_id, String product_id) {
        User user = findUserById(user_id);
        if (user == null)
            return "invalid user";

        Product product = findProductById(product_id);
        if (product == null)
            return "invalid product";

        user.getCart().remove(product);
        return "ok";
    }

    // 8- Extra Endpoint 'placeOrder' allows the user to buy the products in their cart
    public String placeOrder(String user_id) {
        User user = findUserById(user_id);
        if (user == null)
            return "invalid user";

        if (user.getCart().isEmpty())
            return "empty";

        if (user.getBalance() >= getTotalPrice(user)) {
            for (MerchantStock ms : merchantStockService.getMerchantStocks()) {
                if (user.getCart() == null)
                    return "empty";
                for (int i = 0; i < user.getCart().size(); i++) {
                    if (ms.getProductId().equals(user.getCart().get(i).getId())) {
                        if (ms.getStock() > 0) {
                            ms.setStock(ms.getStock() - 1);
                            user.setBalance(user.getBalance() - user.getCart().get(i).getPrice());
                            user.getCart().remove(i);
                        }
                    }
                }
            }
        } else return "no balance";
        return "ok";
    }

    // Helper Methods:

    // A method to check validity of a password
    private boolean isValid(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$");
    }

    // A method to find a user by ID
    private User findUserById(String user_id) {
        for (User u : users) {
            if (u.getId().equals(user_id)) {
                return u;
            }
        }
        return null;
    }

    // A method to find a product by ID
    private Product findProductById(String product_id) {
        for (Product p : productService.getProducts()) {
            if (p.getId().equals(product_id)) {
                return p;
            }
        }
        return null;
    }

    // A method to find a merchant by ID
    private Merchant findMerchantById(String merchant_id) {
        for (Merchant m : merchantService.getMerchants()) {
            if (m.getId().equals(merchant_id)) {
                return m;
            }
        }
        return null;
    }

    // A method to get the total price in user's cart
    private double getTotalPrice(User user) {
        double total = 0;
        for (Product p : user.getCart())
            total += p.getPrice();

        return total;
    }
}
