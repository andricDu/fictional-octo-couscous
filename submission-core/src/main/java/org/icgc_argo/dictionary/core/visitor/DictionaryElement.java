package org.icgc_argo.dictionary.core.visitor;

/**
 * TODO
 */
public interface DictionaryElement {

  public void accept(DictionaryVisitor dictionaryVisitor);
}
