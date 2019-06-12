package org.icgc_argo.dictionary.core.model;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

/**
 * DCC-799: Temporary support to untangle cyclic dependencies between dcc-submission-server and dcc-submission-core.
 * <p>
 * Convention: OriginalClassName_CONSTANT_NAME;
 */
@NoArgsConstructor(access = PRIVATE)
public final class Constants {

  public static final String Authorizations_ADMIN_ROLE = "admin";

  public static final String CodeListRestriction_FIELD = "name";

}
