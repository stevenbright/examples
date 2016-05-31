package com.alexshabanov.tupl2.support.index;

import org.cojen.tupl.Index;

import java.io.IOException;

public interface TuplIndexOperationCallback<T> {
  T call(Index index) throws IOException;
}
