package rui.org.advaitademo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rui.org.advaitademo.model.Stats;
import rui.org.advaitademo.model.Transaction;
import rui.org.advaitademo.repository.TransactionRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Bank transactions controller
 *
 * @author Rui Zhu
 *
 */
@RestController
@RequestMapping("/api")
@Scope("prototype")
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;

    @PostMapping("/transactions")
    public ResponseEntity<Transaction> saveTransaction(@RequestBody Transaction transaction){
        try {
            Transaction _transaction;

            //Check if the input if valid
            if(checkTimeInputValid(transaction.getTime())) {
               _transaction  = transactionRepository.save(new Transaction(transaction.getTime(), transaction.getAmount()));
            }
            else
                //Return 400, if the passed JSON is invalid
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

            if(checkTimeInterval(transaction.getTime()))
                //Return 201 in case of success
                return new ResponseEntity<>(_transaction, HttpStatus.CREATED);
            else
                //Return 204 if the time passed is older than 1 minute
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e){
            //Return 400, if the passed JSON is invalid
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/transactions")
    public ResponseEntity<Stats> getAllTransaction(){
        try{
            Stats stats = new Stats(0.0, 0.0, 0.0, 0.0, 0);

            List<Transaction> transactions = new ArrayList<>();

            transactionRepository.findAll().forEach(transactions::add);

            if(transactions.isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            for(Transaction transaction: transactions){
                if(checkTimeInterval(transaction.getTime())) {
                    stats.setMin(transaction.getAmount());
                    break;
                }
            }

            for(Transaction transaction: transactions){
                if(checkTimeInterval(transaction.getTime())) {
                    stats.setCount(stats.getCount() + 1);
                    stats.setSum(Math.round((stats.getSum() + transaction.getAmount())*100)/100.0);
                    stats.setAvg(Math.round((stats.getSum() / stats.getCount())*100)/100.0);
                    stats.setMax(Math.max(stats.getMax(), transaction.getAmount()));
                    stats.setMin(Math.min(stats.getMin(), transaction.getAmount()));
                }
            }

            return new ResponseEntity<>(stats, HttpStatus.OK);
        } catch (Exception e){
            return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/transactions")
    public ResponseEntity<HttpStatus> deleteAllTransactions(){
        try {
            //Clear all the transaction and return 204 status code
            transactionRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Compare the input time to the current time
    private boolean checkTimeInterval (String time) throws ParseException {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date input = dfs.parse(time);

        Date current = new Date();

        long secs = (current.getTime() - input.getTime())/1000;

        // time passed in 60 secs
        if(secs <= 60 && secs > 0)
            return true;
        else
            return false;
    }

    private boolean checkTimeInputValid(String time) throws ParseException {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date input = dfs.parse(time);

        Date current = new Date();

        long secs = (current.getTime() - input.getTime())/1000;

        if(secs > 0)
            return true;
        else
            return false;
    }
}
