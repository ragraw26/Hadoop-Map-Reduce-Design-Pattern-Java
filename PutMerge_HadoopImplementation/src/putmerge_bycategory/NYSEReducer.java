/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package putmerge_bycategory;

import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 *
 * @author Rajat
 */
public class NYSEReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

    private static DoubleWritable avgPrice = new DoubleWritable(1);

    @Override
    protected void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {

        double totalStockPrice = 0.0;
        int count = 0;
        for (DoubleWritable stockPrice : values) {
            totalStockPrice += stockPrice.get();
            count++;
        }
        avgPrice.set(totalStockPrice / count);
        context.write(key, avgPrice);
    }
}
