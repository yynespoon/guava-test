package com.test.collection;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.junit.Test;

/**
 * @author lixiaoyu
 * @since 2020/3/31
 *
 * HashBasedTable, which is essentially backed by a HashMap<R, HashMap<C, V>>.
 * TreeBasedTable, which is essentially backed by a TreeMap<R, TreeMap<C, V>>.
 * ImmutableTable
 * ArrayTable底层通过二维数组实现来提高效率, 创建的时候要初始化行和列
 * ArrayTable, which requires that the complete universe of rows and columns
 * be specified at construction time, but is backed by a two-dimensional array
 * to improve speed and memory efficiency when the table is dense.
 * ArrayTable works somewhat differently from other implementations;
 * consult the Javadoc for details.
 */
public class TestTable {

    @Test
    public void testHashBaseTable(){
        HashBasedTable<String, String, String> table = HashBasedTable.create();
        table.put("AAA", "bbb", "111");
        table.put("BBB", "bbb", "222");
        table.put("AAA", "aaa", "333");
        //column->map<row, value>
        System.out.println(table.column("bbb"));
        //row->map<column, value>
        System.out.println(table.row("AAA"));
        //getCell
        for (Table.Cell<String, String, String> cell : table.cellSet()) {
            System.out.println(cell.getRowKey());
            System.out.println(cell.getColumnKey());
            System.out.println(cell.getValue());
        }
        //column->map<row, value>
        System.out.println(table.columnMap());
        //row->map<column, value>
        System.out.println(table.rowMap());
        //rowkey
        System.out.println(table.rowKeySet());
        //columnkey
        System.out.println(table.columnKeySet());
    }
}
