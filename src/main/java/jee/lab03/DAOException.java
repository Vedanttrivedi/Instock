package jee.lab03;

import java.sql.SQLException;

public class DAOException extends SQLException{
    DAOException(String msg){
        super(msg);
    }
}
