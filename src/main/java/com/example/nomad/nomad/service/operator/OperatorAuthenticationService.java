package com.example.nomad.nomad.service.operator;

import com.example.nomad.nomad.dto.operatorAuth.OperatorAuthDto;
import com.example.nomad.nomad.dto.operatorAuth.OperatorAuthResponse;
import com.example.nomad.nomad.model.Operator;
import com.example.nomad.nomad.repository.OperatorRepository;
import com.example.nomad.nomad.security.jwt.JwtService;
import com.example.nomad.nomad.service.ticket.impl.TicketServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OperatorAuthenticationService {
    private final OperatorRepository operatorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);


    public OperatorAuthResponse login(OperatorAuthDto operatorAuthDto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        operatorAuthDto.getUsername(),
                        operatorAuthDto.getPassword()
                )
        );
        logger.debug("Operator Auth",operatorAuthDto);
        Operator operator = operatorRepository.findByLogin(operatorAuthDto.getUsername());

        String token = jwtService.generateToken(operator);
        return new OperatorAuthResponse(token);
    }
}
