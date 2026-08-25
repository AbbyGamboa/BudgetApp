package learn.BudgetApp.controller;

import learn.BudgetApp.Security.JwtService;
import learn.BudgetApp.domain.PlaidItemService;
import learn.BudgetApp.domain.Result;
import learn.BudgetApp.domain.UserService;
import learn.BudgetApp.models.PlaidItems;
import learn.BudgetApp.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/connect/bank")
@CrossOrigin
public class PlaidItemController {

    private final PlaidItemService service;
    private final UserService userService;
    private final JwtService jwtService;

    public PlaidItemController(PlaidItemService service, UserService userService, JwtService jwtService) {
        this.service = service;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PlaidItems plaidItems, Authentication authentication) {
        int userId = Integer.parseInt(authentication.getName());

        User user = userService.findByUserId(userId);
        plaidItems.setUser(user);

        Result<PlaidItems> result = service.create(plaidItems);

        if (!result.isSuccess()) {
            return ErrorResponse.build(result);
        }
        return new ResponseEntity<>(result.getpayload(), HttpStatus.CREATED);
    }

}
