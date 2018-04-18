package com.xinchen.cas.auth.handler;

import com.xinchen.cas.auth.UsernamePasswordSysCredential;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.HandlerResult;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;

import javax.security.auth.login.AccountNotFoundException;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 * 用户名系统认证，只要是admin用户加上sso系统就允许通过
 *
 * @author Carl
 * @date 2017/10/23
 * @since 1.6.0
 */
public class UsernamePasswordSystemAuthenticationHandler extends AbstractPreAndPostProcessingAuthenticationHandler {
    public UsernamePasswordSystemAuthenticationHandler(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }

    @Override
    protected HandlerResult doAuthentication(Credential credential) throws GeneralSecurityException, PreventedException {
        System.out.println("==========================================================");
        //当用户名为admin,并且system为sso即允许通过
        UsernamePasswordSysCredential sysCredential = (UsernamePasswordSysCredential) credential;
        if ("admin".equals(sysCredential.getUsername()) && "ssh".equals(sysCredential.getSystem())) {
            //这里可以自定义属性数据
            //返回多属性
//            Map<String, Object> map=new HashMap<>();
//            map.put("email", user.getEmail().toString());
//            map.put("status", user.getStatus().toString());
//            LOGGER.info(map.get("email").toString());
//            LOGGER.info("++++++++++++++++++++zjzjzjz",map);
//
//            if(PasswordUtil.decodePassword(user.getPassword(), pd, username)){
//                return createHandlerResult(transformedCredential, principalFactory.createPrincipal(username, map), null);
//            }

//            最后一个参数null实际上是一组警告，这些警告最终进入认证链并有条件地向用户显示。这种警告的例子包括接近过期日期的密码状态等。
            return createHandlerResult(credential, this.principalFactory.createPrincipal(((UsernamePasswordSysCredential) credential).getUsername(), Collections.emptyMap()), null);
        } else {
            throw new AccountNotFoundException("必须是admin用户才允许通过");
        }
    }


    @Override
    public boolean supports(Credential credential) {
        return credential instanceof UsernamePasswordSysCredential;
    }
}
