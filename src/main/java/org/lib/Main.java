package org.lib;

import org.lib.auth.Auth;

public class Main {
    public static void main(String[] args) {
        Auth auth = new Auth();
        String username = "Florent";
        String password = "Hash";
        String hash = auth.register(username, password);
        System.out.printf("Cheeky Hash: %s\n", hash);
        Integer token = auth.login(username, password);
        String fetchedUsername = auth.getProfile(token);
        System.out.printf("Did it work: %s\n", fetchedUsername);
        String logout = auth.logout(token);
        System.out.printf("logout %s\n", logout);
        String fetchedUsername1 = auth.getProfile(token);
        System.out.printf("This should fail %s\n", fetchedUsername1);
    }
}