package com.challange.singledigit.service;

import com.challange.singledigit.model.User;
import com.challange.singledigit.model.dto.UserResponse;
import com.challange.singledigit.util.RSAUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CryptoService {

    private final UserService userService;

    public User encrypt(UUID userUid, String base64EncodedPublicKey) {
        var user = userService.find(userUid);

        user.setName(RSAUtil.encrypt(user.getName(), base64EncodedPublicKey));
        user.setEmail(RSAUtil.encrypt(user.getEmail(), base64EncodedPublicKey));

        return userService.update(user);
    }

    public User decrypt(UUID userUid, String base64EncodedPrivateKey){
        var user = userService.find(userUid);

        user.setName(RSAUtil.decrypt(user.getName(), base64EncodedPrivateKey));
        user.setEmail(RSAUtil.decrypt(user.getEmail(), base64EncodedPrivateKey));

        return userService.update(user);
    }

}
