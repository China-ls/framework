package com.infinite.eoa.core.object;

import org.bson.Document;

/**
 * Created by hx on 16-7-5.
 */
public interface IDocumentable {
    Document toDocument();

    void fromDocument(Document document);
}
