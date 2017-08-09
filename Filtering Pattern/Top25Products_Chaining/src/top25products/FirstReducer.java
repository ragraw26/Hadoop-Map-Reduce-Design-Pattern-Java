/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package top25products;

 
import java.io.IOException;
 
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
 
public class FirstReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable>{
     
    private static DoubleWritable avgRating = new DoubleWritable(1); 
     
    public void reduce(Text product, Iterable<DoubleWritable> movieRatingArray, Context context) throws IOException, InterruptedException{
        double totalProductRating = 0.0;
        int count = 0;
        for(DoubleWritable movieRating : movieRatingArray ){
            totalProductRating += movieRating.get();
            count++;
        }
        avgRating.set(totalProductRating/count);
        context.write(product, avgRating);     
    }
     
}