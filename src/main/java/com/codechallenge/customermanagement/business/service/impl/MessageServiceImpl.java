package com.codechallenge.customermanagement.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.codechallenge.customermanagement.business.service.MessageService;

import java.util.Locale;

/**
 * Implementation of the {@link MessageService} interface for retrieving localized messages from message properties files.
 * TODO MessageServiceImpl should be moved to a common library and not implemented in every project.
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageSource messageSource;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage(String key) {
        return messageSource.getMessage(key, null, Locale.getDefault());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage(String key, Object[] params) {
        return messageSource.getMessage(key, params, Locale.getDefault());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage(String key, Object[] params, Locale locale) {
        return messageSource.getMessage(key, params, locale);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage(String key, Object[] params, String lang) {
    	Locale locale = parseLocale(lang);
        return messageSource.getMessage(key, params, locale);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Locale parseLocale(String language) {
        if (language != null && !language.isEmpty()) {
            String[] languageParts = language.split("-");
            if (languageParts.length == 1) {
                return new Locale(languageParts[0]);
            } else if (languageParts.length == 2) {
                return new Locale(languageParts[0], languageParts[1]);
            }
        }
        return Locale.getDefault();
    }
}

