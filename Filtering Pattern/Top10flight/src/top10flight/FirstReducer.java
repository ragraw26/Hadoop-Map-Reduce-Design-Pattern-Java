/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package top10flight;

import java.io.IOException;
 
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
 
public class FirstReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable>{
     
    private static DoubleWritable avgRating = new DoubleWritable(1); 
     
    public void reduce(Text movie, Iterable<DoubleWritable> movieRatingArray, Context context) throws IOException, InterruptedException{
        double totalMovieRating = 0.0;
        int count = 0;
        for(DoubleWritable movieRating : movieRatingArray ){
            totalMovieRating += movieRating.get();
            count++;
        }
        avgRating.set(totalMovieRating/count);
        context.write(movie, avgRating);     
    }
     
}