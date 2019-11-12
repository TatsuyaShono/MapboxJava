package com.example.code_for_ube.mapboxjava;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import android.content.res.AssetManager;

import java.io.File;
import java.io.Reader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.univocity.parsers.common.processor.*;
import com.univocity.parsers.csv.*;

/**
 * class CsvUtil
 * reference : https://www.univocity.com/pages/univocity_parsers_tutorial
 */
public class CsvUtil  {

    // debug
    private final static boolean D = true;
    private final static String TAG = "CSV";
    private final static String TAG_SUB = "CsvUtil";

    private final static String[] CSV_HEADERS = new String[]
            { "id"
            , "title"
            , "text"
            , "categories"
            , "tags"
            , "basename"
            , "address"
            , "artimg"
            , "eigyoujikan"
            , "holiday"
            , "latitude"
            , "longitude"
            , "parking"
            , "phone"
            , "price"
            , "trafficdata" };

    private final static String COMMA = ",";
    private final static String SPACE = " ";

    private static final String DIR_NAME = "csv";

    private  Context mContext;
    private AssetManager mAssetManager;

    private List<KankospotEntity> mCache;

    /**
     * constractor
     */
    public CsvUtil(Context context) {
        mContext = context;
        mAssetManager = context.getAssets();
    } // CsvUtil

    /**
     *  readSample
     */
    public List<KankospotEntity> readSample(String fileName) {

        InputStreamReader reader = getAssetInputStreamReader( fileName ) ;
        mCache =  readCsv( reader );
        return mCache;

    } // readSample

    /**
     * readCsv
     */
    private List<KankospotEntity> readCsv(Reader reader ){

        if( reader == null ) return null;

        List<KankospotEntity> list = new ArrayList<>();

        try {
            CsvParserSettings settings = new CsvParserSettings();
            BeanListProcessor<KankospotEntity> rowProcessor = new BeanListProcessor<>(KankospotEntity.class);
            settings.setProcessor(rowProcessor);
            settings.setHeaderExtractionEnabled(true);
            CsvRoutines routines = new CsvRoutines(settings);
            list = routines.parseAll( KankospotEntity.class,  reader );

        }catch (Exception ex) {
            if(D) ex.printStackTrace();
        }

        try {
            if( reader != null ) reader.close();
        }catch (Exception ex) {
            if(D) ex.printStackTrace();
        }

        return list;

    } // readCsv

    /**
     * getAssetInputStreamReader
     */
    private InputStreamReader getAssetInputStreamReader( String fileName ) {

        InputStreamReader reader = null;
        try {
            InputStream is = mAssetManager.open(fileName);
            reader = new InputStreamReader( is );
        } catch (Exception e){
            if (D) e.printStackTrace();
        }

        return reader;
    } // getAssetInputStreamReader

    /**
     * getStorageFile
     */
    private File getStorageFile( String fileName ) {
        File root_dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() );
        File sub_dir = new File( root_dir,  DIR_NAME );
        if ( !sub_dir.exists() ) {
            sub_dir.mkdir();
        }
        File file = new File( sub_dir,  fileName );
        return file;
    } // getStorageFile

    /**
     * parseInt
     */
    private int parseInt( String str ) {

        int num = 0;
        try {
            num = Integer.parseInt( str.trim() );
        } catch (Exception ex) {
            if (D) ex.printStackTrace();
        }

        return num;
    } // parseInt

    /**
     * parseDouble
     */
    private double parseDouble( String str ) {

        double d = 0;
        try {
            d = Double.parseDouble( str.trim() );
        } catch (Exception ex) {
            // if (D) ex.printStackTrace();
        }

        return d;
    } // parseDouble

    /**
     * toast_long
     */
    private void toast_long( String msg ) {
        ToastMaster.makeText( mContext, msg, Toast.LENGTH_LONG ).show();
    } // toast_long

} // class CsvUtil
