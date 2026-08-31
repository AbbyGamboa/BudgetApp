package learn.BudgetApp.controller;

import learn.BudgetApp.Security.JwtService;
import learn.BudgetApp.domain.BudgetCategoryService;
import learn.BudgetApp.domain.Result;
import learn.BudgetApp.models.BudgetCategory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgetcategory")
@CrossOrigin
public class BudgetCategoryController {

    private final BudgetCategoryService service;
    private final JwtService jwtService;

    public BudgetCategoryController(BudgetCategoryService service, JwtService jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    @GetMapping("/{budgetId}")
    public ResponseEntity<?> findByBudget(@PathVariable int budgetId,Authentication authentication){
        int userId = Integer.parseInt(authentication.getName());

        Result<List<BudgetCategory>> result = service.findByBudget(budgetId, userId);
        if(!result.isSuccess()){
            return ErrorResponse.build(result);
        }
        return new ResponseEntity<>(result.getpayload(), HttpStatus.OK);
    }
}
