package learn.BudgetApp.controller;

import learn.BudgetApp.Security.JwtService;
import learn.BudgetApp.domain.CategoryService;
import learn.BudgetApp.domain.Result;
import learn.BudgetApp.models.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin
public class CategoryController {

    private final CategoryService service;
    private final JwtService jwtService;

    public CategoryController(CategoryService service, JwtService jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<?> findByUser(Authentication authentication){
        int userId = Integer.parseInt(authentication.getName());

        Result<List<Category>> result = service.findByUser(userId);
        if (!result.isSuccess()){
            return ErrorResponse.build(result);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> findById(@PathVariable int categoryId, Authentication authentication){
        int userId = Integer.parseInt(authentication.getName());

        Result<Category> result = service.findById(categoryId, userId);
        if (!result.isSuccess()){
            return ErrorResponse.build(result);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Category category, Authentication authentication){
        int userId = Integer.parseInt(authentication.getName());

        Result<Category> result = service.create(category, userId);
        if (!result.isSuccess()){
            return ErrorResponse.build(result);
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> delete(@PathVariable int categoryId, Authentication authentication){
        int userId = Integer.parseInt(authentication.getName());

        Result<Category> result = service.delete(categoryId, userId);
        if (!result.isSuccess()){
            return ErrorResponse.build(result);
        }

        return ResponseEntity.ok(result);
    }
}
