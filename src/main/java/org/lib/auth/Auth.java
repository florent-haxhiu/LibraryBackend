package org.lib.auth;

import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;

public class Auth {

    private String hash;
    public Map<Integer, String> tokens = new HashMap<>();

    // TODO: Need to put ts in a DB tbh
    // TODO: That's an issue for later - for now get the main logic working then I can work on that later once I set it up tomorrow
    public String register(String username, String plain_password) {
        String pw_hash = BCrypt.hashpw(plain_password, BCrypt.gensalt());
        this.hash = pw_hash;
        return pw_hash;
    }

    // TODO: Will need to generate an opaque token that hasn't already been generated before
    // TODO: For now I can do that with a Map to keep count of what has and hasn't but it'll always be a clean run for now
    // TODO: Going to have to keep a count of the IDs given out via SQL query or put it in the cache too
    public Integer login(String username, String candidate_password) {
        if (BCrypt.checkpw(candidate_password, this.hash)) {
            this.tokens.put(1, username);
            return 1;
        }
        else {
            return 0;
        }
    }

    public String getProfile(Integer UserId) {
        return this.tokens.getOrDefault(UserId, "User doesn't exist with that token");
    }

    public String logout(Integer UserId) {
        return this.tokens.remove(UserId);
    }
}