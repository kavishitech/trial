package dao;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import model.User;


public interface UserDAO {

    CompletionStage<User> create(User user);
    List<User> getUsers(String userName) throws InterruptedException, ExecutionException;


}
