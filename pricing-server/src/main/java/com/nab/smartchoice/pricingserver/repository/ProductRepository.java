package com.nab.smartchoice.pricingserver.repository;

import com.nab.smartchoice.pricingserver.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByNameLike(String name);

    List<Product> findAllByIdIn(List<Long> ids);
}
