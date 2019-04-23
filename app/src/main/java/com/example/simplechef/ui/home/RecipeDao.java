package com.example.simplechef.ui.home;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.simplechef.RecipeClass;

import java.util.List;

@Dao
public interface RecipeDao {
    @Insert
    void insert(RecipeClass recipe);

    @Update
    void update(RecipeClass recipe);

    @Delete
    void delete(RecipeClass recipe);

    //TODO:  add query data here to get all data
    @Query("")
    LiveData<List<RecipeClass>> getAllRecipes();
}
