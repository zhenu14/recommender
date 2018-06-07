package com.cdl.recommender.data;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;

import java.io.File;
import java.io.IOException;

public class StringItemIdFileDataModel extends FileDataModel {

    //初始化将String转换为Long的转换器
    public ItemMemIDMigrator memIDMigrator;

    public StringItemIdFileDataModel(File dataFile, String regex) throws IOException {
        super(dataFile,regex);
    }

    @Override
    protected  long readItemIDFromString(String value){
        if(memIDMigrator == null){
            memIDMigrator = new ItemMemIDMigrator();
        }
        //转换为long
        long retValue = memIDMigrator.toLongID(value);
        //存储到缓存
        if (null == memIDMigrator.toStringID(retValue)) {
            try{
                memIDMigrator.singleInit(value);
            }catch (TasteException e){
                e.printStackTrace();
            }
        }
        return retValue;
    }

    //将long转换为String
    String getItemIDAsString(long itemId){
        return memIDMigrator.toStringID(itemId);
    }
}
