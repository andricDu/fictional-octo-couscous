package org.icgc_argo.dictionary.core.model.validation;

import com.mongodb.BasicDBObject;
import org.icgc_argo.dictionary.core.model.Restriction;
import org.icgc_argo.dictionary.core.model.RestrictionType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.PatternSyntaxException;

import static java.util.regex.Pattern.compile;

/**
 * Really just laid the ground here for DCC-904.
 * <p>
 * TODO: use interface instead of Restriction directly
 */
public class CheckRestrictionValidator implements ConstraintValidator<CheckRestriction, Restriction> {

  @Override
  public void initialize(CheckRestriction constraintAnnotation) {
  }

  @Override
  public boolean isValid(Restriction restriction, ConstraintValidatorContext constraintContext) {

    // TODO: address properly instead of nesting (DCC-904)
    if (restriction != null) {
      RestrictionType type = restriction.getType();
      if (type != null) {
        if (type == RestrictionType.REGEX) {
          BasicDBObject config = restriction.getConfig();
          Object object = config.get("pattern");
          if (object instanceof String) {
            String pattern = (String) object;
            try {
              compile(pattern);
            } catch (PatternSyntaxException e) { // must be a pattern that compiles
              return false;
            }
            return true;
          } else { // must be a String if "pattern"
            return false;
          }
        } else { // TODO: other types
          return true;
        }
      } else { // type is null
        return false;
      }
    } else { // no restrictions
      return true;
    }
  }
}
