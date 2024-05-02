package com.azureapplicationinsights.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Value("${azure-ad-logoutURl}")
    private  String azureAdLogoutURL;

//    @Autowired
//    ClientRegistrationRepository clientRegistrationRepository;
//
//    OidcClientInitiatedLogoutSuccessHandler oidcClientInitiatedLogoutSuccessHandler()
//    {
//        OidcClientInitiatedLogoutSuccessHandler oidcClientInitiatedLogoutSuccessHandler=
//                new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
//        oidcClientInitiatedLogoutSuccessHandler.setPostLogoutRedirectUri("http://localhost:8080/home");
//        return oidcClientInitiatedLogoutSuccessHandler;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/signin-oidc/**","/oauth2/**", "/login/**")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .oauth2Login()
//               .defaultSuccessUrl("/home")
//                .and()
//                .logout()
//                . logoutRequestMatcher(new AntPathRequestMatcher("/logout/**"))
//                .logoutSuccessUrl("https://login.microsoftonline.com/c752025a-2006-4f92-babc-8cf4cbf70f6e/oauth2/logout");






        http
                .authorizeRequests()
                .antMatchers("/oauth2/**", "/login/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                . logoutRequestMatcher(new AntPathRequestMatcher("/logout/**"))
                //   .logoutUrl(azureAdLogoutURL)
                .logoutSuccessUrl(azureAdLogoutURL)
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout/**"))
//                .deleteCookies("JSESSIONID")
//                .invalidateHttpSession(true)
//                .logoutSuccessHandler(oidcClientInitiatedLogoutSuccessHandler())
                .and()
                .oauth2Login()
                .and().csrf().disable();

        // http://localhost:8080/logout/oauth2/code/ hit this url to logout from azure AD

        //.logout()
        //.logoutRequestMatcher(new AntPathRequestMatcher("/app/logout"))
        //.deleteCookies("JSESSIONID")
        //.invalidateHttpSession(true)
    }


}


// https://login.microsoftonline.com/c752025a-2006-4f92-babc-8cf4cbf70f6e/oauth2/v2.0/authorize?response_type=code&client_id=ef911886-d26f-407f-aeee-bcc7e76e0776&scope=openid&state=83K9bv8g7-H-W6vYMioIYqI5O_nCpW4PLu1dxHqDbZM%3D&redirect_uri=http://localhost:8080/signin-oidc&nonce=zZYSDqEcumO9NjzNWQXDuezr711j4tGlDMILIt6dCZ4


// register app in azure AD
// go to authorization then web then give redirect url and logout url

