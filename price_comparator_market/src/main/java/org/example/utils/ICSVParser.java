package org.example.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface ICSVParser<T> {
    List<T> parse(Path path) throws IOException;
}
