package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


// Spring Securityの設定を有効にします
@EnableWebSecurity
// WebSecurityConfigurerAdapterを必ず継承
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 認証・認可に関する設定を追加します
    	http.authorizeRequests()
        .antMatchers("/loginForm").permitAll() // /loginFormは、全ユーザからのアクセスを許可
        .anyRequest().authenticated(); // 許可した項目以外は、認証を求める
    	
    	 //ログイン処理
        http.formLogin()
            .loginProcessingUrl("/login") // ログイン処理のパス
            .loginPage("/loginForm") // ログインページの指定
            .usernameParameter("email") // ログインページのメールアドレス
            .passwordParameter("password") // ログインページのパスワード
            .defaultSuccessUrl("/home", true) // ログイン成功後のパス
            .failureUrl("/loginForm?error"); // ログイン失敗時のパス
        
        //ログアウト処理
        http.logout()
            .logoutUrl("/logout") //ログアウト処理のパス
            .logoutSuccessUrl("/loginForm"); //ログアウト成功後のパス
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
    @Override
    // WebSecurity型の引数を持ったconfigure()を追加します
    public void configure(WebSecurity web) throws Exception {
        /** 以下のファイルパス配下のディレクトリ、ファイルすべてを認証・認可の対象から除外します
            src/main/resources/static/css/
            src/main/resources/static/js/
            src/main/resources/static/images/
        */
        web.ignoring().antMatchers("/css/**", "/js/**", "/images/**");
    }
}