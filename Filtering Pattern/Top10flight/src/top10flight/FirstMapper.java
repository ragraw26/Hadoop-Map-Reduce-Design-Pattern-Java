/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package top10flight;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class FirstMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {

    private static DoubleWritable rating = new DoubleWritable();
    private Text movie = new Text();

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String[] input = value.toString().split("::");
        movie.set(input[1]);
        rating.set(Double.parseDouble(input[2]));
        context.write(movie, rating);

    }

}
