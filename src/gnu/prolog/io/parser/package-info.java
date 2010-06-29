/**
 * This package contains the TermParser.jj file which is used by javacc to
 * generate the parser found in {@link gnu.prolog.io.parser.gen}.
 * 
 * There is also a TermParser.g file which is an in progress conversion of
 * JavaCC grammar to antlr.
 * 
 * This package also contains classes such as {@link NameToken} and
 * {@link ReaderCharStream} and {@link TermParserUtils} which contain some of
 * the code needed to integrate the generated parser with the rest of the
 * codebase.
 */
package gnu.prolog.io.parser;
