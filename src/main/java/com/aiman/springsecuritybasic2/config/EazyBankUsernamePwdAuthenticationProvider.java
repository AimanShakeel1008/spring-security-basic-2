package com.aiman.springsecuritybasic2.config;

import com.aiman.springsecuritybasic2.entity.Customer;
import com.aiman.springsecuritybasic2.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EazyBankUsernamePwdAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username=authentication.getName();
        String pwd=authentication.getCredentials().toString();

        List<Customer> customers = customerRepository.findByEmail(username);

        if(customers.size()>0){
            if(passwordEncoder.matches(pwd,customers.get(0).getPwd())){
                List<GrantedAuthority> authorities=new ArrayList<>();

                authorities.add(new SimpleGrantedAuthority(customers.get(0).getRole()));
                return  new UsernamePasswordAuthenticationToken(username,pwd,authorities);
            }
            else{
                throw new BadCredentialsException("Invalid Password.");
            }
        }
        else{
            throw new BadCredentialsException("No user found with this details.");
        }
    }

    @Override
    public boolean supports(Class<?> authenticationType) {
        return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
    }
}
