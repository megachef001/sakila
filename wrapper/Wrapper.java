package com.ongres.wrapper;

public final class Wrapper {

  private Wrapper() {
  }

  public static String wrap(String input, int col) {
    if (input == null || input.isEmpty()) {
      return input;
    }
    if (input.length() <= col) {
      return input;
    }

    StringBuilder wrapped = new StringBuilder();
    char[] chars = input.toCharArray();

    int lastSpaceIndex = -1;
    int lineStart = 0;
    int lineEnd = 0;
    int count = 0;

    for (int i = 0; i < chars.length; i++) {
      char c = chars[i];

      // The fist character of the current line is a whitespace
      if (Character.isWhitespace(c) && count == 0) {
        // Skip current whitespace character and move to the next one
        ++lineStart;
        continue;
      }

      lineEnd = i;
      ++count;

      if (Character.isWhitespace(c)) {
        lastSpaceIndex = i;
      }

      // We reach the line width
      if (count == col) {
        boolean breakBeforeWord = Character.isLetterOrDigit(c) && lastSpaceIndex != -1;
        if (breakBeforeWord) {
          lineEnd = lastSpaceIndex - 1;
        }

        boolean endsWithWhitespace = lastSpaceIndex == i;
        if (endsWithWhitespace) {
          --lineEnd;
        }

        wrapped.append(input.substring(lineStart, lineEnd + 1));
        wrapped.append('\n');

        if (breakBeforeWord || endsWithWhitespace) {
          lineStart = lineEnd + 2;
          count = i - lineStart + 1; // since we are moving forward, we need to count these characters
        } else {
          lineStart = lineEnd + 1;
          count = 0;
        }

        lastSpaceIndex = -1;
      } else if (i == chars.length - 1) {
        wrapped.append(input.substring(lineStart));
      }
    }

    // Line cleanup: remove extra line break
    int last = wrapped.length() - 1;
    if (wrapped.charAt(last) == '\n') {
      wrapped.deleteCharAt(last);
    }

    return wrapped.toString();
  }
}