package org.icgc_argo.dictionary.core.model;

import lombok.NoArgsConstructor;
import lombok.NonNull;

import static lombok.AccessLevel.PRIVATE;

/**
 * Utils methods for {@link String}.
 */
@NoArgsConstructor(access = PRIVATE)
public class Strings2 {

  public static final String DOT = ".";
  public static final String EMPTY_STRING = "";
  public static final String TAB = "\t";
  public static final String UNIX_NEW_LINE = "\n";
  public static final String DOUBLE_QUOTE = "\"";
  public static final String SINGLE_QUOTE = "'";
  public static final String NOT_APPLICABLE = "N/A";

  public static String removeTrailingS(@NonNull final String s) {
    return s.replaceAll("s$", "");
  }

  /**
   * Not appropriate for very big {@link String}s.
   */
  public static boolean isLowerCase(@NonNull final String s) {
    return s.equals(s.toLowerCase());
  }

  /**
   * Not appropriate for very big {@link String}s.
   */
  public static boolean isUpperCase(@NonNull final String s) {
    return s.equals(s.toUpperCase());
  }

  public static boolean isEmpty(CharSequence cs) {
    return cs == null || cs.length() == 0;
  }

  public static boolean isNotEmpty(CharSequence cs) {
    return !isEmpty(cs);
  }

  public static String removeTarget(@NonNull final String s, @NonNull final String target) {
    return s.replace(target, EMPTY_STRING);
  }

  public static String unquote(@NonNull final String s) {
    return s
      .replaceAll(DOUBLE_QUOTE, EMPTY_STRING)
      .replaceAll(SINGLE_QUOTE, EMPTY_STRING);
  }

  public static String getFirstCharacter(@NonNull final String s) {
    return s.substring(0, 1);
  }

}

