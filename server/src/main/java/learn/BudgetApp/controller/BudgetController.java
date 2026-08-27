package learn.BudgetApp.controller;

import learn.BudgetApp.Security.JwtService;
import learn.BudgetApp.domain.BudgetService;
import learn.BudgetApp.domain.Result;
import learn.BudgetApp.models.Account;
import learn.BudgetApp.models.Budget;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budget")
@CrossOrigin
public class BudgetController {

    private final BudgetService service;
    private final JwtService jwtService;

    public BudgetController(BudgetService service, JwtService jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    @GetMapping("/myBudgets")
    public ResponseEntity<?> findByUserId(Authentication authentication){
        int userId = Integer.parseInt(authentication.getName());

        List<Budget> result = service.findByUserId(userId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{budgetId}")
    public ResponseEntity<?> findById(@PathVariable int budgetId,Authentication authentication){
        int userId = Integer.parseInt(authentication.getName());

        Result<Budget> result = service.findById(budgetId, userId);
        if(!result.isSuccess()){
            return ErrorResponse.build(result);
        }
        return new ResponseEntity<>(result.getpayload(), HttpStatus.OK);
    }

    @PutMapping("/edit/{budgetId}")
    public ResponseEntity<?> update(@PathVariable int budgetId, @RequestBody Budget budget, Authentication authentication){
        budget.setBudgetId(budgetId);
        int userId = Integer.parseInt(authentication.getName());

        Result<Budget> result = service.update(budget, userId);
        if(!result.isSuccess()){
            return ErrorResponse.build(result);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
