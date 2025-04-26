package com.lindtsey.pahiramcar.auth;

import com.lindtsey.pahiramcar.config.JwtService;
import com.lindtsey.pahiramcar.customer.Customer;
import com.lindtsey.pahiramcar.customer.CustomerDTO;
import com.lindtsey.pahiramcar.customer.CustomerService;
import com.lindtsey.pahiramcar.user.User;
import com.lindtsey.pahiramcar.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomerService customerService;

    public AuthenticationResponse register(CustomerDTO dto) {

        Customer customer = customerService.toCustomer(dto);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        userRepository.save(customer);

        var jwtToken = jwtService.generateToken(customer);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var user = userRepository.findUserByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();

    }
}
