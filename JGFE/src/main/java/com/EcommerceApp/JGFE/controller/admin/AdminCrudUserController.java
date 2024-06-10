package com.EcommerceApp.JGFE.controller.admin;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.EcommerceApp.JGFE.dto.UserCrudDto;
import com.EcommerceApp.JGFE.services.auth.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminCrudUserController {

    private final AuthService authService;

    @GetMapping("/users")
    public ResponseEntity<List<UserCrudDto>> allusers() {
        List<UserCrudDto> userDtos = authService.allusers();
        return ResponseEntity.ok(userDtos);
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = authService.deleteUser(id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();

    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<UserCrudDto> updateUser(@PathVariable Long userId, @ModelAttribute UserCrudDto userCrudDto)
            throws IOException {
        UserCrudDto updatedDto = authService.updateUser(userId, userCrudDto);

        if (updatedDto != null) {
            return ResponseEntity.ok(updatedDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserCrudDto> getUserById(@PathVariable Long userId) {
        UserCrudDto userCrudDto = authService.getUserById(userId);

        if (userCrudDto != null) {
            return ResponseEntity.ok(userCrudDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
