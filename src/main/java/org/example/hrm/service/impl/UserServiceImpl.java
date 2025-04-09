package org.example.hrm.service.impl;

import org.example.hrm.dto.ChangePasswordRequest;
import org.example.hrm.dto.RoleDto;
import org.example.hrm.dto.SignupRequest;
import org.example.hrm.dto.UserDto;
import org.example.hrm.exception.ChangePasswordException;
import org.example.hrm.mapper.RoleMapper;
import org.example.hrm.mapper.UserMapper;
import org.example.hrm.model.Role;
import org.example.hrm.model.User;
import org.example.hrm.model.enumeration.RoleName;
import org.example.hrm.repository.UserRepository;
import org.example.hrm.service.RoleService;
import org.example.hrm.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final String DEFAULT_PASSWORD = "123456";
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           RoleMapper roleMapper,
                           RoleService roleService,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto create(SignupRequest signupRequest) {
        User user = new User();
        user.setFullName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        Set<Role> roles = new HashSet<>();

        if (signupRequest.getRoles() != null && !signupRequest.getRoles().isEmpty()) {
            roles.addAll(signupRequest.getRoles().stream()
                    .map(roleMapper::toEntity)
                    .collect(Collectors.toSet()));
        } else {
            Role defaultRole = roleService.findByRoleName(RoleName.ROLE_USER);
            roles.add(defaultRole);
        }
        user.setRoles(roles);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public void autoCreate(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        Set<Role> roles = new HashSet<>();
        Role defaultRole = roleService.findByRoleName(RoleName.ROLE_USER);
        roles.add(defaultRole);

        user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        user.setRoles(roles);
        userRepository.save(user);
        userMapper.toDto(user);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void changePassword(String email, ChangePasswordRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + "not found!"));

        if(!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            throw new ChangePasswordException("Old password is incorrect!");
        }

        if(!request.newPassword().equals(request.confirmPassword())) {
            throw new ChangePasswordException("Passwords do not match!");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }

    @Override
    public void update(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail()).orElse(null);
        if(user == null) {
            throw new UsernameNotFoundException(userDto.getEmail() + " not found!");
        }
        Set<Role> roles = roleService.mapToEntity(userDto.getRoles());
        user.setRoles(roles);
        userRepository.save(user);
    }
}
