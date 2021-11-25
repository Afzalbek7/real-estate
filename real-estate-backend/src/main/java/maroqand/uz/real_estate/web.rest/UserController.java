package maroqand.uz.real_estate.web.rest;

import maroqand.uz.real_estate.domain.User;
import maroqand.uz.real_estate.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<User> create(User user) {
        if (!userService.checkPasswordLength(user.getPassword())){
            return new ResponseEntity("Parol uzunligi 5 ta dan kam", HttpStatus.BAD_REQUEST);
        }
        if (userService.checkCustomerName(user.getName())){
            return new ResponseEntity("Bu customer oldin ro'yaxtdan o'tgan", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(userService.save(user));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity getOne(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping("/users")
    public ResponseEntity<User> update(@RequestBody User customer) {
        return ResponseEntity.ok(userService.save(customer));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(id + "-was deleted");
    }
}
