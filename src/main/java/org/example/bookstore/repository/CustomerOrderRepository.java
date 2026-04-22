package org.example.bookstore.repository;

import org.example.bookstore.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    @Query("""
        select distinct co 
        from CustomerOrder co
        left join fetch co.customer
        left join fetch co.orderItems oi
        left join fetch oi.book
        order by co.id
    """)
    List<CustomerOrder> findAllWithOrderItems();
}
