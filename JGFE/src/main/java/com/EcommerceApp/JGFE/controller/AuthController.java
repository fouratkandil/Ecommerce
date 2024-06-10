package com.EcommerceApp.JGFE.controller;

import java.io.IOException;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RestController;

import com.EcommerceApp.JGFE.dto.AuthentificatinRequest;
import com.EcommerceApp.JGFE.dto.SignupRequest;
import com.EcommerceApp.JGFE.dto.UserDto;
import com.EcommerceApp.JGFE.entity.user;
import com.EcommerceApp.JGFE.repository.UserRepository;
import com.EcommerceApp.JGFE.services.auth.AuthService;
import com.EcommerceApp.JGFE.utils.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestController
// @CrossOrigin("*")
public class AuthController {

    private static final String HEADER_STRING = "Authorization";

    private static final String TOKEN_PREFIX = "Bearer ";

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final UserDetailsService userDetailsService;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    private final AuthService authService;

    @PostMapping("/authenticate")
    public void AuthentificationRequest(@RequestBody AuthentificatinRequest authentificatinRequest,
            HttpServletResponse response) throws IOException, JSONException {

        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authentificatinRequest.getUsername(),
                            authentificatinRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authentificatinRequest.getUsername());
        Optional<user> optional = userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        if (optional.isPresent()) {
            response.getWriter().write(new JSONObject()
                    .put("id", optional.get().getId())
                    .put("role", optional.get().getRole())
                    .toString());
            response.addHeader("Access-Control-Expose-Headers", "Authorization");
            response.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, " +
                    "X-Requested-With, Content-Type, Accept, X-Custom-Header");
            response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {

        if (authService.hasUserwithEmail(signupRequest.getEmail())) {
            return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDto userDto = authService.createUser(signupRequest);
        System.out.println(signupRequest);
        return ResponseEntity.ok().body(userDto.getEmail());
    }

}
