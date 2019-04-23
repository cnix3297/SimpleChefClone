package com.example.simplechef.ui.home;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.simplechef.RecipeClass;

import java.util.List;

public class RecipeRepository {
    private static final String TAG = "RecipeRepository";

    private RecipeDao recipeDao;
    private static RecipeRepository instance = null;
    private LiveData<List<RecipeClass>> allRecipes;


    public RecipeRepository(Application application) {
        RecipeDatabase database = RecipeDatabase.getInstance(application);
        recipeDao = database.recipeDao();
        allRecipes = recipeDao.getAllRecipes();

    }

    public void insert(RecipeClass recipe) {
        new InsertRecipeAsyncTask(recipeDao).execute(recipe);

    }

    public void update(RecipeClass recipe) {
        new UpdateRecipeAsyncTask(recipeDao).execute(recipe);

    }

    public void delete(RecipeClass recipe) {
        new DeleteRecipeAsyncTask(recipeDao).execute(recipe);
    }

    public LiveData<List<RecipeClass>> getAllRecipes() {
        return allRecipes;
    }

    private static class InsertRecipeAsyncTask extends AsyncTask<RecipeClass,Void, Void> {
        private RecipeDao recipeDao;

        private InsertRecipeAsyncTask(RecipeDao recipeDao) {
            this.recipeDao = recipeDao;
        }

        @Override
        protected Void doInBackground(RecipeClass... recipeClasses) {
            recipeDao.insert(recipeClasses[0]);
            return null;
        }
    }
    private static class UpdateRecipeAsyncTask extends AsyncTask<RecipeClass,Void, Void> {
        private RecipeDao recipeDao;

        private UpdateRecipeAsyncTask(RecipeDao recipeDao) {
            this.recipeDao = recipeDao;
        }

        @Override
        protected Void doInBackground(RecipeClass... recipeClasses) {
            recipeDao.delete(recipeClasses[0]);
            return null;
        }
    }
    private static class DeleteRecipeAsyncTask extends AsyncTask<RecipeClass,Void, Void> {
        private RecipeDao recipeDao;

        private DeleteRecipeAsyncTask(RecipeDao recipeDao) {
            this.recipeDao = recipeDao;
        }

        @Override
        protected Void doInBackground(RecipeClass... recipeClasses) {
            recipeDao.insert(recipeClasses[0]);
            return null;
        }
    }
}
