package learn.BudgetApp.controller;

import learn.BudgetApp.Security.JwtService;
import learn.BudgetApp.domain.AccountService;
import learn.BudgetApp.domain.Result;
import learn.BudgetApp.domain.UserService;
import learn.BudgetApp.models.Account;
import learn.BudgetApp.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
@CrossOrigin
public class AccountController {

    private final AccountService service;
    private final JwtService jwtService;


    public AccountController(AccountService service, JwtService jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<?> findById(@PathVariable("accountId") int accountId, Authentication authentication){
        int userId = Integer.parseInt(authentication.getName());
        Result<Account> result = service.findById(accountId);
        if (!result.isSuccess()){
            return ErrorResponse.build(result);
        }

        if(result.getpayload().getUser().getUserId() != userId){
            return new ResponseEntity<>(result.getpayload(), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(result.getpayload(), HttpStatus.OK);
    }

    @GetMapping("/myAccounts")
    public ResponseEntity<?> findByUserId(Authentication authentication){
        int userId = Integer.parseInt(authentication.getName());
        Result<List<Account>> result = service.findByUser(userId);
        if (!result.isSuccess()){
            return ErrorResponse.build(result);
        }
        return new ResponseEntity<>(result.getpayload(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Account account, Authentication authentication){
        int userId = Integer.parseInt(authentication.getName());
        Result<Account> result = service.create(account, userId);
        if (!result.isSuccess()){
            return ErrorResponse.build(result);
        }

        return new ResponseEntity<>(result.getpayload(), HttpStatus.CREATED);
    }

    @PutMapping("/edit/{accountId}")
    public ResponseEntity<?> update(@PathVariable int accountId, @RequestBody Account account, Authentication authentication){
        int userId = Integer.parseInt(authentication.getName());

        Account existingAccount = service.findById(accountId).getpayload();
        if (existingAccount != null && existingAccount.getUser().getUserId() != userId){
            return new ResponseEntity<>(List.of("Cannot update an account you do not own"), HttpStatus.FORBIDDEN);
        }

        if(accountId != account.getAccountId()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Account> result = service.update(account, userId);
        if (!result.isSuccess()){
            return ErrorResponse.build(result);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
