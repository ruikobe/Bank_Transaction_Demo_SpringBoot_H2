package rui.org.advaitademo.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Transaction Test
 *
 * @author Rui Zhu
 *
 */
public class TransactionTest {

    @Test
    public void should_getAmount(){
        // Given
        final Transaction transaction = new Transaction("2021-08-15 18:10:26", 10.00);

        // When
        final double amount = transaction.getAmount();

        // Then
        assertThat(amount, is(10.0));
    }

    @Test
    public void should_return_to_string(){
        // Given
        final Transaction transaction = new Transaction("2021-08-15 18:10:26", 10.00);

        // When
        final String toString = transaction.toString();

        // Then
        assertThat(toString, is("Transaction{time='2021-08-15 18:10:26', amount=10.0}"));
    }
}
