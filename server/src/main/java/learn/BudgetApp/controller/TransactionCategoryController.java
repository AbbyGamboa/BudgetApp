package learn.BudgetApp.controller;

import learn.BudgetApp.domain.Result;
import learn.BudgetApp.domain.TransactionCategoryService;
import learn.BudgetApp.models.TransactionCategory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactioncategory")
@CrossOrigin
public class TransactionCategoryController {

    private final TransactionCategoryService service;

    public TransactionCategoryController(TransactionCategoryService service) {
        this.service = service;
    }

    @GetMapping("/{budgetId}")
    public ResponseEntity<?> findByBudget(@PathVariable int budgetId, Authentication authentication){
        int userId = Integer.parseInt(authentication.getName());

        Result<List<TransactionCategory>> result = service.findByBudget(budgetId, userId);

        if (!result.isSuccess()){
            return ErrorResponse.build(result);
        }

        return new ResponseEntity<>(result.getpayload(), HttpStatus.OK);
    }

    @GetMapping("get/{transactionId}")
    public ResponseEntity<?> findByTransaction(@PathVariable int transactionId, Authentication authentication){
        int userId = Integer.parseInt(authentication.getName());

        Result<TransactionCategory> result = service.findByTransaction(transactionId, userId);

        if (!result.isSuccess()){
            return ErrorResponse.build(result);
        }

        return new ResponseEntity<>(result.getpayload(), HttpStatus.OK);
    }

}
