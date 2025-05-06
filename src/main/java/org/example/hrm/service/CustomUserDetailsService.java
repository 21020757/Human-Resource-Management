package org.example.hrm.service;

import org.example.hrm.exception.UserIsDisabledException;
import org.example.hrm.model.CustomUserDetails;
import org.example.hrm.model.Employee;
import org.example.hrm.model.User;
import org.example.hrm.repository.EmployeeRepository;
import org.example.hrm.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    public CustomUserDetailsService(UserRepository userRepository,
                                    EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws RuntimeException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email không tồn tại: " + email));

        if (!user.getActive()) {
            throw new UserIsDisabledException("Tài khoản đã bị vô hiệu hóa: " + email);
        }
        Employee employee = employeeRepository.findEmployeeByEmail(email);
        String position = employee != null ? employee.getPosition() : null;

        return new CustomUserDetails(user, position);
    }
}
