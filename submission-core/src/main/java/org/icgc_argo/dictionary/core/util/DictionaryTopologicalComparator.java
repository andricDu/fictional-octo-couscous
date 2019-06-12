package org.icgc_argo.dictionary.core.util;

import lombok.val;
import org.icgc_argo.dictionary.core.model.FileSchema;
import org.icgc_argo.dictionary.core.model.FileTypes;
import org.icgc_argo.dictionary.core.model.FileTypes.FileType;
import org.icgc_argo.dictionary.core.model.Relation;

import java.util.Comparator;
import java.util.List;

public class DictionaryTopologicalComparator implements Comparator<FileSchema> {

  /**
   * Compares according to {@link Relation}.
   * @return -1 if {@code this} is a parent of the {@code other}<br>
   * 0 if they are equal<br>
   * 1 if {@code this} is a child of the {@code other}
   */
  @Override
  public int compare(FileSchema left, FileSchema right) {
    // Relations point to parents
    val leftRelations = left.getRelations();
    val leftRelationFileTypes = getRelationsFileType(leftRelations);
    val rightFileType = FileType.from(right.getName());
    if (leftRelationFileTypes.contains(rightFileType)) {
      return 1;
    }

    val leftFileType = FileType.from(left.getName());
    val rightRelations = right.getRelations();
    val rightRelationFileTypes = getRelationsFileType(rightRelations);
    if (rightRelationFileTypes.contains(leftFileType)) {
      return -1;
    }

    // Schemas are already equal at this point, but we will try to compare them by proximity to the top in the
    // hierarchy. E.g. meth_array_m and meth_array_probe are equal, but it better to put meth_array_m first.
    val leftRelationsSize = Integer.valueOf(leftRelations.size());
    val rightRelationsSize = Integer.valueOf(rightRelations.size());

    // This is the right comparison order. The 'smaller' FileSchema has more relations
    return rightRelationsSize.compareTo(leftRelationsSize);
  }

  private static List<FileType> getRelationsFileType(List<Relation> relations) {
    return relations.stream()
      .map(relation -> relation.getOtherFileType())
      .collect(Collectors.toImmutableList());
  }

}
