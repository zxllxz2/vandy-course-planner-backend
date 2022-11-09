package edu.vanderbilt.vandycourseplanner.exception;

import edu.vanderbilt.vandycourseplanner.pojo.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Global SQL exception capture
 */
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(SQLException.class)
    public RespBean mySqlException(SQLException e) {
        if (e instanceof SQLIntegrityConstraintViolationException) {
            return RespBean.error("The data has other connections. Fail to operate");
        }
        return RespBean.error("Error in the database. Fail to operate");
    }
}
