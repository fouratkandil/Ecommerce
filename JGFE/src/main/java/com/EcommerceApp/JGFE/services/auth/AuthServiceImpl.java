package com.EcommerceApp.JGFE.services.auth;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.EcommerceApp.JGFE.dto.SignupRequest;
import com.EcommerceApp.JGFE.dto.UserCrudDto;
import com.EcommerceApp.JGFE.dto.UserDto;
import com.EcommerceApp.JGFE.entity.Order;
import com.EcommerceApp.JGFE.entity.user;
import com.EcommerceApp.JGFE.enums.OrderStatus;
import com.EcommerceApp.JGFE.enums.UserRole;
import com.EcommerceApp.JGFE.repository.OrderRepository;
import com.EcommerceApp.JGFE.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public UserDto createUser(SignupRequest signupRequest) {
        user user = new user();

        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        user createUser = userRepository.save(user);

        Order order = new Order();
        order.setAmount(0L);
        order.setTotalAmount(0L);
        order.setDiscount(0L);
        order.setUser(createUser);
        order.setOrderStatus(OrderStatus.Pending);
        orderRepository.save(order);

        UserDto userDto = new UserDto();
        userDto.setId(createUser.getId());

        return userDto;

    }

    public List<UserCrudDto> allusers() {
        List<user> users = userRepository.findAll();
        // Filter users to include only ADMIN role
        List<user> adminUsers = users.stream()
                .filter(user -> user.getRole().equals(UserRole.CUSTOMER))
                .collect(Collectors.toList());
        return adminUsers.stream()
                .map(user::getDto)
                .collect(Collectors.toList());
    }

    public boolean deleteUser(Long id) {
        Optional<user> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public UserCrudDto updateUser(Long userId, UserCrudDto userCrudDto) throws IOException {
        Optional<user> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            user user = optionalUser.get();

            user.setName(userCrudDto.getName());
            if (!hasUserwithEmail(userCrudDto.getEmail())) {
                user.setEmail(userCrudDto.getEmail());
            }
            user.setPassword(new BCryptPasswordEncoder().encode(userCrudDto.getPassword()));
            return userRepository.save(user).getDto();
        } else {
            return null;
        }
    }

    public UserCrudDto getUserById(Long userId) {
        Optional<user> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            return optionalUser.get().getDto();
        }
        return null;
    }

    public Boolean hasUserwithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @PostConstruct
    public void createAdminaccount() {
        user AdminAccount = userRepository.findByRole(UserRole.ADMIN);

        if (null == AdminAccount) {
            user user = new user();
            user.setEmail("admin@gmail.com");
            user.setName("admin");
            user.setRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin123"));
            userRepository.save(user);
        }
    }
    /*
     * @PostConstruct
     * public void createUseraccount() {
     * user UserAccount = new user();
     * UserAccount.setEmail("jh@gmail.com");
     * UserAccount.setName("jh");
     * UserAccount.setRole(UserRole.CUSTOMER);
     * UserAccount.setPassword(new BCryptPasswordEncoder().encode("jh123"));
     * userRepository.save(UserAccount);
     * }
     */

}
