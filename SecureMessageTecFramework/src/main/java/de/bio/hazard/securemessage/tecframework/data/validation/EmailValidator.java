package de.bio.hazard.securemessage.tecframework.data.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class EmailValidator {

    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public EmailValidator() {
	pattern = Pattern.compile(EMAIL_PATTERN);
    }

    public boolean validateFormat(final String mail) {
	matcher = pattern.matcher(mail);
	return matcher.matches();
    }
    
    public boolean validateDomain(final String mail) {
	/*TODO validateDomain
	 *  - ggf. validateFormat und validateDomain private gestalten und nur "validate" aufrufen.
	 *  - ping(?) der Domain wenn das format OK ist
	 */
	throw new NotImplementedException();
    }
}