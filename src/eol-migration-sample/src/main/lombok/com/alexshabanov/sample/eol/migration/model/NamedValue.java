package com.alexshabanov.sample.eol.migration.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents named value.
 */
@EqualsAndHashCode
@ToString
@Getter
@AllArgsConstructor
public class NamedValue {
  private Long id;
  private String name;
}
