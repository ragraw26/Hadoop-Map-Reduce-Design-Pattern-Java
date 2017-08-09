/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package top25products;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class FirstMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {

    private static DoubleWritable rating = new DoubleWritable();
    private Text product = new Text();

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        if (!value.toString().contains("overall")) {
            String[] input = value.toString().split(",");
            product.set(input[0]);
            if (!input[3].isEmpty()) {
                double rate = Double.parseDouble(input[3]);
                rating.set(rate);
                context.write(product, rating);
            }
        }
    }
}
