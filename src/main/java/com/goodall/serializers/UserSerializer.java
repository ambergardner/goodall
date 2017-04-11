package com.goodall.serializers;

import java.util.HashMap;
import java.util.Map;

import com.goodall.entities.HasId;
import com.goodall.entities.User;

public class UserSerializer extends JsonDataSerializer {

    public String getType() {
        return "users";
    }

    public Map<String, Object> getAttributes(HasId entity) {
        Map<String, Object> result = new HashMap<>();
        User user = (User) entity;

        result.put("id", user.getId());
        result.put("username", user.getUsername());
        result.put("email", user.getEmail());

        return result;
    }

    public Map<String, String> getRelationshipUrls() {
        return new HashMap<String, String>();
    }
}
