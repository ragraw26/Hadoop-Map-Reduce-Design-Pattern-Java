/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package putmerge_bycategory;

import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *
 * @author Rajat
 */
public class NYSEMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {

    private static DoubleWritable stockPrice = new DoubleWritable();
    private Text stockName = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        if (value.toString().contains("exchange")) {
            return;
        } else {
            String[] input = value.toString().split(",");
            stockName.set(input[1]);
            stockPrice.set(Double.parseDouble(input[4]));
            context.write(stockName, stockPrice);
        }
    }
}

