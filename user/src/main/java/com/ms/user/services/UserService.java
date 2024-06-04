package com.ms.user.services;

import com.ms.user.models.UserModel;
import com.ms.user.producers.UserProducer;
import com.ms.user.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    final UserRepository userRepository;
    final UserProducer userProducer;

    public UserService(UserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }

    @Transactional
    public UserModel save(UserModel userModel){
        Optional<UserModel> user = userRepository.findByNameAndEmail(userModel.getName(), userModel.getEmail());
        if(user.isPresent())
            throw new IllegalArgumentException("Já existe um usuário com este email e senha.");

        System.out.println("email antes do save: "+ userModel.getEmail());
        userRepository.save(userModel);
        System.out.println("email dps do save: "+ userModel.getEmail());

        String subject = "Cadastro realizado com sucesso!";
        String text = userModel.getName() + ", seja bem vindo(a)! \nAgradecemos o seu cadastro, aproveite agora todos os recursos da nossa plataforma!";
        userProducer.publishMessageNotification(userModel, subject, text);
        return userModel;
    }

    @Transactional
    public void disableUser(UserModel userModel){
        userRepository.disableUser(userModel.getEmail(), userModel.getName());

        String subject = "Cadastro desativado com sucesso!";
        String text = userModel.getName() + ", \nUma pena que você desativou sua conta, caso queira reativar, contate o suporte ao cliente!";
        userProducer.publishMessageNotification(userModel, subject, text);
    }

    public List<UserModel> getAllUsers(){
        return userRepository.findAll();
    }


}
