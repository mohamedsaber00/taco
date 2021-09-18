package com.candybytes.taco.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.candybytes.taco.db.FoodDao
import com.candybytes.taco.db.FoodDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    /**
     * Access food in db
     */
    @Provides
    fun provideFoodDao(foodDb: FoodDb): FoodDao {
        return foodDb.foodDao()
    }

    /**
     * DB init
     */
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context, migration: Migration
    ): FoodDb {
        return Room.databaseBuilder(
            context,
            FoodDb::class.java,
            "food"
        ).addMigrations(migration)
            .createFromAsset("food.db")
            .build()
    }

    /**
     * Provide migration for db
     */
    @Provides
    fun provideMigrate(): Migration {
        return object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //Add new column "foodCount" to categories table
                database.execSQL("ALTER TABLE categories ADD COLUMN foodCount INTEGER")
            }
        }
    }
}
