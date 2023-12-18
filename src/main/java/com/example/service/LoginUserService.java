package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.entity.User;
import com.example.repository.UserRepository;

public class LoginUserService implements UserDetailsService{

	private final UserRepository userRepository;
	
	@Autowired
	public LoginUserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public LoginUser loadUserByUsername(String email) throws UsernameNotFoundException{
		// emailによりデータベースからユーザ情報の取得
		User user = this.userRepository.findByEmail(email);
		
		//ユーザー情報が見つからない場合、例外
		if (user == null) {
			throw new UsernameNotFoundException("ユーザーが見つかりません");
		}
		
		//ユーザ情報が見つかった場合、UserDetailsを生成し返却
		return new LoginUser(user);
	}
}
