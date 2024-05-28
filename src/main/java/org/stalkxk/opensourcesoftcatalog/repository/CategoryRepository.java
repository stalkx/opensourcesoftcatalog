package org.stalkxk.opensourcesoftcatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.stalkxk.opensourcesoftcatalog.entity.Category;
import org.stalkxk.opensourcesoftcatalog.entity.Comment;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
