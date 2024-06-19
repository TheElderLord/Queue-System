package com.example.nomad.nomad.service.operator;

import com.example.nomad.nomad.repository.OperatorRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OperatorDetailServiceImpl implements UserDetailsService {
    private final OperatorService operatorRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) operatorRepository.getOperatorByUsername(username);
    }
}
