package learn.BudgetApp.domain;

import learn.BudgetApp.data.CategoryRepository;
import learn.BudgetApp.data.TestDataHelper;
import learn.BudgetApp.data.UserRepository;
import learn.BudgetApp.models.Category;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    CategoryService service;

    @MockBean
    CategoryRepository repository;

    @MockBean
    UserRepository userRepository;

    @Nested
    class findByUser{
        @Test
        void success(){
            when(userRepository.findById(1)).thenReturn(TestDataHelper.existingUser());
            when(repository.findAllCategoriesForUser(1)).thenReturn(TestDataHelper.categoriesForUserOne());


            Result<List<Category>> expected = new Result<>();
            expected.setpayload(TestDataHelper.categoriesForUserOne());
            Result<List<Category>> actual = service.findByUser(1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenUserNotFound(){
            when(userRepository.findById(99)).thenReturn(null);

            Result<List<Category>> expected = new Result<>();
            expected.addErrorMessage("User not found", ResultType.NOT_FOUND);
            Result<List<Category>> actual = service.findByUser(99);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenCategoryIsEmpty(){
            when(userRepository.findById(1)).thenReturn(TestDataHelper.existingUser());
            when(repository.findAllCategoriesForUser(1)).thenReturn(List.of());


            Result<List<Category>> expected = new Result<>();
            expected.addErrorMessage("User has no categories", ResultType.NOT_FOUND);
            Result<List<Category>> actual = service.findByUser(1);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class findById{
        @Test
        void success(){
            when(repository.findById(1)).thenReturn(TestDataHelper.firstCategory());

            Result<Category> expected = new Result<>();
            expected.setpayload(TestDataHelper.firstCategory());
            Result<Category> actual = service.findById(1, 1);

            assertEquals(expected,actual);
        }

        @Test
        void failsWhenCustomCategoryDoesNotBelongToUser(){
            when(repository.findById(1)).thenReturn(TestDataHelper.customCategory());

            Result<Category> expected = new Result<>();
            expected.addErrorMessage("Cannot access other user's categories", ResultType.INVALID);
            Result<Category> actual = service.findById(1, 2);

            assertEquals(expected,actual);
        }

        @Test
        void failsWhenCategoryNotFound(){
            when(repository.findById(1)).thenReturn(null);

            Result<Category> expected = new Result<>();
            expected.addErrorMessage("Category not found", ResultType.NOT_FOUND);
            Result<Category> actual = service.findById(1, 1);

            assertEquals(expected,actual);
        }
    }


}