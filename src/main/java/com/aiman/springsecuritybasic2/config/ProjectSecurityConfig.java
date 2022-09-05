package com.aiman.springsecuritybasic2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import javax.sql.DataSource;


@Configuration
public class ProjectSecurityConfig {

    /**
     * From Spring Security 5.7, the WebSecurityConfigurerAdapter is deprecated to encourage users
     * to move towards a component-based security configuration. It is recommended to create a bean
     * of type SecurityFilterChain for security related configurations.
     * @param http
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{

        /**
         * Default configurations which will secure all the requests
         */
//		((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)http.authorizeRequests().anyRequest()).
//				authenticated();
//		http.formLogin();
//		http.httpBasic();
//		return (SecurityFilterChain)http.build();


		/**
		 * Custom configurations as per our requirement
		 * /myAccount Secured
		 * /myBalance Secured
		 * /myCards secured
		 * /myLoans secured
		 * /contact not secured
		 * /notices not secured
		 */

		http.authorizeRequests((auth)->{
			auth
					.antMatchers("/myAccount","/myBalance","/myCards","/myLoans").authenticated()
					.antMatchers("/contact","/notices").permitAll();
		}).httpBasic()
				.and()
				.formLogin();

		return http.build();


		/**
		 * Configuration to deny all the requests
		 */
		/*http.authorizeHttpRequests( (auth)->auth
				.anyRequest().denyAll())
				.httpBasic(Customizer.withDefaults());
		return http.build();*/

		/**
		 * Configuration to permit all the requests
		 */
		/*http.authorizeHttpRequests( (auth)->auth
						.anyRequest().permitAll())
				.httpBasic(Customizer.withDefaults());
		return http.build();*/
    }

	//@Bean
//	InMemoryUserDetailsManager userDetailsService(){
//
//     //Approach 1 where we use withDefaultPasswordEncoder() method while creating the user details
//
////		UserDetails admin=User.withDefaultPasswordEncoder()
////				.username("admin")
////				.password("1234567")
////				.authorities("admin")
////				.build();
////
////		UserDetails user= User.withDefaultPasswordEncoder()
////				.username("user")
////				.password("123456")
////				.authorities("read")
////				.build();
////
////		return new InMemoryUserDetailsManager(admin,user);
//
//		 /*Approach 2 where we don't define password encoder while creating the user details.
//		 Instead a separate PasswordEncoder bean will be created.
//		  */
//
//		InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
//		UserDetails admin = User.withUsername("admin").password("12345").authorities("admin").build();
//		UserDetails user = User.withUsername("user").password("12345").authorities("read").build();
//		userDetailsService.createUser(admin);
//		userDetailsService.createUser(user);
//		return userDetailsService;
//	}

	/**
	 * NoOpPasswordEncoder is not recommended for production usage.
	 * Use only for non-prod.
	 *
	 * @return
	 */

	@Bean
	UserDetailsService userDetailsService(DataSource dataSource){
		return new JdbcUserDetailsManager(dataSource);
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
