package illuminometer.com.example.android.mytestble.dao;

import android.database.Cursor;

import illuminometer.com.example.android.mytestble.domain.Document;

/**
 * Created by android on 2017/10/13.
 */
public interface DocumentDao  {
    void insertDocument(Document document);
    void deleteDocument(Integer documentid);
    void modifiyDocument(Integer documentid);
    Cursor getAllDocument();
    Document getDocument(Integer documentid);
    Document getTopDocument();



}
