package rui.org.advaitademo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rui.org.advaitademo.model.Transaction;

/**
 * Bank transactions repository
 *
 * @author Rui Zhu
 *
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
