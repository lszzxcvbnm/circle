package com.circlett.demo.conf;


import com.circlett.demo.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity//安全策略配置
@EnableGlobalMethodSecurity(prePostEnabled = true )//开启权限注解
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	LoginFailureHandler loginFailureHandler;

	@Autowired
	LoginSuccessHandler loginSuccessHandler;



	@Autowired
	JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@Autowired
	UserDetailServiceImpl userDetailService;

	@Autowired
	JwtLogoutSuccessHandler jwtLogoutSuccessHandler;
   //注入jwt过滤器
	@Bean
	JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
		return jwtAuthenticationFilter;
	}
    //加入密码，
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {

		return new BCryptPasswordEncoder();
	}

	private static final String[] URL_WHITELIST = {

			"/login",
			"/api/user/register",
			"/logout",
			"test/pass",
			"/favicon.ico",//图像
			"/static/**",


	};

   //登录页
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable()

				// 登录配置
				.formLogin()
				.successHandler(loginSuccessHandler)
				.failureHandler(loginFailureHandler)

				.and()
				.logout()
				.logoutSuccessHandler(jwtLogoutSuccessHandler)

				// 禁用session
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				// 配置拦截规则
				.and()
				.authorizeRequests()
				.antMatchers(URL_WHITELIST).permitAll()//白名单跳过不用拦截
				.anyRequest().authenticated()//对登录才能访问

				// 异常处理器
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)//处理器
				.accessDeniedHandler(jwtAccessDeniedHandler)

				// 配置自定义的过滤器
				.and()
				.addFilter(jwtAuthenticationFilter())
//				.addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)

		;



	}



	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService);
	}
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
