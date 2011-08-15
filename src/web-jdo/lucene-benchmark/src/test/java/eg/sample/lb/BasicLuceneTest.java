package eg.sample.lb;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Tests lucene usage
 */
public class BasicLuceneTest {
    private final static Version LUCENE_VERSION = Version.LUCENE_31;

    @Test
    public void testLuceneUsage() throws IOException, ParseException {
        final Analyzer analyzer = new StandardAnalyzer(LUCENE_VERSION);

        // Store the index in memory:
        final Directory directory = new RAMDirectory();
        // To store an index on disk, use this instead:
        //Directory directory = FSDirectory.getDirectory("/tmp/testindex");

        final IndexWriterConfig writerConfig = new IndexWriterConfig(LUCENE_VERSION, analyzer);
        writerConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        IndexWriter indexWriter = new IndexWriter(directory, writerConfig);

        // the following is deprecated
        //indexWriter.setMaxFieldLength(25000);

        final Document luceneDocument = new Document();
        final String text = "This is the text to be indexed.";
        luceneDocument.add(new Field("fieldname", text, Field.Store.YES,
                Field.Index.ANALYZED));
        indexWriter.addDocument(luceneDocument);
        indexWriter.optimize();
        indexWriter.close();

        // Now search the index:
        IndexSearcher indexSearcher = new IndexSearcher(directory);
        // Parse a simple query that searches for "text":
        QueryParser parser = new QueryParser(LUCENE_VERSION, "fieldname", analyzer);
        Query query = parser.parse("text");
        final TopDocs hits = indexSearcher.search(query, 10);
        assertEquals(1, hits.totalHits);

        // Iterate through the results:
        for (int i = 0; i < hits.totalHits; i++) {
            final ScoreDoc scoreDoc = hits.scoreDocs[i];
            final Document hitDoc = indexSearcher.doc(scoreDoc.doc);

            assertEquals("This is the text to be indexed.", hitDoc.get("fieldname"));
        }

        indexSearcher.close();
        directory.close();
    }
}
