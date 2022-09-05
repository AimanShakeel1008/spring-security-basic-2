package com.aiman.springsecuritybasic2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.web.SecurityFilterChain;

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
}
