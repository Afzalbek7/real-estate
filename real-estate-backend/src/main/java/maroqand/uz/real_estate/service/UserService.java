package maroqand.uz.real_estate.service;

import maroqand.uz.real_estate.domain.Role;
import maroqand.uz.real_estate.domain.User;
import maroqand.uz.real_estate.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(User user) {
        Role role = new Role();
        role.setName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getById(Long id) {
        Optional<User> customer = userRepository.findById(id);
        return customer.orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Boolean checkPasswordLength(String password) {
        return password.length() >= 5;
    }

    public Boolean checkCustomerName(String name) {
        return userRepository.existsByName(name);
    }
}
