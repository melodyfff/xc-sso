package com.xinchen.cas.pm.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apereo.cas.CipherExecutor;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.UsernamePasswordCredential;
import org.apereo.cas.authentication.support.password.PasswordEncoderUtils;
import org.apereo.cas.configuration.model.support.pm.PasswordManagementProperties;
import org.apereo.cas.pm.BasePasswordManagementService;
import org.apereo.cas.pm.PasswordChangeBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/5/3 21:47
 */
public class JdbcPasswordManagementService extends BasePasswordManagementService {
    private static final Logger LOGGER = LoggerFactory.getLogger(org.apereo.cas.pm.jdbc.JdbcPasswordManagementService.class);
    private final JdbcTemplate jdbcTemplate;

    public JdbcPasswordManagementService(CipherExecutor<Serializable, String> cipherExecutor, String issuer, PasswordManagementProperties passwordManagementProperties, DataSource dataSource) {
        super(cipherExecutor, issuer, passwordManagementProperties);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean changeInternal(Credential credential, PasswordChangeBean bean) {
        UsernamePasswordCredential c = (UsernamePasswordCredential)credential;
        PasswordEncoder encoder = PasswordEncoderUtils.newPasswordEncoder(this.properties.getJdbc().getPasswordEncoder());
        String password = encoder.encode(bean.getPassword());
        int count = this.jdbcTemplate.update(this.properties.getJdbc().getSqlChangePassword(), new Object[]{password, c.getId()});
        return count > 0;
    }

    @Override
    public String findEmail(String username) {
        try {
            String email = (String)this.jdbcTemplate.queryForObject(this.properties.getJdbc().getSqlFindEmail(), String.class, new Object[]{username});
            if (StringUtils.isNotBlank(email) && EmailValidator.getInstance().isValid(email)) {
                return email;
            } else {
                LOGGER.debug("Username [{}] not found when searching for email", username);
                return null;
            }
        } catch (EmptyResultDataAccessException var3) {
            LOGGER.debug("Username [{}] not found when searching for email", username);
            return null;
        }
    }

    @Override
    public Map<String, String> getSecurityQuestions(String username) {
        String sqlSecurityQuestions = this.properties.getJdbc().getSqlSecurityQuestions();
        Map<String, String> map = new LinkedHashMap();
        List<Map<String, Object>> results = this.jdbcTemplate.queryForList(sqlSecurityQuestions, new Object[]{username});
        results.forEach((row) -> {
            if (row.containsKey("question") && row.containsKey("answer")) {
                map.put(row.get("question").toString(), row.get("answer").toString());
            }

        });
        LOGGER.debug("Found [{}] security questions for [{}]", map.size(), username);
        return map;
    }
}