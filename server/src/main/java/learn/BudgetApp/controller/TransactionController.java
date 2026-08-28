package learn.BudgetApp.controller;

import learn.BudgetApp.domain.Result;
import learn.BudgetApp.domain.TransactionService;
import learn.BudgetApp.models.Account;
import learn.BudgetApp.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@CrossOrigin
public class TransactionController {

    private final TransactionService service;


    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<?> findById(@PathVariable int transactionId, Authentication authentication){
        int userid = Integer.parseInt(authentication.getName());

        Result<Transaction> result = service.findById(transactionId, userid);
        if (!result.isSuccess()){
            return ErrorResponse.build(result);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<?> findByAccount(@PathVariable int accountId, Authentication authentication){
        int userid = Integer.parseInt(authentication.getName());

        Result<List<Transaction>> result = service.findByAccount(accountId, userid);
        if (!result.isSuccess()){
            return ErrorResponse.build(result);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/date/{accountId}")
    public ResponseEntity<?> findByDate(@PathVariable int accountId, @RequestParam LocalDate start, @RequestParam LocalDate end,Authentication authentication){
        int userid = Integer.parseInt(authentication.getName());

        Result<List<Transaction>> result = service.findByDate(accountId,start,end,userid);
        if (!result.isSuccess()){
            return ErrorResponse.build(result);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/{accountId}")
    public ResponseEntity<?> create(@PathVariable int accountId, @RequestBody Transaction transaction, Authentication authentication){
        int userId = Integer.parseInt(authentication.getName());
        Result<Transaction> result = service.create(transaction, accountId, userId);
        if (!result.isSuccess()){
            return ErrorResponse.build(result);
        }

        return new ResponseEntity<>(result.getpayload(), HttpStatus.CREATED);
    }
 }
