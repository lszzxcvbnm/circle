package com.circlett.demo.security;
import com.circlett.demo.model.auto.User;
import com.circlett.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	IUserService iUserService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User sysUser = iUserService.loginQuery(username);
		if (sysUser == null) {
			throw new UsernameNotFoundException("用户名不存在,请注册");
		}
		return new AccountUser(sysUser.getUserId(), sysUser.getUserName(), sysUser.getPassWord(), getUserAuthority(sysUser.getIsAdmin()));
	}

	/**
	 * 获取用户权限信息（角色)
	 * @param
	 * @return
	 */
	public List<GrantedAuthority> getUserAuthority(String Username){

		return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_"+Username);
	}
}
