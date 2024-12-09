package com.riki.simsppob.service.impl;

import com.riki.simsppob.exception.ResourceAlreadyExistedException;
import com.riki.simsppob.model.Balance;
import com.riki.simsppob.model.User;
import com.riki.simsppob.model.request.LoginRequest;
import com.riki.simsppob.model.request.RegisterRequest;
import com.riki.simsppob.model.response.LoginResponse;
import com.riki.simsppob.repository.BalanceRepository;
import com.riki.simsppob.repository.UserRepository;
import com.riki.simsppob.security.jwt.JwtUtils;
import com.riki.simsppob.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BalanceRepository balanceRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Transactional
    @Override
    public void register(RegisterRequest request) {
        if (this.userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistedException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(this.passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setProfileImage(String.format("https://ui-avatars.com/api/?name=%s+%s&background=random&bold=true",
                request.getFirstName(), request.getLastName()));

        this.userRepository.save(user);

        Balance balance = new Balance();
        balance.setBalanceAmount(0L);
        balance.setUserId(user.getId());

        this.balanceRepository.save(balance);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtUtils.generateToken(userDetails);

        return LoginResponse
                .builder()
                .token(token)
                .build();
    }
}
