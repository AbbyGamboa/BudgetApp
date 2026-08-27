package learn.BudgetApp.controller;

import learn.BudgetApp.domain.Result;
import learn.BudgetApp.domain.TransactionService;
import learn.BudgetApp.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
}
