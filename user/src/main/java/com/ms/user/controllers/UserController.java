package com.ms.user.controllers;

import com.ms.user.dtos.UserRecordDto;
import com.ms.user.models.UserModel;
import com.ms.user.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveUser(@RequestBody @Valid UserRecordDto userRecordDto) {
        try {
            var userModel = new UserModel();
            BeanUtils.copyProperties(userRecordDto, userModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

    }

    @PutMapping("/disable")
    public ResponseEntity<String> disableUser(@RequestBody @Valid UserRecordDto userRecordDto) {
        var userModel = new UserModel();
        BeanUtils.copyProperties(userRecordDto, userModel);
        userService.disableUser(userModel);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserModel>> listUser() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

}
