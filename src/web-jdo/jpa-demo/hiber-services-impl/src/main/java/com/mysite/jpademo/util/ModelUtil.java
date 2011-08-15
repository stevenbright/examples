package com.mysite.jpademo.util;

import com.mysite.jpademo.model.Book;
import com.mysite.jpademo.model.Chapter;

import java.util.ArrayList;
import java.util.List;

public final class ModelUtil {

    public static Book createBook(String title, int count, String ... chapterNames) {
        final Book book = new Book();

        book.setTitle(title);
        book.setItemCount(count);

        if (chapterNames.length > 0) {
            final List<Chapter> chapters = new ArrayList<Chapter>(chapterNames.length);
            for (final String chapterName : chapterNames) {
                final Chapter chapter = new Chapter();
                chapter.setName(chapterName);
                chapter.setReviewed(false);
                chapters.add(chapter);
            }
            book.setChapters(chapters);
        }

        return book;
    }

    public static String chapterToString(Chapter chapter) {
        final StringBuilder builder = new StringBuilder();

        builder.append("Chapter{");

        builder.append("id=");
        builder.append(chapter.getId());

        builder.append(", name='");
        builder.append(chapter.getName());

        builder.append("', reviewed=");
        builder.append(chapter.isReviewed() ? "Yes" : "No");

        builder.append("}");

        return builder.toString();
    }

    public static String bookToString(Book book) {
        final StringBuilder builder = new StringBuilder();

        builder.append("Book{");

        builder.append("id=");
        builder.append(book.getId());

        builder.append(", title='");
        builder.append(book.getTitle());

        builder.append("', itemCount=");
        builder.append(book.getItemCount());

        builder.append(", chapters=[");
        if (book.getChapters() != null && book.getChapters().size() > 0) {
            boolean isFirst = true;
            for (final Chapter chapter : book.getChapters()) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    builder.append(", ");
                }
                builder.append(chapterToString(chapter));
            }
        }
        builder.append("]");

        builder.append("}");

        return builder.toString();
    }

    private ModelUtil() {}
}
