package be.pielambr.minerva4j.exceptions;

/**
 * Created by Pieterjan Lambrecht on 16/06/2015.
 */
public class LoginFailedException extends Exception {

    public LoginFailedException() {
        super("Your login failed, please try again");
    }

}
