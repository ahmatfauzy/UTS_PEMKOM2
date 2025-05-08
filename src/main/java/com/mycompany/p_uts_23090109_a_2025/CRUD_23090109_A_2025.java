package com.mycompany.p_uts_23090109_a_2025;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.Arrays;

public class CRUD_23090109_A_2025 {

//    INISIASI URL, DATABASE, COLLECTION
    private static final String CONNECTION_NAME = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "uts_23090109_A_2025";
    private static final String COLLECTION_NAME = "coll_23090109_A_2025";

    public static void main(String[] args) {
//        TRY CATCTH UNTUK DEBUGGING MODE
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_NAME)) {
            MongoDatabase db = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = db.getCollection(COLLECTION_NAME);

//            EKSEKUSI
            createData(collection);
            readData(collection);
            updateData(collection);
            deleteData(collection);
            searchData(collection);

        } catch (Exception e) {
            System.out.println("Terjadi error: " + e.getMessage());
        }
    }

    // CREATE
    private static void createData(MongoCollection<Document> collection) {
        Document identitas = new Document("name", "Ahmat Fauzi")
                .append("TTL", "Cirebon, 23 Oktober 2005")
                .append("Riwayat Sekolah", Arrays.asList("SDN 1 Panggangsari", "MTsN 8 Cirebon", "SMAN 1 Losari"))
                .append("Umur", 19);
        collection.insertOne(identitas); //INSERTONE = MENAMBAH SATU DOC
        System.out.println("Dokumen berhasil dibuat.");
    }

    // READ
    private static void readData(MongoCollection<Document> collection) {
        System.out.println("Isi koleksi:");
        FindIterable<Document> result = collection.find(); //FIND UNTUK MENCARI SEMUA DOC
        for (Document d : result) {
            System.out.println(d.toJson()); //MENAMPILKAN DOC
        }
    }

    // UPDATE 
    private static void updateData(MongoCollection<Document> collection) {
        Bson filter = Filters.eq("name", "Ahmat Fauzi"); //MENCARI DOCUMENT DENGAN KEY VALUE INI
        Bson updateOperation = Updates.set("name", "Fauzyy aja"); //UPDATE KE DOCUMENT BARU INI
        var result = collection.updateOne(filter, updateOperation); // UPDATEONE = UPDATE HANYA SATU DOCUMENT SAJA
        if (result.getModifiedCount() > 0) { // PERKONDISIAN UNTUK TES BERHASIL/TIDAK
            System.out.println("Dokumen berhasil diperbarui.");
        } else {
            System.out.println("Tidak ada dokumen yang cocok untuk diperbarui.");
        }
    }

    // DELETE
    private static void deleteData(MongoCollection<Document> collection) {
        Bson deleteFilter = Filters.eq("name", "Fauzyy aja"); // MENCARI MENGGUNAKAN KEY VALLUE
        long deletedCount = collection.deleteOne(deleteFilter).getDeletedCount(); // DELETEONE = HAPUS SATU
        if (deletedCount > 0) { // PERKONDISIAN UNTUK TES BERHASIL/TIDAK
            System.out.println("Dokumen berhasil dihapus.");
        } else {
            System.out.println("Tidak ada dokumen yang cocok untuk dihapus.");
        }
    }

    // SEARCH 
    private static void searchData(MongoCollection<Document> collection) {
        Bson searchFilter = Filters.eq("name", "Ahmat Fauzi");  // MENCARI MENGGUNAKAN KEY VALLUE
        Document foundDocument = collection.find(searchFilter).first(); //FIND
        if (foundDocument != null) { // PERKONDISIAN UNTUK TES BERHASIL/TIDAK
            System.out.println("Dokumen ditemukan:");
            System.out.println(foundDocument.toJson());
        } else {
            System.out.println("Dokumen tidak ditemukan.");
        }
    }
}
