package com.example.spendnee.repository;

import com.example.spendnee.api.API;
import com.example.spendnee.model.BaseResponse;
import com.example.spendnee.model.User;
import com.example.spendnee.service.UserService;

import retrofit2.Call;

public class UserRepository {

    private UserService userService;

    public UserRepository() {
        this.userService = getUserService();
    }

    public Call<BaseResponse> login(User user){
        return userService.login(user.getUsername(), user.getPassword());
    }

    public Call<BaseResponse> signup(User user){
        return userService.signup(user.getUsername(), user.getName(), user.getPassword(), user.getEmail(), user.getImage());
    }
    public Call<BaseResponse> checkEmail(String email){
        return userService.checkEmail(email);
    }

    public Call<BaseResponse> checkUsername(String username){
        return userService.checkUsername(username);
    }

    public UserService getUserService() {
        return API.getAccount().create(UserService.class);
    }

    public Call<BaseResponse> updatePassword(User user){
        return userService.updatePassword(user.getUsername(), user.getPassword());
    }
}
