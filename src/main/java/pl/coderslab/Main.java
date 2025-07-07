package pl.coderslab;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

public class Main {
    public static void main(String[] args) {

        // create
        User user = new User();
        user.setUserName("Jasio");
        user.setEmail("jasio@google.com");
        user.setPassword("Jasio-java-coder");

        UserDao userDao = new UserDao();

//        userDao.create(user);

        user.setUserName("Anastazja");
        user.setEmail("anastazja@visa.com");
        user.setPassword("Anastazja-java-coder");

//        userDao.create(user);

        // read
        User userFromDB = new User();
        userFromDB = userDao.read(7);
        System.out.println(userFromDB.getId() + ". " + userFromDB.getUserName() + " " + userFromDB.getEmail());

        // update
        userFromDB.setUserName("Grazyna");
        userDao.update(userFromDB);
        System.out.println("After update " + userFromDB.getId() + ". " + userFromDB.getUserName() + " " + userFromDB.getEmail());

        // delete
        userDao.delete(userFromDB.getId());
        System.out.println("User " + userFromDB.getUserName() + " deleted");

        // find all
       User[] all = userDao.findAll();
       for (User u : all) {
           System.out.println(u.getId() + " " + u.getUserName() + " " + u.getEmail());
       }
    }
}