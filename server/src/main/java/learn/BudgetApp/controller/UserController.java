package learn.BudgetApp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import learn.BudgetApp.Security.JwtService;
import learn.BudgetApp.data.DataAccessException;
import learn.BudgetApp.domain.Result;
import learn.BudgetApp.domain.ResultType;
import learn.BudgetApp.domain.UserService;
import learn.BudgetApp.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    private final UserService service;
    private final JwtService jwtService;

    public UserController(UserService service, JwtService jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) throws DataAccessException, JsonProcessingException {
        Result<User> result = service.authenticate(user.getEmail(), user.getPassword());

        ResultType outcome = result.getResultType();
        if (outcome == ResultType.INVALID){
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.UNAUTHORIZED);
        } else if (outcome == ResultType.NOT_FOUND){
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.NOT_FOUND);
        }

        String token = jwtService.generateToken(result.getpayload());
        return ResponseEntity.ok(Map.of("token", token));

    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user) {
        Result<User> result = service.create(user);

        if (!result.isSuccess()) {
            return ErrorResponse.build(result);
        }
        return new ResponseEntity<>(result.getpayload(), HttpStatus.CREATED);
    }
}
