package com.alexshabanov.sample.eol.migration.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * Represents book meta information
 */
@EqualsAndHashCode
@ToString
@Getter
@AllArgsConstructor
public class BookMeta {
  private Long id;
  private String title;
  private int fileSize;
  private Date addDate;
  private Long langId;
  private Long originId;
}
