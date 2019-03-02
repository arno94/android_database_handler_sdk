package com.arno.database_handler_library.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * BaseEntity for handling database
 */
@Entity
public class BaseEntity {

    /**
     * Auto generated id for the database entity
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * Default constructor, must contain for handling database transactions
     */
    public BaseEntity(){

    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

}
