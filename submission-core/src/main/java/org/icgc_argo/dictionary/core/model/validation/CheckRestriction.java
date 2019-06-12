package org.icgc_argo.dictionary.core.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = CheckRestrictionValidator.class)
@Documented
public @interface CheckRestriction {

  String message() default "Restriction config must consistent with restriction type"; // TODO: parameterize (DCC-904)

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
