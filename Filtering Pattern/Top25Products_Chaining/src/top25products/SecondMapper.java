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


public class SecondMapper extends Mapper<LongWritable, Text, DoubleWritable, Text> {

    private static DoubleWritable avgRating = new DoubleWritable();
    private Text product = new Text();

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String[] input = value.toString().split("\t");
        product.set(input[0]);
        avgRating.set(Double.parseDouble(input[1]));
        context.write(avgRating, product);

    }

}