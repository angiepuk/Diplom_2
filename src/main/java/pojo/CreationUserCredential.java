package pojo;

import org.apache.commons.lang3.RandomStringUtils;

public class CreationUserCredential {
    private String name;
    private String email;
    private String password;

    public CreationUserCredential(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public CreationUserCredential() {
    }

    public static String creationName(){
        return RandomStringUtils.random(8, true, false);
    }

    public static String creationPassword(){
        return RandomStringUtils.random(8, true, false);
    }

    public static String creationEmail(){
        return RandomStringUtils.random(8, true, false)+ "@yandex.ru";
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
