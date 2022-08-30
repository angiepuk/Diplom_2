package POJO;

import jdk.jfr.ContentType;
import org.apache.commons.lang3.RandomStringUtils;

public class CreationUserPojo {
    private String name;
    private String email;
    private String password;

    public CreationUserPojo(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static CreationUserPojo getRandomCredentials(){
        String email = RandomStringUtils.random(5, true, true) + "@yandex.ru";
        String password = RandomStringUtils.random(5, true, true);
        String name= RandomStringUtils.random(8, true, false);
        return new CreationUserPojo(email, password, name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
