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

    @Nested
    class create{
        @Test
        void success(){
            Category created = TestDataHelper.createCategory();

            when(repository.create(created)).thenReturn(created);

            Result<Category> expected = new Result<>();
            expected.setpayload(created);

            Result<Category> actual = service.create(created, 1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenCategoryIsNull(){
            Category created = null;
            Result<Category> expected = new Result<>();
            expected.addErrorMessage("Cannot add category", ResultType.NOT_FOUND);

            Result<Category> actual = service.create(created, 1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenCategoryIsMissingInformation(){
            Category createdNoName = new Category(4, null, TestDataHelper.existingUser());
            Result<Category> expectedNoName = new Result<>();
            expectedNoName.addErrorMessage("Name is required", ResultType.INVALID);

            Result<Category> actual = service.create(createdNoName, 1);

            assertEquals(expectedNoName, actual);

            Category createdNoUser = new Category(4, "TEST", null);
            Result<Category> expectedNoUser = new Result<>();
            expectedNoUser.addErrorMessage("User is required to add category", ResultType.INVALID);

            actual = service.create(createdNoUser, 1);

            assertEquals(expectedNoUser, actual);

        }

        @Test
        void failsWhenCategoryDoesNotBelongToUser(){
            Category created = TestDataHelper.createCategory();
            Result<Category> expected = new Result<>();
            expected.addErrorMessage("Cannot add category for another user", ResultType.INVALID);

            Result<Category> actual = service.create(created, 2);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenRepoFails(){
            Category created = TestDataHelper.createCategory();
            Result<Category> expected = new Result<>();
            expected.addErrorMessage("Cannot add category", ResultType.INVALID);

            when(repository.create(created)).thenReturn(null);

            Result<Category> actual = service.create(created, 1);

            assertEquals(expected, actual);
        }
    }


}