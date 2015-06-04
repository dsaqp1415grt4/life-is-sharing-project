package edu.upc.eetac.dsa.iarroyo.lifeissharing.api;

/**
 * Created by nacho on 30/05/15.
 */
public class AppException extends Exception{

    public AppException() {
        super();
    }

    public AppException(String detailMessage) {
        super(detailMessage);
    }

}
