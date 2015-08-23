package com.alexshabanov.sample.eol.migration.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Alexander Shabanov
 */
@EqualsAndHashCode
@ToString
@Getter
@AllArgsConstructor
public final class SeriesPos {
  private Long seriesId;
  private int pos;
}
