package com.ktnet.auth_server.client;

import com.ktnet.auth_server.user.User;
import com.ktnet.auth_server.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final UserRepository userRepository;

    private final ClientRepository clientRepository;

    @Transactional
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void createDummy() {
        User findUser1 = userRepository.findByEmail("test1");
        Client client = Client.builder().resourceId("testRes1").redirectUrl("http://localhost:8181/success").user(findUser1).build();
        clientRepository.save(client);

        Client client2 = Client.builder().resourceId("testRes2").redirectUrl("http://localhost:8183/success").user(findUser1).build();
        clientRepository.save(client2);
    }
}
