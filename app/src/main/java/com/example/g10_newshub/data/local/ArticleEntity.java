package com.example.g10_newshub.data.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "saved_articles")
public class ArticleEntity {
    @PrimaryKey public int id;
    public String title, image, content, categoryName;
    public long savedAt;
}
