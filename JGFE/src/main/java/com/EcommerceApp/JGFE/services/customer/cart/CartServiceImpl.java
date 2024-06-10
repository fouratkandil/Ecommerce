package com.EcommerceApp.JGFE.services.customer.cart;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.EcommerceApp.JGFE.dto.AddProducttoCartDto;
import com.EcommerceApp.JGFE.dto.CartItemsDto;
import com.EcommerceApp.JGFE.dto.OrderDto;
import com.EcommerceApp.JGFE.dto.PlaceOrderDto;
import com.EcommerceApp.JGFE.entity.CartItems;
import com.EcommerceApp.JGFE.entity.Coupon;
import com.EcommerceApp.JGFE.entity.Order;
import com.EcommerceApp.JGFE.entity.Product;
import com.EcommerceApp.JGFE.entity.user;
import com.EcommerceApp.JGFE.enums.OrderStatus;
import com.EcommerceApp.JGFE.enums.exceptions.ValidationException;
import com.EcommerceApp.JGFE.repository.CartItemRepository;
import com.EcommerceApp.JGFE.repository.CouponRepository;
import com.EcommerceApp.JGFE.repository.OrderRepository;
import com.EcommerceApp.JGFE.repository.ProductRepository;
import com.EcommerceApp.JGFE.repository.UserRepository;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CouponRepository couponRepository;

    public ResponseEntity<?> addProductToCart(AddProducttoCartDto addProducttoCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProducttoCartDto.getUserId(),
                OrderStatus.Pending);
        if (activeOrder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Active order not found!");
        }
        Optional<CartItems> optionalCartItems = cartItemRepository.findByProductIdAndOrderIdAndUserId(
                addProducttoCartDto.getProductId(),
                activeOrder.getId(), addProducttoCartDto.getUserId());

        if (optionalCartItems.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            Optional<Product> optionalProduct = productRepository.findById(addProducttoCartDto.getProductId());
            Optional<user> optionalUser = userRepository.findById(addProducttoCartDto.getUserId());

            if (optionalProduct.isPresent() && optionalUser.isPresent()) {
                CartItems cartItems = new CartItems();
                cartItems.setProduct(optionalProduct.get());
                cartItems.setPrice(optionalProduct.get().getPrice());
                cartItems.setQuantity(1L);
                cartItems.setUser(optionalUser.get());
                cartItems.setOrder(activeOrder);

                CartItems updatedCart = cartItemRepository.save(cartItems);

                activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cartItems.getPrice());
                activeOrder.setAmount(activeOrder.getAmount() + cartItems.getPrice());
                activeOrder.getCartItems().add(cartItems);

                orderRepository.save(activeOrder);

                return ResponseEntity.status(HttpStatus.CREATED).body(cartItems);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Product not found!");
            }
        }
    }

    public OrderDto getCartByUserId(Long userId) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);

        if (activeOrder != null) {
            List<CartItemsDto> cartItemsDtoList = activeOrder.getCartItems().stream()
                    .map(CartItems::getCartDto)
                    .collect(Collectors.toList());

            OrderDto orderDto = new OrderDto();
            orderDto.setAmount(activeOrder.getAmount());
            orderDto.setId(activeOrder.getId());
            orderDto.setDiscount(activeOrder.getDiscount());
            orderDto.setOrderStatus(activeOrder.getOrderStatus());
            orderDto.setTotalAmount(activeOrder.getTotalAmount());
            orderDto.setCartItems(cartItemsDtoList);

            if (activeOrder.getCoupon() != null) {
                orderDto.setCouponName(activeOrder.getCoupon().getName());
            }

            return orderDto;
        } else {
            // Handle case when activeOrder is null (no pending order found for the user)
            // You can return null or an empty OrderDto, depending on your requirements
            return null;
        }
    }

    public OrderDto applyCoupon(Long userId, String code) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new ValidationException("Coupon not found."));

        if (couponIsExpired(coupon)) {
            throw new ValidationException("Coupon has expired");
        }

        double discountAmount = ((coupon.getDiscount() / 100.0) * activeOrder.getTotalAmount());
        double netAmount = activeOrder.getTotalAmount() - discountAmount;

        activeOrder.setAmount((long) netAmount);
        activeOrder.setDiscount((long) discountAmount);
        activeOrder.setCoupon(coupon);

        orderRepository.save(activeOrder);

        return activeOrder.getOrderDto();
    }

    private boolean couponIsExpired(Coupon coupon) {
        Date currentDate = new Date();
        Date expirationDate = coupon.getExpirationDate();

        return expirationDate != null && currentDate.after(expirationDate);
    }

    public OrderDto increaseProductQuantity(AddProducttoCartDto addProducttoCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProducttoCartDto.getUserId(),
                OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepository.findById(addProducttoCartDto.getProductId());
        Optional<CartItems> optionalCartItems = cartItemRepository.findByProductIdAndOrderIdAndUserId(
                addProducttoCartDto.getProductId(), activeOrder.getId(), addProducttoCartDto.getUserId());

        if (optionalProduct.isPresent() && optionalCartItems.isPresent()) {
            CartItems cartItems = optionalCartItems.get();
            Product product = optionalProduct.get();

            activeOrder.setAmount(activeOrder.getAmount() + product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() + product.getPrice());

            cartItems.setQuantity(cartItems.getQuantity() + 1);

            if (activeOrder.getCoupon() != null) {
                double discountAmount = ((activeOrder.getCoupon().getDiscount() / 100.0)
                        * activeOrder.getTotalAmount());
                double netAmount = activeOrder.getTotalAmount() - discountAmount;

                activeOrder.setAmount((long) netAmount);
                activeOrder.setDiscount((long) discountAmount);
            }

            cartItemRepository.save(cartItems);
            orderRepository.save(activeOrder);

            return activeOrder.getOrderDto();
        }
        return null;
    }

    public OrderDto decreaseProductQuantity(AddProducttoCartDto addProducttoCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProducttoCartDto.getUserId(),
                OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepository.findById(addProducttoCartDto.getProductId());
        Optional<CartItems> optionalCartItems = cartItemRepository.findByProductIdAndOrderIdAndUserId(
                addProducttoCartDto.getProductId(), activeOrder.getId(), addProducttoCartDto.getUserId());

        if (optionalProduct.isPresent() && optionalCartItems.isPresent()) {
            CartItems cartItems = optionalCartItems.get();
            Product product = optionalProduct.get();

            activeOrder.setAmount(activeOrder.getAmount() - product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() - product.getPrice());

            cartItems.setQuantity(cartItems.getQuantity() - 1);

            if (activeOrder.getCoupon() != null) {
                double discountAmount = ((activeOrder.getCoupon().getDiscount() / 100.0)
                        * activeOrder.getTotalAmount());
                double netAmount = activeOrder.getTotalAmount() - discountAmount;

                activeOrder.setAmount((long) netAmount);
                activeOrder.setDiscount((long) discountAmount);
            }

            cartItemRepository.save(cartItems);
            orderRepository.save(activeOrder);

            return activeOrder.getOrderDto();
        }
        return null;
    }

    public OrderDto placeOrder(PlaceOrderDto placeOrderDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(),
                OrderStatus.Pending);
        Optional<user> optionalUser = userRepository.findById(placeOrderDto.getUserId());

        if (optionalUser.isPresent()) {
            activeOrder.setOrderDescription(placeOrderDto.getOrderDescription());
            activeOrder.setAddress(placeOrderDto.getAddress());
            activeOrder.setDate(new Date());
            activeOrder.setOrderStatus(OrderStatus.Placed);
            activeOrder.setTrackingId(UUID.randomUUID());

            orderRepository.save(activeOrder);

            Order order = new Order();
            order.setAmount(0L);
            order.setTotalAmount(0L);
            order.setDiscount(0L);
            order.setUser(optionalUser.get());
            order.setOrderStatus(OrderStatus.Pending);
            orderRepository.save(order);

            return activeOrder.getOrderDto();
        }
        return null;
    }

    public List<OrderDto> getMyPlacedOrder(Long userId) {
        return orderRepository.findByUserIdAndOrderStatusIn(userId, List.of(OrderStatus.Placed,
                OrderStatus.Shipped, OrderStatus.Delivered)).stream().map(Order::getOrderDto)
                .collect(Collectors.toList());
    }

    public OrderDto searchOrderByTrackingId(UUID trackingId) {
        Optional<Order> optionalOrder = orderRepository.findByTrackingId(trackingId);

        if (optionalOrder.isPresent()) {
            return optionalOrder.get().getOrderDto();
        }
        return null;
    }

    public ResponseEntity<?> removeFromCart(AddProducttoCartDto addProducttoCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProducttoCartDto.getUserId(),
                OrderStatus.Pending);
        if (activeOrder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Active order not found!");
        }

        Optional<CartItems> optionalCartItems = cartItemRepository.findByProductIdAndOrderIdAndUserId(
                addProducttoCartDto.getProductId(),
                activeOrder.getId(), addProducttoCartDto.getUserId());

        if (!optionalCartItems.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found in cart!");
        }

        CartItems cartItems = optionalCartItems.get();
        double itemPrice = cartItems.getPrice();

        // Remove the item from the order
        activeOrder.setTotalAmount(activeOrder.getTotalAmount() - (long) (itemPrice * cartItems.getQuantity()));
        activeOrder.setAmount(activeOrder.getAmount() - (long) (itemPrice * cartItems.getQuantity()));
        activeOrder.getCartItems().remove(cartItems);

        // Remove the item from cart
        cartItemRepository.delete(cartItems);

        orderRepository.save(activeOrder);

        return ResponseEntity.status(HttpStatus.OK).body("Product removed from cart successfully!");
    }
}
