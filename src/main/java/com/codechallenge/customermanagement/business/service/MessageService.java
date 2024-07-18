package com.codechallenge.customermanagement.business.service;

import java.util.Locale;

/**
 * Service interface for retrieving localized messages from message properties files.
 * TODO MessageService should be moved to a common library and not implemented in every project.
 */
public interface MessageService {

    /**
     * Retrieves a message based on the given key and the default locale.
     *
     * @param key the key of the message to be retrieved
     * @return the localized message
     */
    String getMessage(String key);

    /**
     * Retrieves a message based on the given key, parameters, and the default locale.
     *
     * @param key    the key of the message to be retrieved
     * @param params the parameters to be used in the message
     * @return the localized message
     */
    String getMessage(String key, Object[] params);

    /**
     * Retrieves a message based on the given key, parameters, and the specified locale.
     *
     * @param key    the key of the message to be retrieved
     * @param params the parameters to be used in the message
     * @param locale the locale to be used for retrieving the message
     * @return the localized message
     */
    String getMessage(String key, Object[] params, Locale locale);
    
    /**
     * Retrieves a message based on the given key, parameters, and the specified locale.
     *
     * @param key    the key of the message to be retrieved
     * @param params the parameters to be used in the message
     * @param lang the language to be used for retrieving the message
     * @return the localized message
     */
    String getMessage(String key, Object[] params, String lang);
    
    /**
     * Parses the language string into a Locale object.
     *
     * @param language the language string from the Accept-Language header
     * @return the Locale object corresponding to the language string, or the default Locale if language is null or empty
     */
    Locale parseLocale(String language);
    
}

