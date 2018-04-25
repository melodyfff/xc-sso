package com.xinchen.cas.auth;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apereo.cas.authentication.RememberMeUsernamePasswordCredential;
import org.apereo.cas.authentication.UsernamePasswordCredential;

import javax.persistence.Entity;
import javax.validation.constraints.Size;

/**
 * 用户名，密码，系统
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/4/22 18:46
 */
@Entity
public class UsernamePasswordSysCredential extends RememberMeUsernamePasswordCredential {
    @Size(min = 2, message = "require system")
    private String system;

    public String getSystem() {
        return system;
    }

    public UsernamePasswordSysCredential setSystem(String system) {
        this.system = system;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(this.system)
                .toHashCode();
    }
}
