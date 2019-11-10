package com.example.code_for_ube.mapboxjava;

import java.io.FileReader;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;

/**
 * CSVファイルとのやり取りを行うDaoクラス。
 */
public class KankospotFileDao {
    // 書き出し用メソッド
    public void write(Writer writer, List<KankospotEntity> beans) throws CsvException {
        StatefulBeanToCsv<KankospotEntity> beanToCsv
                = new StatefulBeanToCsvBuilder<KankospotEntity>(writer).build();
        beanToCsv.write(beans);
    }
    //読み込み用メソッド
    public List<KankospotEntity> read(Reader reader) throws CsvException {
        CsvToBean<KankospotEntity> csvToBean
                = new CsvToBeanBuilder<KankospotEntity>(reader).withType(KankospotEntity.class).build();
        return csvToBean.parse();
    }
}
