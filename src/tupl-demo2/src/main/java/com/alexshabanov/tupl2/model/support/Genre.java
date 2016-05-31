package com.alexshabanov.tupl2.model.support;

import com.alexshabanov.tupl2.model.IGenre;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Genre domain model.
 */
public final class Genre implements IGenre {
  private String id;
  private String shortName = "";
  private String longName = "";
  private String description = "";

  @Override
  @Nullable
  public String getId() {
    return id;
  }

  public void setId(@Nullable String id) {
    this.id = id;
  }

  @Override
  @Nonnull
  public String getShortName() {
    return shortName;
  }

  public void setShortName(@Nonnull String shortName) {
    this.shortName = shortName;
  }

  @Override
  @Nonnull
  public String getLongName() {
    return longName;
  }

  public void setLongName(@Nonnull String longName) {
    this.longName = longName;
  }

  @Override
  @Nonnull
  public String getDescription() {
    return description;
  }

  public void setDescription(@Nonnull String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Genre genre = (Genre) o;

    return id != null ? id.equals(genre.id) :
        genre.id == null && shortName.equals(genre.shortName) &&
            longName.equals(genre.longName) && description.equals(genre.description);

  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + shortName.hashCode();
    result = 31 * result + longName.hashCode();
    result = 31 * result + description.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Genre{" +
        "id='" + getId() + '\'' +
        ", shortName='" + getShortName() + '\'' +
        ", longName='" + getLongName() + '\'' +
        ", description='" + getDescription() + '\'' +
        '}';
  }
}
