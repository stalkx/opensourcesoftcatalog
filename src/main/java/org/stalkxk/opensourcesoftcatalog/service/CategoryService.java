package org.stalkxk.opensourcesoftcatalog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.stalkxk.opensourcesoftcatalog.entity.Category;
import org.stalkxk.opensourcesoftcatalog.repository.CategoryRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category saveCategory(Category category){
        return categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public Optional<Category> findById(Integer categoryId){
        return categoryRepository.findById(categoryId);
    }

    @Transactional(readOnly = true)
    public Page<Category> findAllCategory(Pageable pageable){
        return categoryRepository.findAll(pageable);
    }

    public Category updateCategory(Category updatedCategory){
        Category category = findById(updatedCategory.getCategoryId()).orElseThrow();
        category.setCategoryId(updatedCategory.getCategoryId());
        category.setCategoryName(updatedCategory.getCategoryName());
        category.setProgramList(updatedCategory.getProgramList());
        return categoryRepository.save(category);
    }

    public void removeCategory(Category category){
        categoryRepository.delete(category);
    }
}
