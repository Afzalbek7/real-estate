package maroqand.uz.real_estate.service;

import maroqand.uz.real_estate.domain.User;
import maroqand.uz.real_estate.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(User user) {
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
