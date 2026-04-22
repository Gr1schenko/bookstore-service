package org.example.bookstore.repository;

import org.example.bookstore.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("""
        select distinct c 
        from Customer c
        left join fetch c.customerOrders
        order by c.id
    """)
    List<Customer> findAllWithOrders();
}
