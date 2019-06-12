package org.icgc_argo.dictionary.core.visitor;

import org.icgc_argo.dictionary.core.model.*;

/**
 * Allows a visitor implementation to visit all {@code Dictionary}-related objects
 */
public interface DictionaryVisitor {

  public String getProjectKey();

  public void visit(Dictionary dictionary);

  public void visit(FileSchema fileSchema);

  public void visit(Field field);

  public void visit(Restriction restriction);

  public void visit(Relation relation);

}
