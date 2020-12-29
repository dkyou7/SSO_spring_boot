package com.ktnet.auth_server.admin.manage_client;

import com.ktnet.auth_server.client.Client;
import com.ktnet.auth_server.client.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(Client client){
        String encodedSecret = passwordEncoder.encode(client.getClient_secret());
        client.updateSecret(encodedSecret);
        clientRepository.save(client);
    }
}
