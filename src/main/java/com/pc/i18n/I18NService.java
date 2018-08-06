package com.pc.i18n;

import java.util.Locale;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author Prashant
 *
 */
@Service
public class I18NService {

	public static final Logger log = LoggerFactory.getLogger(I18NService.class); 
	
	@Autowired
	MessageSource messageSource;
	
	/**
	 * @param messageId
	 * @return
	 */
	public String getMessage(String messageId)
	{
		log.debug("getting local message");
		Locale locale = LocaleContextHolder.getLocale();
		return getMessage(messageId,locale);
	}
	
	/**
	 * @param messageId
	 * @param locale
	 * @return
	 */
	public String getMessage(String messageId, Locale locale)
	{
		return messageSource.getMessage(messageId, null, locale);
	}
}

